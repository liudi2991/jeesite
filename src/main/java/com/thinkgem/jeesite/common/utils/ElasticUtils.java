package com.thinkgem.jeesite.common.utils;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.cms.entity.ArticleEs;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * es搜索文章
 *
 * @author ThinkGem
 * @version 2018-10-12
 */
public class ElasticUtils {

    /**
     * 相当于数据库名称（数据量小）
     */
    public static String indexName = Global.getConfig("elasticsearch.indexName");

    /**
     * es地址
     */
    public static String httpHost = Global.getConfig("elasticsearch.url");

    /**
     * es端口
     */
    public static Integer port = Integer.parseInt(Global.getConfig("elasticsearch.port"));



    /**
     * 初始化api客户端  可配置集群
     */
    public static RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(
                    new HttpHost(httpHost, port, "http")
            ));

    /**
     * 关键字搜索 指定匹配类型
     *
     * @param type
     * @param fieldNames 列名
     * @param keyword    关键字
     * @param start      开始
     * @param count      数量
     * @param sort       排序(倒序)
     * @return
     * @throws IOException
     */
    public static Map search(String type, String keyword, int start, int count, String sort, String... fieldNames) throws IOException {

        //定义返回map
        Map<String, Object> result = new HashMap<>(2);

        SearchRequest searchRequest = new SearchRequest(indexName);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(keyword, fieldNames);
        sourceBuilder.query(queryBuilder);

        //哪条开始查询
        sourceBuilder.from(start);
        //页数量
        sourceBuilder.size(count);


        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        for(String field : fieldNames){
            //高亮field
            highlightBuilder.field(field);
        }
        //高亮标签
        highlightBuilder.preTags("<em>").postTags("</em>");
        //高亮内容长度
        highlightBuilder.fragmentSize(200);
        sourceBuilder.highlighter(highlightBuilder);

        //排序, 需要设置fielddata为true
        if (StringUtils.isNotBlank(sort)) {
            //匹配度从高到低
            sourceBuilder.sort(sort, (SortOrder.DESC));
        }
        searchRequest.source(sourceBuilder);
        searchRequest.types(type);
        SearchResponse searchResponse = client.search(searchRequest);

        SearchHits hits = searchResponse.getHits();
        //获取总数
        long total = hits.getTotalHits();
        List<Map<String, Object>> matchRsult = new LinkedList<Map<String, Object>>();

        for (SearchHit hit : hits.getHits()) {
            matchRsult.add(hit.getSourceAsMap());
        }
        result.put("list", matchRsult);
        result.put("total", total);
        return result;
    }

    /**
     * 删除指定类型
     *
     * @param object
     * @throws IOException
     */
    public static void deleteDocument(Object object) throws IOException {
        if (object instanceof ArticleEs) {
            DeleteRequest deleteRequest = new DeleteRequest(indexName, "article", ((ArticleEs) object).getId());
            System.out.println("已经从ElasticSearch服务器上删除id=" + ((ArticleEs) object).getId() + "的article文档");
            client.delete(deleteRequest);
        }
    }

    /**
     * 获得指定type指定id的数据 json
     *
     * @param type
     * @param id
     * @return
     * @throws IOException
     */
    public static Map getDocument(String type, String id) throws IOException {
        // TODO Auto-generated method stub
        GetRequest request = new GetRequest(
                indexName,
                type,
                id);

        GetResponse response = client.get(request);

        if (!response.isExists()) {
            System.out.println("检查到服务器上 " + type + " id=" + id + "的文档不存在");
            return null;
        } else {
            String source = response.getSourceAsString();
            System.out.print("获取到服务器上 " + type + " id=" + id + "的文档内容是：");
            System.out.println(source);
            return response.getSourceAsMap();
        }
    }

    /**
     * 插入指定type，数据
     *
     * @param object
     * @throws IOException
     */
    public static void addDocument(Object object) throws IOException {
        Map<String, Object> jsonMap = new HashMap<String, Object>(4);
        if (object instanceof ArticleEs) {
            jsonMap.put("id", ((ArticleEs) object).getId());
            jsonMap.put("title", ((ArticleEs) object).getTitle());
            jsonMap.put("keyword", ((ArticleEs) object).getKeyword());
            jsonMap.put("content", ((ArticleEs) object).getContent());
            jsonMap.put("weight", ((ArticleEs) object).getWeight());
            IndexRequest indexRequest = new IndexRequest(indexName, "article", ((ArticleEs) object).getId())
                    .source(jsonMap);
            client.index(indexRequest);
            System.out.println("已经向ElasticSearch服务器增加article：" + object);
        }

    }

    /**
     * 更新数据
     *
     * @param object
     * @throws IOException
     */
    public static void updateDocument(Object object) throws IOException {

        if (object instanceof ArticleEs) {
            UpdateRequest updateRequest = new UpdateRequest(indexName, "article", ((ArticleEs) object).getId())
                    .doc("title", ((ArticleEs) object).getTitle())
                    .doc("keyword", ((ArticleEs) object).getKeyword())
                    .doc("detail", ((ArticleEs) object).getContent())
                    .doc("weight", ((ArticleEs) object).getWeight());
            client.update(updateRequest);
            System.out.println("已经在ElasticSearch服务器修改产品为：" + object);
        }


    }

    private static boolean checkExistIndex(String indexName) throws IOException {
        boolean result = true;
        try {

            OpenIndexRequest openIndexRequest = new OpenIndexRequest(indexName);
            client.indices().open(openIndexRequest).isAcknowledged();

        } catch (ElasticsearchStatusException ex) {
            String m = "Elasticsearch exception [type=index_not_found_exception, reason=no such index]";
            if (m.equals(ex.getMessage())) {
                result = false;
            }
        }
        if (result) {
            System.out.println("索引:" + indexName + " 是存在的");

        } else {
            System.out.println("索引:" + indexName + " 不存在");

        }
        return result;

    }

    private static void deleteIndex(String indexName) throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        client.indices().delete(request);
        System.out.println("删除了索引：" + indexName);

    }

    private static void createIndex(String indexName) throws IOException {
        // TODO Auto-generated method stub
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        client.indices().create(request);
        System.out.println("创建了索引：" + indexName);
    }

    // 批量插入
//    private static void batchInsert(List<Product> products) throws IOException {
//        // TODO Auto-generated method stub
//        BulkRequest request = new BulkRequest();
//
//        for (Product product : products) {
//            Map<String,Object> m  = product.toMap();
//            IndexRequest indexRequest= new IndexRequest(indexName, "product", String.valueOf(product.getId())).source(m);
//            request.add(indexRequest);
//        }
//
//        client.bulk(request);
//        System.out.println("批量插入完成");
//    }
    public static String getIndexName() {
        return indexName;
    }

    public static void setIndexName(String indexName) {
        ElasticUtils.indexName = indexName;
    }

    public static RestHighLevelClient getClient() {
        return client;
    }

    public static void setClient(RestHighLevelClient client) {
        ElasticUtils.client = client;
    }

    public static void main(String[] args) {
        try {
            Map a = search("article","内容",0,100,null,"content");
            System.out.println(a);
        }catch (Exception e) {

        }
    }

}
