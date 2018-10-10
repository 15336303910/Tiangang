package cn.plou.component.es;

//import static cn.plou.common.constant.ESConstant.ES_ADDR;
//import static cn.plou.common.constant.ESConstant.ES_CLIENT_CONCURRENT_LIMIT;
//import static cn.plou.common.constant.ESConstant.ES_CLIENT_QUEUE_MAX_SIZE;
//import static cn.plou.common.constant.ESConstant.ES_CLIENT_QUEUE_SIZE;
//import static cn.plou.common.constant.ESConstant.ES_CLUSTER_NAME;
//import static cn.plou.common.constant.ESConstant.ES_CLUSTER_NAME_CONFIG;
//import static cn.plou.common.constant.ESConstant.INDEX_TYPE;
//import static cn.plou.common.constant.ESConstant.PRIMARY_ID;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

import org.apache.commons.lang.time.StopWatch;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.ClearScrollRequestBuilder;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequestBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.InternalNumericMetricsAggregation.SingleValue;
import org.elasticsearch.search.aggregations.metrics.valuecount.InternalValueCount;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.plou.es.sql.SQLParseEngine;
import org.plou.es.sql.exception.SqlParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;

import cn.plou.common.constant.CKey;
import cn.plou.common.constant.LogicSymbol;
import cn.plou.common.utils.Tools;
import cn.plou.common.custom.exception.ElasticSearchException;
import cn.plou.common.custom.function.ESFunction;
import cn.plou.common.utils.EsScript;

/**
 * @Project : tg-micro
 * @File : ESTool.java
 * @Author : WangJiWei
 * @Date : 2018年5月14日下午1:16:54
 *
 * @Comments :
 * 
 */
// @Component
public class ESTool {
    private static final Logger log = LoggerFactory.getLogger(ESTool.class);
    private static BlockingQueue<Client> cache = null; /* 缓存所有es连接 */
    private final static TimeValue TV = new TimeValue(6000);/* 需要保持搜索的上下文环境多长时间 */
    public static DruidDataSource esSource = null;
    private Map<String, Object> config;

    public ESTool(Map<String, Object> config) {
	this.config = config;
    }

    public Map<String, Object> getConfig() {
	return Collections.unmodifiableMap(this.config);
    }

    public void setConfig(Map<String, Object> config) {
	this.config = config;
    }

    public void start() throws UnknownHostException {
	Assert.notEmpty(this.config, "this map must not be empty");
	Integer queueSize = (Integer) config.get(ESToolConfig.ES_CLIENT_QUEUE_SIZE);
	cache = Queues.newLinkedBlockingQueue(queueSize);
	for (int i = 0; i < queueSize; i++) {
	    Client client = createESClient();
	    if (client != null) {
		cache.add(client);
	    }
	}
	log.info("====== ES Client is inited successfully =====");
    }

    /**
     * 
     */
    private Client createESClient() throws UnknownHostException {
	Settings settings = Settings.builder().put(ESToolConfig.ES_CLUSTER_NAME_CONFIG,
		(String) this.config.get(ESToolConfig.ES_CLUSTER_NAME)).build();
	TransportClient client = new PreBuiltTransportClient(settings);
	try {
	    client.addTransportAddresses(
		    parseESAddr((String) this.config.get(ESToolConfig.ES_ADDR)));
	} catch (UnknownHostException e) {
	    e.printStackTrace();
	    log.error("es client init exception", e);
	    throw e;
	}
	return client;
    }

