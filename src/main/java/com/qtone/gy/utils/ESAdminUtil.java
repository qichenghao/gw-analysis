package com.qtone.gy.utils;/**
 * @ClassName ESAdminUtil.java
 * @author qichenghao
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年08月31日 15:28:00
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 　　* @className: ESAdminUtil
 * 　　* @author qichenghao
 * 　　* @date 2019/8/31 15:28
 * ES的AdminUtil
 *
 */
public class ESAdminUtil {


    //创建连接
    public static TransportClient createClient() throws UnknownHostException {
        TransportClient client =
                new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        return client;
    }

    // 创建索引
    public static void createIndex(String index) throws UnknownHostException {
        TransportClient client = createClient();
        client.admin().indices()
                .prepareCreate(index)
                .setSettings(Settings.builder() //设置分区和备份数量
                        .put("index.number_of_shards", 3)
                        .put("index.number_of_replicas", 2)
                )
                .get();
        client.close();
    }

    //修改索引setting
    public static void updateIndexSetting(String index) throws UnknownHostException {
        TransportClient client = createClient();
        client.admin().indices()
                .prepareUpdateSettings(index)
                .setSettings(Settings.builder() //设置分区和备份数量
                        .put("index.number_of_shards", 3)
                        .put("index.number_of_replicas", 2)
                )
                .get();
        client.close();
    }

    // 删除索引
    private static void deleteIndex(String index) throws UnknownHostException {
        TransportClient client = createClient();
        client.admin().indices().prepareDelete(index).get();
        client.close();
    }

    // 新增一个文档
    private static void createDocument(String index, String type) throws JsonProcessingException, UnknownHostException {
        TransportClient client = createClient();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",4);
        map.put("name","罗罗诺亚·索隆");
        map.put("age",18);
        map.put("height",183);
        map.put("weight",56.1);
        map.put("dt",new Date());
        String source = new ObjectMapper().writeValueAsString(map);
        // 新增
        client.prepareIndex(index, type).setSource(map).get();
        client.close();
    }

    //创建mapping
    public static void createMapping(String index, String type) throws IOException {
        //1:settings
        HashMap<String, Object> settings_map = new HashMap<String, Object>(2);
        settings_map.put("number_of_shards", 3);
        settings_map.put("number_of_replicas", 1);
        
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                    .field("dynamic", "true")
                    //设置type中的属性
                    .startObject("properties")
                        //id属性
                        .startObject("num")
                            //类型是integer
                            .field("type", "integer")
                            //不分词，但是建索引
                            .field("index", "true")
                            //在文档中存储
                            .field("store", "true")
                        .endObject()
                        //name属性
                        .startObject("name")
                            //string类型
                            .field("type", "text")
                            //在文档中存储
                            .field("store", "true")
                            //建立索引
                            .field("index", "false")
                        .endObject()
                    .endObject()
                .endObject();
        TransportClient client = createClient();
        CreateIndexRequestBuilder prepareCreate = client.admin().indices().prepareCreate(index);
        //管理索引（user_info）然后关联type（user）
        prepareCreate.setSettings(settings_map).addMapping(type, builder).get();
        client.close();
    }

//    private void createMapping() throws Exception {
//        // 配置映射关系
//        Map<String, Object> mappings = new HashMap<>();
//
//        Map<String, Object> type = new HashMap<>();
//        mappings.put(TYPE, type);
//        type.put("dynamic", false);
//
//        Map<String, Object> properties = new HashMap<>();
//        type.put("properties", properties);
//
//        // 文档的id映射
//        Map<String, Object> idProperties = new HashMap<>();
//        idProperties.put("type", "integer");
//        idProperties.put("store", "yes");
//        properties.put("id", idProperties);
//
//        // 文档的title映射
//        Map<String, Object> titleProperties = new HashMap<>();
//        titleProperties.put("type", "string");
//        titleProperties.put("store", "yes");
//        titleProperties.put("analyzer", "ik");
//        properties.put("title", titleProperties);
//
//        String json = MAPPER.writeValueAsString(mappings);
//        System.out.println(json);
//
//        PutMappingRequest request = Requests.putMappingRequest(INDEX).type(TYPE).source(json);
//        this.client.admin().indices().putMapping(request).get();
//    }


    public static void main(String[] args) throws IOException {
       // createDocument("gw","test");
//        deleteIndex("gw_3");
//        createDocument("gw_3","test");
        System.out.println((int) ("中国".charAt(1)));
//        new StringExpress().getDictionary(c);
    }


}
