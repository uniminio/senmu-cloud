package com.zzyi.senmu.cloud.commons.utils;

import com.zzyi.senmu.cloud.commons.dto.StatueResult;
import com.zzyi.senmu.cloud.pojo.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zzyi
 * @version 1.0.0
 * @date 2020/1/13 16:34
 */
@Component
public class FileUtil {

    @Value(value = "${file.root-path}")
    private String fileRootPath;

    /**
     * 上传文件
     * @param file 要上传的文件
     * @param username 用户名(用于隔离不同用户的文件)
     * @return 处理结果
     */
    public StatueResult fileUpload(MultipartFile file,String username) {
        // 先设定一个放置上传文件的文件夹(该文件夹可以不存在，下面会判断创建)
        String deposeFilesDir = fileRootPath+username+"\\";
        // 判断文件手否有内容
        if (file.isEmpty()) {
            return StatueResult.fail("该文件无任何内容!!!");
        }
        // 获取附件原名(有的浏览器如chrome获取到的是最基本的含 后缀的文件名,如myImage.png)
        // 获取附件原名(有的浏览器如ie获取到的是含整个路径的含后缀的文件名，如C:\\Users\\images\\myImage.png)
        String fileName = file.getOriginalFilename();
        // 如果是获取的含有路径的文件名，那么截取掉多余的，只剩下文件名和后缀名
        int index = fileName.lastIndexOf("\\");
        if (index > 0) {
            fileName = fileName.substring(index + 1);
        }

        // 根据文件的全路径名字(含路径、后缀),new一个File对象dest
        File dest = new File(deposeFilesDir + fileName);
        // 如果该文件的上级文件夹不存在，则创建该文件的上级文件夹及其祖辈级文件夹;
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            // 将获取到的附件file,transferTo写入到指定的位置(即:创建dest时，指定的路径)
            file.transferTo(dest);
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return StatueResult.success("上传文件 "+fileName+" 成功");
    }

    /**
     * 返回该用户文件夹下的所有内容
     * @param username 用户名
     * @return 该用户文件夹下的文件列表信息
     */
    public List<FileInfo> folderDisplay(String username) {
        String folderPath = fileRootPath+username;
        File files[]=new File(folderPath).listFiles();
        List<FileInfo> fileInfos = new ArrayList<FileInfo>();
        for (File file:files) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(file.getName());
            fileInfo.setLastModifiedDate(lastModified(file));
            fileInfo.setFileSize(getFileSize(file));
            fileInfos.add(fileInfo);
        }
        return fileInfos;
    }


    /**
     * 返回文件的最后修改日期
     * @param file 文件
     * @return 最后修改日期
     */
    public String lastModified(File file) {
        long time = file.lastModified();//返回文件最后修改时间，是以个long型毫秒数
        String ctime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(time));
        return ctime;
    }

    /**
     * 返回文件大小
     * @param file 文件
     * @return 文件大小
     */
    public String getFileSize(File file){
        String size = "";
        if(file.exists() && file.isFile()){
            long fileS = file.length();
            DecimalFormat df = new DecimalFormat("#.00");
            if (fileS < 1024) {
                size = df.format((double) fileS) + "Byte";
            } else if (fileS < 1048576) {
                size = df.format((double) fileS / 1024) + "KB";
            } else if (fileS < 1073741824) {
                size = df.format((double) fileS / 1048576) + "MB";
            } else {
                size = df.format((double) fileS / 1073741824) +"GB";
            }
        }else if(file.exists() && file.isDirectory()){
            size = "";
        }else{
            size = "0Byte";
        }
        return size;
    }

    /**
     * 下载文件
     * @param filename 文件名
     * @param response 浏览器响应
     */
    public void fileDownLoad(String filename,String username,HttpServletResponse response) {

        try {
            String fileName = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
            response.setHeader("content-disposition", "attachment;filename=" + fileName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ServletOutputStream out = null;
        InputStream in = null;

        try {
            in = new FileInputStream(new File(fileRootPath+File.separator+username+File.separator+filename));
            out = response.getOutputStream();
            IOUtils.copy(in, out);
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StatueResult fileDelete(String filename,String username) {
        File file = new File(fileRootPath+File.separator+username+File.separator+filename);
        if (file.exists()) {//文件是否存在
            if (file.delete()) {//存在就删了，返回1
                return StatueResult.success("文件 "+file.getName()+" 已成功删除");
            } else {
                return StatueResult.fail("文件 "+file.getName()+" 删除失败");
            }
        } else {
            return StatueResult.fail("文件 "+file.getName()+" 不存在");
        }
    }
}