    /**
     * 返回es集群监控状况
     */
    public ESHealth clusterHealth() {
	Client client = null;
	try {
	    client = getClient();
	    ESHealth esHealth = new ESHealth();
	    ClusterHealthResponse healths = client.admin().cluster().prepareHealth().get();
	    esHealth.setClusterName(healths.getClusterName());
	    esHealth.setNumberOfDataNodes(healths.getNumberOfDataNodes());
	    esHealth.setNumberOfNodes(healths.getNumberOfNodes());
	    healths.getIndices().values().forEach(health -> {
		IndexInfo index = new IndexInfo();
		index.setIndexName(health.getIndex());
		index.setNumberOfShards(health.getNumberOfShards());
		index.setNumberOfReplicas(health.getNumberOfReplicas());
		index.setClusterHealthStatus(health.getStatus());
		esHealth.getIndexs().add(index);
	    });
	    return esHealth;
	} finally {
	    releaseClient(client);
	}
    }

    private InetSocketTransportAddress[] parseESAddr(String esAddr)
	    throws NumberFormatException, UnknownHostException {
	List<InetSocketTransportAddress> list = Lists.newArrayList();
	for (String hostAndPort : CKey.COMMA_P.split(esAddr)) {
	    String[] fields = CKey.COLON_P.split(hostAndPort);
	    list.add(new InetSocketTransportAddress(InetAddress.getByName(fields[0]),
		    Integer.parseInt(fields[1])));
	}
	return list.toArray(new InetSocketTransportAddress[] {});
    }

