package com.zzyi.senmu.cloud.service;

import com.zzyi.senmu.cloud.commons.dto.StatueResult;
import com.zzyi.senmu.cloud.dto.LoginUserDto;
import com.zzyi.senmu.cloud.dto.RegisterUserDto;

/**
 * 进行用户的增删改查
 * @author zzyi
 * @version 1.0.0
 * @date 2020/1/13 12:32
 */
public interface UserService {
    /**
     * 注册一位用户
     * @param registerUserDto 用户的注册信息
     * @return 处理结果
     */
    public StatueResult register(RegisterUserDto registerUserDto);

    /**
     * 用户信息有效性效验
     * @param registerUserDto 用户的注册信息
     * @return 处理结果
     */
    public StatueResult registerCheckout(RegisterUserDto registerUserDto);

    /**
     * 用户登陆
     * @param loginUserDto 用户登陆信息
     * @return 处理结果
     */
    public StatueResult login(LoginUserDto loginUserDto);

}
