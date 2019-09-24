package com.qtone.gy.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.InnerHitBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.range.DateRangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.percentiles.PercentilesAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.ParsedSum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.collapse.CollapseBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.joda.time.DateTimeZone;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * @title 
 * @description 
 * @author qichenghao  
 * @updateTime 2019/8/29 19:38 
 * @return 
 * @throws 
 */
public class ESUtil {

    /**
     * @title createClient
     * @description 用于创建索引的连接初始化
     * @author qichenghao
     * @updateTime 2019/8/31 15:24
     * @return void
     * @throws
     */
    public static void createClient() throws UnknownHostException {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        client.admin().indices().prepareCreate("gw").get();
        client.close();
    }


    /**
     * @title createHighClient
     * @description Java高阶API连接
     * @author qichenghao
     * @updateTime 2019/8/31 15:26
     * @return void
     * @throws
     */
    public static void createHighClient(){
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
    }

    public static void apiGet(){
        GetRequest getRequest = new GetRequest(
                "gw",
                "test",
                "123");
        //getRequest.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
        System.out.println(getRequest.toString());
    }

    //根据分词查询(比如'你好啊'-> '你好'，'啊'，可以根据'你好'和'啊'进行索引,如果事keyword类型的就会根据keyword进行索引)
    // QueryBuilders.termQuery("","")
    public static void termQuery() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
        SearchRequest searchRequest = new SearchRequest("gw").types("test");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("name","test"));
        //searchSourceBuilder.query(QueryBuilders.existsQuery("age"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest);
        SearchHits hits = search.getHits();
        SearchHit[] hit = hits.getHits();
        for (SearchHit documentFields : hit) {
            //将文档中的每一个对象转换json串值
            String sourceAsString = documentFields.getSourceAsString();
            System.out.println(sourceAsString);
        }
        client.close();
    }

    //纠错模糊查询(
    // 这里的模糊查询是根据fuzziness(Fuzziness.ZERO)这个参数设定的值来进行模糊查询的,
    // 比如一个单词是test，如果设定Fuzziness.ONE的话搜索tes是可以搜索到的，te是搜索不到的
    // 再比如Fuzziness.TWO 搜索test的时候将test写错位teaa也是可以搜索到的)
    //所以fuzz是允许错误的模糊查询
    public static void fuzzyQuery() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
        SearchRequest searchRequest = new SearchRequest("gw").types("test");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.fuzzyQuery("name","teaa").fuzziness(Fuzziness.TWO));
        //searchSourceBuilder.query(QueryBuilders.existsQuery("age"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest);
        SearchHits hits = search.getHits();
        SearchHit[] hit = hits.getHits();
        for (SearchHit documentFields : hit) {
            //将文档中的每一个对象转换json串值
            String sourceAsString = documentFields.getSourceAsString();
            System.out.println(sourceAsString);
        }
        client.close();
    }

    //前缀查询
    public static void prefixQuery() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
        SearchRequest searchRequest = new SearchRequest("gw").types("test");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.prefixQuery("name","test_"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest);
        SearchHits hits = search.getHits();
        SearchHit[] hit = hits.getHits();
        for (SearchHit documentFields : hit) {
            String sourceAsString = documentFields.getSourceAsString();
            System.out.println(sourceAsString);
        }
        client.close();
    }

    //通配符查询
    public static void wildcardQuery() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
        SearchRequest searchRequest = new SearchRequest("gw").types("test");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.fetchSource(new String[]{"name"}, null); //指定查询的字段
        searchSourceBuilder.query(QueryBuilders.wildcardQuery("name","tes*"));
        //searchSourceBuilder.query(QueryBuilders.existsQuery("age"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest);
        SearchHits hits = search.getHits();
        SearchHit[] hit = hits.getHits();
        for (SearchHit documentFields : hit) {
            //将文档中的每一个对象转换json串值
            String sourceAsString = documentFields.getSourceAsString();
            System.out.println(sourceAsString);
        }
        client.close();
    }

    //对查询的条件自动分词进行查询
    public static void matchQuery() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
        SearchRequest searchRequest = new SearchRequest("gw").types("test");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("name","test"));
        //searchSourceBuilder.query(QueryBuilders.existsQuery("age"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest);
        SearchHits hits = search.getHits();
        SearchHit[] hit = hits.getHits();
        for (SearchHit documentFields : hit) {
            //将文档中的每一个对象转换json串值
            String sourceAsString = documentFields.getSourceAsString();
            System.out.println(sourceAsString);
        }
        client.close();
    }

    //多个字段匹配
    public static void multiMatchQuery() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
        SearchRequest searchRequest = new SearchRequest("gw").types("test");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //searchSourceBuilder.fetchSource(new String[]{"name"}, null);
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("test","id","name"));
        //searchSourceBuilder.query(QueryBuilders.existsQuery("age"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest);
        SearchHits hits = search.getHits();
        SearchHit[] hit = hits.getHits();
        for (SearchHit documentFields : hit) {
            //将文档中的每一个对象转换json串值
            String sourceAsString = documentFields.getSourceAsString();
            System.out.println(sourceAsString);
        }
        client.close();
    }


    //正则查询
    public static void regexpQuery() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
        SearchRequest searchRequest = new SearchRequest("gw").types("test");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //searchSourceBuilder.fetchSource(new String[]{"name"}, null); //查询指定字段
        searchSourceBuilder.query(QueryBuilders.regexpQuery("name","te.*"));
        //searchSourceBuilder.query(QueryBuilders.existsQuery("age"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest);
        SearchHits hits = search.getHits();
        SearchHit[] hit = hits.getHits();
        for (SearchHit documentFields : hit) {
            //将文档中的每一个对象转换json串值
            String sourceAsString = documentFields.getSourceAsString();
            System.out.println(sourceAsString);
        }
        client.close();
    }

    //查询所有QueryBuilders.matchAllQuery()
    public static void apiSearch() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
        SearchRequest searchRequest = new SearchRequest("gw_3").types("test");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        QueryBuilders.matchPhraseQuery("id","1");
