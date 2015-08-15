package com.alex.app.lifeassistant.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.alex.app.lifeassistant.R;

/**
 * Created by alex.lee on 2015-07-02.
 */
// TODO: 2015-07-03 自定义dialog 还没完成 
public class CustomDialog extends Dialog {
	private Context context;
	
	public CustomDialog(Context context) {
		super(context);
		this.context = context;
	}
	
	public CustomDialog(Context context, int theme) {
		super(context, theme);
	}
	
	public static class Builder {
		private Context context;
		private String title;
		private String message;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private DialogInterface.OnClickListener positiveClickListener;
		private DialogInterface.OnClickListener negativeClickListener;
		
		public Builder setContext(Context context) {
			this.context = context;
			return this;
		}
		
		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}
		
		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}
		
		public Builder setPositiveButtonText(String positiveButtonText) {
			this.positiveButtonText = positiveButtonText;
			return this;
		}
		
		public Builder setNegativeButtonText(String negativeButtonText) {
			this.negativeButtonText = negativeButtonText;
			return this;
		}
		
		public Builder setContentView(View contentView) {
			this.contentView = contentView;
			return this;
		}
		
		public Builder setPositiveButton(int positiveButtonText, DialogInterface.OnClickListener listener) {
			this.positiveButtonText = (String) context.getText(positiveButtonText);
			this.positiveClickListener = listener;
			return this;
		}
		
		public Builder setPositiveButton(String positiveButtonText, DialogInterface.OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveClickListener = listener;
			return this;
		}
		
		public Builder setNegativeButton(int negativeButtonText, DialogInterface.OnClickListener listener) {
			this.negativeButtonText = (String) context.getText(negativeButtonText);
			this.negativeClickListener = listener;
			return this;
		}
		
		public Builder setNegativeButton(String negativeButtonText, DialogInterface.OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeClickListener = listener;
			return this;
		}
		
		public CustomDialog create() {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			CustomDialog dialog = new CustomDialog(context, R.style.custom_dialog);
			View layout = inflater.inflate(R.layout.dialog_custom, null);
			return null;
		}
	}
}
