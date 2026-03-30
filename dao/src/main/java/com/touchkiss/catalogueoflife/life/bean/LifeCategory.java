package com.touchkiss.catalogueoflife.life.bean;

import lombok.Data;

/**
 * Created on 2020-03-19 13:04:20
 *
 * @author Touchkiss
 */
@Data
public class LifeCategory {
    private Integer id; //
    private String snId; //
    private String name; //名称
    private String type; //分类等级
    private Integer parentId; //
    private Integer numChildren; //
    private Integer estimation; //估计数量
    private Integer total; //总数
    private Integer nrFossil; //提供者数量
    private Integer nrExtant; //现存数量
    private String estimateSource; //估计来源
    private Boolean extinct; //是否灭绝
    private Boolean hasPreholocene; //是否是“前全新世”
    private Boolean hasModern; //是否现生
    private String image; //
    private String url; //
    private Boolean fetched; //是否经过爬取
    private Long createTime; //
    private Long lastModifyTime; //
}
