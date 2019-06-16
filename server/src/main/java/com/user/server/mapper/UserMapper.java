package com.user.server.mapper;

import com.user.server.entity.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    @Insert("INSERT INTO user(username,password,salt) VALUES(#{username}, #{password}, #{salt})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addUser(UserInfo user);

    @Select("SELECT * FROM user WHERE id = #{id}")
    UserInfo getUserById(String id);

    @Select("SELECT * FROM user WHERE username = #{username}")
    UserInfo getUserByName(String username);
}
