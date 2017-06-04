package g.service;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import g.dao.ProductDao;
import g.pojo.ResultModel;

@Service
public class ProductServiceImpl implements ProductService {

	private static final Integer PAGE_SIZE = 60;
	
	@Autowired
	private ProductDao productDao;

	@Override
	public ResultModel query(String queryString, String catalog_name, String price, String sort, Integer page)
			throws Exception {
		//创建查询条件对象
		SolrQuery solrQuery = new SolrQuery();
		//设置默认搜索域
		solrQuery.set("df", "product_keywords");
		//设置查询关键字
		if(queryString != null && !"".equals(queryString)){
			solrQuery.setQuery(queryString);
		} else {
			solrQuery.setQuery("*:*");
		}
		
		//设置过滤条件按照分类名称进行过滤
		if(catalog_name != null && !"".equals(catalog_name)){
			solrQuery.addFilterQuery("product_catalog_name:" + catalog_name);
		}
		//设置过滤条件按照价格进行过滤
		if(price != null && !"".equals(price)){
			String[] split = price.split("-");
			if(split != null && split.length > 1){
				solrQuery.addFilterQuery("product_price:["+split[0]+" TO "+split[1]+"]");
			}
		}
		//设置排序
		if("1".equals(sort)){
			solrQuery.addSort("product_price", ORDER.asc);
		} else {
			solrQuery.addSort("product_price", ORDER.desc);
		}
		
		//设置分页
		if(page == null){
			page = 1;
		}
		Integer start = (page - 1) * PAGE_SIZE;
		//从第几天记录开始查
		solrQuery.setStart(start);
		//每页显示多少条
		solrQuery.setRows(PAGE_SIZE);
		
		//设置高亮显示
		solrQuery.setHighlight(true);
		//设置高亮显示的域
		solrQuery.addHighlightField("product_name");
		//设置高亮前缀
		solrQuery.setHighlightSimplePre("<span style=\"color:red\">");
		//设置高亮后缀
		solrQuery.setHighlightSimplePost("</span>");
		
		//查询返回结果
		ResultModel resultModel = productDao.queryProduct(solrQuery);
		
		resultModel.setCurPage(Long.parseLong(page.toString()));
		
		//计算总页数
		Long pageCount = resultModel.getRecordCount() / PAGE_SIZE;
//		if(resultModel.getRecordCount() % PAGE_SIZE > 0){
//			pageCount ++;
//		}
		resultModel.setPageCount(resultModel.getRecordCount() % PAGE_SIZE > 0 ? ++pageCount : pageCount);
		return resultModel;
	}
}
