package com.qtone.gy.utils;


import com.qtone.gy.dto.PageGridDto;
import com.qtone.gy.dto.ResponseDto;
import com.qtone.gy.dto.ResponsePageDto;
import com.qtone.gy.enums.BaseResultEnum;
import com.qtone.gy.enums.StatusCodeEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by huangguangxi on 2017/4/12.
 */
public class ResponseUtil {

    /**
     * 成功返回默认消息
     *
     * @param data
     * @return
     */
    public static ResponseDto success(Object data) {
        ResponseDto response = new ResponseDto();
        response.setCode(StatusCodeEnum.SUCCESS.getCode());
        response.setMsg(StatusCodeEnum.SUCCESS.getMsg());
        response.setSuccess(true);
        if (null != data) {
        	 response.setData(data);
        }
        return response;
    }
    /**
     * 成功返回加消息
     * @author huangguangxi
     * @date:   2017年9月21日 下午2:16:35    
     * @param statusEnum
     * @param data
     * @return      
     * @return: ResponseDto      
     * @throws
     */
    public static ResponseDto success(BaseResultEnum statusEnum, Object data) {
        ResponseDto response = new ResponseDto();
        response.setCode(statusEnum.getCode());
        response.setMsg(statusEnum.getMsg());
        response.setSuccess(true);
        if (null != data) {
       	 response.setData(data);
        }
        return response;
    }

    /**
     * 异常时返回
     *
     * @param statusEnum
     * @return
     */
    public static ResponseDto error(BaseResultEnum statusEnum) {
        ResponseDto response = new ResponseDto();
        response.setCode(statusEnum.getCode());
        response.setMsg(statusEnum.getMsg());
        response.setSuccess(false);
        //response.setData(null);

        return response;
    }

    /**
     * 异常时返回
     * @author renfei
     * @date 2017年12月5日 下午1:59:45
     * @param statusEnum
     * @param data
     * @return
     */
    public static ResponseDto imError(BaseResultEnum statusEnum, Object data) {
        ResponseDto response = new ResponseDto();
        response.setCode(statusEnum.getCode());
        response.setMsg(statusEnum.getMsg());
        response.setSuccess(false);
		if (null != data) {
			response.setData(data);
		}
        return response;
    }

    /**
     * 异常时返回
     *
     * @param code     状态码
     * @param errorMsg 错误信息
     * @return
     */
    public static ResponseDto error(int code, String errorMsg) {
        ResponseDto response = new ResponseDto();
        response.setCode(code);
        response.setMsg(errorMsg);
        response.setSuccess(false);
        //response.setData(null);

        return response;
    }
    
    /**
     * 分页成功时返回
     * @author huangguangxi
     * @date:   2017年9月19日 下午2:04:27   
     * @param data 分页数据
     * @param currentPage 当前页
     * @param pageSize 每页大小
     * @param total 总条数
     * @return: ResponsePageDto      
     * @throws
     */
    public static ResponsePageDto pageSuccess(Object data, Integer currentPage, Integer pageSize, Long total) {
    	ResponsePageDto response = new ResponsePageDto();
        response.setCode(StatusCodeEnum.SUCCESS.getCode());
        response.setMsg(StatusCodeEnum.SUCCESS.getMsg());
        response.setSuccess(true);
        response.setData(data);
        //分页信息
        PageGridDto pageDto = new PageGridDto();
        pageDto.setCurrentPage(currentPage);
        pageDto.setPageSize(pageSize);
        pageDto.setTotal(total);
        
        response.setPageInfo(pageDto);
        return response;
    }
    /**
     * 分页错误时返回
     * @author huangguangxi
     * @date:   2017年9月19日 下午2:04:05   
     * @param statusEnum
     * @return      
     * @return: ResponsePageDto      
     * @throws
     */
    public static ResponsePageDto pageError(BaseResultEnum statusEnum) {
    	ResponsePageDto response = new ResponsePageDto();
        response.setCode(statusEnum.getCode());
        response.setMsg(statusEnum.getMsg());
        response.setSuccess(false);
        //response.setData(null);

        return response;
    }

    /**
     * 分页错误时返回
     * @author renfei
     * @date 2017年9月29日 下午3:19:12
     * @param statusEnum
     * @param strings
     * @return
     */
    public static ResponsePageDto pageError(BaseResultEnum statusEnum, String...strings) {
    	ResponsePageDto response = new ResponsePageDto();

        response.setCode(statusEnum.getCode());
        response.setMsg(errorMsgReplace(statusEnum.getMsg(),strings));
        response.setSuccess(false);

        return response;
    }

    /**
     * 带枚举，替换占位符的方法，下面main方法有使用的例子
     * @author :huangguangxi
     * @date :2017年6月28日 下午3:15:58
     * @param resultService
     * @param strings
     * @return
     */
    public static ResponseDto error(BaseResultEnum statusEnum, String...strings) {
    	ResponseDto baseResult = new ResponseDto();
    	baseResult.setCode(statusEnum.getCode());
    	baseResult.setMsg(errorMsgReplace(statusEnum.getMsg(),strings));
    	baseResult.setSuccess(false);
    	baseResult.setData(null);

        return baseResult;
    }
    
    /**
     * 替换带有占位符的信息
     * @author :huangguangxi
     * @date :2017年6月28日 下午3:00:16
     * @param errorMsg 带有占位符的错误信息
     * @param strings 替换占位符的字符串数组
     * @return
     */
    private static String errorMsgReplace(String errorMsg, String...strings){
    	if (StringUtils.isBlank(errorMsg)) {
    		return "";
    	}
    	//没有占位符直接返回原信息
    	if (!errorMsg.contains("{0}")) {
    		return errorMsg;
    	}
    	for (int i = 0; i < strings.length; i++) {
    		errorMsg = errorMsg.replace("{" + i + "}", strings[i]);
		}
    	return errorMsg;
    }
}
