package com.touchkiss.catalogueoflife.spider.base;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2020/04/16 10:31
 *
 * @author Touchkiss
 */
public class ThreadPoolExecutors {
    public final static ThreadPoolExecutor pool = new ThreadPoolExecutor(24, 96, 1000L, TimeUnit.SECONDS, new LinkedBlockingDeque());
}
