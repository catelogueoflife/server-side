package com.touchkiss.catalogueoflife.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 2020/04/07 16:56
 *
 * @author Touchkiss
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private int code;
    private String msg;
    private T data;
}
