package com.alex.app.lifeassistant.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alex.app.lifeassistant.R;
import com.alex.app.lifeassistant.fragment.ChatMachineFragment;
import com.alex.app.lifeassistant.fragment.EntertainmentFragment;
import com.alex.app.lifeassistant.fragment.ExpressFragment;
import com.alex.app.lifeassistant.fragment.HomeFragment;
import com.alex.app.lifeassistant.fragment.OftenUsedFragment;
import com.alex.app.lifeassistant.fragment.PaymentFragment;
import com.alex.app.lifeassistant.fragment.SettingFragment;
import com.alex.app.lifeassistant.fragment.WeatherFragment;
import com.alex.app.lifeassistant.view.TitleBarView;
import com.slidingmenu.lib.SlidingMenu;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
	// 日常使用界面的内容
	private int[] oftenImgs = {
			R.drawable.lottery_query, R.drawable.lhl_logo, R.drawable.icon_flashlight,
			R.drawable.slide_menu_often_used, R.drawable.compass, R.drawable.flow_manager,
			R.drawable.icon_note, R.drawable.slide_menu_setting, R.drawable.slide_menu_more
	};

	private String[] oftenTitles = {
			"开奖查询", "老黄的历", "小手电",
			"今日要闻", "指南的针", "流量管理",
			"助手便签", "生活小助手", "敬请期待"
	};

	private Class[] oftenClazz = {
			null, LaoHuangLiActivity.class, FlashLightActivity.class, NewsActivity.class, CompassActivity.class, FlowManagerActivity.class, NoteActivity.class
	};

	// 影音娱乐界面的内容
	private int[] enterImgs = {
			R.drawable.lottery_query, R.drawable.lhl_logo, R.drawable.icon_flashlight,
			R.drawable.slide_menu_often_used, R.drawable.compass, R.drawable.flow_manager,
			R.drawable.slide_menu_more, R.drawable.slide_menu_setting, R.drawable.slide_menu_payment
	};

	private String[] enterTitles = {
			"开奖查询", "老黄的历", "小手电",
			"今日要闻", "指南的针", "流量管理",
			"生活小助手", "生活小助手", "生活小助手"
	};

	private Class[] enterClazz = {
			null, LaoHuangLiActivity.class, FlashLightActivity.class, NewsActivity.class, CompassActivity.class, FlowManagerActivity.class
	};

	private TitleBarView titleBarView;

	private Fragment fgHome;
	private Fragment fgOftenUsed;
	private Fragment fgExpress;
	private Fragment fgWeather;
	private Fragment fgEntertainment;
	private Fragment fgPayment;
	private Fragment fgChatMachine;
	private Fragment fgSetting;
	private Fragment fgCurrent;

	private RelativeLayout rlGotoHome;
	private RelativeLayout rlOftenUsed;
	private RelativeLayout rlExpressQuery;
	private RelativeLayout rlWeatherQuery;
	private RelativeLayout rlEntertainment;
	private RelativeLayout rlPayment;
	private RelativeLayout rlChatMachine;
	private RelativeLayout rlSetting;

	public String com;

	// 侧滑布局
	private View slideView;
	// 侧滑列表
	private SlidingMenu slideMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
	}

	/**
	 * 初始化界面
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void initView() {
		initSlideMenu();

		if (fgHome == null) {
			fgHome = new HomeFragment();
		}

		if (!fgHome.isAdded()) {
			getSupportFragmentManager().beginTransaction().add(R.id.frame_container, fgHome).commit();
		}

		fgCurrent = fgHome;

		titleBarView = (TitleBarView) findViewById(R.id.title_bar);
		titleBarView.showLeftImg("主页",
				getResources().getDrawable(R.drawable.main_navigation),
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						slideMenu.toggle();
					}
				});
	}

	@Override
	public void onBackPressed() {

	}

	/**
	 * 初始化侧滑菜单
	 */
	private void initSlideMenu() {
		// 初始化侧滑视图
		slideView = View.inflate(MainActivity.this, R.layout.menu_slide, null);
		// 初始化侧滑菜单
		slideMenu = new SlidingMenu(this);
		slideMenu.setMode(SlidingMenu.LEFT);
		slideMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slideMenu.setShadowWidthRes(R.dimen.slide_menu_shadow);
		slideMenu.setShadowDrawable(R.drawable.slide_menu_shadow);
		slideMenu.setBehindOffsetRes(R.dimen.slide_menu_behind);
		slideMenu.setFadeDegree(0.35f);
		slideMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slideMenu.setMenu(slideView);

		// 初始化侧滑菜单项目栏
		initSlideMenuItem();
	}

	/**
	 * 初始化侧滑菜单栏项目
	 */
	private void initSlideMenuItem() {
		rlGotoHome = (RelativeLayout) slideView.findViewById(R.id.rl_gob_to_home);
		rlOftenUsed = (RelativeLayout) slideView.findViewById(R.id.rl_often_used);
		rlExpressQuery = (RelativeLayout) slideView.findViewById(R.id.rl_express_query);
		rlWeatherQuery = (RelativeLayout) slideView.findViewById(R.id.rl_weather_query);
		rlEntertainment = (RelativeLayout) slideView.findViewById(R.id.rl_entertainment);
		rlPayment = (RelativeLayout) slideView.findViewById(R.id.rl_payment);
		rlChatMachine = (RelativeLayout) slideView.findViewById(R.id.rl_chat_machine);
		rlSetting = (RelativeLayout) slideView.findViewById(R.id.rl_setting);

		rlGotoHome.setOnClickListener(this);
		rlOftenUsed.setOnClickListener(this);
		rlExpressQuery.setOnClickListener(this);
		rlWeatherQuery.setOnClickListener(this);
		rlEntertainment.setOnClickListener(this);
		rlPayment.setOnClickListener(this);
		rlChatMachine.setOnClickListener(this);
		rlSetting.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		slideMenu.toggle();
		switch (v.getId()) {
			case R.id.rl_gob_to_home:
				titleBarView.setTitle("主页");

				if (fgHome == null) {
					fgHome = new HomeFragment();
				}
				addOrShowFragmet(getSupportFragmentManager().beginTransaction(), fgHome);

				break;
			case R.id.rl_often_used:
				titleBarView.setTitle(getContentById(R.id.tv_often_used));

				if (fgOftenUsed == null) {
					fgOftenUsed = new OftenUsedFragment(oftenImgs, oftenTitles, oftenClazz);
				}
				addOrShowFragmet(getSupportFragmentManager().beginTransaction(), fgOftenUsed);

				break;
			case R.id.rl_express_query:
				titleBarView.setTitle(getContentById(R.id.tv_express_query));

				if (fgExpress == null) {
					fgExpress = new ExpressFragment();
				}
				addOrShowFragmet(getSupportFragmentManager().beginTransaction(), fgExpress);

				break;
			case R.id.rl_weather_query:
				titleBarView.setTitle(getContentById(R.id.tv_weather_query));

				if (fgWeather == null) {
					fgWeather = new WeatherFragment();
				}
				addOrShowFragmet(getSupportFragmentManager().beginTransaction(), fgWeather);

				break;
			case R.id.rl_entertainment:
				titleBarView.setTitle(getContentById(R.id.tv_entertainment));

				if (fgEntertainment == null) {
					fgEntertainment = new EntertainmentFragment(enterImgs, enterTitles, enterClazz);
				}
				addOrShowFragmet(getSupportFragmentManager().beginTransaction(), fgEntertainment);

				break;
			case R.id.rl_payment:
				titleBarView.setTitle(getContentById(R.id.tv_payment));

				if (fgPayment == null) {
					fgPayment = new PaymentFragment();
				}
				addOrShowFragmet(getSupportFragmentManager().beginTransaction(), fgPayment);

				break;
			case R.id.rl_chat_machine:
				titleBarView.setTitle(getContentById(R.id.tv_chat_machine));

				if (fgChatMachine == null) {
					fgChatMachine = new ChatMachineFragment();
				}
				addOrShowFragmet(getSupportFragmentManager().beginTransaction(), fgChatMachine);

				break;
			case R.id.rl_setting:
				titleBarView.setTitle(getContentById(R.id.tv_setting));

				if (fgSetting == null) {
					fgSetting = new SettingFragment();
				}
				addOrShowFragmet(getSupportFragmentManager().beginTransaction(), fgSetting);

				break;
		}
	}

	/**
	 * 添加或显示Fragment
	 *
	 * @param transaction fragment事务
	 * @param fragment    fragment
	 */
	private void addOrShowFragmet(FragmentTransaction transaction, Fragment fragment) {
		if (fragment == fgCurrent) {
			return;
		}

		if (!fragment.isAdded()) {
			transaction.hide(fgCurrent).add(R.id.frame_container, fragment).commit();
		} else {
			transaction.hide(fgCurrent).show(fragment).commit();
		}

		fgCurrent = fragment;
	}

	/**
	 * 根据资源ID获取TextView内容
	 *
	 * @param resId 资源ID
	 *
	 * @return TextView里的文本
	 */
	private String getContentById(int resId) {
		return ((TextView) findViewById(resId)).getText().toString();
	}
}