//        QueryBuilders.existsQuery()
//        QueryBuilders.matchQuery()
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        boolQueryBuilder.mustNot().add(QueryBuilders.matchPhraseQuery("id","1"));
//        boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("id","1"));
//        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.query(QueryBuilders.matchAllQuery())
                .sort("name", SortOrder.DESC) // 排序
                .from(0).size(2); //分页
        //searchSourceBuilder.query(QueryBuilders.existsQuery("age"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest);
        SearchHits hits = search.getHits();
        System.out.println(hits.getTotalHits()); //查询总条数
        SearchHit[] hit = hits.getHits();
        for (SearchHit documentFields : hit) {
            //将文档中的每一个对象转换json串值
            String sourceAsString = documentFields.getSourceAsString();
            System.out.println(sourceAsString);
        }
        client.close();
    }

    //多条件查询
    public static void query() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
        SearchRequest searchRequest = new SearchRequest("gw_3").types("test");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("name","罗罗"));
//        boolQueryBuilder.mustNot(QueryBuilders.matchPhraseQuery("age","18"));
        boolQueryBuilder.must(QueryBuilders.rangeQuery("weight").gte(56).lte(57));
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest);
//        search.toString();
        Object o = JSONObject.toJSON(search.toString());
        System.out.println(o);
        client.close();
    }

    //聚合操作
    //select name,id,sum(id) from table group by name
    //这里有个问题是：5.0是否有group by两个字段的情况
    public static  void apiAgg() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
        SearchRequest searchRequest = new SearchRequest("gw_2").types("test");
        //指定索引和type
        //SearchRequestBuilder builder = client.prepareSearch("player_info_7").setTypes("player");
        //SearchSourceBuilder builder = new SearchSourceBuilder();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("na")
                .field("name");
        TermsAggregationBuilder field = AggregationBuilders.terms("average_age")
                .field("id");
        SumAggregationBuilder field1 = AggregationBuilders.sum("sum").field("id");
