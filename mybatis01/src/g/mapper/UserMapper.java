package g.mapper;

import java.util.List;

import g.pojo.User;

/**
 * mybatis动态代理的mapper接口
 * @author G
 *
 */
public interface UserMapper {

	public User findUserById(Integer id);
	
	//动态代理中,如果返回结果集为List,那么mybatis会在生成实现类的使用会自动调用selectList方法
	public List<User> findUserByUserName(String userName);
	
	public void insertUser(User user);
}
