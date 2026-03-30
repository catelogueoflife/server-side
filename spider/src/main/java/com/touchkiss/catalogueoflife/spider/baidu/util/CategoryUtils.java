package com.touchkiss.catalogueoflife.spider.baidu.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created on 2020/04/07 15:06
 *
 * @author Touchkiss
 */
public class CategoryUtils {
    final static Set<Set<String>> titleSet = new HashSet() {{
        add(new HashSet() {{
            addAll(Arrays.asList("简介"));
        }});
        add(new HashSet() {{
            addAll(Arrays.asList("中文名", "中文学名", "英文名", "英文名称", "拉丁学名", "同义学名", "别称", "二名法", "同义学名", "物种别名", "英文缩写", "外文名", "中文意思", "西班牙语"));
        }});
        add(new HashSet() {{
            addAll(Arrays.asList("分布区域", "分布范围", "栖息环境", "主要分布", "生存地点", "地理分布", "分布现状", "种属分布", "涵盖地域", "分布水体", "生活区域"));
        }});
        add(new HashSet() {{
            addAll(Arrays.asList("界"));
        }});
        add(new HashSet() {{
            addAll(Arrays.asList("门", "亚门"));
        }});
        add(new HashSet() {{
            addAll(Arrays.asList("纲", "亚纲", "下纲"));
        }});
        add(new HashSet() {{
            addAll(Arrays.asList("目", "亚目", "总目", "高目", "小目", "下目"));
        }});
        add(new HashSet() {{
            addAll(Arrays.asList("科", "亚科"));
        }});
        add(new HashSet() {{
            addAll(Arrays.asList("属", "压属"));
        }});
        add(new HashSet() {{
            addAll(Arrays.asList("种", "压种"));
        }});
        add(new HashSet() {{
            addAll(Arrays.asList("保护级别", "保护现状", "保护状况", "国家保护等级"));
        }});
        add(new HashSet() {{
            addAll(Arrays.asList("生活习性", "栖息环境", "种群现状", "科学家探索", "生活适应", "环境适应", "生态习性", "习性", "终宿主", "中间宿主", "社交生活", "生活史", "寄主", "生长特点"));
        }});
        add(new HashSet() {{
            addAll(Arrays.asList("物种命名", "命名者及年代", "命名时间", "命名者", "命名人", "发现命名"));
        }});
        add(new HashSet() {{
            addAll(Arrays.asList("形态特征", "生理结构", "外形特征", "生理构造", "特征", "型态特征", "形态", "生物学性状", "颜色", "体毛", "形态构造"));
        }});
        add(new HashSet() {{
            addAll(Arrays.asList("繁殖方式", "生长繁殖", "保育措施", "繁衍发育", "人工饲养", "繁殖发育", "饲养管理", "繁殖技术", "日常管理", "繁殖", "种群繁殖", "繁殖习惯"));
        }});
        add(new HashSet() {{
            addAll(Arrays.asList("文化意义", "价值作用", "利用及保护", "大众文化", "经济意义", "危害", "研究价值", "主要价值", "经济价值", "生物的应用", "有益方面", "相关文化", "科学价值"));
        }});
        add(new HashSet() {{
            addAll(Arrays.asList("相关研究", "研究进展", "发现过程"));
        }});
        add(new HashSet() {{
            addAll(Arrays.asList("物种分类", "亚种分化", "下属物种", "主要亚科", "下级分类", "常见物种", "分类", "区分鉴别", "动物科属", "分类讨论", "代表性动物", "现生生物", "品种分类", "个体间差异", "现存种类", "分类情况", "同种分化", "近种区别", "分类介绍", "常见品种", "该科物种"));
        }});
        add(new HashSet() {{
            addAll(Arrays.asList("物种学史", "演化支", "演化历程", "进化历史", "动物学史", "起源", "演化学上的重要性", "种群演化", "早期"));
        }});
        add(new HashSet() {{
            addAll(Arrays.asList("恐龙体重", "体重"));
        }});
//        描述特点
        add(new HashSet() {{
            addAll(Arrays.asList("主要特征", "先进特性", "毒液毒性", "中国特有", "特征", "休眠现象", "休眠方式", "特点"));
        }});
//        描述速度
        add(new HashSet() {{
            addAll(Arrays.asList("短跑冠军", "奔跑", "时速"));
        }});
//        描述食物
        add(new HashSet() {{
            addAll(Arrays.asList("主要致病", "流行原则", "致病性", "防治方法", "危害"));
        }});
//        描述食物
        add(new HashSet() {{
            addAll(Arrays.asList("恐龙食物", "营养方式", "食物", "食性", "觅食"));
        }});
//        描述尺寸
        add(new HashSet() {{
            addAll(Arrays.asList("恐龙体长", "体长", "样本大小", "尾巴长", "体型", "长度", "高度", "尺寸"));
        }});
//        描述生存年代
        add(new HashSet() {{
            addAll(Arrays.asList("生存年代", "化石时期", "化石发现", "寿命", "生日", "年龄", "年代"));
        }});
//        描述简介
        add(new HashSet() {{
            addAll(Arrays.asList("介绍", "简介", "概述", "基本介绍", "解释", "物种概述", "总体介绍"));
        }});
//        描述天敌
        add(new HashSet() {{
            addAll(Arrays.asList("天敌"));
        }});
    }};

    public static Set<String> matchCase(String key) {
        for (Set<String> strings : titleSet) {
            if (strings.contains(key)) {
                return strings;
            }
        }
        return new HashSet() {{
            add(key);
        }};
    }
}
