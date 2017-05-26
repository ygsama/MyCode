package g.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import g.dao.UserDao;
import g.pojo.User;

public class UserDaoTest {

	private ApplicationContext applicationContext;
	
	@Before
	public void setup() throws Exception{
		String configLocation = "classpath:applicationContext.xml";
		applicationContext = new ClassPathXmlApplicationContext(configLocation);
	}
	
	@Test
	public void testFindUserById() throws Exception{
		//获取UserDao对象, getBean中的字符串是在ApplicationContext.xml中配置的
		UserDao userDao = (UserDao)applicationContext.getBean("userDao");
		
		User user = userDao.findUserById(1);
		System.out.println(user);
	}
	
	
}
