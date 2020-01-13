package com.zzyi.senmu.cloud.controller;

import com.zzyi.senmu.cloud.commons.dto.StatueResult;
import com.zzyi.senmu.cloud.dto.RegisterUserDto;
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
@RequestMapping(value = "user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "register",method = RequestMethod.GET)
    public String register() {
//        model.addAttribute("userInfo",new RegisterUserDto());
        return "register";
    }

    @RequestMapping(value = "register",method = RequestMethod.POST)
    public String register(RegisterUserDto registerUserDto, Model model) {
        StatueResult statueResult = userService.register(registerUserDto);

        if (statueResult.getStatueCode()==500) {
            model.addAttribute("statueResult",statueResult);
            model.addAttribute("userInfo",registerUserDto);
            return "register";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login";
    }
}
