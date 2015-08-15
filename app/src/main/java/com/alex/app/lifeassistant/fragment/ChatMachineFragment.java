package com.alex.app.lifeassistant.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.alex.app.lifeassistant.R;
import com.alex.app.lifeassistant.adapter.ChatMessageAdapter;
import com.alex.app.lifeassistant.constans.Constans;
import com.alex.app.lifeassistant.domain.tuling.ChatMessage;
import com.alex.app.lifeassistant.utils.ToastUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex.lee on 2015-06-30.
 */
public class ChatMachineFragment extends Fragment {
	/**
	 * 展示消息的listview
	 */
	private ListView mChatView;
	/**
	 * 文本域
	 */
	private EditText mMsg;
	private Button mSend;
	/**
	 * 存储聊天消息
	 */
	private List<ChatMessage> mDatas = new ArrayList<>();
	/**
	 * 适配器
	 */
	private ChatMessageAdapter mAdapter;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_chat_machine, container, false);
		initViews(view);

		mAdapter = new ChatMessageAdapter(getActivity(), mDatas);
		mChatView.setAdapter(mAdapter);

		return view;
	}
	
	private void initViews(View view) {
		mMsg = (EditText) view.findViewById(R.id.et_msg);
		mSend = (Button) view.findViewById(R.id.btn_msg);
		mChatView = (ListView) view.findViewById(R.id.lv_chat);
		mDatas.add(new ChatMessage(ChatMessage.Type.INPUT, "您好, 很高兴为您服务~ "));

		mMsg.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mSend.setBackgroundResource(R.drawable.chat_btn_active);
				mSend.setTextColor(Color.WHITE);
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		mSend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 1. 判断输入情况
				final String info = mMsg.getText().toString();
				if (TextUtils.isEmpty(info)) {
					ToastUtil.show(getActivity(), "您还没输入内容呢!");
					return;
				}
				// 把发送的消息更新到界面上
				updateSendUI(info);

				// 2. 发送请求
				RequestParams params = new RequestParams("utf-8");
				params.addBodyParameter("key", Constans.TULING_KEY);
				params.addBodyParameter("info", info);
				final HttpUtils http = new HttpUtils(3000);
				http.send(HttpRequest.HttpMethod.POST, Constans.TULING_URL, params,
						new RequestCallBack<String>() {
							@Override
							public void onSuccess(ResponseInfo<String> responseInfo) {
								// 3. 接收返回数据 更新界面
								try {
									ChatMessage from = new ChatMessage(ChatMessage.Type.INPUT, new JSONObject(responseInfo.result).getString("text"));
									mDatas.add(from);

									mAdapter.notifyDataSetChanged();
									mChatView.setSelection(mDatas.size() - 1);
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}

							@Override
							public void onFailure(HttpException e, String s) {
								ToastUtil.show(getActivity(), "失败 -> " + s);
							}
						});
			}
		});
	}

	/**
	 * 将发送想消息更新到界面上
	 *
	 * @param info 发送的消息
	 */
	private void updateSendUI(String info) {
		ChatMessage to = new ChatMessage(ChatMessage.Type.OUTPUT, info);
		mDatas.add(to);

		mMsg.setText("");

		mAdapter.notifyDataSetChanged();
		mChatView.setSelection(mDatas.size() - 1);

		// 改变软键盘状态
//		changeIMEStatus();
	}

	/**
	 * 改变软键盘状态
	 */
	private void changeIMEStatus() {
		// 关闭软键盘
		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		// 得到InputMethodManager的实例
		if (imm.isActive()) {
			// 如果开启
			imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
			// 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
		}
	}
}
