package com.touchkiss.catalogueoflife.spider.baidu.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 2020/07/08 23:13
 *
 * @author Touchkiss
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaikeItemResponse {
    private String key;
    private String value;
}
