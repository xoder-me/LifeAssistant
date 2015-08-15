package com.alex.app.lifeassistant.constans;

/**
 * Created by alex.lee on 2015-06-30.
 */
public interface Constans {
	// 图灵机器人
	String TULING_URL = "http://www.tuling123.com/openapi/api";
	String TULING_KEY = "91c333d9cca63d218bdbf0f420cb3164";
	//文本类数据
	int MSG_TYPE_TEXT = 100000;  // 文本
	int MSG_TYPE_URL = 200000;   // 网址
	int MSG_TYPE_TRAIN = 305000; // 列车
	int MSG_TYPE_AIR = 306000;   // 航班
	int MSG_TYPE_NEWS = 302000;  // 新闻
	int MSG_TYPE_OTHER = 308000; // 菜谱、视频、小说

	// API Store
	// 新闻
	String API_NEWS_URL = "http://apis.baidu.com/showapi_open_bus/channel_news/search_news";
	String API_STORE_KEY = "e108402221c65b6390d28ecdf12fdbdd";
	// Channel
	/**
	 * 国内最新
	 */
	String NEW_GUO_NEI = "5572a109b3cdc86cf39001db";
	/**
	 * 社会最新
	 */
	String NEW_SHE_HEUI = "5572a10bb3cdc86cf39001f8";
	/**
	 * 国际最新
	 */
	String NEW_GUO_JI = "5572a109b3cdc86cf39001de";
	/**
	 * 房产焦点
	 */
	String NEW_DIAN_YING = "5572a10ab3cdc86cf39001ec";

	// 聚合数据
	// 快递查询
	int JUHE_EXPRESS_ID = 43;
	String JUHE_EXPRESS_URL = "http://v.juhe.cn/exp/index";
	String JUHE_EXPRESS_COM = "http://v.juhe.cn/exp/com";
	// 生活常用
	//   彩票
	int JUHE_LOTTERY_ID = 53;
	String JUHE_LOTTERY_URL = "http://v.juhe.cn/lottery/query";
	//   黄历
	int JUHE_HUANGLI_ID = 65;
	String JUHE_HUANGLI_URL = "http://v.juhe.cn/laohuangli/d";
	String JUHE_HUANGLI_TIME = "http://v.juhe.cn/laohuangli/h";

	//易源数据
	int YIYUAN_APP_ID = 1953;
	String YIYUAN_SIGN = "0968a89403c3438ca6317a5f27065854";
}