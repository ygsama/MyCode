package g.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import g.mapper.UserMapper;
import g.pojo.User;

public class UserMapperTest {

	private ApplicationContext applicationContext;
	
	@Before
	public void setup() throws Exception{
		String configLocation = "classpath:applicationContext.xml";
		applicationContext = new ClassPathXmlApplicationContext(configLocation);
	}
	
	@Test
	public void testFindUserById() throws Exception{
		//获取UserDao对象, getBean中的字符串是在ApplicationContext.xml中配置的
		UserMapper mapper = (UserMapper)applicationContext.getBean("userMapper");
		
		User user = mapper.findUserById(1);
		System.out.println(user);
	}
	
	
}
