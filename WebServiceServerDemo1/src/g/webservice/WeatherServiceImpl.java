package g.webservice;

public class WeatherServiceImpl implements IWeatherService {

	@Override
	public String getWeatherByCityName(String cityName) {
		if("北京".equals(cityName)){
			return "晴";
		}else if ("上海".equals(cityName)) {
			return "小雨";
		}
		
		return "查询失败";
	}

	
}
