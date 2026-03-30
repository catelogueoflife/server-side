package com.touchkiss.catalogueoflife.spider.life.bean.response;

import com.touchkiss.catalogueoflife.spider.life.bean.bo.LifeCategoryBO;
import lombok.Data;

import java.util.List;

/**
 * Created on 2020/03/18 17:41
 *
 * @author Touchkiss
 */
@Data
public class LifeCategoryResponse {
    private String identifier;
    private List<LifeCategoryBO> items;
    private String label;
}
