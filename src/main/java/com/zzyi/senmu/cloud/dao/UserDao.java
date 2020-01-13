package com.zzyi.senmu.cloud.dao;

import com.zzyi.senmu.cloud.dto.LoginUserDto;
import com.zzyi.senmu.cloud.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author zzyi
 * @version 1.0.0
 * @date 2020/1/13 12:34
 */
@Mapper
public interface UserDao {
    @Insert("INSERT INTO users(username,password) VALUE(#{username},#{password})")
    public void insert(User user);

    @Select("SELECT * FROM users WHERE username=#{username}")
    public User selectByUsername(String username);


    @Select("SELECT * FROM users WHERE username=#{username} AND password=#{password}")
    public User selectByUsernameAndPassword(LoginUserDto loginUserDto);
}
