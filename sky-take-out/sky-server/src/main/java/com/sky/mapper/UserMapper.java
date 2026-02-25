package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /**
     * 根据openId查询本地后端服务器中的用户
     *
     * @param openId
     * @return
     */
    @Select("select * from sky_take_out.user where openid = #{openId}")
    User getByOpenId(String openId);

    /**
     * 新插入用户数据
     *
     * @param localUser
     */
    void insert(User localUser);
}
