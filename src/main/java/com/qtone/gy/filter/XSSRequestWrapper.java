package com.qtone.gy.filter;

import com.alibaba.fastjson.JSON;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class XSSRequestWrapper extends HttpServletRequestWrapper{

	HttpServletRequest xssRequest = null; 
	
	
	public XSSRequestWrapper(HttpServletRequest request) {
		super(request);
		xssRequest = request;
	}

	 @Override  
	 public String getParameter(String name) {  
	      String value = super.getParameter(replaceXSS(name));  
	        if (value != null) {  
	            value = replaceXSS(value);  
	        }  
	        return value;  
	 }  

	 @Override
	public String[] getParameterValues(String name) {
		 String[] values = super.getParameterValues(replaceXSS(name));
		 if(values != null && values.length > 0){
			 for(int i =0; i< values.length ;i++){
				 values[i] = replaceXSS(values[i]);
			 }
		 }
		return values;

	 }
	 
	 @Override
     public Object getAttribute(String name) {
        Object value = super.getAttribute(name);
        if (null != value && value instanceof String) {
            value = replaceXSS((String) value);
        }
        return value;
     }
	 
	 @Override
	    public ServletInputStream getInputStream() throws IOException {
	        String str=getRequestBody(super.getInputStream());
	        Map<String,Object> map= JSON.parseObject(str,Map.class);
	        Map<String,Object> resultMap=new HashMap<>();
	        for(String key:map.keySet()){
	            Object val=map.get(key);
	            if(map.get(key) instanceof String){
	                resultMap.put(key,replaceXSS(val.toString()));
	            }else if(map.get(key) instanceof List){
	            	List list = (List) map.get(key);
	            	Map<String,Object> listmap = new  HashMap<String,Object>();
	            	for (int i = 0; i < list.size(); i++) {
	            		listmap = (Map) list.get(i);
	            		Map<String,Object> newlistmap=new HashMap<>();
	            		for(String listmapkey:listmap.keySet()){
	            			Object listmapval=listmap.get(listmapkey);
	            			if(listmap.get(listmapkey) instanceof String){
	            				newlistmap.put(listmapkey,replaceXSS(listmapval.toString()));
	            			}else{
	            				newlistmap.put(listmapkey,listmapval);
	            			}
	            		}
	            		list.remove(i);
	            		list.add(newlistmap);
					}
	            	resultMap.put(key, list);
	            }
	            else{
	                resultMap.put(key,val);
	            }

	        }
	        str=JSON.toJSONString(resultMap);
	        final ByteArrayInputStream bais = new ByteArrayInputStream(str.getBytes());

	        return new ServletInputStream() {

	            @Override
	            public int read() throws IOException {
	                return bais.read();
	            }

	            public boolean isFinished() {
	                return false;
	            }

	            public boolean isReady() {
	                return false;
	            }

	        };
	    }

	 
	 private String getRequestBody(InputStream stream) {
	        String line = "";
	        StringBuilder body = new StringBuilder();
	        int counter = 0;

	        // 读取POST提交的数据内容
	        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
	        try {
	            while ((line = reader.readLine()) != null) {

	                body.append(line);
	                counter++;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return body.toString();
	    }

	 
	 @Override  
	 public String getHeader(String name) {  
	        String value = super.getHeader(replaceXSS(name));  
	        if (value != null) { 
	            value = replaceXSS(value);  
	        }  
	        return value;  
	    } 

	 /**
	  * 去除待带script、src的语句，转义替换后的value值
	  */

	public static String replaceXSS(String value) {
    	if (value != null) {
	        try{
	//	        	value = value.replace("+","%2B");   //'+' replace to '%2B'
	        	value = value;
	//	        	value = URLDecoder.decode(value, "utf-8");
	        }catch(IllegalArgumentException e){
	        }
			Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Remove any lonesome </script> tag
			scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Remove any lonesome <script ...> tag
			scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid alert:... e­xpressions
			scriptPattern = Pattern.compile("alert", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid onload= e­xpressions
			scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
		}	        

//	    return filter(value);
	    return value;

	}

		/**
		 * 过滤特殊字符
		 */

		public static String filter(String value) {

	        if (value == null) {
	            return null;
	        }        
	        StringBuffer result = new StringBuffer(value.length());
//	        for (int i=0; i<value.length(); ++i) {
//
//	            switch (value.charAt(i)) {
//
//		            case '<':
//
//		                result.append("<");
//
//		                break;
//
//		            case '>': 
//
//		                result.append(">");
//
//		                break;
//
//		            case '"': 
//
//		                result.append("\"");
//
//		                break;
//
//		            case '\'': 
//
//		                result.append("'");
//
//		                break;
//
//		            case '%': 
//
//		                result.append("%");
//
//		                break;
//
//		            case ';': 
//
//		                result.append(";");
//
//		                break;
//
//			        case '(': 
//
//		                result.append("(");
//
//		                break;
//
//		            case ')': 
//
//		                result.append(")");
//
//		                break;
//
//		            case '&': 
//
//		                result.append("&");
//
//		                break;
//
//		            case '+':
//		                result.append("+");
//		                break;
//		            default:
//		                result.append(value.charAt(i));
//		                break;
//		        }  
//	        }
	        return result.toString();
	    }
}
