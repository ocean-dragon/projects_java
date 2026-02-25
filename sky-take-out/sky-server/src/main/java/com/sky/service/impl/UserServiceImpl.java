package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    //微信小程序登录官方接口地址
    private static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户端微信登录
     *
     * @param userLoginDTO
     * @return
     */
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        //调用微信官方的接口服务，获取当前微信用户的openid
        String openId = getOpenId(userLoginDTO.getCode());

        //判断openid是否有效、合法
        if (openId == null || openId.equals("")) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        //是否是新用户：是-注册并保存数据到当前后端服务器数据库中
        User localUser = userMapper.getByOpenId(openId);
        if (localUser == null) {
            localUser = User.builder()
                    .openid(openId)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(localUser);
        }

        //返回用户信息
        return localUser;
    }

    /**
     * 调用微信官方的接口服务，获取当前微信用户的openid
     *
     * @return
     */
    public String getOpenId(String code) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", weChatProperties.getAppid());
        params.put("secret", weChatProperties.getSecret());
        params.put("js_code", code);
        params.put("grant_type", "authorization_code");
        String jsonData = HttpClientUtil.doGet(WX_LOGIN, params);

        JSONObject jsonObject = JSON.parseObject(jsonData);
        String openId = jsonObject.getString("openid");

        return openId;
    }
}
