package g.service;

import g.pojo.ResultModel;

public interface ProductService {

	public ResultModel query(String queryString, String catalog_name, String price, 
			String sort, Integer page) throws Exception;
}
