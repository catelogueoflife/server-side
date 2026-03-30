package com.touchkiss.catalogueoflife.base;

import com.github.pagehelper.Page;
import lombok.Data;

import java.util.List;

/**
 * Created on 2020/03/19 16:41
 *
 * @author Touchkiss
 */
@Data
public class PageResult<T> {
    private Integer draw;
    private Long count;
    private Long recordsTotal;
    private Long recordsFiltered;
    private List<T> results;
    private List<T> data;

    public PageResult() {
    }

    public PageResult(Integer draw, Long recordsTotal, Long recordsFiltered, List<T> results) {
        this.draw = draw;
        this.count = recordsTotal;
        this.recordsTotal = recordsFiltered;
        this.recordsFiltered = recordsFiltered;
        this.results = results;
        this.data = results;
    }

    /**
     * 构建Datatables返回结果
     *
     * @param draw 抽签
     * @param page 分页结果
     */
    public PageResult(Integer draw, Page<T> page) {
        this.draw = draw;
        this.count = page == null ? 0 : page.getTotal();
        this.recordsFiltered = page == null ? 0 : page.getTotal();
        this.recordsTotal = this.recordsFiltered;
        this.results = page == null ? null : page.getResult();
        this.data = results;
    }

    public PageResult(Page<T> page) {
        this.count = page == null ? 0 : page.getTotal();
        this.recordsFiltered = page == null ? 0 : page.getTotal();
        this.recordsTotal = this.recordsFiltered;
        this.results = page == null ? null : page.getResult();
        this.data = results;
    }
}
