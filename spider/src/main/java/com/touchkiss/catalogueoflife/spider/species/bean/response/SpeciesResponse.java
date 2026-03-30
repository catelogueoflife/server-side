package com.touchkiss.catalogueoflife.spider.species.bean.response;

import com.touchkiss.catalogueoflife.spider.species.bean.bo.SpeciesBO;
import lombok.Data;

import java.util.List;

/**
 * Created on 2020/03/20 11:41
 *
 * @author Touchkiss
 */
@Data
public class SpeciesResponse {
    private String id;
    private String name;
    private int total_number_of_results;
    private int number_of_results_returned;
    private int start;
    private String error_message;
    private String version;
    private String rank;
    private List<SpeciesBO> results;
}
