package com.touchkiss.categoryoflife.spider.life.bean.convertor;

import com.touchkiss.categoryoflife.life.bean.SourceDatabase;
import com.touchkiss.categoryoflife.spider.base.BaseConvertor;
import com.touchkiss.categoryoflife.spider.life.bean.bo.SourceDatabaseBO;
import org.springframework.stereotype.Component;

/**
 * Created on 2020/03/19 12:57
 *
 * @author Touchkiss
 */
@Component
public class SourceDatabaseConvertor implements BaseConvertor<SourceDatabase, SourceDatabaseBO> {
    @Override
    public SourceDatabase convert(SourceDatabaseBO sourceDatabaseBO) {
        SourceDatabase sourceDatabase = new SourceDatabase();
        sourceDatabase.setId(sourceDatabaseBO.getSource_database_id());
        sourceDatabase.setShortName(sourceDatabaseBO.getShort_name());
        sourceDatabase.setFullName(sourceDatabaseBO.getFull_name());
        return sourceDatabase;
    }
}
