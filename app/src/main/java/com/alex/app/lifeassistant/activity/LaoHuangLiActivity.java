package com.alex.app.lifeassistant.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.app.lifeassistant.R;
import com.alex.app.lifeassistant.constans.Constans;
import com.alex.app.lifeassistant.domain.juhe.huangli.HuangLiBean;
import com.alex.app.lifeassistant.utils.ToastUtil;
import com.alex.app.lifeassistant.view.TitleBarView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 万年老黄历
 * <p/>
 * Created by alex.lee on 2015-07-06.
 */
public class LaoHuangLiActivity extends Activity {
	private Context mContext;
	private TitleBarView titleBarView;

	private TextView tvDay;
	private TextView tvXingzuo;
	private TextView tvYinliYear;
	private TextView tvYinliDate;
	private TextView tvDayCn;
	private TextView tvDayEng;
	private TextView tvYi;
	private TextView tvJi;
	private TextView tvJishen;
	private TextView tvXiongshen;
	private TextView tvBaiji;
	private TextView tvWuxing;
	private TextView tvChongSha;

	private ImageView ivXingzuo;
	private ImageView ivShengxiao;

	private DateTime dateTime;
	private Map<String, String> weekDay;

	private SweetAlertDialog loading;
	/**
	 * 生肖
	 */
	public static final String[] zodiacArr = {
			"猴", "鸡", "狗",
			"猪", "鼠", "牛",
			"虎", "兔", "龙",
			"蛇", "马", "羊"
	};
	/**
	 * 生肖图片
	 */
	private static final int[] zodiacImgArr = {
			R.drawable.lhl_monkey_red, R.drawable.lhl_chicken_red, R.drawable.lhl_dog_red,
			R.drawable.lhl_pig_red, R.drawable.lhl_mourse_red, R.drawable.lhl_cattle_red,
			R.drawable.lhl_tiger_red, R.drawable.lhl_rabbit_red, R.drawable.lhl_dragon_red,
			R.drawable.lhl_snake_red, R.drawable.lhl_horse_red, R.drawable.lhl_sheep_red,
	};
	/**
	 * 星座
	 */
	private static final String[] constellationArr = {
			"水瓶座", "双鱼座", "牡羊座",
			"金牛座", "双子座", "巨蟹座",
			"狮子座", "处女座", "天秤座",
			"天蝎座", "射手座", "魔羯座"
	};
	/**
	 * 星座图片
	 */
	private static final int[] constellationImgArr = {
			R.drawable.llh_aquarius_red, R.drawable.llh_pisces_red, R.drawable.llh_aries_red,
			R.drawable.llh_taurus_red, R.drawable.llh_gemini_red, R.drawable.llh_cancer_red,
			R.drawable.llh_leo_red, R.drawable.llh_virgo_red, R.drawable.llh_libra_red,
			R.drawable.llh_scorpio_red, R.drawable.llh_sagittarius_red, R.drawable.llh_capricorn_red
	};
	private static final int[] constellationEdgeDay = {
			20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_laohuangli);

		// 初始化界面
		initViews();
	}

	/**
	 * 初始化界面
	 */
	private void initViews() {
		//----------------------------------------------- 初始化其他对象
		mContext = LaoHuangLiActivity.this;
		dateTime = new DateTime(DateTimeZone.forID("Asia/Shanghai"));

		//----------------------------------------------- 初始化Dialog
		loading = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
		loading.getProgressHelper().setBarColor(getResources().getColor(R.color.lhl_base));
		loading.setTitleText("数据加载中...");
		loading.show();

		//----------------------------------------------- 初始化界面组件
		titleBarView = (TitleBarView) findViewById(R.id.title_bar);

		tvDay = (TextView) findViewById(R.id.tv_lhl_day);
		tvXingzuo = (TextView) findViewById(R.id.tv_lhl_xingzuo);
		tvYinliYear = (TextView) findViewById(R.id.tv_lhl_yinli_year);
		tvYinliDate = (TextView) findViewById(R.id.tv_lhl_yinli_date);
		tvDayCn = (TextView) findViewById(R.id.tv_lhl_day_cn);
		tvDayEng = (TextView) findViewById(R.id.tv_lhl_day_eng);
		tvYi = (TextView) findViewById(R.id.tv_lhl_yi);
		tvJi = (TextView) findViewById(R.id.tv_lhl_ji);
		tvJishen = (TextView) findViewById(R.id.tv_lhl_jishen);
		tvXiongshen = (TextView) findViewById(R.id.tv_lhl_xiongshen);
		tvBaiji = (TextView) findViewById(R.id.tv_lhl_baiji);
		tvWuxing = (TextView) findViewById(R.id.tv_lhl_wuxing);
		tvChongSha = (TextView) findViewById(R.id.tv_lhl_chongsha);

		ivXingzuo = (ImageView) findViewById(R.id.iv_lhl_xingzuo);
		ivShengxiao = (ImageView) findViewById(R.id.iv_lhl_shengxiao);

		//----------------------------------------------- 设置交互
		// 顶部Title
		titleBarView.showCentenrImg(true);
		titleBarView.setBackColor(getResources().getColor(R.color.lhl_base));
		titleBarView.setCenterImgClick(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ToastUtil.show(LaoHuangLiActivity.this, "选择日期");
			}
		});
		titleBarView.showLeftImgAndRightStr(getNormalDate(), "吉时", getResources().getDrawable(R.drawable.express_back),
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				},
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// 跳转到吉时界面
						startActivity(new Intent(mContext, JiShiActivity.class));
					}
				});

		// 当前日期显示
		tvDay.setText(DateTime.now().toString("dd"));
		// 执行数据加载
		dateQuery(getExactDate());
		// dialog消失
