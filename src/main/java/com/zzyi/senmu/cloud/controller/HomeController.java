package com.zzyi.senmu.cloud.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.zzyi.senmu.cloud.commons.dto.StatueResult;
import com.zzyi.senmu.cloud.commons.utils.FileUtil;
import com.zzyi.senmu.cloud.dto.LoginUserDto;
import com.zzyi.senmu.cloud.pojo.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zzyi
 * @version 1.0.0
 * @date 2020/1/13 11:16
 */

@Controller
public class HomeController {

    @Autowired
    private FileUtil fileUtil;


    @RequestMapping(value = {"/","/login"},method = RequestMethod.GET)
    public String home() {
        return "login";
    }

    @RequestMapping(value = "space",method = RequestMethod.GET)
    public String space(HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
        if (request.getSession().getAttribute("user")==null) {
            redirectAttributes.addFlashAttribute("statueResult", StatueResult.fail("您还未登录"));
            return "redirect:login";
        }
        LoginUserDto loginUserDto = (LoginUserDto) request.getSession().getAttribute("user");
        List<FileInfo> fileInfos = fileUtil.folderDisplay(loginUserDto.getUsername());
        model.addAttribute("fileInfos",fileInfos);
        return "space";
    }
}
