package com.alex.app.lifeassistant.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.alex.app.lifeassistant.R;

import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.View.MeasureSpec.UNSPECIFIED;
import static android.view.View.MeasureSpec.makeMeasureSpec;

/**
 * Created by alex.lee on 2015-07-05.
 */
public class GridLayoutView extends ViewGroup {
	int count = 0;
	int colums = 2;
	int margin = 2;// 格子间默认间距

	private GridLayoutAdapter adapter;

	public GridLayoutView(Context context) {
		super(context, null);
	}

	public GridLayoutView(Context context, AttributeSet attrs) {
		super(context, attrs);

		if (attrs != null) {
			TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.GridLayoutView);
			margin = array.getInteger(R.styleable.GridLayoutView_item_margin, 2);
			colums = array.getInteger(R.styleable.GridLayoutView_item_columns, 2);
		}
	}

	public GridLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		count = getChildCount();

		if (count == 0) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			return;
		}

		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() == GONE) {
				continue;
			}

			child.measure(UNSPECIFIED, UNSPECIFIED);
		}

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int left;
		int top = margin;
		int width = r - l; // 布局区域宽度
		int height = b - t;// 布局区域高度
		int rows = count % colums == 0 ? count / colums : count / colums + 1; // 控制行数
		if (count == 0) {
			return;
		}
		int girdHeight = (height - margin * rows) / rows;                     // 控制格子高度
		int gridWidth = (width - margin * (colums - 1)) / colums;             // 控制格子宽度

		for (int i = 0; i < rows; i++) {// 遍历行
			for (int j = 0; j < colums; j++) { // 遍历每行元素
				View child = this.getChildAt(i * colums + j);

				if (child == null) {
					return;
				}

				left = j * gridWidth + j * margin;
				// 若果当前布局宽度和测量宽度不一样, 就直接用当前布局的宽度重新测量
				if (gridWidth != child.getMeasuredWidth() || girdHeight != child.getMeasuredHeight()) {
					child.measure(makeMeasureSpec(gridWidth, EXACTLY), makeMeasureSpec(girdHeight, EXACTLY));
				}

				child.layout(left, top, left + gridWidth, top + girdHeight);
			}
			top += girdHeight + margin;
		}
	}

	public interface GridLayoutAdapter {
		int getCount();

		View getView(int index);
	}

	public interface OnItemClickListener {
		void onItemClick(View view, int index);
	}

	/**
	 * 设置适配器
	 *
	 * @param adapter 适配器
	 */
	public void setAdapter(GridLayoutAdapter adapter) {
		this.adapter = adapter;
		// 动态添加视图
		for (int i = 0; i < adapter.getCount(); i++) {
			addView(adapter.getView(i));
		}
	}

	/**
	 * 设置Item 点击事件
	 *
	 * @param listener 监听器
	 */
	public void setOnItemClickListener(final OnItemClickListener listener) {
		if (this.adapter == null) {
			return;
		}
		
		for (int i = 0; i < adapter.getCount(); i++) {
			final int index = i;
			View child = getChildAt(index);
			child.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					listener.onItemClick(v, index);
				}
			});
		}
	}
}
