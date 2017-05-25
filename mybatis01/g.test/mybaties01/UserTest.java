package mybaties01;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import g.pojo.User;

/**
 * mybaties的CRUD测试
 * @author G
 *
 */
public class UserTest {

	@Test
	public void testFindUserById() throws Exception{
		String resource = "SqlMapConfig.xml";
		// 获得核心配置文件输入流
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// 通过核心配置文件输入流创建会话工厂
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
		// 通过工厂创建会话
		SqlSession openSession = factory.openSession();
		// 调用sql语句namespace.sql的id,传入sql参数
		User user = openSession.selectOne("test.findUserById",1);
		
		System.out.println(user);
		
		openSession.close();
	}
	
	@Test
	public void testFindUserByUserName() throws Exception{
		
		String resource = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession openSession = factory.openSession();
		List<User> list = openSession.selectList("test.findUserByUserName","王");
		for (User user : list) {	
			System.out.println(user);
		}
		openSession.close();
	}
	
	@Test
	public void testInsertUser() throws Exception{
		
		String resource = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession openSession = factory.openSession();
		User user = new User("张三","1",new Date(),"北京");
		System.out.println("id---->"+user.getId());
		int insert = openSession.insert("test.insertUser",user);
		//提交事务
		openSession.commit();
		openSession.close();
		System.out.println("insert---->"+insert);
		System.out.println("id---->"+user.getId());
	}
	
	@Test
	public void testDelUserById() throws Exception{
		
		String resource = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession openSession = factory.openSession();

		openSession.delete("test.delUserById",28);
		//提交事务
		openSession.commit();
		openSession.close();

	}
	
	@Test
	public void testUpdateUserById() throws Exception{
		
		String resource = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession openSession = factory.openSession();
		User user = new User();
		user.setId(1);
		user.setUsername("王麻子");
		openSession.update("test.updateUserById",user);
		//提交事务
		openSession.commit();
		openSession.close();
		
	}


	
}
