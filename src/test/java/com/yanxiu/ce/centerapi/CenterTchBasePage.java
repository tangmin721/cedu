package com.yanxiu.ce.centerapi;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Description:
 * @author: tangmin
 * @date: 2017年03月09日 12:05
 * @version: 1.0
 */
public class CenterTchBasePage {
    private Integer page;
    private Integer total;
    private Integer records;
    private List<CenterTchBase> rows = Lists.newArrayList();

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getRecords() {
        return records;
    }

    public void setRecords(Integer records) {
        this.records = records;
    }

    public List<CenterTchBase> getRows() {
        return rows;
    }

    public void setRows(List<CenterTchBase> rows) {
        this.rows = rows;
    }
}
