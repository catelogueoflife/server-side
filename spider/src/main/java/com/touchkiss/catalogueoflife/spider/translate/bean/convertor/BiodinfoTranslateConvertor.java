package com.touchkiss.catalogueoflife.spider.translate.bean.convertor;

import com.touchkiss.catalogueoflife.spider.base.BaseConvertor;
import com.touchkiss.catalogueoflife.spider.translate.bean.bo.BiodinfoTranslateBO;
import com.touchkiss.catalogueoflife.translate.bean.Translate;
import org.springframework.stereotype.Component;

/**
 * Created on 2020/03/21 17:08
 *
 * @author Touchkiss
 */
@Component
public class BiodinfoTranslateConvertor implements BaseConvertor<Translate, BiodinfoTranslateBO> {
    @Override
    public Translate convert(BiodinfoTranslateBO biodinfoTranslateBO) {
        if (biodinfoTranslateBO == null) {
            return null;
        }
        Translate translate = new Translate();
        translate.setCanonicalName(biodinfoTranslateBO.getCanonicalName());
        translate.setTranslate(biodinfoTranslateBO.getChineseName());
        translate.setFullName(biodinfoTranslateBO.getFullName());
        translate.setLanguage("Chinese");
        translate.setLid(biodinfoTranslateBO.getId());
        translate.setTreeId(biodinfoTranslateBO.getTreeId());
        translate.setFetched(false);
        return translate;
    }
}
