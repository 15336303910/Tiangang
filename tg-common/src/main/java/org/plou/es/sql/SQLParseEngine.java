package org.plou.es.sql;

import java.sql.SQLFeatureNotSupportedException;
import org.elasticsearch.client.Client;
import org.plou.es.sql.exception.SqlParseException;
import org.plou.es.sql.query.ESActionFactory;
import org.plou.es.sql.query.QueryAction;

public class SQLParseEngine {

    private Client client = null;

    public SQLParseEngine(Client client) {
	this.client = client;
    }

    public Client getClient() {
	return client;
    }

    /**
     * Prepare action And transform sql into ES ActionRequest
     * 
     * @param sql
     *            SQL query to execute.
     * @return ES request
     * @throws SqlParseException
     */
    public QueryAction buildQueryAction(String sql)
	    throws SqlParseException, SQLFeatureNotSupportedException {
	return ESActionFactory.create(client, sql);
    }

    public String explain(String sql) throws SqlParseException, SQLFeatureNotSupportedException {
	return buildQueryAction(sql).explain().explain();
    }

}
