package g.webservice.invoking;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.Test;

import g.webservice.IWeatherService;

public class WeatherServiceInvokingClient {

	@Test
	public void testWeather(){
		
		// 生成一个客户端代理工厂
		JaxWsProxyFactoryBean client = new JaxWsProxyFactoryBean();
		
		// 设置服务端访问地址
		client.setAddress("http://localhost:12345/weather?wsdl");
		
		// 设置服务端接口
		client.setServiceClass(IWeatherService.class);
		
		// 创建客户端对象
		IWeatherService iws = (IWeatherService) client.create();
		
		// 调用远程服务端提供的方法
		String result = iws.getWeatherByCityName("北京");
		
		System.out.println(result);
	}
}
