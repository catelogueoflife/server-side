package com.touchkiss.catalogueoflife.spider.baidu.services;

import com.touchkiss.catalogueoflife.es.services.BaseESServiceImpl;
import com.touchkiss.catalogueoflife.spider.baidu.bean.ESBaikeItem;
import org.springframework.stereotype.Service;

/**
 * Created on 2020/04/14 17:51
 *
 * @author Touchkiss
 */
@Service
public class ESBaikeItemServiceImpl extends BaseESServiceImpl<ESBaikeItem> {
    public ESBaikeItemServiceImpl(com.touchkiss.catalogueoflife.es.repositories.BaseRepository baseRepository) {
        super(baseRepository);
    }
}

