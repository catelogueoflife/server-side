package com.touchkiss.catalogueoflife.spider.life.bean.convertor;

import com.touchkiss.catalogueoflife.life.bean.LifeCategory;
import com.touchkiss.catalogueoflife.spider.base.BaseConvertor;
import com.touchkiss.catalogueoflife.spider.life.bean.bo.LifeCategoryBO;
import com.touchkiss.catalogueoflife.utils.NumberUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created on 2020/03/18 17:09
 *
 * @author Touchkiss
 */
@Component
public class LifeCategoryConvertor implements BaseConvertor<LifeCategory, LifeCategoryBO> {
    @Override
    public LifeCategory convert(LifeCategoryBO lifeCategoryBO) {
        if (lifeCategoryBO != null) {
            LifeCategory lifeCategory = new LifeCategory();
            lifeCategory.setId(lifeCategoryBO.getId());
            lifeCategory.setSnId(lifeCategoryBO.getSnId());
            lifeCategory.setName(lifeCategoryBO.getName());
            lifeCategory.setType(lifeCategoryBO.getType());
            lifeCategory.setParentId(lifeCategoryBO.getParentId());
            lifeCategory.setNumChildren(lifeCategoryBO.getNumChildren());
            lifeCategory.setEstimation(NumberUtil.stringToInt(lifeCategoryBO.getEstimation()));
            lifeCategory.setTotal(NumberUtil.stringToInt(lifeCategoryBO.getTotal()));
            lifeCategory.setNrFossil(NumberUtil.stringToInt(lifeCategoryBO.getNr_fossil()));
            lifeCategory.setNrExtant(NumberUtil.stringToInt(lifeCategoryBO.getNr_extant()));
            lifeCategory.setEstimateSource(lifeCategoryBO.getEstimate_source());
            lifeCategory.setExtinct(lifeCategoryBO.getIs_extinct() == null ? null : lifeCategoryBO.getIs_extinct().equals(1));
            lifeCategory.setHasPreholocene(lifeCategoryBO.getHas_preholocene() == null ? null : lifeCategoryBO.getHas_preholocene().equals(1));
            lifeCategory.setHasModern(lifeCategoryBO.getHas_modern() == null ? null : lifeCategoryBO.getHas_modern().equals(1));
            if (StringUtils.isNotBlank(lifeCategoryBO.getImage()) && lifeCategoryBO.getImage().length() > 5) {
                lifeCategory.setImage("http://www.catalogueoflife.org" + lifeCategoryBO.getImage());
            }
            if (StringUtils.isNotBlank(lifeCategoryBO.getUrl()) && lifeCategoryBO.getUrl().startsWith("/col")) {
                lifeCategory.setUrl("http://www.catalogueoflife.org" + lifeCategoryBO.getUrl());
            }
            return lifeCategory;
        }
        return null;
    }
}
