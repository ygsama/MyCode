package g.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import g.pojo.ProductModel;
import g.pojo.ResultModel;

@Repository
public class ProductDaoImpl implements ProductDao{

	@Autowired
	private SolrServer solrServer;
	
	@Override
	public ResultModel queryProduct(SolrQuery solrQuery) throws Exception {
		// 查询，获得响应
		QueryResponse queryResponse = solrServer.query(solrQuery);
		// 从响应中获取document结果集
		SolrDocumentList documentList = queryResponse.getResults();
		// 创建返回的结果对象
		ResultModel resultModel = new ResultModel();
		List<ProductModel> productList = new ArrayList<ProductModel>();
		// 遍历结果集
		if(documentList != null){
			resultModel.setRecordCount(documentList.getNumFound());
			for (SolrDocument doc : documentList) {
				ProductModel productModel = new ProductModel();
				productModel.setPid(String.valueOf(doc.get("id")));
				// 高亮
				Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
				if(highlighting!=null){
					List<String> list = highlighting.get(doc.get("id")).get("product_name");
					if(list!=null && list.size()>0){
						productModel.setName(list.get(0));
					}else{
						productModel.setName(String.valueOf(doc.get("product_name")));
					}
				}else{
					productModel.setName(String.valueOf(doc.get("product_name")));
				}
				if(doc.get("product_price")!=null && !"".equals(doc.get("product_price"))){
					productModel.setPrice(Float.valueOf(doc.get("product_price").toString()));
				}
				productModel.setPicture(String.valueOf(doc.get("product_picture")));
				productModel.setCatalog_name(String.valueOf(doc.get("product_catalog_name")));
				productList.add(productModel);
			}
			resultModel.setProductList(productList);
		}
		return resultModel;
	}
}