//        AggregationBuilders.adjacencyMatrix()
//        AggregationBuilders.percentileRanks("",[0,1]);
        TermsAggregationBuilder termsAggregationBuilder = aggregation.subAggregation(field1);
        searchSourceBuilder.aggregation(field.subAggregation(termsAggregationBuilder));
        //aggregation.subAggregation(AggregationBuilders.sum("sum").field("id"));
        //aggregation.subAggregation(AggregationBuilders.sum("sum").field("id"));
        //ValueCountAggregationBuilder field = AggregationBuilders.count("average_age").field("id");
        //searchSourceBuilder.query(QueryBuilders.matchQuery("name","2"));

        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest);
        Map<String, Aggregation> asMap = search.getAggregations().getAsMap();
        Terms teams = (Terms) asMap.get("average_age");
        for (Terms.Bucket teamBucket : teams.getBuckets()) {
            //分组的属性名
            Long team = (Long) teamBucket.getKey();
            //System.out.println(team);
            Map<String, Aggregation> asMap1 = teamBucket.getAggregations().getAsMap();
            //ParsedValueCount ages = (ParsedValueCount)asMap1.get("count");
            ParsedSum sum = (ParsedSum) asMap1.get("sum");
//            Aggregation average_age = asMap1.get("average_age");
//            average_age.getMetaData().get("average_age");
            //ParsedLongTerms ids = (ParsedLongTerms) asMap1.get("id");
            double value = sum.getValue();
            //double value1 = sum.getValue();
            System.out.println(team+":  "+":    "+ value);
            Terms teams2 = (Terms) asMap1.get("average_age");
            for (Terms.Bucket teamBucket2 : teams2.getBuckets()) {

                System.out.println(teamBucket2.getDocCount());
                System.out.println(teamBucket2.getKey());
            }
        }
        client.close();
    }

    //测试group by 多个字段
    public static  void apiAggV2() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
        SearchRequest searchRequest = new SearchRequest("gw").types("test");
        //指定索引和type
        //SearchRequestBuilder builder = client.prepareSearch("player_info_7").setTypes("player");
        //SearchSourceBuilder builder = new SearchSourceBuilder();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("na")
                .field("name.keyword");
        TermsAggregationBuilder field = AggregationBuilders.terms("average_age")
                .field("id.keyword");
        SumAggregationBuilder field1 = AggregationBuilders.sum("sum").field("id.keyword");
        TermsAggregationBuilder termsAggregationBuilder = aggregation.subAggregation(field1);
        //aggregation.subAggregation(AggregationBuilders.sum("sum").field("id"));
        //aggregation.subAggregation(AggregationBuilders.sum("sum").field("id"));
        //ValueCountAggregationBuilder field = AggregationBuilders.count("average_age").field("id");
        //searchSourceBuilder.query(QueryBuilders.matchQuery("name","2"));
        searchSourceBuilder.aggregation(field.subAggregation(termsAggregationBuilder));

        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest);
        Map<String, Aggregation> asMap = search.getAggregations().getAsMap();
        Terms teams = (Terms) asMap.get("average_age");
        for (Terms.Bucket teamBucket : teams.getBuckets()) {
            //分组的属性名
            String age = (String) teamBucket.getKey();
            //System.out.println(team);
            Map<String, Aggregation> asMap1 = teamBucket.getAggregations().getAsMap();
            Terms teams2 = (Terms) asMap1.get("na");
            for (Terms.Bucket teamBucket2 : teams2.getBuckets()) {
                String name = (String) teamBucket2.getKey();
                Map<String, Aggregation> asMap2 = teamBucket2.getAggregations().getAsMap();
                ParsedSum sum = (ParsedSum) asMap2.get("sum");
                System.out.println(age+":"+name+":"+sum.getValue());
            }

        }
        client.close();
    }

    //按时间区间进行分段统计
    public static  void  testDateHistogram() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
        SearchRequest searchRequest = new SearchRequest("gw_3").types("test");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.rangeQuery("height")
                .gte(180).lte(181)
