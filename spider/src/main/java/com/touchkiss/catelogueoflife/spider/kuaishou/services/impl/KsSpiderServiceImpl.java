package com.touchkiss.catelogueoflife.spider.kuaishou.services.impl;

import com.touchkiss.catelogueoflife.spider.kuaishou.bean.*;
import com.touchkiss.catelogueoflife.spider.kuaishou.services.KsSpiderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created on 2020/07/21 11:12
 *
 * @author Touchkiss
 */
@Service
@Slf4j
public class KsSpiderServiceImpl implements KsSpiderService {
    public static int cookieCursor = 0, emptyCount = 0;
    static Random random = new Random();
    static List<String> ksCookieSet = new ArrayList<>();

    static {
        ksCookieSet.add("clientid=13; client_key=f60ac815; kpn=WECHAT_SMALL_APP; kpf=OUTSIDE_ANDROID_H5; mod=microsoft; sys=win10; wechatVersion=7.0.9; language=zh_CN; brand=microsoft; sid=kuaishou.wechat.app; appId=ks_wechat_small_app_2; did=wxo_6bc354bdb0f7c42bba3224d6804c93ad6e6c; session_key=1230cfbb93d2ba26fe1fe4f2fae161835d9e6f7e395dd1ccc943eb94c6f6175c6c4a522008e05f5393bac249f12b713676ce1a120329d922ea3f4e91a87a3014fd7b1e7ace54222052a1b009f7d17f74421a7d8f6b8beb6875402817cefdacfd6e5766140da3c0e428053001; eUserStableOpenId=1230938d175caeb8b3950f9f333f68132e39d8821335bd06839bdba1368ed1258c38b514cf26b83cebdd3dcec2c6d22f3ab51a12bb92bd9c2290489ba7a733708a4a446de83822206d4124af862c649145a80b6f7b8fd1b2ad3d58a570d09161f232f349f7840c5b28053001; openId=o5otV42AmlyGPa7adA2FohjvPYlc; nickName=PROFILE_SERVICE_ERROR; kuaishou.wechat.app_st=ChZrdWFpc2hvdS53ZWNoYXQuYXBwLnN0EqABi2Ea0GjXi5mn4IJGPPgvWeGY7YlnTQQncigVEIvxH-7aavP7lrrN7iFFu_jmGkYON-Ndmq7PgbxHX9Jk1WEkRCp5W2OlweZb1s6B0itJmPHtNBt4uWKErjU7g-KKmQEslgcIBTBpqLb-ptSFJ0_awqKNyFNVlMrzf6Vp7pBrbqkPw6GloXwjx7m6tlddEJBR_1qUN7YzLlZtYZEigQRhZxoScdPXBtqcRdupjj5ImnWVHvLXIiDGBRWq78I-GvTaCmQ5xD9Qfc2ll5bCIWBkR7JQoVZ1QCgFMAE; passToken=ChNwYXNzcG9ydC5wYXNzLXRva2VuEpABF-RP_3EPvu4-ovqAIn9AeBc2wQ75vr-v7rf8N4eRDDxJjYeVKxpHjughUEjjPT5FPb1gt16fVdCsa8z9wjC28b1f5u8pSMPmrp6kZ1GckX8VBxCJBHJpXBwTjJwkBs2OHXYaRDdIyWcgrE-i5pienM9holGCztH7YEjmz9XquKMiC-JFcfizhXLoXe5Fx4-PGhLmcxJU_iJJ_bzbaUG-W3aMOLciIItFxP0mxK7WCKG6yrJyShN25LPrwE1Zexrx2kkcOqPqKAUwAQ; userId=2032794788; unionid=PROFILE_SERVICE_ERROR");
        ksCookieSet.add("clientid=13; client_key=f60ac815; kpn=WECHAT_SMALL_APP; kpf=OUTSIDE_ANDROID_H5; mod=microsoft; sys=win10; wechatVersion=7.0.9; language=zh_CN; brand=microsoft; sid=kuaishou.wechat.app; appId=ks_wechat_small_app_2; did=wxo_6bc354bdb0f7c42bba3224d6804c93ad6e6c; session_key=1230cfbb93d2ba26fe1fe4f2fae161835d9e6f7e395dd1ccc943eb94c6f6175c6c4a522008e05f5393bac249f12b713676ce1a120329d922ea3f4e91a87a3014fd7b1e7ace54222052a1b009f7d17f74421a7d8f6b8beb6875402817cefdacfd6e5766140da3c0e428053001; eUserStableOpenId=1230938d175caeb8b3950f9f333f68132e39d8821335bd06839bdba1368ed1258c38b514cf26b83cebdd3dcec2c6d22f3ab51a12bb92bd9c2290489ba7a733708a4a446de83822206d4124af862c649145a80b6f7b8fd1b2ad3d58a570d09161f232f349f7840c5b28053001; openId=o5otV42AmlyGPa7adA2FohjvPYlc; nickName=PROFILE_SERVICE_ERROR; kuaishou.wechat.app_st=ChZrdWFpc2hvdS53ZWNoYXQuYXBwLnN0EqABi2Ea0GjXi5mn4IJGPPgvWeGY7YlnTQQncigVEIvxH-7aavP7lrrN7iFFu_jmGkYON-Ndmq7PgbxHX9Jk1WEkRCp5W2OlweZb1s6B0itJmPHtNBt4uWKErjU7g-KKmQEslgcIBTBpqLb-ptSFJ0_awqKNyFNVlMrzf6Vp7pBrbqkPw6GloXwjx7m6tlddEJBR_1qUN7YzLlZtYZEigQRhZxoScdPXBtqcRdupjj5ImnWVHvLXIiDGBRWq78I-GvTaCmQ5xD9Qfc2ll5bCIWBkR7JQoVZ1QCgFMAE; passToken=ChNwYXNzcG9ydC5wYXNzLXRva2VuEpABF-RP_3EPvu4-ovqAIn9AeBc2wQ75vr-v7rf8N4eRDDxJjYeVKxpHjughUEjjPT5FPb1gt16fVdCsa8z9wjC28b1f5u8pSMPmrp6kZ1GckX8VBxCJBHJpXBwTjJwkBs2OHXYaRDdIyWcgrE-i5pienM9holGCztH7YEjmz9XquKMiC-JFcfizhXLoXe5Fx4-PGhLmcxJU_iJJ_bzbaUG-W3aMOLciIItFxP0mxK7WCKG6yrJyShN25LPrwE1Zexrx2kkcOqPqKAUwAQ; userId=2032794788; unionid=PROFILE_SERVICE_ERROR");
        ksCookieSet.add("clientid=13; client_key=f60ac815; kpn=WECHAT_SMALL_APP; kpf=OUTSIDE_ANDROID_H5; mod=microsoft; sys=win10; wechatVersion=7.0.9; language=zh_CN; brand=microsoft; sid=kuaishou.wechat.app; appId=ks_wechat_small_app_2; did=wxo_fa7851869eccc220b5c3b9312ae71daed7a0; session_key=1230c6147003d83621f669170074d2241f0dd397fd9daac8a51b2e6e72982cd8be1a52bb36cce8f533e166df54b94eaa69ae1a128fb4330677da412ca8433be272d2eef588802220f46ead09c9bc8c8a380a4c2e75d3f370f1414ce537aaf03d958629ad7c946e0028053001; eUserStableOpenId=123057b0b63ac80824ad2772c0b8d84c4b39be0a613b4ec23c2057e4e2f35a78bc46209f3e7c769222baecc60aa7a18a15731a127fc8232c423947f6b789399f3798fde6add72220a219cafaa49c3629b10ef09e879f42d64a34bb2e6f7309b7fb057d9fc699f7c428053001; openId=o5otV4-3WaoWoD4s2GHOhQU2Qn4s; nickName=PROFILE_SERVICE_ERROR; kuaishou.wechat.app_st=ChZrdWFpc2hvdS53ZWNoYXQuYXBwLnN0ErABib0VIbp7OC7dk2GTZc9NKarUdfQS3_7GaO8diSmneD3GrFybFNf0FU6U2vWbjt4jplA1G2yUSHkS6q6XnFZH77SLSV4EFE5egdNZUg6rpsIuVUi-IjLzDso7fc6Tp5T8jnZF1Xevw8-IzAjXWe319cflqfSbLzTI_9uk00U_LvafANCT0Mqe-bJNTJSSRjCA6Y_aivKz0vW_vC4Cd9heTVds6muDq_7aaF_q5iVTHTcaEnHT1wbanEXbqY4-SJp1lR7y1yIgzuhfDQGKEYoa_U8UB06Ot7pvZgxT57-gshOXKGhsobIoBTAB; passToken=ChNwYXNzcG9ydC5wYXNzLXRva2VuEpAB-DG5tO1PqsKkTpeT1ml4FaWUX3MZvlhzgDQ2869WjfLw5wmSZTLRNKi2dTTFdY5ZuOMbqXnm2vTLSHq-OIMxqZrf35WkdwX_xYD_jy2dyCxot7I75troItFDaCVutLm3LMLmHBJGV9KmMoLM_32U6MnTP3GL9j36kmurSK9dOB9E_LjkmMOt6OhZqG4mC-21GhLmcxJU_iJJ_bzbaUG-W3aMOLciIOc6aYjCg-KHaT0_DVfiI1ClIvxYHZcTLLH5-BkbpCudKAUwAQ; userId=2033295060; unionid=PROFILE_SERVICE_ERROR");
        ksCookieSet.add("clientid=13; client_key=f60ac815; kpn=WECHAT_SMALL_APP; kpf=OUTSIDE_ANDROID_H5; mod=microsoft; sys=win10; wechatVersion=7.0.9; language=zh_CN; brand=microsoft; sid=kuaishou.wechat.app; appId=ks_wechat_small_app_2; did=wxo_8427703be1fe7cd21368457b4e0e3a4c99f5; session_key=12301328a448f4b056e979c3c8c51842c738fc0da10b6f1630223f30ae6689db7125b9e14b6bc3eaf6e041c1c4e62bc503651a1236cd2f69c25d4ac2a2531ebcd28c179da14b222042c25228e7607db515aa4ae29b578b38481e28540f45215492840d6ce31acaf028053001; eUserStableOpenId=12304cda8c9231962b26449e67e96cda95ab10141b4ae7ebf725db54321b3d31442bbcbb289b33fb8bebe1466fb55ed671851a12578aaf78a3674cbaa58ecb218168caa24bbf222097ed2596e25a28191d5f8ff9567d979d87570987f28f9e6581fec80c63b43f0728053001; openId=o5otV4ywMqLAbd156EoSOrQVOo24; nickName=%E7%AC%91%E7%9C%8B%E9%A3%8E%E4%BA%91; kuaishou.wechat.app_st=ChZrdWFpc2hvdS53ZWNoYXQuYXBwLnN0EqABWjUtGI86GhpdvRH7wZaVCufiWmmyzwzm3zRUti3R0EEyO7vffVD1AkowMKWkfUgJhb4iUFNlICAbq5fXDFShSZtPK-GEjVdWrcF7YAJcPtCi0Fpor-4PmxZF8RlEFPZn28oSRYQQ5EwefgLK4Cv6h9WfPdtz9-x5FdrTtXsG64bmAEhKXHw0qHtRttpu1HkAdU8_bDpES7y5KIhOnYASJBoSr7EJrYe_SOy6kfPhC5ElIZtMIiAUrl7mreEMvpLcN5nr29cASU9krSS0D5FH9w4eDjuxkygFMAE; passToken=ChNwYXNzcG9ydC5wYXNzLXRva2VuEpABT8ro6HoSBG_jhuiVEK75H3MSjTXmBVSMNoSBDzRxrCUC1SXZv7YFYNOOXr2qUKXYQJSLbhZABEi9-9o_8Rb_LvLILgzjMGUmdoAwjvrD1Oq7woLas9ocCMmF2SZqnWi6utrst-Us290MXt3z6euOAl7kTj8K8pTobXj4iPmEsB6RdxDHPJQVfwQYbeSpcVoaGhKJiTZIVk1EQI6jVHwd9ON9CIUiIBrRQAZoAgVjTSKNkmgD3jdWqSle99Kxi8y7TZUnVZeCKAUwAQ; userId=2033980676; unionid=V2:12305c370090688aaf186018ab54dc31406c5ed003d41964ed686661a0548afa52fafe4940af3fe8f834b973325557e5444a1a1295825083148e4dd4a83b89493d4453d0041e2220ace0bdbaed4a0527fd430eebd87e09cab4b7f1aa47d556c55cd90c1d5495824828053001");
        ksCookieSet.add("clientid=13; client_key=f60ac815; kpn=WECHAT_SMALL_APP; kpf=OUTSIDE_ANDROID_H5; mod=microsoft; sys=win10; wechatVersion=7.0.9; language=zh_CN; brand=microsoft; sid=kuaishou.wechat.app; appId=ks_wechat_small_app_2; did=wxo_6b79e0eb6cf1f9aab9ea4675d738e77263b2; session_key=12304daf18c6d52b53320e69dd1d7d49821e23dca92222bfefa14b5c11f807d978e486dc68254b04a0b6827b61e74e1e87091a1250f4124831ff4b21bb1407aa4b03ce52fda522209333b1dacf7ceca9bafa3151399c105747d56bd9810be345a1d9aa21e7eff67428053001; eUserStableOpenId=12309345491a8cdc4ce4aa33348c6b1de1ae12c1b63172940dce6061561808ebcf849ddf1f14fdf7679a387bdf76404d8b3a1a1230b7186912344668bc4685735a9da9d9724a2220804b908423c49ceaa970554c0aeffe8c82a533e01c70eae1c99cd130c0094f5628053001; openId=o5otV47nUGrsUyrTwM6PVwYUiusA; kuaishou.wechat.app_st=ChZrdWFpc2hvdS53ZWNoYXQuYXBwLnN0EqAB2Ev52xDLOPKAQz0CnGrX4vdHTBNBnWcLKkU6KXetYweke9FHRkkPxAB3MXjNA_2Q7vxQ-_lfJUCDerQiQ7mZPMN8BhpsOJUozRVhCpb7v-hyhLRc5rHWiAe7KuRuVssfIQ4OEJ--lF3fbv1RcbgMZROTJKI9y8KbJT1Igyi9QKOuKhX8YPfDVn6G20-y3BD6a6HRQSJL1531nMhHMukJSRoScdPXBtqcRdupjj5ImnWVHvLXIiAUVx38_gsYbQiVoDxL6dlGOptVvInyEXga2gHofrtfnygFMAE; passToken=ChNwYXNzcG9ydC5wYXNzLXRva2VuEsABqC0yiySUAqwZVFZni9sBPcXpcik9flzYJfvsvYGvHhEKYRPGVwwKpj_-VdYZHwKT5hzMxc1glHWpL3UvS69yh7ONjUrLooqyiePBfs3dheI287ffdFN6KqpoKbc3WvbvFxqpg2KfPuwnqZvdGoFV-m1y154cEq8BOzUTmlDexfo1O7diMpSzlKptrNKr1erKXo3qrzTB4Ff8tJ-tkd5E0bGystNuWtCqH3tVtrTedm6orgiKgVCfcH0YAUoR7u50GhKKuu7N4whAZLzzxMcR5srWdDAiIHXt5DNPv2Pi0DAaD1boXN53AZsTQXZ3flQdLtiXm4o7KAUwAQ; userId=834536258; unionid=V2:123098d072cdf5775d4dfc28ca4e0d7b7ad8d91b23936e51c012b17bc247c9d39b3a8f952acda70750654f1d080dbc54855f1a123da33778aeab416eaaa321e76dd76d461cb82220be3979c3d99c0357b0cc178982e86897d6969da26ce925da62eec43196e7a38328053001");
        ksCookieSet.add("clientid=13; client_key=f60ac815; kpn=WECHAT_SMALL_APP; kpf=OUTSIDE_ANDROID_H5; mod=microsoft; sys=win10; wechatVersion=7.0.9; language=zh_CN; brand=microsoft; sid=kuaishou.wechat.app; appId=ks_wechat_small_app_2; did=wxo_5c82d61e3714f97edfed283e733e2c7d423e; session_key=1230bb1e44bb6dcee609354bfa98782e934b551185ff4639689322beb087f8ca4765df04200d9525522c185d0a5ed2fa82e91a128fb4330677da412ca8433be272d2eef588802220b248a626cd36e8e16acb0dbfb794475008f795cf0b9d4091fa02b1c2b199f6a328053001; eUserStableOpenId=1230bd7811f418fc88dc57b12d77abb7cd8eb66d83eed998f48b7f6eb701c707582fce110884a48b7c9630b55d8debb687271a128f407510d11e45cb99d205ebfa31dd81e8db2220f624b93e6e4698408c028c18ba24bfd96dd730503445d1e4603eea007c6ceb7428053001; openId=o5otV46Te6jhANrqGKNdfVxEQaOc; nickName=PROFILE_SERVICE_ERROR; kuaishou.wechat.app_st=ChZrdWFpc2hvdS53ZWNoYXQuYXBwLnN0EqABMQEdEfnGOna7-bNbeF4Qjf8RWdAYZ4hOYSakEB4TpHiiidlN5N89oQLuuAsCTXNrpV7mQ7PA1Owq6SLa5I2oe4PpdL9qXKTSNcU_53e5taM1bGCpu36vz3Iwp5HrdGEValBEs8lsmDGNsECYhJkRldpbP1jsqMgjvmipL3FmEYAvWBuNhWm5gzUQOxV6Tw2KOtgLuxuZvAPeg5tVTUa3BRoSonnoARDeQ2W5Twulr3gHJzCsIiDNJ6DBiVh2ANzvrEw9cKUTQ6qRY6ozL7vZ-I5BbsO2bigFMAE; passToken=ChNwYXNzcG9ydC5wYXNzLXRva2VuEpABPWPBr27Joh7FVs83v6BbBgeobCpcKpFy6Zk7QS7XIxUCxjOPdw5-c9bM6bIzLyEMsXJRMNfEtpJeze9KshlxCRon21rTBLnlSMK5MGY--CbNExoHRYtDp9ZWM2TP_IxxfV-ZkbKnXXIbH4_fgbuJyyvvV4W4NliaG-gRP4J8-wvV8eYDLPNlgoqx58rDb8RSGhJFAfdliwZGuJ7Wv2CYERbLbMoiIKlMKdROtC-gboC84Olj6c1AQGFWuRzeoSlJpekhwODtKAUwAQ; userId=2033064784; unionid=PROFILE_SERVICE_ERROR");
        ksCookieSet.add("clientid=13; client_key=f60ac815; kpn=WECHAT_SMALL_APP; kpf=OUTSIDE_ANDROID_H5; mod=microsoft; sys=win10; wechatVersion=7.0.9; language=zh_CN; brand=microsoft; sid=kuaishou.wechat.app; appId=ks_wechat_small_app_2; did=wxo_2684ef11137923b3bba207530c1978277b2c; session_key=12307da33b6e2fc1259f9aa1030a7919e386b957e5bac06f36054c3a019b0c55d5c656c7f5c6524fde91e218984053254cce1a128fb4330677da412ca8433be272d2eef58880222046082dbc2b268576cad861155fcd3424840a543e25a295c9e1f0f7ce794a011c28053001; eUserStableOpenId=1230984d353c9b3a9fb8fa065ea27cfc606e09abbcf49aeb5b5d8bf63eb732bcb47d509b93ab6736f9ee9b2f99726a1bd9771a127fc8232c423947f6b789399f3798fde6add722204a5e8abcbc1f49d3d015d2a882a8cf2d40bbb69e2375e192d5f84ff2be1b7b6d28053001; openId=o5otV40bUqDtrwFgSXyCj3fWPdus; nickName=PROFILE_SERVICE_ERROR; kuaishou.wechat.app_st=ChZrdWFpc2hvdS53ZWNoYXQuYXBwLnN0EqABWIPP5ALa77BxinhhhK4_L4Rj8kEycXuzlPTKAzwyBvyoervkul308Ndai1oSsuPDGpmlrLlmO2B4HNrSj59jffMtf-E-DxiQTep9KyDeeLUkIL47iIhogDWj81NcGuSdHE6L1x6o1BdHwdu-1WrQmYpPCa23B4yj4FmrUcB6iHYsN7bl-_c0QJi4YEIGMnyd2Li2UeX_YhuwLAISCFVo6RoSJkFLJVQQTW2OZOPxyRanPl1JIiAj2uW9qc6rp5wPo_UtvRiTRJn30_oWF6oBif1aHwNMQygFMAE; passToken=ChNwYXNzcG9ydC5wYXNzLXRva2VuEpABH5g__TkHgmbaxj6esPudzSyPNY20THTiVI6jUk_7J2xX_ChgwsrNw4oVGr-SdpUbq--oZJwSgscAYQl8e1VEjo2F_c5rPFDji_iRiO85Cr1xG35-dXtrwsOiylsnck2M8XrUiToI0I1M-4EoJJMOX4mDNy4Rd-KM8QQkki3j8wxZ0_uaax8ItSn7vdAmbqqqGhLmcxJU_iJJ_bzbaUG-W3aMOLciILrPlE3kCBcdf9V9ha5ihpBwutTpRmFJuYzmV99EfdsCKAUwAQ; userId=2035012919; unionid=PROFILE_SERVICE_ERROR");
        ksCookieSet.add("clientid=13; client_key=f60ac815; kpn=WECHAT_SMALL_APP; kpf=OUTSIDE_ANDROID_H5; mod=microsoft; sys=win10; wechatVersion=7.0.9; language=zh_CN; brand=microsoft; sid=kuaishou.wechat.app; appId=ks_wechat_small_app_2; did=wxo_833e4297c1fe92990d9e7d0beb3a819e7324; session_key=123056695c81531a333f493107a3274079bbbf311cd4ff11fd1f4e218a7036662323f82ae00595731db28c06b1ed467bfde61a120329d922ea3f4e91a87a3014fd7b1e7ace5422201cec3ed1ffe9ca10c8ff33482070d1eb3c42d8a37204a2da314d2a771f3ae1eb28053001; eUserStableOpenId=12308bd2ab75983661f4b76d95ae07d6904a95bbb4d16c64ab7296ce809248b3fdfbba485e367667cc3cbd06b89ea56becfe1a1230e38d22b11d4389b2c7b098321abf5fb64d2220f771459dfefce079aad999a2a6d5d57e7579cc88c3a8668591864e1e4669363a28053001; openId=o5otV4wZ_Lv0txB1lrquvaIZNKm4; nickName=PROFILE_SERVICE_ERROR; kuaishou.wechat.app_st=ChZrdWFpc2hvdS53ZWNoYXQuYXBwLnN0ErABWAmU5aO4M0XKrY5j77hiV0jSLEovrGgxMfVeDdcJ6f1lfuCEtL2bYtHatDqy9sniRhuW7R1c9bM0XfZO3dCI8UIJyOdFkg0VZg7aMHQAFl5nMGxtDF4TB52tZKJ4JaXiD1CUy389RQTRuz4jKqapzLKFgrVARScVPpsKUaajdZu5osQvnvAQeBPEHDGq_VWElITJocYG2aLphO5zf3DK9BE3TQE5uFa7MYa1B5Jmq9EaEuUXT3K4aU1mmXtb0-EB81drIyIgKT-9biu0H7XosAqcHyLUDdyAb5_Qg_QoReDnKTK2v1goBTAB; passToken=ChNwYXNzcG9ydC5wYXNzLXRva2VuEpAB9WW8MAcupjJWfvJteoraTqC8BwmPiwJu2_c_3u406iH_g_Da2LcNUo-cmcZqruOmeLvxtPZJQMe4Y9Cfb7kTJGFdV9vuwnE_5oUItuyzm8DeBGIWPybZVhODnHgumrJ6Bs-w0UArO__dD5Pq5sJBMgdVjsVt5aoWXUXf1tyk5j_gqs_cXHvPcv3f1VNRirfbGhJtYO6YtmpASpQwAdvDtVmKVRAiIFkYmY5Bd1GbOVANxmtipQWvKzUyjZj0mZEckXQ0DP_mKAUwAQ; userId=2035184902; unionid=PROFILE_SERVICE_ERROR");
        ksCookieSet.add("clientid=13; client_key=f60ac815; kpn=WECHAT_SMALL_APP; kpf=OUTSIDE_ANDROID_H5; mod=microsoft; sys=win10; wechatVersion=7.0.9; language=zh_CN; brand=microsoft; sid=kuaishou.wechat.app; appId=ks_wechat_small_app_2; did=wxo_d78f820b83d42e3120d2e2f6e73374ad1fe4; session_key=12300ac9acae84d0e2fd78fb46cae608682bea5d9f61523287af5504d537524ce48e5fc75bfabce66cc2364d007a193dddc61a1230b7186912344668bc4685735a9da9d9724a222097508f145a3977bae5ec1aef2eee5cc42f1d1761aea5e526923427d04c27bd8328053001; eUserStableOpenId=1230a86d7230f5926d33ae650408f76c6eef1da1a1a8bd5a19edc643e4df8552381b313ea69108d4aa6d7c9400b3947a7b371a1295825083148e4dd4a83b89493d4453d0041e2220322adbd07ed45b19790fc70a25bf8d67f06a5ffbb35fe4374beea27689731dab28053001; openId=o5otV44CEGyLQPuOyUThJJivPUFg; nickName=PROFILE_SERVICE_ERROR; kuaishou.wechat.app_st=ChZrdWFpc2hvdS53ZWNoYXQuYXBwLnN0EqABsmvl7RxZkeZi-qZW7jSEEuXiMhUZqXqUz2kfONv3CN6D9vqKkrrRjVM__B0dFf5JU-FZssEzdet8mNMQ4wicG8gtE2dD7ewk2aVX45nVVX2NBrLgTtedhIDwYVCsoT5BX9412NsQKaid-1GXm8DOlFg2K41KLYszodnQCcFHgL5Iy66cfyplpHvXk2ryUjkQZaQqlSIoUcyFDOG-zpq1dxoSzyg3CbJxSLKB-98q6heUeRt5IiCKlaGrEsmDOiDiRd_Z8XU5Tj_EACguSFu-G-WFABhN0SgFMAE; passToken=ChNwYXNzcG9ydC5wYXNzLXRva2VuEpABFW1L7oUzMC6Vw_OCFNGXLegTRI_1nRxeavRSkHBR9WH8dq7aRW_6mRG4zueJS8vvHZ2bTD-TZXLrleNHqQ3FFoGMqN5KE9Wa-_6f1rVJMSkhAkDUXs7reMJQ-S74bq3j2bSHEgYBnYqYmiVVaPWw56xHL-nh3YiFjeDp-Im7ZUQ4u1mZe4zLGj6lPyj_ZZXOGhJtYO6YtmpASpQwAdvDtVmKVRAiINGfKE12ZeW6lB9XB0YdRAkHfiecj5feMpP1f57KJ6FdKAUwAQ; userId=2035179042; unionid=PROFILE_SERVICE_ERROR");
        ksCookieSet.add("clientid=13; client_key=f60ac815; kpn=WECHAT_SMALL_APP; kpf=OUTSIDE_ANDROID_H5; mod=microsoft; sys=win10; wechatVersion=7.0.9; language=zh_CN; brand=microsoft; sid=kuaishou.wechat.app; appId=ks_wechat_small_app_2; did=wxo_0ecf7c74632f7dbed41e2a4b6c06b6e581f7; session_key=1230277d7c2b71cbb21fc617a9a3a389380d6d3926bdc7b65b3a3840723a8cc83b305ee0f9b3a8d3c9ed176abb9f15e58cfd1a129ea034f7525d4a5a84dede9c4d20c07375b92220a2b82f66ac6e2ccb56621e0544b4821a5108e061005fc64392cb6764ad915c8a28053001; eUserStableOpenId=1230cb38565540e1c17d55023024d3c273566267ef8cd5234171e15205f811da3f97cc7ec2eec03d0e85f1be47830cf4b3731a129ea034f7525d4a5a84dede9c4d20c07375b92220ee4f39b33851f989ecdbb7004dfcf211cddafb2a39a67b29ed53f4d6b612b09128053001; openId=o5otV48zhOQprNIAwoqp1UZeMdXo; nickName=PROFILE_SERVICE_ERROR; kuaishou.wechat.app_st=ChZrdWFpc2hvdS53ZWNoYXQuYXBwLnN0EqABrFiYRPUOnSM5DdYRfJ0ojFg3uT3DnES3ggfk4nlgXZLydzdXM14oL0hHYMXaFW7akp6y3HaW1XdhbB8yJROXAllTFwN57DKOX1t3-G4mwCEdywVtIH2NfBLbCdDl-56_bcJpER93k-7xsPwQ1G_JHCwwKVP7zZeHq9lN5NfG8UJLrKg38MrEGmwi80seATLKyN_QhP_qA1NncckQ0ok0WxoS0skWqH-TRx6uxsd-ptkCqRm8IiDHRDJ4CAt9VABpku8p6haibLq4YRYxg3uoRsWX8xESlygFMAE; passToken=ChNwYXNzcG9ydC5wYXNzLXRva2VuEpAB9ASnGbvv5z6SIB35FhYMyOTzcPkSzwxT8KEMyN1qxycS7JXqYR3i8XjYqWbEICake2KsiIKG2dQZ8hFxKrbX5Zey_KLm5sdY0Pcb9mgvz9JSGmhprhNDD2oEOCQEnP6KUzo9Ihnr6KuSdQ9NnHkvIe_aWSSZBmjd7E3un_wOi4q-TRSxcym8H6HUuJ_5jopHGhL3QnOa8TtA6YtcaYvP-QtxljkiIL6tyZ6PyY_2iIu3pdlCbkYf54f3KIWmaX7H1BUb3v0aKAUwAQ; userId=2035210254; unionid=PROFILE_SERVICE_ERROR");
        ksCookieSet.add("clientid=13; client_key=f60ac815; kpn=WECHAT_SMALL_APP; kpf=OUTSIDE_ANDROID_H5; mod=microsoft; sys=win10; wechatVersion=7.0.9; language=zh_CN; brand=microsoft; sid=kuaishou.wechat.app; appId=ks_wechat_small_app_2; did=wxo_04d0cf4ecd54a511d5cbb49b019c218ad517; session_key=1230f46746e0c9a65025bb30d3218daed0cdc5440b09440be6b8ac468ac4a0ab1952c4899253b2f957367f50d0d58317e0d81a12da4c4c26181d432b873ec39432f410196c6c2220556852e170dd1c8f448cd37b64f3d622ff9fa1ef9f0384a208b952ea14b2cdf328053001; eUserStableOpenId=1230be48d02cbf9a273af87a9fcc021415b520005bb0a0b73fe158582c9067401ac4d3410501472bbfb2797658f5ce299b9c1a1245b7c8dae5cf46e1bf526a8341fa77b603b6222047775c6149e8c54c6f1a0f647f2ab2a06dc08cacd755fcd7f45fc6b771523a9c28053001; openId=o5otV44wadgyyb5NkwZTdrJWmE4o; nickName=%E7%BB%8F%E6%B5%8E; kuaishou.wechat.app_st=ChZrdWFpc2hvdS53ZWNoYXQuYXBwLnN0ErABRrlmCYQ85LCm9rK2Q72AvzycBOi6pmFjzOif2lLrQ_kH4wINakTEJ1VP309gqDS80RtxpaHcahsi1iNHFnllfPeoxLyTuR-KOJKT2UP9avfoHqNzqPx0v_KR016CF2gbipMKPWsbQUvU0PFHch9qXb-TuaH_kzTzmhprNaZTqNl6EdiQ7iwOlGVuLTDnh3GvNfdyCjBRCmIrsq-_GrF8kuiYyRh-hK_QsueliSGNlggaEnK4IIZkp0milVxxErFxRujnLyIg1gEtVsMhxxDYsKh2U5rPl0bIIPhuluef2OCmIkuDBOsoBTAB; passToken=ChNwYXNzcG9ydC5wYXNzLXRva2VuEpAB_Gkn7zNXfCd2obtvMsTgThGXa-dTeGqpCHLNK9Sywv4aumvtgbIy6dR6jPUP-b6ZwkRnxikjmT3fAoyfChgfFTpuMZ5oTCRYgF1CrdtiAflSSZpOehCILomaz2sTP22u0NdugMFGdEOIvraN70TnlDz0zMdogC1Q5pa5fa3-3F7HwUAS2C2D_N76HnCNZ8TzGhL3QnOa8TtA6YtcaYvP-QtxljkiIOZ9L4woNdbvSHFLshG2EGzffgzWBmz7RXsyj2Pz0gwHKAUwAQ; userId=2035312858; unionid=V2:1230e89cb64344e277d85a1b6b8afa65d2cfe50fd45cc581be5b923dc44c0536bb24e48925427c40f869d366b98e265483cb1a128fb4330677da412ca8433be272d2eef588802220217995500c3a793deada646229edcad29742ac30b1b7e52212f170791336ecc328053001");
        ksCookieSet.add("clientid=13; client_key=f60ac815; kpn=WECHAT_SMALL_APP; kpf=OUTSIDE_ANDROID_H5; mod=microsoft; sys=win10; wechatVersion=7.0.9; language=zh_CN; brand=microsoft; sid=kuaishou.wechat.app; appId=ks_wechat_small_app_2; did=wxo_6711b4d4a8c14b457e4d1fd004798219d0b9; session_key=12307f6483d33d12cea7a06864ee2873d5a2af5b261f6bc86cc51ccb5ac32cbd6b9df20840196c8ca10dd29b5eeddda000941a12c8a668b8133f4605b963494bdaed34c5f9f9222012aa153720ef0788c67f0f1dae76590b01758f3eb2f4eb264375c4bfef7b780f28053001; eUserStableOpenId=1230204bfc4e72913b8622a5e97aff1fb510109df1381e56b786358d366808661f214c4790a0f7c0700c186cdd70c45fcb021a12aa8cdc0df0ba428ba5e37e6c5d99bd75839b2220644f8f8e758329abea9ef83ab53fa36df86beff2b4b212cbc89b1aa4f8f9ad9628053001; openId=o5otV4wUE7D-AiP2nuE1wm3bfSIA; nickName=PROFILE_SERVICE_ERROR; kuaishou.wechat.app_st=ChZrdWFpc2hvdS53ZWNoYXQuYXBwLnN0EqABGnczk5BaY7mXwK39AQXGn--DMOht9kesRpq5gwuF2LLpfdCzfbcVAuyJmRSuiCClUbLWT4Eiuib6X6zeIQ30W_ZYQt1ocwv-3IZirxO2S5mHGAk1vehWcDqPLLgFKyqnP7Frh2W0nQBroEy4HX-8DJKKI2bIwBj5R4GBpgsMiqdQZKoqDjFD52cpPQH662m5kpVn5qvxqWulcxAQMjoi1xoS8LEPhLmAQ3SoR2v1MQv2OkreIiDbfgjR9j-NYUCIwlZeton6SJebxvWKDKtYuAkE1m-6FigFMAE; passToken=ChNwYXNzcG9ydC5wYXNzLXRva2VuEpABJDcEAv4jXAvN5gq4hPTHfYyuxE7BW0x8pUcmJ5uhSJpNNj0Bi2py-BZchGCzruyhQ_mAgNfB1DoNkhxoTfP-v8twCIuJoXZTeZEo59OMffqf8PbwAtrRJJZlM9Dg2OKy2R_B_qrXzHTK01qWlTSKw6MUV29YyM4aTLydy80GWBIe3aV821rxHAlel4KklKZfGhLTMI_Or8FFNK1uPHroJkDWiPUiIDFsI7UzAss1rEPQBQhffRJ25s8p3FthVMTLP57VnyhnKAUwAQ; userId=2035315128; unionid=PROFILE_SERVICE_ERROR");
        ksCookieSet.add("clientid=13; client_key=f60ac815; kpn=WECHAT_SMALL_APP; kpf=OUTSIDE_ANDROID_H5; mod=microsoft; sys=win10; wechatVersion=7.0.9; language=zh_CN; brand=microsoft; sid=kuaishou.wechat.app; appId=ks_wechat_small_app_2; did=wxo_08379c4f2af27262227b09008969b85c53a1; session_key=1230e5bff14e81c0e8a38de96be2e3d5ef5e8d8e1d8828fcbaa2e5582d623f7171836dd5d0c3ae42c90c17149b106818017a1a123da33778aeab416eaaa321e76dd76d461cb822205e9f81e36ccfc1caab80a64e21f86dd6b946ed005bd4eee0e661a8b0736e179a28053001; eUserStableOpenId=1230a8fcedf55ff29381448127ed970d8f4590801ab642a34f3e15ea495376b93c229dd4a8f5f1e8c607c5ca8e6b57b07e261a12578aaf78a3674cbaa58ecb218168caa24bbf22200df31c96574b6ec71baee47d0e7b444cf6f033cdbe66c502621086f7a2ed224728053001; openId=o5otV49O7a9xMPgNIw97UJcbSXlk; nickName=PROFILE_SERVICE_ERROR; kuaishou.wechat.app_st=ChZrdWFpc2hvdS53ZWNoYXQuYXBwLnN0EqABGEG-4-KNAmlWTOXnEuwvW3wHHEtZ8hNbR6EGHDrBJHajWmeDe8hujuRGVWCqkjuRapNw9gqMjQteRNvaxwOry4eBf7sAaBnc_Nig7w6tXllMvhRFZGgD0dGShTjqXKZscqenK4x0sSwNR7OLnwKJX4shtsS-KCfYXsW4xc4gOJlWt1l6NMdzwgWO3p6JO-kh6ptWta5Ig4O-Ar0wWvLdMxoS0skWqH-TRx6uxsd-ptkCqRm8IiD8ir5T6vupHK4p34VwRNF_rHCRS0cwRMwt3LrCbKHoUSgFMAE; passToken=ChNwYXNzcG9ydC5wYXNzLXRva2VuEpABvpGm47hcDRPEmetB9PXi5nPi0vtkEPkFlqkk7aGkmlIWjkgdEWZn0G2SdxmLg5n-gEX7fbbTGb9oo5em9YBsmUw35SbO65_J4n59fkTBP5uRQdoJrBrcYP6qXDpp9Q0lmNlSezCEkvgB3MbTEO6bHoJT2vBzGkcxR72r2Z6ewI8ZuaKXYfOK8hSkAb3t_kfWGhIJBRfr_qpOzqtFszTciGp_TnYiIPHcWxcdz1bpKYCcI1xfEXx2tmL-nYqfugrAMu0ZzTB8KAUwAQ; userId=2032916667; unionid=PROFILE_SERVICE_ERROR");
//        ksCookieSet.add("clientid=13; client_key=f60ac815; kpn=WECHAT_SMALL_APP; kpf=OUTSIDE_ANDROID_H5; mod=microsoft; sys=win10; wechatVersion=7.0.9; language=zh_CN; brand=microsoft; sid=kuaishou.wechat.app; appId=ks_wechat_small_app_2; did=wxo_339d0313fe47e5d7181a0eda49b4e76b7537; session_key=12307aa08297ec15a00ac957695ff29f5df50feb7f4653eb616db7678212c31deef02eb4a4419d9bef949130205780790e251a128f407510d11e45cb99d205ebfa31dd81e8db2220a3e67bc804af328ca65c7b059b95dbf19e7cf574d4f5d4702c3e87de5260d8da28053001; eUserStableOpenId=1230fbe6d042d8e4eda7032d2a40c75ec34322a60b2d8abf9012945bf75507f0e7bd69838fa73a1b902e00a3ee9bd6aa75861a12bb92bd9c2290489ba7a733708a4a446de838222001c4f506d0b9a3d118c83a54a25c17181f19f17d5ff1ae9ecbc622ff2f6f2dea28053001; openId=o5otV49o6xB5ruqDAz7qezgT4tZ0; nickName=PROFILE_SERVICE_ERROR; kuaishou.wechat.app_st=ChZrdWFpc2hvdS53ZWNoYXQuYXBwLnN0EqABhb-srL3sjQlFf4psUgdWWBlp6vM_5NO9IamsJxM3wDJQaRwjK-TGyrVxzCxnQYEX1SfcFJlOelHvKwpDE_Jeia0ht5LdaiGF3H4kdjujkVLizdeB9tAZTVVaL5q8kd3wDrVoDQPB3pYNweqHj2Z4YPsg7xWIyQR_xvPYsQO6jW9oH4mWNM1Bg7FDJcmsq8X-4WvQZWkxHDDKgF1QmmoVjBoSJkFLJVQQTW2OZOPxyRanPl1JIiAw3SS--1WbvZejOJlybFGEL4YkHabBB6ZDAZwM1d1O6SgFMAE; passToken=ChNwYXNzcG9ydC5wYXNzLXRva2VuEpABPzOra6S3fOjN_AuDncaYXPRdjNzuOufdmcF5VqUH75eAShEbciDtK7PCYZkNBZFRmd_TWuwZLL3XonyE0ATEbQlTZhgSSfyWraBMTfdKFyWQMyvnR5HmIigf48bA6U2t2geLGK78I6S7LpnAR2XDXj-EzZ9WD-LkgTnlH9_ySAZnE-DnXqoTq1pIHQLtQA_oGhLmcxJU_iJJ_bzbaUG-W3aMOLciIJNMGvQXFBkGeNSQaJj4FJHsxf471f8dbFeePR9AuxMSKAUwAQ; userId=2036304352; unionid=PROFILE_SERVICE_ERROR");
        ksCookieSet.add("clientid=13; client_key=f60ac815; kpn=WECHAT_SMALL_APP; kpf=OUTSIDE_ANDROID_H5; mod=microsoft; sys=win10; wechatVersion=7.0.9; language=zh_CN; brand=microsoft; sid=kuaishou.wechat.app; appId=ks_wechat_small_app_2; did=wxo_e6fda152e4ef8bdf755807fa8b268c6a9772; session_key=1230e0b3321f0ca43a9e9b548fa8c71365f5384c05198f534865e4ce7d04b1c58186dc1b5c8a4ee29ff7d6bb6f242418ec6e1a1230b7186912344668bc4685735a9da9d9724a22205a69b139d5c8c9c3bbf87dacd3fa937ecd82798b9c9fcc9094f67423c4bded4e28053001; eUserStableOpenId=1230d30e5c2cf64aebac05f11de547808c699c283f16434949a76ad82963a0d1f66bf5d3937a7b845714f21a87285179d97a1a129df1be81f6bd42648be78e2f1e3b51c82bb922202f1746e88362e0580252438aa8a2715920e3670c0643d939242ad0c9720ab3f628053001; openId=o5otV46Xh3q-pmfbbJ9D6y8i4FwM; nickName=PROFILE_SERVICE_ERROR; kuaishou.wechat.app_st=ChZrdWFpc2hvdS53ZWNoYXQuYXBwLnN0ErAB5WwZBLeztAfhOUDe5IULI3ed0FPV-xeO2LUaKLu_agcSFbcagWgWlrARGlWbkiYM_4wENt8xZUlULg1mv6tYSkq8CvrkKC-lftsvvqEufCLYMEMRFh97bW8ce_G6ty6TnSpzZaVVVIDNcI9GTUjGGbAcqtdY7hCoPJ2AP-vU7oYtirHB0m0zYYy7o1A24LgHevUCmraKVTIv_jxM7mODUsen9M8vwwZx72eIqloPQysaEm7bo76xjUVmm4DF2I0g-mmVISIgUqu5EQHNXn8YUDNtKkAAQNZYUNWMUVDue5hoBU8eeZEoBTAB; passToken=ChNwYXNzcG9ydC5wYXNzLXRva2VuEpAB1UJtnUsnAAkubEulPu6XgD9Tej1LjI7sHQdXspsapyVrQXRP8A_oV0GhYM9q_9lItdq68_tTN7xX47wJ_S7gmHzAJ8OsxKP3e4WI9AlFDe1GXLb1Hl0pr3tF086mCo9fsgJpYcqvag5GIPcWy0e5m4CBMJjcl_Bno9eHPTEi7FYL7CyXxEynsxCjoSp8prRUGhKKuu7N4whAZLzzxMcR5srWdDAiILpQ2qxznB-jOIuzCTs4NZ5vH9joTONH7m9_31jRQlhjKAUwAQ; userId=2036309399; unionid=PROFILE_SERVICE_ERROR");
    }