//		loading.dismiss();
	}

	/**
	 * 查询老黄历
	 *
	 * @param date 形如yyyy-MM-dd 的日期字符串
	 */
	public void dateQuery(final String date) {
		System.out.println("开始数据抓取");
		// 初始化参数
		Parameters params = new Parameters();
		params.add("date", date);

		JuheData.executeWithAPI(mContext, Constans.JUHE_HUANGLI_ID, Constans.JUHE_HUANGLI_URL, JuheData.POST, params,
				new DataCallBack() {
					@Override
					public void onSuccess(int i, String s) {
						System.out.println(s);
						JSONObject jsonObject = JSON.parseObject(s);
						if (jsonObject.getString("reason").equals("successed")) {
							HuangLiBean huangLi = JSON.parseObject(jsonObject.getString("result"), HuangLiBean.class);

							System.out.println(huangLi);

							String[] dates = huangLi.getYinli().split("月");
							String[] cs = huangLi.getChongsha().split("\\(|\\)");
							tvYinliYear.setText(dates[0] + "月");
							tvYinliDate.setText(dates[1]);
							tvDayCn.setText(getWeekDayCN(date));
							tvDayEng.setText(getWeekDayUK(date));
							tvChongSha.setText(cs[0] + cs[2]);
							tvXingzuo.setText(constellationArr[date2Constellation(date)]);
							tvYi.setText(huangLi.getYi());
							tvJi.setText(huangLi.getJi());
							tvJishen.setText(huangLi.getJishen());
							tvXiongshen.setText(huangLi.getXiongshen());
							tvWuxing.setText(huangLi.getWuxing());
							tvBaiji.setText(huangLi.getBaiji().replace(" ", "\n"));

							ivShengxiao.setImageResource(zodiacImgArr[date2Zodica(date)]);
							ivXingzuo.setImageResource(constellationImgArr[date2Constellation(date)]);
						}
					}

					@Override
					public void onFinish() {
						// dialog消失
						loading.dismiss();
					}

					@Override
					public void onFailure(int i, String s, Throwable throwable) {
						ToastUtil.show(mContext, throwable.getMessage());
					}
				});
	}

	/**
	 * 获取日期 - 精确月份
	 *
	 * @return yyyy-MM 形式的日期字符串
	 */
	public String getNormalDate() {
		return dateTime.toString("yyyy-MM");
	}

	/**
	 * 获取日期 - 精确日期
	 *
	 * @return yyyy-MM-dd 形式的日期字符串
	 */
	public String getExactDate() {
		return dateTime.toString("yyyy-MM-dd");
	}

	/**
	 * 获取今儿周几
	 *
	 * @param date 日期
	 *
	 * @return 周几
	 */
	public String getWeekDayCN(String date) {
		dateTime = DateTime.parse(date);
		return dateTime.toString("EEEE", Locale.CHINA);
	}

	/**
	 * 获取今儿周几
	 *
	 * @param date 日期
	 *
	 * @return 英文的周几
	 */
	public String getWeekDayUK(String date) {
		dateTime = DateTime.parse(date);
		return dateTime.toString("EEEE", Locale.UK);
	}

	/**
	 * 根据日期获取生肖
	 *
	 * @param date 日期
	 *
	 * @return 生肖
	 */
	public int date2Zodica(String date) {
		dateTime = DateTime.parse(date);
		return dateTime.getYearOfEra() % 12;
	}

	/**
	 * 根据日期获取星座
	 *
	 * @param date 日期
	 *
	 * @return 星座
	 */
	public int date2Constellation(String date) {
		dateTime = DateTime.parse(date);
		int month = dateTime.getMonthOfYear();
		int day = dateTime.getDayOfMonth();
		if (day < constellationEdgeDay[month - 1]) {
			month -= 1;
		}
		if (month == 12) {
			month = 0;
		}
		return month;
	}
}
