package com.yanxiu.ce.system.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.redis.JedisClient;
import com.yanxiu.ce.system.dao.MenuDao;
import com.yanxiu.ce.system.dto.MenuPermTreeDto;
import com.yanxiu.ce.system.entity.Menu;
import com.yanxiu.ce.system.entity.MenuQuery;
import com.yanxiu.ce.system.entity.MenuTreeNode;
import com.yanxiu.ce.system.entity.Permission;
import com.yanxiu.ce.system.entity.PermissionQuery;
import com.yanxiu.ce.system.entity.Role;
import com.yanxiu.ce.system.service.MenuService;
import com.yanxiu.ce.system.service.PermissionService;
import com.yanxiu.ce.system.service.RoleMenuService;
import com.yanxiu.ce.system.service.RolePermService;
import com.yanxiu.ce.system.service.UserRoleService;

@Service("menuService")
public class MenuServiceImpl extends BaseServiceImpl<Menu, MenuQuery> implements MenuService{
	@Autowired
	private MenuDao dao;
	
	@Autowired
	private RoleMenuService roleMenuService;
	
	@Autowired
	private RolePermService rolePermService;

	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${KEY_getPermTreeGlobal}")
	private String KEY_getPermTreeGlobal;
	
	@Value("${KEY_loadTreeNodes}")
	private String KEY_loadTreeNodes;
	
	@Value("${COMMON_EXPIRE}")
	private Integer COMMON_EXPIRE;
	
	@Override
	protected BaseDao<Menu, MenuQuery> dao() {
		return this.dao;
	}
	
	/**
	 * 获取seq
	 */
	@Override
	public Integer selectMaxSeq(Long pid) {
		Integer selectMaxSeq = this.dao.selectMaxSeq(pid);
		if(selectMaxSeq!=null){
			return selectMaxSeq;
		}
		return 0;
	}

	/**
	 * 新增or修改
	 */
	@Override
	@Transactional
	//@CacheEvict(value="menuCache", allEntries=true)
	public String saveOrUpdate(Menu entity) {
		String msg = "";
		if(entity.getId()==null){
			this.insert(entity);
			msg = "添加成功！";
		}else {
			this.update(entity);
				msg = "编辑成功！";
		}
		
		//同步缓存
		this.syncLoadTreeNodes();
		this.syncGetPermTreeGlobal();
		
		return msg;
	}
	
	/**
	 * 覆写删除，同步缓存
	 */
	@Override
	@Transactional
	public Long deleteByIds(List<Long> ids){
		Long deleteByIds = super.deleteByIds(ids);
		
		//同步缓存
		this.syncLoadTreeNodes();
		this.syncGetPermTreeGlobal();
		return deleteByIds;
	}

	/**
	 * 根据pid获取一层节点
	 */
	@Override
	public List<MenuTreeNode> selectByPid(Long pid) {
		List<MenuTreeNode> nodes = Lists.newArrayList();
		
		MenuQuery query = new MenuQuery();
		query.setOrderField("seq");
		query.setPid(pid.toString());
		query.setActive(true);//已激活状态的菜单
		
		List<Menu> menus = this.selectList(query);
		for(Menu menu:menus){
			nodes.add(new MenuTreeNode(menu));
		};
		return nodes;
	}
	
	public List<MenuTreeNode> selectByPidForUser(Long pid,Set<Long> menuIds) {
		List<MenuTreeNode> nodes = Lists.newArrayList();
		
			MenuQuery query = new MenuQuery();
			query.setOrderField("seq");
			query.setPid(pid.toString());
			query.setActive(true);//已激活状态的菜单
			
			List<Menu> menus = this.selectList(query);
			for(Menu menu:menus){
				if(menuIds.contains(menu.getId())){
					nodes.add(new MenuTreeNode(menu));
				}
			};
		
		return nodes;
	}
	
	@Override
	public List<MenuTreeNode> selectAllByPid(Long pid,Set<Long> menuIds) {
		List<MenuTreeNode> nodes = Lists.newArrayList();
		List<MenuTreeNode> treeNodes = selectByPid(pid);
		if(menuIds.contains(pid)){
			for(MenuTreeNode node:treeNodes){
				if(menuIds.contains(node.getId())){
					nodes.add(node);
					nodes.addAll(selectAllByPid(node.getId(),menuIds));//递归调用
				}
			}
		}
		return nodes;
	}

