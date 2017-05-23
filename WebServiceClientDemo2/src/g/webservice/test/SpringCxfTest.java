package g.webservice.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import g.webservice.IWeatherService;

public class SpringCxfTest {

	@Test
	public void testCxf(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		IWeatherService iws = (IWeatherService) ac.getBean("weatherBean");
		
		String result = iws.getWeatherByCityName("上海");
		
		System.out.println(result);
	}
}
