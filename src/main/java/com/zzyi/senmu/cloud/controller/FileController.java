package com.zzyi.senmu.cloud.controller;

import com.zzyi.senmu.cloud.commons.dto.StatueResult;
import com.zzyi.senmu.cloud.commons.utils.FileUtil;
import com.zzyi.senmu.cloud.dto.LoginUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @author zzyi
 * @version 1.0.0
 * @date 2020/1/13 11:52
 */
@Controller
public class FileController {
    @Autowired
    private FileUtil fileUtil;

    @RequestMapping(value = "/file/upload", method = RequestMethod.POST)
    public String fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        LoginUserDto user = (LoginUserDto) (request.getSession().getAttribute("user"));
        StatueResult statueResult = fileUtil.fileUpload(file,user.getUsername());
        redirectAttributes.addFlashAttribute("statueResult",statueResult);
        return "redirect:/space";
    }

    @RequestMapping(value = "/file/download",method = RequestMethod.GET)
    public String fileDownload(@RequestParam(name = "fileName")String fileName, HttpServletRequest request, HttpServletResponse response) {
        LoginUserDto user = (LoginUserDto) (request.getSession().getAttribute("user"));
        fileUtil.fileDownLoad(fileName,user.getUsername(),response);
        return null;
    }

    @RequestMapping(value = "file/delete",method = RequestMethod.GET)
    public String fileDelete(@RequestParam(name = "fileName")String fileName, HttpServletRequest request,RedirectAttributes redirectAttributes) {
        LoginUserDto user = (LoginUserDto) (request.getSession().getAttribute("user"));
        StatueResult statueResult = fileUtil.fileDelete(fileName,user.getUsername());
        redirectAttributes.addFlashAttribute("statueResult",statueResult);
        return "redirect:/space";
    }


}
