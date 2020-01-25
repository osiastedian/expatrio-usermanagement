package com.expatrio.usermanager.authentication;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @RequestMapping(method = RequestMethod.GET, path = "/login")
    ModelAndView showLoginPage() {
        ModelAndView view = new ModelAndView("login");
        return view;
    }
}