    /**
     * 获取连接
     */
    public Client getClient() {
	// 如果配置了阻塞,则当Client-Queue没有可用Client时阻塞
	Client client = null;
	try {
	    boolean flag = (boolean) this.config.get(ESToolConfig.ES_CLIENT_CONCURRENT_LIMIT);
	    if (flag) {/* true */
		client = cache.take();
	    } else {
		client = cache.poll();
		if (client == null) {
		    log.info("*** esClient-cache is empty. Preparing to create a new client");
		    Integer maxSize = (Integer) this.config
			    .get(ESToolConfig.ES_CLIENT_QUEUE_MAX_SIZE);
		    if (cache.size() < maxSize) {
			client = createESClient();
		    } else {
			log.info(
				"The esClient-cache is already the maximum size and Wait for a valid connection.");
			client = cache.take();
		    }
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    log.error(e.getMessage());
	}
	return client;
    }

    /**
     * 创建索引<br>
     * 默认setting : <number_of_shards,3> , <number_of_replicas,1>
     */
    public boolean createIndex(String index, @Nullable Map<String, Object> setting) {
	Assert.notNull(index, "No index defined");
	Client client = null;
	try {
	    /** 默认 */
	    Map<String, Object> sets = Maps.newHashMap();
	    sets.put("index.number_of_shards", 3);
	    sets.put("index.number_of_replicas", 1);
	    if (setting != null) {
		sets.putAll(setting);
	    }
	    client = getClient();
	    CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices()
		    .prepareCreate(index);
	    createIndexRequestBuilder.setSettings(setting);
	    return createIndexRequestBuilder.execute().actionGet().isAcknowledged();
	} catch (Exception e) {
	    e.printStackTrace();
	    log.error(e.getMessage());
	    throw new ElasticSearchException(e.getMessage());
	} finally {
	    releaseClient(client);
	}
    }

    /**
     * 索引是否存在
     */
    public Boolean isExist(String index) {
	Assert.notNull(index, "No index defined");
	Client client = null;
	try {
	    client = getClient();
	    IndicesExistsRequest inExistsRequest = new IndicesExistsRequest(index);
	    IndicesExistsResponse inExistsResponse = client.admin().indices()
		    .exists(inExistsRequest).actionGet();
	    return inExistsResponse.isExists();
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new ElasticSearchException(e.getMessage());
	} finally {
	    releaseClient(client);
	}
    }

    public Boolean isExist(Client client, String index) {
	Assert.notNull(index, "No index defined");
	IndicesExistsRequest isExistsRequest = new IndicesExistsRequest(index);
	IndicesExistsResponse isExistsResponse = client.admin().indices()//
		.exists(isExistsRequest).actionGet();
	return isExistsResponse.isExists();
    }

    /**
     * 删除索引
     */
    public boolean deleteIndex(String index) {
	Assert.notNull(index, "No index defined");
	Client client = null;
	try {
	    client = getClient();
	    if (isExist(client, index)) {
		return client.admin().indices().prepareDelete(index).execute().get()
			.isAcknowledged();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new ElasticSearchException(e.getMessage());
	} finally {
	    releaseClient(client);
	}
	return false;
    }

    /**
     * 索引设置mapping
     */
    @SuppressWarnings({ "deprecation", "rawtypes" })
    public boolean putMapping(String index, Object mapping) {
	Assert.notNull(index, "No index defined");
	Assert.notNull(mapping, "No mapping defined");
	Client client = null;
	try {
	    client = getClient();
	    PutMappingRequestBuilder requestBuilder = client.admin().indices()
		    .preparePutMapping(index).setType(ESToolConfig.INDEX_TYPE);
	    if (mapping instanceof String) {
		requestBuilder.setSource(String.valueOf(mapping));
	    } else if (mapping instanceof Map) {
		requestBuilder.setSource((Map) mapping);
	    } else if (mapping instanceof XContentBuilder) {
		requestBuilder.setSource((XContentBuilder) mapping);
	    }
	    return requestBuilder.execute().actionGet().isAcknowledged();
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new ElasticSearchException(e.getMessage());
	} finally {
	    releaseClient(client);
	}
    }

    /**
     * 获取指定ID的文档
     * 
     * @throws Exception
     */
    public Map<String, Object> get(String index, String id) {
	Assert.notNull(index, "No index defined");
	Assert.notNull(id, "No id defined");
	Client client = null;
	Map<String, Object> map = null;
	try {
	    client = getClient();
	    map = client.prepareGet(index, ESToolConfig.INDEX_TYPE, id)//
		    .get().getSource();
	} finally {
	    releaseClient(client);
	}
	return map;
    }

    /**
     * 根据多个ID查询
     */
    public List<Map<String, Object>> multiGet(String index, String... ids) {
	Assert.notNull(index, "No index defined");
	Assert.notNull(ids, "No ids defined");
	Client client = null;
	try {
	    client = getClient();
	    List<Map<String, Object>> list = Lists.newArrayList();
	    MultiGetRequestBuilder builder = client.prepareMultiGet();
	    builder.add(index, ESToolConfig.INDEX_TYPE, ids);
	    builder.get().forEach(itr -> {
		GetResponse res = itr.getResponse();
		if (res.isExists()) {
		    list.add(res.getSource());
		}
	    });
	    return list;
	} finally {
	    releaseClient(client);
	}
    }

    public boolean add(String index, Map<String, Object> map) {
	return bulkAdd(index, Arrays.asList(map));
    }

    /**
     * 批量添加
     */
    // public <T> boolean bulkAdd(String index, List<T> list) {
    // Assert.notNull(index, "No index defined");
    // if (list == null || list.size() == 0) {
    // return true;
    // }
    // Client client = null;
    // try {
    // client = getClient();
    // final BulkRequestBuilder bulkRequest = client.prepareBulk();
    // for (Object t : list) {
    // bulkRequest.add(client.prepareIndex(index,
    // INDEX_TYPE).setSource(JSON.toJSONString(t),XContentType.JSON))
    // ;
    // }
    // BulkResponse response = bulkRequest.get();
    // if (response.hasFailures()) {
    // log.error("Failure of batch processing.");
    // return false;
    // }
    // // refresh(index);
    // return true;
    // } catch (Exception e) {
    // e.printStackTrace();
    // throw new ElasticSearchException(e.getMessage());
    // } finally {
    // releaseClient(client);
    // }
    // }

    /**
     * 批量添加
     */
    public boolean bulkAdd(String index, List<Map<String, Object>> list) {
	Assert.notNull(index, "No index defined");
	if (list == null || list.size() == 0) {
	    return true;
	}
	Client client = null;
	try {
	    client = getClient();
	    final BulkRequestBuilder bulkRequest = client.prepareBulk();
	    for (Map<String, Object> map : list) {
		IndexRequestBuilder req = client.prepareIndex(index, ESToolConfig.INDEX_TYPE)
			.setSource(map);
		String pid = (String) map.get(ESToolConfig.PRIMARY_ID);
		if (!Tools.isNull(pid)) {
		    req.setId(pid);
		}
		bulkRequest.add(req);
	    }
	    BulkResponse response = bulkRequest.get();
	    if (response.hasFailures()) {
		log.error(response.buildFailureMessage());
		return false;
	    }
	    // refresh(index);
	    return true;
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new ElasticSearchException(e.getMessage());
	} finally {
	    releaseClient(client);
	}
    }

    /**
     * 更新文档,指定字段指定值
     */
    public void update(String index, String id, String key, Object value) {
	Assert.notNull(index, "No index defined");
	Assert.notNull(id, "No id defined");
	Client client = null;
	try {
	    client = getClient();
	    XContentBuilder doc = XContentFactory.jsonBuilder()//
		    .startObject()//
		    .field(key, value)//
		    .endObject();
	    client.prepareUpdate(index, ESToolConfig.INDEX_TYPE, id).setDoc(doc).get();
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new ElasticSearchException("ES update failure");
	} finally {
	    releaseClient(client);
	}
    }

    public void update(String index, String id, Map<String, Object> kv) {
	Assert.notNull(index, "No index defined");
	Assert.notNull(id, "No id defined");
	Assert.notNull(kv, "No kv defined");
	Client client = null;
	try {
	    client = getClient();
	    XContentBuilder doc = XContentFactory.jsonBuilder().map(kv);//
	    client.prepareUpdate(index, ESToolConfig.INDEX_TYPE, id).setDoc(doc).get();
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new ElasticSearchException("ES update failure");
	} finally {
	    releaseClient(client);
	}
    }

    /**
     * 刷新索引
     */
    public void refresh(String indexName) {
	Assert.notNull(indexName, "No index defined");
	Client client = null;
	try {
	    client = getClient();
	    client.admin().indices().refresh(Requests.refreshRequest(indexName)).actionGet();
	} catch (Exception e) {
	    e.printStackTrace();
	    log.error(e.getMessage());
	    throw new ElasticSearchException("ES'index refresh failure");
	} finally {
	    releaseClient(client);
	}
    }

    /**
     * 删除指定ID的文档
     */
    public void delete(String index, String id) {
	Assert.notNull(index, "No index defined");
	Assert.notNull(id, "No id defined");
	Client client = null;
	try {
	    client = getClient();
	    client.prepareDelete(index, ESToolConfig.INDEX_TYPE, id).get();
	} catch (Exception e) {
	    e.printStackTrace();
	    log.error(e.getMessage());
	    throw new ElasticSearchException("ES'index delete failure");
	} finally {
	    releaseClient(client);
	}
    }

    /**
     * 返回搜索到的结果集
     */
    private SearchHits searchHits(Client client, String index, String jsonName,
	    @Nullable Map<String, Object> conditions) {
	SearchResponse res = buildSearchResponse(client, index, jsonName, conditions);
	return res.getHits();
    }

//    private SearchHits searchHits(Client client, String index, String sql)
//	    throws SQLFeatureNotSupportedException, SqlParseException {
//	SearchResponse res = buildSearchResponse(client, index, sql);
//	return res.getHits();
//    }

    /**
     * 返回搜索到的结果集
     */
    public SearchHits searchHits(String index, String jsonName,
	    @Nullable Map<String, Object> conditions) {
	Client client = getClient();
	SearchResponse res = buildSearchResponse(client, index, jsonName, conditions);
	releaseClient(client);
	return res.getHits();
    }

    /**
     * 查询
     */
    public List<Map<String, Object>> search(String index, String jsonName,
	    @Nullable Map<String, Object> conditions) {
	Assert.notNull(index, "No index defined");
	Client client = null;
	List<Map<String, Object>> list;
	try {
	    client = getClient();
	    list = Lists.newArrayList();
	    SearchHits hits = searchHits(client, index, jsonName, conditions);
	    hits.forEach(h -> {
		list.add(h.getSource());
	    });
	} finally {
	    releaseClient(client);
	}
	return list;
    }

    /**
     * 查询
     */
//    public List<Map<String, Object>> search(String index, String sql)
//	    throws SQLFeatureNotSupportedException, SqlParseException {
//	Assert.notNull(index, "No index defined");
//	Client client = null;
//	List<Map<String, Object>> list;
//	try {
//	    client = getClient();
//	    list = Lists.newArrayList();
//	    SearchHits hits = searchHits(client, index, sql);
//	    hits.forEach(h -> {
//		list.add(h.getSource());
//	    });
//	} finally {
//	    releaseClient(client);
//	}
//	return list;
//    }

    /**
     * 查询
     */
//    public SearchHits searchForHits(String index, String sql)
//	    throws SQLFeatureNotSupportedException, SqlParseException {
//	Assert.notNull(index, "No index defined");
//	Client client = null;
//	try {
//	    client = getClient();
//	    SearchHits hits = searchHits(client, index, sql);
//	    return hits;
//	} finally {
//	    releaseClient(client);
//	}
//    }

    /**
     * 指定查询返回实体
     */
    public <T> List<T> search(String index, String jsonName,
	    @Nullable Map<String, Object> conditions, Class<T> clzz) {
	List<Map<String, Object>> lms = search(index, jsonName, conditions);
	List<T> list = JSON.parseArray(JSON.toJSONString(lms), clzz);
	return list;
    }

    /**
     * 指定查询返回实体
     * 
     * @throws SqlParseException
     * @throws SQLFeatureNotSupportedException
     */
//    public <T> List<T> search(String index, String sql, Class<T> clzz)
//	    throws SQLFeatureNotSupportedException, SqlParseException {
//	List<Map<String, Object>> lms = search(index, sql);
//	List<T> list = JSON.parseArray(JSON.toJSONString(lms), clzz);
//	return list;
//    }

    /**
     * 指定查询返回实体
     * 
     * @throws SqlParseException
     * @throws SQLFeatureNotSupportedException
     */
//    public <T> List<T> aggsSearch(String index, String sql, Class<T> clzz)
//	    throws SQLFeatureNotSupportedException, SqlParseException {
//	Assert.notNull(index, "No index defined");
//	Assert.notNull(sql, "No sql defined");
//	Client client = getClient();
//	SQLParseEngine parseEngine = new SQLParseEngine(client);
//	String script = parseEngine.explain(sql);
//	// TODO
//	return null;
//    }

    /**
     * 小数据量下查询10000以下
     * 
     * @throws SqlParseException
     * @throws SQLFeatureNotSupportedException
     */
//    public SearchResponse buildSearchResponse(Client client, String index, String sql)
//	    throws SQLFeatureNotSupportedException, SqlParseException {
//	Assert.notNull(client, "No client defined");
//	SearchResponse response = buildSearchResponse(sql, index, client);
//	return response;
//    }

    /**
     * 小数据量下查询10000以下
     */
    public SearchResponse buildSearchResponse(Client client, String index, String jsonName,
	    @Nullable Map<String, Object> conditions) {
	Assert.notNull(index, "No index defined");
	Assert.notNull(client, "No client defined");
	String script = EsScript.read(jsonName);
	SearchResponse response = new SearchTemplateRequestBuilder(client)//
		.setScript(script)//
		.setScriptType(ScriptType.INLINE)//
		.setScriptParams(conditions)//
		.setRequest(new SearchRequest().indices(index).types(ESToolConfig.INDEX_TYPE))//
		.get()//
		.getResponse();
	return response;
    }

    /**
     * 滚动查询,大数据量下使用(历史数据的大批量导出)
     */
    public SearchResponse buildScrollResponse(Client client, String index, String jsonName,
	    @Nullable Map<String, Object> conditions) {
	String script = EsScript.read(jsonName);
	// TODO 新增特性
	// SliceBuilder sliceBuilder = new SliceBuilder(0, 3);
	SearchRequest req = client.prepareSearch(index).setTypes(ESToolConfig.INDEX_TYPE)//
		.setScroll(TV)/* .slice(sliceBuilder) */.request();
	SearchResponse response = new SearchTemplateRequestBuilder(client)//
		.setScript(script)//
		.setScriptType(ScriptType.INLINE)//
		.setScriptParams(conditions)//
		.setRequest(req)//
		.get()//
		.getResponse();
	return response;
    }

    /**
     * 
     * 
     * @Comments : 滚动处理数据
     * @param func
     *            : 处理数据的逻辑函数
     */
    public void scrollHandle(String index, String jsonName,
	    @Nullable Map<String, Object> conditions, ESFunction<SearchResponse> func) {
	Assert.notNull(index, "No index defined");
	Assert.notNull(func, "No func defined");
	Client client = null;
	try {
	    client = getClient();
	    List<String> scrollIds = Lists.newArrayList();
	    StopWatch watch = new StopWatch();
	    watch.start();
	    SearchResponse response = buildScrollResponse(client, index, jsonName, conditions);
	    do {
		String scrollId = response.getScrollId();
		scrollIds.add(scrollId);
		func.handle(response);
		response = client.prepareSearchScroll(scrollId).setScroll(TV).get();
	    } while (checkRes(response));
	    // 不支持聚合后的滚动处理
	    clearScroll(client, scrollIds);/** 及时清除滚动 */
	    watch.stop();
	    System.out.println("cost time = " + watch.getTime());
	} finally {
	    releaseClient(client);
	}
    }

    private boolean checkRes(SearchResponse response) {
	return null != response && 0 != response.getHits().getHits().length;
    }

    public boolean clearScroll(Client client, List<String> scrollIdList) {
	ClearScrollRequestBuilder clearScrollRequestBuilder = client.prepareClearScroll();
	clearScrollRequestBuilder.setScrollIds(scrollIdList);
	ClearScrollResponse response = clearScrollRequestBuilder.get();
	return response.isSucceeded();
    }

    /**
     * 清除滚动
     * 
     */
    public boolean clearScroll(Client client, String scrollId) {
	ClearScrollRequestBuilder clearScrollRequestBuilder = client.prepareClearScroll();
	clearScrollRequestBuilder.addScrollId(scrollId);
	ClearScrollResponse response = clearScrollRequestBuilder.get();
	return response.isSucceeded();
    }

    /**
     * 查询并返回json
     * 
     * @throws Exception
     */
    public JSONObject searchAndReturnJSON(String index, String jsonName,
	    @Nullable Map<String, Object> conditions) {
	Assert.notNull(index, "No index defined");
	Client client = null;
	JSONObject result;
	try {
	    client = getClient();
	    result = new JSONObject();
	    SearchResponse response = buildSearchResponse(client, index, jsonName, conditions);
	    response.getHits().forEach(h -> {
		result.put(h.getId(), h.getSource());
	    });
	} finally {
	    releaseClient(client);
	}
	return result;
    }

    /**
     * 查询并返回聚合Aggregations
     */
    public Aggregations searchForAgg(String index, String jsonName,
	    @Nullable Map<String, Object> conditions) {
	Assert.notNull(index, "No index defined");
	Client client = null;
	Aggregations result;
	try {
	    client = getClient();
	    SearchResponse response = buildSearchResponse(client, index, jsonName, conditions);
	    result = response.getAggregations();
	} finally {
	    releaseClient(client);
	}
	return result;
    }

//    public Long count(String index, String sql)
//	    throws SQLFeatureNotSupportedException, SqlParseException {
//	Assert.notNull(sql, "sql is not null.");
//	Client client = getClient();
//	try {
//	    SearchResponse response = buildSearchResponse(sql, index, client);
//	    Aggregations aggs1 = response.getAggregations();
//	    for (Aggregation g : aggs1) {
//		Long coun = ((InternalValueCount) g).getValue();
//		return coun;
//	    }
//	    return null;
//	} finally {
//	    releaseClient(client);
//	}
//    }
    
    /**
     * @throws SqlParseException
     * @throws SQLFeatureNotSupportedException
     * 
     */
//    public List<Map<String, Object>> searchForAggBySQL_1(String index, String sql)
//	    throws SQLFeatureNotSupportedException, SqlParseException {
//	Assert.notNull(sql, "sql is not null.");
//	Client client = getClient();
//	try {
//	    List<Map<String, Object>> list = new ArrayList<>();
//	    SearchResponse response = buildSearchResponse(sql, index, client);
//	    Aggregations aggs1 = response.getAggregations();
//	    for (Aggregation g : aggs1) {
//		Terms gradeTerms = (Terms) g;
//		Iterator<? extends Bucket> gradeBucketIt = gradeTerms.getBuckets().iterator();
//		while (gradeBucketIt.hasNext()) {
//		    Bucket gradeBucket = gradeBucketIt.next();
//		    Aggregations aggss = gradeBucket.getAggregations();
//		    Map<String, Object> map = null;
//		    map = new HashMap<>();
//		    for (Aggregation c : aggss) {
//			map.put(gradeTerms.getName(), gradeBucket.getKey());
//			if (c == null) {
//			    continue;
//			}
//			if (c instanceof InternalValueCount) {
//			    map.put(c.getName(), ((InternalValueCount) c).getValue());
//			} else {
//			    map.put(c.getName(), ((SingleValue) c).value());
//			}
//		    }
//		    list.add(map);
//		}
//	    }
//	    return list;
//	} finally {
//	    releaseClient(client);
//	}
//    }

//    public List<Map<String, Object>> searchForAggBySQL_2(String index, String sql)
//	    throws SQLFeatureNotSupportedException, SqlParseException {
//	Assert.notNull(sql, "sql is not null.");
//	Client client = getClient();
//	try {
//	    List<Map<String, Object>> list = new ArrayList<>();
//	    SearchResponse response = buildSearchResponse(sql, index, client);
//	    Aggregations aggs = response.getAggregations();
//	    for (Aggregation g : aggs) {
//		Terms gradeTerms = (Terms) g;
//		Iterator<? extends Bucket> gradeBucketIt = gradeTerms.getBuckets().iterator();
//		while (gradeBucketIt.hasNext()) {
//		    Bucket gradeBucket = gradeBucketIt.next();
//		    Aggregations aggss = gradeBucket.getAggregations();
//		    for (Aggregation gg : aggss) {
//			StringTerms classTerms = (StringTerms) gg;
//			Iterator<? extends Bucket> classBucketIt = classTerms.getBuckets()
//				.iterator();
//			Map<String, Object> map = null;
//			while (classBucketIt.hasNext()) {
//			    Bucket classBucket = classBucketIt.next();
//			    Aggregations aggregations = classBucket.getAggregations();
//			    map = new HashMap<>();
//			    map.put(gradeTerms.getName(), gradeBucket.getKey());
//			    map.put(classTerms.getName(), classBucket.getKey());
//			    for (Aggregation c : aggregations) {
//				if (c == null) {
//				    continue;
//				}
//				if (c instanceof InternalValueCount) {
//				    map.put(c.getName(), ((InternalValueCount) c).getValue());
//				} else {
//				    map.put(c.getName(), ((SingleValue) c).value());
//				}
//			    }
//			    list.add(map);
//			}
//		    }
//		}
//	    }
//	    return list;
//	} finally {
//	    releaseClient(client);
//	}
//    }

//    private SearchResponse buildSearchResponse(String sql, String index, Client client)
//	    throws SqlParseException, SQLFeatureNotSupportedException {
//	SQLParseEngine sqlParse = new SQLParseEngine(client);
//	String script = sqlParse.explain(sql);
//	SearchResponse response = new SearchTemplateRequestBuilder(client)//
//		.setScript(script)//
//		.setScriptType(ScriptType.INLINE)//
//		.setRequest(new SearchRequest().indices(index).types(ESToolConfig.INDEX_TYPE))//
//		.get()//
//		.getResponse();
//	return response;
//    }

    /**
     * 分桶处理<br>
     * // TODO 下钻
     */
    public void bucketHandle(String index, String jsonName,
	    @Nullable Map<String, Object> conditions, String aggName, Consumer<Bucket> func) {
	Aggregations aggs = searchForAgg(index, jsonName, conditions);
	if (aggs != null) {
	    Terms aggregation = aggs.get(aggName);
	    List<? extends Bucket> buckets = aggregation.getBuckets();
	    buckets.forEach(func);
	}
    }

    public void releaseClient(Client client) {
	if (client != null) {
	    if (!cache.offer(client)) {
		log.info("ES cache is full, close client");
		client.close();
	    }
	}
    }

    /**
     * 查询删除
     * 
     * @throws Exception
     */
    public long deleteByQuery(String index, Map<String, Object> conditions, LogicSymbol sym)
	    throws Exception {
	BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
	if (sym == LogicSymbol.AND) {
	    conditions.forEach((k, v) -> {
		queryBuilder.must(QueryBuilders.termQuery(k, v));
	    });
	} else if (sym == LogicSymbol.OR) {
	    conditions.forEach((k, v) -> {
		queryBuilder.should(QueryBuilders.termQuery(k, v));
	    });
	}
	return deleteByQuery(index, queryBuilder);
    }

    /**
     * 通过条件查询删除 => delete from ${index} where ${queryBuilder}
     *
     */
    public long deleteByQuery(String index, QueryBuilder queryBuilder) throws Exception {
	Client client = null;
	try {
	    client = getClient();
	    BulkByScrollResponse response = DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
		    .filter(queryBuilder).source(index).get();
	    long deleted = response.getDeleted();
	    return deleted;
	} finally {
	    releaseClient(client);
	}
    }

    public static void main(String[] args) throws Exception {
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("province", "江苏");
	// map.put("latency", 68);
	// new ESTool().deleteByQuery("website", map, LogicSymbol.AND);

	// List<Map<String, Object>> list = Lists.newArrayList();
	// Map<String, Object> e = new HashMap<>();
	//
	// e.put("sid", "1355");
	// e.put("sname", "asd");
	// e.put("snote", "123");
	// e.put("flee", 15.6);
	// e.put("speed", 20.5);
	// e.put("timestamp", 10000);
	//
	// list.add(e);
	// ESHelper2.add("ttt5", "data", e);

	// Map<String, Object> map = Maps.newHashMap();
	// map.put("size", 10);
	// JSONObject json = ESHelper2.searchAndReturnJSON("ttt", "data",
	// "search", map);
	// System.out.println(json);
	// ESTool.start();
	// Map<String, Object> map = Maps.newHashMap();
	// map.put("timestamp_from", 1525916762250L);
	// map.put("timestamp_to", 1525916762500L);
	// Aggregations agg = ESTool.searchForAgg("ttt", "sum", map);
	// InternalSum s = agg.get("sss");
	// System.out.println(s.value());

	// Instant start = Instant.now();
	// ESTool.bucketHandle("heatbiaohis", "heat/meter_increment_daily",
	// null,
	// "group_by_meterid", b -> {
	// Aggregations ag = b.getAggregations();
	// SingleValue max = ag.get("maxf");
	// SingleValue min = ag.get("minf");
	// System.out.println(b.getKey() + " : " + max.value() + " - " +
	// min.value());
	// });
	// System.out.println("cost is = "+Duration.between(start,
	// Instant.now()).toMillis());
    }

}