	/**
	 * 生成菜单树
	 */
	@Override
	//@Cacheable(value="menuCache", key="'loadTreeNodes'+#id+','+#userId")
	public Collection<MenuTreeNode> loadTreeNodes(Long id,Long userId) {
		
		List<MenuTreeNode> nodes = selectByPid(1l);//查找根节点
		
		List<Role> roles = userRoleService.selectRolesByUserId(userId);
		
		//得到结果之前从redis中获取数据
		try {
			String roleIds = "";
			for(Role role:roles){
				roleIds = roleIds + role.getId()+",";
			}
			String json = jedisClient.get(KEY_loadTreeNodes+":"+roleIds);
			if(StringUtils.isNoneBlank(json)){
				List<MenuTreeNode> result = JSON.parseArray(json, MenuTreeNode.class);
				return result;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		System.out.println("=======================================================================================================================================================");
		System.out.println("==========================================================数据库查找loadTreeNodes==========================================================================");
		System.out.println("=======================================================================================================================================================");
		
		
		
		Set<Long> menuIds = Sets.newHashSet();//用户拥有的菜单id 
		for (Role role : roles) {
			List<Menu> menus = roleMenuService.selectShowerMenusByRoleId(role.getId());
			for(Menu menu:menus){
				menuIds.add(menu.getId());
			}
		}
		final Set<Long> menuIdCps = menuIds;
        Collection<MenuTreeNode> filterList = Collections2.filter(nodes, new Predicate<MenuTreeNode>(){  
            @Override
            public boolean apply(MenuTreeNode node) {
                //过滤菜单树，把不包含在权限菜单内的节点删除
                if(menuIdCps.contains(node.getId())){
                    return true;
                }else{
                    return false;
                }
            }  
        });  
		
		for(MenuTreeNode node:filterList){
			if(menuIds.contains(node.getId())){
				node.getLevelOnechildren().addAll(selectByPidForUser(node.getId(),menuIds));
				for(MenuTreeNode node2:node.getLevelOnechildren()){
					if(menuIds.contains(node2.getId())){
						node2.getLevelTwochildren().addAll(selectAllByPid(node2.getId(),menuIds));
					}
				}
			}
		}
		
		//返回结果之前向redis中添加数据
		try {
			String roleIds = "";
			for(Role role:roles){
				roleIds = roleIds + role.getId()+",";
			}
			
			jedisClient.set(KEY_loadTreeNodes+":"+roleIds, JSON.toJSONString(filterList));
			//设置过期时间
			jedisClient.expire(KEY_loadTreeNodes+":"+roleIds, COMMON_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return filterList;
	}

	
	/**
	 * 同步缓存
	 */
	@Override
	public void syncGetPermTreeGlobal() {
		jedisClient.delKeys(KEY_getPermTreeGlobal+":*");
	}
	
	/**
	 * 同步缓存
	 */
	@Override
	public void syncLoadTreeNodes() {
		jedisClient.delKeys(KEY_loadTreeNodes+":*");
	}
	
	/**
	 * 清除menu缓存
	 */
	@Override
//	@CacheEvict(value="menuCache", allEntries=true)
	public void removeMenuCache(){
		//同步缓存
		this.syncLoadTreeNodes();
		this.syncGetPermTreeGlobal();
	    System.out.println("移除缓存中的所有数据!!!");
	    
	}
	
	/**
	 * 根据roleId获取菜单权限树（整体）
	 */
	@Override
	public MenuPermTreeDto getPermTreeGlobal(Long roleId){
		
		//得到结果之前从redis中获取数据
		try {
			String json = jedisClient.get(KEY_getPermTreeGlobal+":"+roleId);
			if(StringUtils.isNoneBlank(json)){
				MenuPermTreeDto dto = JSON.parseObject(json, MenuPermTreeDto.class);
				return dto;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		MenuPermTreeDto root = new MenuPermTreeDto();
		Menu rootMenu = this.selectById(1l);//根节点
		root.setId(rootMenu.getId());
		root.setName(rootMenu.getName());
		root.setFaicon(rootMenu.getFaicon());
		
		//菜单选中的id集合
		List<Long> menuCheckedIds = Lists.newArrayList();
		//权限选中的id集合
		List<Long> permCheckedIds = Lists.newArrayList();
		
		List<Menu> menuCheckeds = roleMenuService.selectMenusByRoleId(roleId);
		for(Menu m:menuCheckeds){
			menuCheckedIds.add(m.getId());
		}
		
		List<Permission> permCheckeds = rolePermService.selectPermsByRoleId(roleId);
		for(Permission p:permCheckeds){
			permCheckedIds.add(p.getId());
		}
		
		//置为选中
		if(menuCheckedIds.size()>0){
			if(menuCheckedIds.contains(rootMenu.getId())){
				root.setChecked(true);
			}
		}
		
		//根据父节点去递归调用
		root.setChildren(selectMenuPermAllByPid(root.getId(),menuCheckedIds,permCheckedIds));
		
		
		//返回结果之前向redis中添加数据
		try {
			//hset 不能设置过期时间，
			//jedisClient.hset(REDIS_DICT_CATLOG, code, JSON.toJSONString(selectItems));
			
			//所以推荐以命名规则来 用 普通set  key的规则：redis_dict_catlog:id
			jedisClient.set(KEY_getPermTreeGlobal+":"+roleId, JSON.toJSONString(root));
			//设置过期时间
			jedisClient.expire(KEY_getPermTreeGlobal+":"+roleId, COMMON_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return root;
	}
	
	/**
	 * 根据pid获取一层MenuPermTreeDto节点，并根据roid获取保存的信息
	 */
	public List<MenuPermTreeDto> selectMenuPermByPid(Long pid,List<Long> menuCheckedIds,List<Long> permCheckedIds) {
		List<MenuPermTreeDto> nodes = Lists.newArrayList();
		
		MenuQuery query = new MenuQuery();
		query.setOrderField("seq");
		query.setPid(pid.toString());
		query.setActive(true);//已激活状态的菜单
		
		List<Menu> menus = this.selectList(query);
		for(Menu menu:menus){
			MenuPermTreeDto dto = new MenuPermTreeDto();
			dto.setId(menu.getId());
			dto.setPid(menu.getPid());
			dto.setName(menu.getName());
			dto.setFaicon(menu.getFaicon());
			
			//置为选中
			if(menuCheckedIds.size()>0){
				if(menuCheckedIds.contains(menu.getId())){
					dto.setChecked(true);
				}
			}
			
			//如果是最下级，获取permission
			if(menu.getTheLast()!=null&&menu.getTheLast()){
				List<MenuPermTreeDto> permChildren = Lists.newArrayList();
				PermissionQuery permQuery = new PermissionQuery();
				permQuery.setMenuId(menu.getId().toString());
				List<Permission> permissions = permissionService.selectList(permQuery);
				
				for(Permission perm:permissions){
					MenuPermTreeDto permdto = new MenuPermTreeDto();
					permdto.setId(perm.getId());
					permdto.setPid(perm.getMenuId());
					permdto.setName(perm.getName());
					permdto.setFaicon(perm.getFaicon());
					permdto.setPerm(true);
					
					//置为选中
					if(permCheckedIds.size()>0){
						if(permCheckedIds.contains(perm.getId())){
							permdto.setChecked(true);
						}
					}
					
					permChildren.add(permdto);
				}
				dto.setChildren(permChildren);
			}
			nodes.add(dto);
		};
		return nodes;
	}
	
	/**
	 * 递归调用MenuPermTreeDto
	 * @param pid
	 * @return
	 */
	public List<MenuPermTreeDto> selectMenuPermAllByPid(Long pid,List<Long> menuCheckedIds,List<Long> permCheckedIds) {
		List<MenuPermTreeDto> nodes = Lists.newArrayList();
		List<MenuPermTreeDto> treeNodes = selectMenuPermByPid(pid,menuCheckedIds,permCheckedIds);
		for(MenuPermTreeDto node:treeNodes){
			nodes.add(node);
			node.getChildren().addAll(selectMenuPermAllByPid(node.getId(),menuCheckedIds,permCheckedIds));//递归调用
		}
		return nodes;
	}

	
}