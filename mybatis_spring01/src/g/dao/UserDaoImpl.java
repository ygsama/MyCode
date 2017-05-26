package g.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import g.pojo.User;

public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {

	@Override
	public User findUserById(Integer id) {
		//sqlSesion是线程不安全的,所以它的最佳使用范围在方法体内
		SqlSession openSession = this.getSqlSession();
		User user = openSession.selectOne("test.findUserById", id);
		return user;
	}

	@Override
	public List<User> findUserByUserName(String userName) {
		SqlSession openSession = this.getSqlSession();
		List<User> list = openSession.selectList("test.findUserByUserName", userName);
		return list;
	}
	
	
}
