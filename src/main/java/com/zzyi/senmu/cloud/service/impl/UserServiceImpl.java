package com.zzyi.senmu.cloud.service.impl;

import com.zzyi.senmu.cloud.commons.dto.StatueResult;
import com.zzyi.senmu.cloud.dao.UserDao;
import com.zzyi.senmu.cloud.dto.LoginUserDto;
import com.zzyi.senmu.cloud.dto.RegisterUserDto;
import com.zzyi.senmu.cloud.pojo.User;
import com.zzyi.senmu.cloud.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zzyi
 * @version 1.0.0
 * @date 2020/1/13 12:33
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public StatueResult register(RegisterUserDto registerUserDto) {
        StatueResult statueResult = registerCheckout(registerUserDto);
        if (statueResult.getStatueCode()==200) {
            User user = new User();
            user.setUsername(registerUserDto.getUsername());
            user.setPassword(registerUserDto.getPassword());
            userDao.insert(user);
        }
        return statueResult;
    }

    @Override
    public StatueResult registerCheckout(RegisterUserDto registerUserDto) {
        if (StringUtils.isBlank(registerUserDto.getUsername())) {
            return StatueResult.fail("用户名不能为空");
        }
        else if (StringUtils.isBlank(registerUserDto.getPassword())) {
            return StatueResult.fail("密码不能为空");
        }
        else if (!registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
            return StatueResult.fail("两次输入密码不一致");
        }
        else if (userDao.selectByUsername(registerUserDto.getUsername())!=null) {
            return StatueResult.fail("用户名已存在");
        }
        return StatueResult.success("注册成功");
    }

    @Override
    public StatueResult login(LoginUserDto loginUserDto) {
        if (userDao.selectByUsernameAndPassword(loginUserDto)==null) {
            return StatueResult.fail("用户名或密码错误");
        }
        else {
            return StatueResult.success("登陆成功");
        }
    }
}
