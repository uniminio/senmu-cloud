package com.zzyi.senmu.cloud.dto;

import lombok.Data;

/**
 * @author zzyi
 * @version 1.0.0
 * @date 2020/1/13 14:02
 */
@Data
public class RegisterUserDto {
    private String username;
    private String password;
    private String confirmPassword;
}
