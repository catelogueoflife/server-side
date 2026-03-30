package com.touchkiss.catalogueoflife.spider.baidu.bean;

import com.touchkiss.catalogueoflife.es.annotations.EsDocument;
import com.touchkiss.catalogueoflife.es.annotations.EsField;

/**
 * Created on 2020/04/13 17:50
 *
 * @author Touchkiss
 */
@EsDocument(index = "baidu_baike", id = "id", replicas = 0)
public class ESBaikeItem {
    @EsField(type = EsField.FieldType.Keyword)
    private String id;
    @EsField(type = EsField.FieldType.Text, analyzer = "ik_max_word")
    private String title;
    @EsField(type = EsField.FieldType.Keyword)
    private String title_strict;
    @EsField(type = EsField.FieldType.Text, analyzer = "ik_max_word")
    private String property;
    @EsField(type = EsField.FieldType.Keyword)
    private String property_strict;
    @EsField(type = EsField.FieldType.Text, analyzer = "ik_max_word")
    private String value;
    @EsField(type = EsField.FieldType.Long)
    private Long createTime;
    @EsField(type = EsField.FieldType.Keyword)
    private String source;

    public ESBaikeItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_strict() {
        return title_strict;
    }

    public void setTitle_strict(String title_strict) {
        this.title_strict = title_strict;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getProperty_strict() {
        return property_strict;
    }

    public void setProperty_strict(String property_strict) {
        this.property_strict = property_strict;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    private ESBaikeItem(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.title_strict = builder.title_strict;
        this.property = builder.property;
        this.property_strict = builder.property_strict;
        this.value = builder.value;
        this.createTime = builder.createTime;
        this.source = builder.source;
    }

    public static Builder newESBaikeItem() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String title;
        private String title_strict;
        private String property;
        private String property_strict;
        private String value;
        private Long createTime;
        private String source;

        private Builder() {
        }

        public ESBaikeItem build() {
            return new ESBaikeItem(this);
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder title_strict(String title_strict) {
            this.title_strict = title_strict;
            return this;
        }

        public Builder property(String property) {
            this.property = property;
            return this;
        }

        public Builder property_strict(String property_strict) {
            this.property_strict = property_strict;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Builder createTime(Long createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder source(String source) {
            this.source = source;
            return this;
        }
    }
}
