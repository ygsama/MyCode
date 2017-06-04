package g.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import g.pojo.ResultModel;
import g.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	@RequestMapping("/list")
	public String list(String queryString, String catalog_name, String price, 
			String sort, Integer page, Model model) throws Exception{
		
		ResultModel result = productService.query(queryString, catalog_name, price, sort, page);
		// 返回查询结果
		model.addAttribute("result", result);
		
		model.addAttribute("queryString", queryString);
		model.addAttribute("catalog_name", catalog_name);
		model.addAttribute("price", price);
		model.addAttribute("sort", sort);
		return "product_list";
	}
	
}
