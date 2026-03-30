package com.touchkiss.catalogueoflife.species.bean;

import lombok.Data;

/**
 * Created on 2020-03-21 14:04:25
 *
 * @author Touchkiss
 */
@Data
public class Species {
    private String id; //
    private String name; //名称
    private Boolean nameAccepted; //是否是接受名
    private String genus; //分类
    private String subgenus; //
    private String species; //
    private String infraspeciesMarker; //
    private String infraspecies; //
    private String author; //作者
    private String onlineResource; //在线资源
    private Boolean extinct; //已灭绝
    private String sourceDatabase; //数据来源
    private String sourceDatabaseUrl; //数据来源url
    private String bibliographicCitation; //文献引用
    private String url; //col url
    private String distribution; //分布范围
    private Long createTime; //
    private Long lastModifyTime; //
}
