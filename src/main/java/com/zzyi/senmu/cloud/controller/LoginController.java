package com.zzyi.senmu.cloud.controller;

import com.zzyi.senmu.cloud.commons.dto.StatueResult;
import com.zzyi.senmu.cloud.dto.LoginUserDto;
import com.zzyi.senmu.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zzyi
 * @version 1.0.0
 * @date 2020/1/13 11:51
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String login(LoginUserDto loginUserDto, Model model, HttpServletRequest request) {
        StatueResult statueResult = userService.login(loginUserDto);
        if (statueResult.getStatueCode()==500) {
            model.addAttribute("statueResult",statueResult);
            model.addAttribute("loginUserInfo",loginUserDto);
            return "login";
        }
        else {
            request.getSession().setAttribute("user",loginUserDto);
            return "redirect:space";
        }
    }

}
