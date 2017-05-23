package g.webservice;

import javax.jws.WebService;

@WebService    //注解，Webservice标志
public interface IWeatherService {

	/**
	 * 根据城市名返回天气结果
	 * @param cityName
	 * @return
	 */
	public String getWeatherByCityName(String cityName);
}
