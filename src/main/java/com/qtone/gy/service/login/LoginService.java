package com.qtone.gy.service.login;

import com.qtone.gy.dto.ResponseDto;
import com.qtone.gy.utils.LRUCache;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName LoginService.java
 * @author qichenghao
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年08月14日 16:13:00
 */

public interface LoginService {

    /**
     * @title login
     * @description  登陆service
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
    ResponseDto login(String userName, String password, Integer userType,
                      String verificationCode, String key, LRUCache<Object, Object> lruCache, HttpServletRequest request);

    ResponseDto checkUserRepwdinfo(Map<String, Object> map, LRUCache<Object, Object> lruCache);

    ResponseDto verifyMessage(Map<String, Object> map);

    ResponseDto resetPwd(Map<String, Object> map);

    ResponseDto verifyLongin(Map<String, Object> map);
}
