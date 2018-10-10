package org.plou.es.plugin;

import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.*;
import org.plou.es.plugin.executors.ActionRequestRestExecuterFactory;
import org.plou.es.plugin.executors.RestExecutor;
import org.plou.es.sql.SQLParseEngine;
import org.plou.es.sql.exception.SqlParseException;
import org.plou.es.sql.query.QueryAction;

import java.io.IOException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.*;


public class RestSqlAction extends BaseRestHandler {

//    public static final RestSqlAction INSTANCE = new RestSqlAction();


	public RestSqlAction(Settings settings, RestController restController) {
        super(settings);
		restController.registerHandler(RestRequest.Method.POST, "/_sql/_explain", this);
		restController.registerHandler(RestRequest.Method.GET, "/_sql/_explain", this);
		restController.registerHandler(RestRequest.Method.POST, "/_sql", this);
		restController.registerHandler(RestRequest.Method.GET, "/_sql", this);
	}

    @Override
    protected RestChannelConsumer prepareRequest(RestRequest request, NodeClient client) throws IOException {
        String sql = request.param("sql");

        if (sql == null) {
            sql = request.content().utf8ToString();
        }
        try {
        SQLParseEngine searchDao = new SQLParseEngine(client);
        QueryAction queryAction= null;

            queryAction = searchDao.buildQueryAction(sql);

        // TODO add unittests to explain. (rest level?)
        if (request.path().endsWith("/_explain")) {
            final String jsonExplanation = queryAction.explain().explain();
            return channel -> channel.sendResponse(new BytesRestResponse(RestStatus.OK, jsonExplanation));
        } else {
            Map<String, String> params = request.params();
            RestExecutor restExecutor = ActionRequestRestExecuterFactory.createExecutor(params.get("format"));
            final QueryAction finalQueryAction = queryAction;
            //doing this hack because elasticsearch throws exception for un-consumed props
            Map<String,String> additionalParams = new HashMap<>();
            for (String paramName : responseParams()) {
                if (request.hasParam(paramName)) {
                    additionalParams.put(paramName, request.param(paramName));
                }
            }
            return channel -> restExecutor.execute(client,additionalParams, finalQueryAction,channel);
        }
        } catch (SqlParseException | SQLFeatureNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Set<String> responseParams() {
        Set<String> responseParams = new HashSet<>(super.responseParams());
        responseParams.addAll(Arrays.asList("sql", "flat", "separator", "_score", "_type", "_id", "newLine", "format"));
        return responseParams;
    }
}