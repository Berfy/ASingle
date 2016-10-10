package cn.berfy.framework.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Berfy on 2016/9/1.
 * 全局配置
 */
public class Constants {

    public static final String XML_TEMP = "temp";

    public static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(10);
}
