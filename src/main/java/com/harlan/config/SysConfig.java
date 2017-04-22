package com.harlan.config;

import cn.dreampie.mail.MailerPlugin;
import com.harlan.intercepter.GlobalVariableInterceptor;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.plugin.tablebind.AutoTableBindPlugin;
import com.jfinal.ext.route.AutoBindRoutes;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import net.dreamlu.event.EventPlugin;
import org.beetl.ext.jfinal.BeetlRenderFactory;

import java.io.File;

/**
 * Created by Harlan1994 on 2017/4/14.
 */
public class SysConfig extends JFinalConfig {

    /**
     * 配置常量值
     *
     * @param me
     */
    @Override
    public void configConstant(Constants me) {

        // 配置开启开发者模式，JFinal将会对每次请求输出报告
        boolean isDevMode = PropKit.use("jfinal.properties").getBoolean("devMode");
        me.setDevMode(isDevMode);

        // 配置beetl渲染模版引擎
        me.setMainRenderFactory(new BeetlRenderFactory());

        // 配置404、500错误页面
        me.setErrorView(404, "/errorPages/error404.html");
        me.setErrorView(500, "/errorPages/error500.html");

        // resource/upload/filse
        me.setUploadedFileSaveDirectory("resource" + File.separator + "upload" + File.separator + "files");
    }

    /**
     * 配置路由
     *
     * @param me
     */
    @Override
    public void configRoute(Routes me) {

        // 配置自动匹配路由
        me.add(new AutoBindRoutes().autoScan(true));
    }

    /**
     * 配置插件
     *
     * @param me
     */
    @Override
    public void configPlugin(Plugins me) {

        // 配置数据库连接池插件 — Druid
        String jdbcUrl = PropKit.use("jfinal.properties").get("jdbcUrl");
        String username = PropKit.use("jfinal.properties").get("username");
        String password = PropKit.use("jfinal.properties").get("password");
        String driverClass = PropKit.use("jfinal.properties").get("driverClass");
        DruidPlugin druidPlugin = new DruidPlugin(jdbcUrl, username, password, driverClass, "wall");
        me.add(druidPlugin);

        // 配置表自动绑定插件 - AutoTableBind
        boolean isDevMode = PropKit.use("jfinal.properties").getBoolean("devMode");
        AutoTableBindPlugin atbp = new AutoTableBindPlugin(druidPlugin);
        atbp.autoScan(false);
        atbp.setShowSql(isDevMode);
        me.add(atbp);

        // 配置缓存插件 - EhCache
        me.add(new EhCachePlugin());

        // 配置邮件插件 - Mailer
        me.add(new MailerPlugin());

        // 配置事件插件 - Event
        EventPlugin eventPlugin = new EventPlugin();
        eventPlugin.async(5);
        eventPlugin.scanPackage("com.harlan.listener");
        me.add(eventPlugin);

        //定时任务
//        QuartzPlugin quartz = new QuartzPlugin();
//        quartz.setJobs("quartzJob.properties");
//        me.add(quartz);
    }

    /**
     * 配置全局拦截器
     *
     * @param me
     */
    @Override
    public void configInterceptor(Interceptors me) {
       // me.add(new GlobalVariableInterceptor());
    }

    /**
     * 配置全局管理器(高于拦截器)
     *
     * @param me
     */
    @Override
    public void configHandler(Handlers me) {
        me.add(new ContextPathHandler("base"));
    }

    public static void main(String[] args) throws Exception {
        JFinal.start("webapp", 80, "/", 5);
    }
}