//                .format("epoch_millis")
        );
        DateHistogramInterval hours = DateHistogramInterval.hours(1);
        //DateHistogramInterval.weeks(1);
        DateHistogramAggregationBuilder dt = AggregationBuilders.dateHistogram("date").field("dt").
                dateHistogramInterval(hours).timeZone(DateTimeZone.UTC).minDocCount(1);
        searchSourceBuilder.aggregation(dt);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest);
        Object o = JSONObject.toJSON(search.toString());
        System.out.println(o);

        search.getHits();
        Map<String, Aggregation> asMap = search.getAggregations().getAsMap();
        client.close();
    }

    //date区间范围
    public static void dateRange() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
        SearchRequest searchRequest = new SearchRequest("gw_3").types("test");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        DateRangeAggregationBuilder dateRangeAggregationBuilder = AggregationBuilders
                .dateRange("agg")
                .field("dt")
                .format("yyyy-MM-dd")
                .addUnboundedFrom("2019-09-02")
                .addRange("2019-09-02", "2019-09-03").addUnboundedTo("2019-09-03");
        searchSourceBuilder.aggregation(dateRangeAggregationBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest);
        Object o = JSONObject.toJSON(search.toString());
        System.out.println(o);
        client.close();
    }

    //去重统计 select count(distinct id) from table
    public static void cardinality() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
        SearchRequest searchRequest = new SearchRequest("gw_2").types("test");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        CardinalityAggregationBuilder field = AggregationBuilders.cardinality("id").field("id");
        searchSourceBuilder.aggregation(field);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest);
        Map<String, Aggregation> asMap = search.getAggregations().getAsMap();
        //Aggregation aggregation = asMap.get("cardinality#id");
        Cardinality aggregation = (Cardinality) asMap.get("id");
        System.out.println(aggregation.getValue());
        Object o = JSONObject.toJSON(search.toString());
        System.out.println(o);
        client.close();
    }

    //折叠窗口
    public static void collapse() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
        SearchRequest searchRequest = new SearchRequest("gw_2").types("test");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.collapse(
                new CollapseBuilder("id")
                .setInnerHits(new InnerHitBuilder("top")
                        .setSize(2)
                ));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest);
        Object o = JSONObject.toJSON(search.toString());
        System.out.println(o);
        client.close();
    }

    //测试条件查询+去重折叠
    public static void queryCollapse() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
        SearchRequest searchRequest = new SearchRequest("gw_3").types("test");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.rangeQuery("height")
                .gte(180).lte(181)
        );
        searchSourceBuilder.collapse(
                new CollapseBuilder("name.keyword")
//                        .setInnerHits(new InnerHitBuilder("top")
//                                .setSize(2))
        );
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest);
        SearchHits hits = search.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit documentFields : hits1) {
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            Object o = JSONObject.toJSON(sourceAsMap);
            System.out.println(o);
            DocumentField name = documentFields.getFields().get("name.keyword");
            System.out.println(name.getValue());
        }
//        Object o = JSONObject.toJSON(search.toString());
//        System.out.println(o);
        client.close();
    }


    public static  void percentiles() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
        SearchRequest searchRequest = new SearchRequest("gw_3").types("test");
        //指定索引和type
        //SearchRequestBuilder builder = client.prepareSearch("player_info_7").setTypes("player");
        //SearchSourceBuilder builder = new SearchSourceBuilder();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        double[] doubles = {1, 50, 99};
        System.out.println(doubles);
        PercentilesAggregationBuilder id = AggregationBuilders.percentiles("id").field("id")
                .percentiles(doubles).keyed(false);
        searchSourceBuilder.aggregation(id);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest);
        Object o = JSONObject.toJSON(search);
//        Map<String, Aggregation> asMap = search.getAggregations().getAsMap();
//        Object o = JSONObject.toJSON(asMap);
        System.out.println(o);
        client.close();
    }

    public static void main(String[] args) throws IOException {
//        System.out.println(client);
//        GetRequest getRequest = new GetRequest(
//                "store",
//                "books",
//                "1");
//        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
//        System.out.println(getResponse.getFields());

        //client.admin().indices().prepareCreate("twitter").get();

        //apiGet();
        //apiSearch();
        //apiAggV2();
        //apiSearch();
//        termQuery();
//        wildcardQuery();
//        testDateHistogram();
//        dateRange();
//        cardinality();
//        collapse();
//        queryCollapse();
        percentiles();
    }

}
