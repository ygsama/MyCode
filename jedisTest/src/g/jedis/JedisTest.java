package g.jedis;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Jedis单独测试
 * @author G
 *
 */
public class JedisTest {

	@Test
	public void testJedis1(){
		// 创建连接
		Jedis jedis = new Jedis("ip地址", 6379);
		
		jedis.set("key1", "1");
		System.out.println(jedis.get("key1"));
		jedis.close();
	}
	
	@Test
	public void testJedisPool() {
		// 创建连接池
		JedisPool jedisPool = new JedisPool("106.14.149.247", 6379);
		
		Jedis jedis = jedisPool.getResource();
		jedis.set("key2", "2");
		System.out.println(jedis.get("key2"));
		// 关闭后自动回到连接池供其他人获取使用
		jedis.close();
	}
}
