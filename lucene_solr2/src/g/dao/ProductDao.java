package g.dao;

import org.apache.solr.client.solrj.SolrQuery;

import g.pojo.ResultModel;

public interface ProductDao {

	public ResultModel queryProduct(SolrQuery solrQuery) throws Exception ;
}
