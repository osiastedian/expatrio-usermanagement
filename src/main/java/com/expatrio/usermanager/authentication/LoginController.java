package com.expatrio.usermanager.authentication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @RequestMapping(method = RequestMethod.GET, path = "/login")
    ModelAndView showLoginPage(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        ModelAndView view = new ModelAndView("login");
        return view;
    }
}