package g.publisher;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

import g.webservice.WeatherServiceImpl;

public class PublisherTest {

	public static void main(String[] args) {
		// 1.创建一个工厂类
		JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
		
		// 2.设置服务地址
		factory.setAddress("http://localhost:12345/weather");
		
		// 3.设置提供服务的实现类
		factory.setServiceBean(new WeatherServiceImpl());
		
		// 4.创建webservice服务
		factory.create();
		
		// 运行main方法后，浏览器可以访问http://localhost:12345/weather?wsdl查看说明
		// 使用jdk的wsimport工具获得代码，wsimport -s . http://localhost:12345/weather?wsdl
	}
}
