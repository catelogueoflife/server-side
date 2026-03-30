package com.touchkiss.catalogueoflife.spider.cndbpedia.bean;

import java.util.List;

/**
 * Created on 2020/04/03 15:29
 *
 * @author Touchkiss
 */
public class CNDbPediaResponse {

    /**
     * status : ok
     * ret : [["中文学名","蓝鲸"],["拉丁学名","Balaenoptera musculus"],["别名","剃刀鲸"],["界","动物界"],["门","脊索动物门"],["亚门","脊椎动物亚门"],["纲","哺乳纲"],["目","鲸目"],["亚目","须鲸亚目"],["科","鳁鲸科"],["属","鳁鲸属"],["种","蓝鲸"],["亚种","4亚种"],["命名者及年代","Linnaeus, 1758"],["英文名称","Blue Whale"],["英文名称","Pygmy Blue Whale"],["英文名称","Sibbald's Rorqual"],["英文名称","Sulphur-bottom Whale"],["DESC","蓝鲸（学名：Balaenoptera musculus）是一种海洋哺乳动物，属于须鲸亚目。共有四个亚种。蓝鲸被认为是已知的地球上生存过的体积最大的动物，长可达33米，重达181吨。蓝鲸的身躯瘦长，背部是青灰色的，不过在水中看起来有时颜色会比较淡。与其他须鲸一样，蓝鲸主要以小型的甲壳类（例如磷虾）与小型鱼类为食，有时也包括鱿鱼。通常蓝鲸白天需要在超过100米深度的海域来觅食，在夜晚才能到水面觅食。蓝鲸在晚秋开始交配，并一直持续到冬末 ，雌鲸通常2-3年生产一次，在经过10-12个月的妊娠期后，一般会在冬初产下幼鲸。四大洋均有分布。"],["CATEGORY_ZH","动物"],["CATEGORY_ZH","自然"],["CATEGORY_ZH","生物"]]
     */

    private String status;
    private List<List<String>> ret;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<List<String>> getRet() {
        return ret;
    }

    public void setRet(List<List<String>> ret) {
        this.ret = ret;
    }
}
