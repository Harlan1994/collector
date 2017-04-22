package com.harlan.intercepter;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;


public class GlobalVariableInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        Integer userId = controller.getSessionAttr("uid");
        controller.setAttr("allUnreadNumber", "Hello world");
        inv.invoke();
    }
}
