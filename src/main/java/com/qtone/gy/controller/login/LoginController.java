package com.qtone.gy.controller.login;

import com.qtone.gy.dto.ResponseDto;
import com.qtone.gy.enums.CommonResultEnum;
import com.qtone.gy.exception.BusinessException;
import com.qtone.gy.model.user.UserDto;
import com.qtone.gy.service.login.LoginService;
import com.qtone.gy.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName LoginController.java
 * @author qichenghao
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年08月14日 16:02:00
 */

@RestController
@RequestMapping("/api/login/")
public class LoginController {

    /**验证码本机地址*/
    @Value("${verifiesDir}")
    private String verifiesDir;

    @Autowired
    private LoginService loginService;

    /**验证码缓存对象，最多缓存500条*/
    private LRUCache<Object, Object> lruCache = new LRUCache<>(10000);

    /**
     * @title loginWeb
     * @description  用户登陆
     * @author qichenghao
     * @param: user
     * @param: request
     * @param: response
     * @updateTime 2019/8/14 16:12
     * @return com.qtone.gy.dto.ResponseDto
     * @throws
     */
    @RequestMapping(value="/signin/v1",method= RequestMethod.POST)
    public ResponseDto loginWeb(
            @RequestBody UserDto user,
            HttpServletRequest request,
            HttpServletResponse response) throws BusinessException {

        //1.接受参数，校验参数是否合法
        String userName = StringUtils.objToStr(user.getUsername());
        String password = StringUtils.objToStr(user.getPassword());
        Integer userType = StringUtils.objToInt(user.getUserType());
        String verificationCode = StringUtils.objToStr(user.getVerificationCode());
        String key = StringUtils.objToStr(user.getKey());
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)
                || userType <= 0 || userType > 3
                || StringUtils.isEmpty(verificationCode)
                || StringUtils.isEmpty(key)){
            throw new BusinessException(CommonResultEnum.COMMON_ERROR_602);
        }
        if(!StringUtils.isPhone(userName)){
            throw new BusinessException(CommonResultEnum.COMMON_ERROR_616);
        }
        return this.loginService.login(userName, password, userType,
                verificationCode, key,lruCache,request);
    }

    /**
     * @title VerifyCode
     * @description  获取图片验证码
     * @author qichenghao
     * @param: request
     * @param: response
     * @updateTime 2019/9/5 16:42
     * @return com.qtone.gy.dto.ResponseDto
     * @throws
     */
    @SuppressWarnings("restriction")
    @RequestMapping(value="/verificationCode/v1",method=RequestMethod.POST)
    public ResponseDto VerifyCode(HttpServletRequest request,
                                  HttpServletResponse response) throws IOException {

        // 生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        // 生成图片
        int w = 200, h = 80;
        //方案二开始
        File file = new File(verifiesDir, verifyCode + ".jpg");
        VerifyCodeUtils.outputImage(w, h, file, verifyCode);
        //base编码图片
        BASE64Encoder encoder = new BASE64Encoder();
        BufferedImage bi = ImageIO.read(file);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        String str = encoder.encodeBuffer(bytes).trim();
		/*str = "data:image/jpeg;base64," + str.replaceAll("\r\n", "");*/
        str = "data:image/jpeg;base64," + str.replaceAll("\r", "").replaceAll("\n", "");
        //编码结果放到map里面,时间戳放到map
        String key = EncryptUtil.encrypt(System.currentTimeMillis()+"");
        lruCache.put(key, verifyCode);
        Map<String,Object> map = new HashMap<>();
        map.put("verifyCode", str);
        map.put("key", key);
        //删除本地验证码
        file.delete();

        //方案二结束
        return ResponseUtil.success(map);
    }

    /**
     * @title checkUserRepwdinfo
     * @description 用户忘记密码，对输入的手机号，验证码进行校验
     * @author qichenghao
     * @param: map
     * @updateTime 2019/9/5 16:42
     * @return com.qtone.gy.dto.ResponseDto
     * @throws
     */
    @RequestMapping(value="/resetPwd/checkUserinfo/v1",method=RequestMethod.POST)
    public ResponseDto checkUserRepwdinfo(
            @RequestBody Map<String,Object> map) throws BusinessException {
        return loginService.checkUserRepwdinfo(map,lruCache);
    }

    /**
     * @title verifyMessage
     * @description 校验短信验证码
     * @author qichenghao
     * @param: map
     * @updateTime 2019/9/5 16:43
     * @return com.qtone.gy.dto.ResponseDto
     * @throws
     */
    @RequestMapping(value="/verify/v1", method=RequestMethod.POST)
    public ResponseDto verifyMessage(
            @RequestBody Map<String,Object> map) throws BusinessException{
        return loginService.verifyMessage(map);
    }

    /**
     * @title resetPwd
     * @description 重置密码
     * @author qichenghao
     * @param: map
     * @updateTime 2019/9/5 16:45
     * @return com.qtone.gy.dto.ResponseDto
     * @throws
     */
    @RequestMapping(value="/resetPwd/v1",method=RequestMethod.POST)
    public ResponseDto resetPwd(
            @RequestBody Map<String,Object> map) throws BusinessException {
        return this.loginService.resetPwd(map);
    }

    /**
     * @title verifyLogin
     * @description 手机验证码登陆
     * @author qichenghao
     * @param: map
     * @updateTime 2019/9/5 16:46
     * @return com.qtone.gy.dto.ResponseDto
     * @throws
     */
    @RequestMapping(value="/verifyLogin/v1",method=RequestMethod.POST)
    public ResponseDto verifyLogin(@RequestBody Map<String,Object> map) throws UnknownHostException, BusinessException {
        return this.loginService.verifyLongin(map);
    }



    @RequestMapping(value = "/batchSaveStudent/v1",method = RequestMethod.POST)
    public ResponseDto batchSaveStudent(@RequestBody List<Map<String,Object>> paramList) throws BusinessException {
        if(null == paramList){

        }
        return null;
    }
}
