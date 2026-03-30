package com.touchkiss.catalogueoflife.es.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created on 2020/04/10 9:46
 *
 * @author Touchkiss
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ESPage<T> {
    private Long recordsTotal;
    private List<T> data;
}
