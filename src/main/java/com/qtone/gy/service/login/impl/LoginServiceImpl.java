package com.qtone.gy.service.login.impl;/**
 * @ClassName LoginServiceImpl.java
 * @author qichenghao
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年08月14日 16:14:00
 */

import com.qtone.gy.dto.ResponseDto;
import com.qtone.gy.service.login.LoginService;
import com.qtone.gy.utils.LRUCache;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {


    /**
     * @title login
     * @description 登陆service实现方法
     * @author qichenghao
     * @param: userName
     * @param: password
     * @param: userType
     * @param: verificationCode
     * @param: key
     * @param: lruCache
     * @param: request
     * @updateTime 2019/8/14 16:20
     * @return com.qtone.gy.dto.ResponseDto
     * @throws
     */
    @Override
    public ResponseDto login(String userName, String password, Integer userType,
                             String verificationCode, String key, LRUCache<Object, Object> lruCache, HttpServletRequest request) {

        return null;
    }

    @Override
    public ResponseDto checkUserRepwdinfo(Map<String, Object> map, LRUCache<Object, Object> lruCache) {
        return null;
    }

    @Override
    public ResponseDto verifyMessage(Map<String, Object> map) {
        return null;
    }

    @Override
    public ResponseDto resetPwd(Map<String, Object> map) {
        return null;
    }

    @Override
    public ResponseDto verifyLongin(Map<String, Object> map) {
        return null;
    }
}