    @Autowired
    private RestTemplate restTemplate;

    private static String getKsCookie() {
        return ksCookieSet.get(cookieCursor % ksCookieSet.size());
    }

    @Override
    public synchronized KsSearchFeedResponse searchFeed(KsSearchFeedRequest ksSearchFeedRequest) {
        try {
            Thread.sleep(5000);
        } catch (Exception ignore) {
        }
        HttpEntity requestEntity = new HttpEntity(ksSearchFeedRequest, getHttpHeaders());
        KsSearchFeedResponse ksSearchFeedResponse = restTemplate.postForEntity("https://wxmini-api.uyouqu.com/rest/wd/search/feed", requestEntity, KsSearchFeedResponse.class).getBody();
        if (CollectionUtils.isEmpty(ksSearchFeedResponse.getFeeds())) {
            emptyCount++;
            if (emptyCount % 5 == 0) {
                cookieCursor++;
            }
            log.error("失败一次");
        } else {
            log.info("成功一次");
        }
        return ksSearchFeedResponse;
    }

    @Override
    public synchronized KsSearchUserResponse searchUser(KsSearchFeedRequest ksSearchFeedRequest) {
        try {
            Thread.sleep(5000);
        } catch (Exception ignore) {
        }
        HttpEntity requestEntity = new HttpEntity(ksSearchFeedRequest, getHttpHeaders());
        KsSearchUserResponse ksSearchUserResponse = restTemplate.postForEntity("https://wxmini-api.uyouqu.com/rest/wd/search/user", requestEntity, KsSearchUserResponse.class).getBody();
        if (CollectionUtils.isEmpty(ksSearchUserResponse.getUsers())) {
            emptyCount++;
            if (emptyCount % 5 == 0) {
                cookieCursor++;
            }
            log.error("失败一次");
        } else {
            log.info("成功一次");
        }
        return ksSearchUserResponse;
    }

