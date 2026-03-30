package com.touchkiss.catalogueoflife.spider.life.bean.bo;

import lombok.Data;

import java.util.List;

/**
 * Created on 2020/03/18 17:02
 *
 * @author Touchkiss
 */
@Data
public class LifeCategoryBO {
    private Integer id; //
    private String snId; //
    private String name; //名称
    private String type; //分类等级
    private Integer parentId; //
    private Integer numChildren; //
    private String estimation; //估计数量
    private String total; //总数
    private String nr_fossil; //提供者数量
    private String nr_extant; //现存数量
    private String estimate_source; //估计来源
    private Integer is_extinct; //是否灭绝
    private Integer has_preholocene; //是否是“前全新世”
    private Integer has_modern; //是否现生
    private String image; //
    private String rank; //
    private String url; //
    private String percentage;
    private List<SourceDatabaseBO> source_databases;
}
