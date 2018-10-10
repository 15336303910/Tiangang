package org.plou.es.plugin.executors;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.RestChannel;
import org.plou.es.sql.query.QueryAction;
import org.plou.es.sql.query.SqlElasticRequestBuilder;

import java.util.Map;

/**
 * Created by Eliran on 26/12/2015.
 */
public interface RestExecutor {
    public void execute(Client client, Map<String, String> params, QueryAction queryAction, RestChannel channel) throws Exception;

    public String execute(Client client, Map<String, String> params, QueryAction queryAction) throws Exception;
}
