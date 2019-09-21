package com.qtone.gy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qtone.gy.dto.ResponseDto;
import com.qtone.gy.exception.BusinessException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.script.ScriptException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName APIController.java
 * @author qichenghao
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年07月30日 10:51:00
 */
@RestController
@RequestMapping("/api/app")
public class APIController {


    @RequestMapping(value="/test/v1",method= RequestMethod.GET)
    public ResponseDto selectAllteacher() throws Exception {
      return null;
    }

    @RequestMapping(value = "/batchSaveStudent/v1",method = RequestMethod.POST)
    public ResponseDto batchSaveStudent(@RequestBody List<Map<String,Object>> paramList) throws BusinessException {
        if(null == paramList){

        }
        return null;
    }

    @RequestMapping(value="/test/v2",method= RequestMethod.POST)
    public ResponseDto testAllteacher(@RequestBody List<Map<String,Object>> map) throws Exception {
        if(map != null ){
            for (Map<String,Object> stringObjectMap : map) {
                System.out.println(stringObjectMap);
            }
        }
        return null;
    }

    @RequestMapping(value="/test/v3",method= RequestMethod.GET)
    public ResponseDto testAllteacher2() throws Exception {
        List<Map<String,Object>> paramMap = new ArrayList<>();
        Map<String,Object> map = new HashMap<String ,Object>();
        map.put("id",1);
        map.put("name",1);
        paramMap.add(map);
        // paramMap解析为字符串
        String params = null;
        try {
            params = JSON.toJSONString(paramMap);
            System.out.println("params+"+params);
        } catch (Exception e1) {
            e1.printStackTrace();
           // logger.error("map转换json字符串失败");
        }
        HttpURLConnection connection = null;
        Map<String, Object> resultMap = new HashMap<>();
        try {
            //String strURL = PropertiesUtil.getPropertiesByKey("JIGUANG_URL");
            URL url = new URL("http://localhost:8081/api/studentManage/batchSaveStudent/v1");// 创建连接
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("token", "95E81BF0960C608636567262226D93806FCF3A057CCAF017CF6D0543383EAA0F900F5BD93D90FE948788A3750F3FA8BA");
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(params);
            out.flush();
            out.close();
            // 读取响应
            InputStream inputStream = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            StringBuffer sb = new StringBuffer();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append("\r\n");
                line = br.readLine();
            }
            br.close();
            inputStream.close();
            resultMap = (Map<String, Object>) JSONObject.parse(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
            //如果是极光推送，根据状态码确认是否推送成功(不是200即失败)
            Integer responseCode = connection.getResponseCode();
            resultMap.put("code", responseCode);
        }
        //如果是极光推送，根据状态码确认是否推送成功(不是200即失败)
        Integer responseCode = connection.getResponseCode();
        resultMap.put("code", responseCode);
        return null;
    }

    public static void main(String[] args) throws InterruptedException, ScriptException {

//        System.out.printf("%1$tF %1$tA%n", new Date());
//        System.out.println(String.format("%1$tF %1$tA%n", new Date()));
//        System.out.printf("%s = %s%n", "Name", "Zhangsan");
//        System.out.println(String.format("%s = %s%n", "Name", "Zhangsan"));


        /*String str = "(a or b) and c";
        str = str.replaceAll("or", "||");
        str = str.replaceAll("and", "&&");
        System.out.println(str);
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        engine.put("a",true);
        engine.put("b",false);
        engine.put("c",true);
        Object result = engine.eval(str);
        System.out.println("结果类型:" + result.getClass().getName() + ",计算结果:" + result);*/
        String a = "a > b and b < c";
        String[] ands = a.split(">");
        for(String s : ands){
            s.split("and");
        }
        System.out.println(ands.length);
    }



}