    @Override
    public synchronized KsUserProfileResponse userProfile(KsUserProfileRequest ksUserProfileRequest) {
        try {
            Thread.sleep(5000);
        } catch (Exception ignore) {
        }
        HttpEntity requestEntity = new HttpEntity(ksUserProfileRequest, getHttpHeaders());
        KsUserProfileResponse ksUserProfileResponse = restTemplate.postForEntity("https://wxmini-api.uyouqu.com/rest/wd/wechatApp/user/profile", requestEntity, KsUserProfileResponse.class).getBody();
        return ksUserProfileResponse;
    }

    @Override
    public synchronized KsFeedProfileResponse userFeed(KsFeedProfileRequest ksFeedProfileRequest) {
        try {
            Thread.sleep(5000);
        } catch (Exception ignore) {
        }
        HttpEntity requestEntity = new HttpEntity(ksFeedProfileRequest, getHttpHeaders());
        KsFeedProfileResponse ksFeedProfileResponse = restTemplate.postForEntity("https://wxmini-api.uyouqu.com/rest/wd/wechatApp/feed/profile", requestEntity, KsFeedProfileResponse.class).getBody();
        if (CollectionUtils.isEmpty(ksFeedProfileResponse.getFeeds())) {
            emptyCount++;
            if (emptyCount % 5 == 0) {
                cookieCursor++;
            }
            log.error("失败一次");
        } else {
            log.info("成功一次");
        }
        return ksFeedProfileResponse;
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36 MicroMessenger/7.0.9.501 NetType/WIFI MiniProgramEnv/Windows WindowsWechat");
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add("cookie", getKsCookie());
        requestHeaders.add("Referer", "https://servicewechat.com/wx79a83b1a1e8a7978/231/page-frame.html");
        return requestHeaders;
    }

}
