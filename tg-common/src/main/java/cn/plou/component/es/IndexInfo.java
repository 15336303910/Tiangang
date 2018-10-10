package cn.plou.component.es;

import org.elasticsearch.cluster.health.ClusterHealthStatus;

/** 
 * @Project : tg-micro 
 * @File : IndexInfo.java
 * @Author : WangJiWei
 * @Date : 2018年6月1日上午9:16:08 
 *
 * @Comments : 
 * 
 */
public class IndexInfo {

    private String indexName;
    private int numberOfShards;
    private int numberOfReplicas;
    private ClusterHealthStatus clusterHealthStatus;
    public String getIndexName() {
        return indexName;
    }
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }
    public int getNumberOfShards() {
        return numberOfShards;
    }
    public void setNumberOfShards(int numberOfShards) {
        this.numberOfShards = numberOfShards;
    }
    public int getNumberOfReplicas() {
        return numberOfReplicas;
    }
    public void setNumberOfReplicas(int numberOfReplicas) {
        this.numberOfReplicas = numberOfReplicas;
    }
    public ClusterHealthStatus getClusterHealthStatus() {
        return clusterHealthStatus;
    }
    public void setClusterHealthStatus(ClusterHealthStatus clusterHealthStatus) {
        this.clusterHealthStatus = clusterHealthStatus;
    }
    
}

