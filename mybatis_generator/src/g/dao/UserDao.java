package g.dao;

import java.util.List;

import g.pojo.User;

public interface UserDao {

	public User findUserById(Integer id);
	
	public List<User> findUserByUserName(String userName);
}
