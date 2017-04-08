package com.yanxiu.ce.centerapi;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: tangmin
 * @date: 2017年03月09日 11:00
 * @version: 1.0
 */
public class MyClient {

    public static void main(String[] args) throws Exception{
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpResponse response = null;
        System.out.println("******************************页面转向******************************");

        String newUrl = "http://jiaoshi.e21.cn:8081/mgmt/tbBizJsjbxx/listTbBizJsjbxx";
        HttpPost httpPost = new HttpPost(newUrl);
        httpPost.addHeader(new BasicHeader("Cookie",
                "BizAc_cookie=%00__path__%3A%2Fbizdict%2F%7Bid%7D%00%00__method__%3AfindDictById%00%00__controller__%3Acom.chinasofti.ro.bizframework.modules.dict.DictController%00; JSID-MGMT=pvqxGZmBVctXFXi4b5H5fS82Hrp_OEUIsWacKY5rc2tzt4wgWuyz!-748133233!263904744"));
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        httpPost.addHeader("Host", "jiaoshi.e21.cn:8081");
        httpPost.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpPost.addHeader("Accept-Encoding", "gzip, deflate");
        httpPost.addHeader("Connection", "keep-alive");
        httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
        httpPost.addHeader("Origin", "http://jiaoshi.e21.cn:8081");
        httpPost.addHeader("Referer", "http://jiaoshi.e21.cn:8081/mgmt/tbBizJsjbxx");

        //配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(5000)
                .setConnectTimeout(5000)
                .setSocketTimeout(5000).build();
        httpPost.setConfig(requestConfig);

        //传入参数
        List<NameValuePair> paramList = new ArrayList<>();
        paramList.add(new BasicNameValuePair("pageNo", "1"));
        paramList.add(new BasicNameValuePair("pageSize", "200"));
        paramList.add(new BasicNameValuePair("orderFields", ""));
        paramList.add(new BasicNameValuePair("order", ""));
        paramList.add(new BasicNameValuePair("cxtjJson", "[{\"ZDLX\":\"0\",\"ZDBS2\":null,\"QZSJZD\":null,\"XSMS\":null,\"ZDCD\":\"100\",\"BKZZDBS\":null,\"QZSJFH\":null,\"XSWS\":null,\"DQFS\":\"0\",\"XSFS\":null,\"ZWLS\":\"1\",\"SFZD\":\"0\",\"SFBT\":\"1\",\"RQGS\":null,\"ZDBS\":\"XM\",\"PCZD\":\"0\",\"MRZ\":\"\",\"HZSJFH\":null,\"MKBS\":\"JZGJBXX\",\"CCZD\":\"XM\",\"HZSJZD\":null,\"YXDX\":true,\"ZDMC\":\"姓名\",\"XSKD\":\"80\",\"KZZDBS\":null,\"XZFS\":null,\"ZDZ\":null,\"SFXYKZ\":null,\"ZXZ\":null},{\"ZDLX\":\"1\",\"ZDBS2\":\"XB\",\"QZSJZD\":null,\"XSMS\":\"0\",\"ZDCD\":null,\"BKZZDBS\":null,\"QZSJFH\":null,\"XSWS\":null,\"DQFS\":\"1\",\"XSFS\":\"0\",\"ZWLS\":\"1\",\"SFZD\":\"0\",\"SFBT\":\"1\",\"RQGS\":null,\"ZDBS\":\"XB\",\"PCZD\":\"0\",\"MRZ\":\"\",\"HZSJFH\":null,\"MKBS\":\"JZGJBXX\",\"CCZD\":\"XB\",\"HZSJZD\":null,\"YXDX\":true,\"ZDMC\":\"性别\",\"XSKD\":\"80\",\"KZZDBS\":null,\"XZFS\":\"1\",\"ZDZ\":null,\"SFXYKZ\":\"1\",\"ZXZ\":null},{\"ZDLX\":\"0\",\"ZDBS2\":null,\"QZSJZD\":null,\"XSMS\":null,\"ZDCD\":\"50\",\"BKZZDBS\":null,\"QZSJFH\":null,\"XSWS\":null,\"DQFS\":\"0\",\"XSFS\":null,\"ZWLS\":\"1\",\"SFZD\":\"0\",\"SFBT\":\"1\",\"RQGS\":null,\"ZDBS\":\"SFZJH\",\"PCZD\":\"0\",\"MRZ\":\"\",\"HZSJFH\":null,\"MKBS\":\"JZGJBXX\",\"CCZD\":\"SFZJH\",\"HZSJZD\":null,\"YXDX\":true,\"ZDMC\":\"身份证件号\",\"XSKD\":\"150\",\"KZZDBS\":null,\"XZFS\":null,\"ZDZ\":null,\"SFXYKZ\":null,\"ZXZ\":null},{\"ZDLX\":\"2\",\"ZDBS2\":null,\"QZSJZD\":\"CSRQ,CJGZNY\",\"XSMS\":null,\"ZDCD\":null,\"BKZZDBS\":null,\"QZSJFH\":\">=\",\"XSWS\":null,\"DQFS\":\"1\",\"XSFS\":null,\"ZWLS\":\"1\",\"SFZD\":\"0\",\"SFBT\":\"1\",\"RQGS\":\"yyyy-MM\",\"ZDBS\":\"JBXNY\",\"PCZD\":\"0\",\"MRZ\":\"\",\"HZSJFH\":null,\"MKBS\":\"JZGJBXX\",\"CCZD\":\"JBXNY\",\"HZSJZD\":null,\"YXDX\":true,\"ZDMC\":\"进本校年月\",\"XSKD\":\"100\",\"KZZDBS\":null,\"XZFS\":null,\"ZDZ\":null,\"SFXYKZ\":null,\"ZXZ\":null},{\"ZDLX\":\"1\",\"ZDBS2\":\"JZGLY\",\"QZSJZD\":null,\"XSMS\":\"1\",\"ZDCD\":null,\"BKZZDBS\":null,\"QZSJFH\":null,\"XSWS\":null,\"DQFS\":\"1\",\"XSFS\":\"1\",\"ZWLS\":\"1\",\"SFZD\":\"0\",\"SFBT\":\"1\",\"RQGS\":null,\"ZDBS\":\"JZGLY\",\"PCZD\":\"0\",\"MRZ\":\"\",\"HZSJFH\":null,\"MKBS\":\"JZGJBXX\",\"CCZD\":\"JZGLY\",\"HZSJZD\":null,\"YXDX\":true,\"ZDMC\":\"教职工来源\",\"XSKD\":\"100\",\"KZZDBS\":null,\"XZFS\":\"0\",\"ZDZ\":null,\"SFXYKZ\":\"1\",\"ZXZ\":null},{\"ZDLX\":\"1\",\"ZDBS2\":\"JZGLB\",\"QZSJZD\":null,\"XSMS\":\"0\",\"ZDCD\":null,\"BKZZDBS\":null,\"QZSJFH\":null,\"XSWS\":null,\"DQFS\":\"1\",\"XSFS\":\"1\",\"ZWLS\":\"1\",\"SFZD\":\"0\",\"SFBT\":\"1\",\"RQGS\":null,\"ZDBS\":\"JZGLB\",\"PCZD\":\"0\",\"MRZ\":\"\",\"HZSJFH\":null,\"MKBS\":\"JZGJBXX\",\"CCZD\":\"JZGLB\",\"HZSJZD\":null,\"YXDX\":true,\"ZDMC\":\"教职工类别\",\"XSKD\":\"100\",\"KZZDBS\":null,\"XZFS\":\"1\",\"ZDZ\":null,\"SFXYKZ\":\"1\",\"ZXZ\":null},{\"ZDLX\":\"1\",\"ZDBS2\":\"SFZB\",\"QZSJZD\":null,\"XSMS\":\"0\",\"ZDCD\":null,\"BKZZDBS\":null,\"QZSJFH\":null,\"XSWS\":null,\"DQFS\":\"1\",\"XSFS\":\"0\",\"ZWLS\":\"1\",\"SFZD\":\"0\",\"SFBT\":\"1\",\"RQGS\":null,\"ZDBS\":\"SFZB\",\"PCZD\":\"0\",\"MRZ\":\"\",\"HZSJFH\":null,\"MKBS\":\"JZGJBXX\",\"CCZD\":\"SFZB\",\"HZSJZD\":null,\"YXDX\":true,\"ZDMC\":\"是否在编\",\"XSKD\":\"100\",\"KZZDBS\":null,\"XZFS\":\"1\",\"ZDZ\":null,\"SFXYKZ\":\"0\",\"ZXZ\":null},{\"ZDLX\":\"1\",\"ZDBS2\":\"ZGQK\",\"QZSJZD\":null,\"XSMS\":\"0\",\"ZDCD\":null,\"BKZZDBS\":null,\"QZSJFH\":null,\"XSWS\":null,\"DQFS\":\"1\",\"XSFS\":\"0\",\"ZWLS\":\"1\",\"SFZD\":\"0\",\"SFBT\":\"1\",\"RQGS\":null,\"ZDBS\":\"ZGQK\",\"PCZD\":\"0\",\"MRZ\":\"\",\"HZSJFH\":null,\"MKBS\":\"JZGJBXX\",\"CCZD\":\"ZGQK\",\"HZSJZD\":null,\"YXDX\":true,\"ZDMC\":\"人员状态\",\"XSKD\":\"100\",\"KZZDBS\":null,\"XZFS\":\"1\",\"ZDZ\":null,\"SFXYKZ\":\"0\",\"ZXZ\":null}]"));
        paramList.add(new BasicNameValuePair("_search", "false"));
        paramList.add(new BasicNameValuePair("nd", "1489030386359"));
        // 模拟表单，设置参数
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"utf-8");
        httpPost.setEntity(entity);

        HttpResponse httpResponse = httpclient.execute(httpPost);
        String responseString = EntityUtils.toString(httpResponse.getEntity());
        // 登录后首页的内容
        System.out.println("::::::::::::::"+responseString);

        CenterTchBasePage centerPage = JSONObject.parseObject(responseString, CenterTchBasePage.class);
        System.out.println(centerPage);

        List<CenterTchBase> rows =centerPage.getRows();
        for (CenterTchBase row : rows) {
            System.out.println("id:"+row.getID()+",姓名:"+row.getXM()+",证件类型："+row.getSFZJLX()+",号码："+row.getSFZJH()+",性别："+row.getXB());
        }

        System.out.println(rows.size());

        httpPost.releaseConnection();
    }
}
