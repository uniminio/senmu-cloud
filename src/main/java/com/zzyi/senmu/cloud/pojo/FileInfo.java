package com.zzyi.senmu.cloud.pojo;

import lombok.Data;

import java.io.File;

/**
 * @author zzyi
 * @version 1.0.0
 * @date 2020/1/13 17:34
 */
@Data
public class FileInfo {
    private String fileName;
    private String lastModifiedDate;
    private String fileSize;
}
