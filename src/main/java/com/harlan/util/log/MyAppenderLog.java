package com.harlan.util.log;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Priority;

/**
 * Created by lyyao09 on 2015/10/14.
 * 定义自己的Appender类，继承DailyRollingFileAppender，改写针对Threshold 的设置说明（重写针对级别的比较方法）
 */
public class MyAppenderLog extends DailyRollingFileAppender {
    @Override
    public boolean isAsSevereAsThreshold(Priority priority) {
        //只判断是否相等，而不判断优先级
        //将日志进行分类输出
        return this.getThreshold().equals(priority);
    }
}
