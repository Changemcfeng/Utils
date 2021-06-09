package com.mcfeng.es;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class EsApplicationTests {

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    /**创建索引*/
    @Test
    void createIndex() throws IOException {
        /**创建索引*/
        CreateIndexRequest index = new CreateIndexRequest("mcfeng_index");
        /**客户端发送请求 */
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(index, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse.toString());
    }
    /**获取索引*/
    @Test
    void getIndex() throws IOException {
        /**得到索引请求*/
        GetIndexRequest mcfeng_index = new GetIndexRequest("mcfeng_index");
        /**客户端发送请求，判断索引是否存在 */
        boolean exists = restHighLevelClient.indices().exists(mcfeng_index, RequestOptions.DEFAULT);
        System.out.println(exists);
    }
    /**删除索引*/
    @Test
    void deleteIndex() throws IOException {
        /**删除索引请求*/
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("mcfeng_index");
        /**客户端发送请求 */
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());
    }
    /**
     * 文档操作
     * */

    /**增加文档*/
    @Test
    void addDocument() throws IOException {
        Domain domain = new Domain("马克吐温","20");
        //创建请求
        IndexRequest indexRequest = new IndexRequest("mcfeng_index");
        //文档id
        indexRequest.id("1");
        //响应超时时间
        indexRequest.timeout("3s");
        //文档内容
        indexRequest.source(JSON.toJSONString(domain), XContentType.JSON);
        //发送请求
        IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(index.toString());
    }
    /**是否存在文档 by文档id*/
    @Test
    void isExists() throws IOException{
        GetRequest getRequest = new GetRequest("mcfeng_index","1");
        //指定需要返回字段的上下文，是storedFields的补充与完善，支持通配符(过滤数据用)
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        //显示的指定需要返回的字段，默认会返回_source中所有字段
        getRequest.storedFields("_none_");
        //发送请求
        boolean exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
    }
    /**获取文档内容*/
    @Test
    void getDocument() throws IOException{
        GetRequest getRequest = new GetRequest("mcfeng_index","1");
        //发送请求
        GetResponse documentFields = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(documentFields);
        System.out.println(JSON.toJSONString(documentFields.getSource()));
    }
    /**更新文档内容*/
    @Test
    void updateDocument() throws IOException{
        UpdateRequest request = new UpdateRequest("mcfeng_index", "1");
        Domain domain = new Domain("苏格拉底", "50");
        request.timeout("3s");
        /**使用JSON.toJSON(domain)，无法修改addDocument的内容*/
        /**request.doc(JSON.toJSON(domain),XContentType.JSON);*/
        request.doc(JSON.toJSONString(domain),XContentType.JSON);
        //发送请求
        UpdateResponse update = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        System.out.println(update);
    }
    /**删除指定文档*/
    @Test
    void DeleteDocument() throws IOException{
        //指定索引,文档的编号
        DeleteRequest deleteRequest = new DeleteRequest("mcfeng_index", "1");
        //响应超过3秒不执行
        deleteRequest.timeout("3s");
        DeleteResponse delete = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(delete.status());
    }
    /**批量增加文档*/
    @Test
    void bulkAddDocument() throws IOException{
        //创建批量请求
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("5s");
        List<Domain> domains=new ArrayList<>();
        domains.add(new Domain("马克吐温","40"));
        domains.add(new Domain("亚里士多德","50"));
        domains.add(new Domain("柏拉图","31"));
        domains.add(new Domain("尼采","94"));
        //增加数据
        for (int i = 0; i < domains.size(); i++) {
            bulkRequest.add(new IndexRequest("mcfeng_index")
            .id(""+(i+1)).source(JSON.toJSONString(domains.get(i)),XContentType.JSON));
        }
        //发送请求
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk.hasFailures());
    }
    /**批量更新文档*/
    @Test
    void bulkUpdateDocument() throws IOException{
        //创建批量请求
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("5s");
        List<Domain> domains=new ArrayList<>();
        domains.add(new Domain("马克吐温1","40"));
        domains.add(new Domain("亚里士多德1","50"));
        domains.add(new Domain("柏拉图1","31"));
        domains.add(new Domain("尼采1","94"));
        //创建批量请求数据
        for (int i = 0; i < domains.size(); i++) {
            bulkRequest.add(new UpdateRequest().index("mcfeng_index").id(""+(i+1)).doc(JSON.toJSONString(domains.get(i)),XContentType.JSON));
        }
        //发送请求
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk.hasFailures());
    }
    /**批量更新文档*/
    @Test
    void bulkDeleteDocument() throws IOException{
        //创建请求
        BulkRequest bulkRequest = new BulkRequest();
        List<String > domains=new ArrayList<>();
        domains.add("1");
        domains.add("2");
        domains.add("3");
        domains.add("4");
        //添加数据
        for (int i = 0; i < domains.size(); i++) {
            bulkRequest.add(new DeleteRequest().index("mcfeng_index").id(domains.get(i)));
        }
        //发送请求
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk.hasFailures());
    }
    /**精确查询*/
    @Test
    void searchTerm() throws IOException{
        //创建查询请求
        SearchRequest searchRequest= new SearchRequest("mcfeng_index");
        //如果是中文需要在字段后面加.keyword ;未带中文就不用了加.keyword
        //创建精确查询builder
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name.keyword", "马克吐温");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //请求参数构建
        SearchSourceBuilder query = searchSourceBuilder.query(termQueryBuilder);
        query.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //查询请求构建
        searchRequest.source(query);
        //发送请求
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(search);
        System.out.println(JSON.toJSONString(search.getHits()));
    }
    /**关键字查询*/
    @Test
    void searchMatch() throws IOException{
        //创建查询请求
        SearchRequest searchRequest= new SearchRequest("mcfeng_index");

        //创建精确查询builder
        MatchQueryBuilder matchQuery= QueryBuilders.matchQuery("name", "马克");

        // match_phrase 时会精确匹配查询的&&短语&&，需要全部单词和顺序要完全一样，标点符号除外
        //QueryBuilders.matchPhraseQuery(“supplierName”,param)

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //请求参数构建
        SearchSourceBuilder query = searchSourceBuilder.query(matchQuery);

        query.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //查询请求构建
        searchRequest.source(query);
        //发送请求
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(search);
        System.out.println(JSON.toJSONString(search.getHits()));
    }

    /**查询所有*/
    @Test
    void searchMatchAll() throws IOException{
        //创建查询请求
        SearchRequest searchRequest= new SearchRequest("mcfeng_index");

        //创建精确查询builder
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();

        // match_phrase 时会精确匹配查询的&&短语&&，需要全部单词和顺序要完全一样，标点符号除外
        //QueryBuilders.matchPhraseQuery(“supplierName”,param)

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //请求参数构建
        SearchSourceBuilder query = searchSourceBuilder.query(matchAllQueryBuilder);

        query.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //查询请求构建
        searchRequest.source(query);
        //发送请求
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(search);
        System.out.println(JSON.toJSONString(search.getHits()));
    }

}
