package com.harlan.controller;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;

/**
 * Created by Harlan1994 on 2017/4/14.
 */
@ControllerBind(controllerKey = "/")
public class IndexController extends Controller {

    public void index() {
        render("/front/index.html");
    }

    public void login() {
        render("/front/login.html");
    }
}
