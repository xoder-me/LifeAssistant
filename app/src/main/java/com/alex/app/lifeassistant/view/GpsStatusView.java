package com.alex.app.lifeassistant.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.alex.app.lifeassistant.R;

public class GpsStatusView extends View {
	private float mYaw = 0;
	private float mRotation = 0;
	private int mW = 0;
	private int mH = 0;
	private float mRelativeSize = 1;

	private Paint activePaint;
	private Paint inactivePaint;
	private Paint northPaint;
	private Paint gridPaint;
	private Paint gridBorderPaint;
	private Paint labelPaint;
	private Path northArrow = new Path();
	private Path labelPathN = new Path();
	private Path labelPathE = new Path();
	private Path labelPathS = new Path();
	private Path labelPathW = new Path();

	
	//FIXME: these two should be DPI-dependent, this is OK for MDPI
	private int gridStrokeWidth = 2;

	// Compensation for display rotation. Use Surface.ROTATION_* as index (0, 90, 180, 270 deg).
	@SuppressWarnings("boxing")
	private final static Integer zeroYaw[] = {0, 90, 180, 270};
	
	public GpsStatusView(Context context) {
		super(context);
		doInit();
	}

	public GpsStatusView(Context context, AttributeSet attrs) {
		super(context, attrs);
		doInit();
	}
	
	public GpsStatusView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		doInit();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int mSize = (int) (Math.min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec)) * mRelativeSize);
		setMeasuredDimension(mSize, mSize);
	}

	private void doInit() {
		activePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		activePaint.setColor(Color.parseColor("#FF33B5E5"));
		activePaint.setStyle(Paint.Style.FILL);
		
		inactivePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		inactivePaint.setColor(Color.parseColor("#FFFF4444"));
		inactivePaint.setStyle(Paint.Style.FILL);
		
		gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		gridPaint.setColor(Color.parseColor("#FFFF8800"));
		gridPaint.setStyle(Paint.Style.STROKE);
		gridPaint.setStrokeWidth(gridStrokeWidth);
		
		gridBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		gridBorderPaint.setColor(Color.parseColor("#50FF8800"));
		gridBorderPaint.setStyle(Paint.Style.STROKE);
		
		northPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		northPaint.setColor(Color.parseColor("#FFCC0000"));
		northPaint.setStyle(Paint.Style.FILL);
		
		labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		labelPaint.setColor(Color.parseColor("#FFFF8800"));
		labelPaint.setStyle(Paint.Style.FILL);
		labelPaint.setTextAlign(Paint.Align.CENTER);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		int cx = mW / 2;
		int cy = mH / 2;

		//Log.d("GpsStatusView", String.format("Drawing on a %dx%d canvas", w, h));

		canvas.translate(cx, cy);
		canvas.rotate(-mRotation);
		
		canvas.drawCircle(0, 0, mW * 0.37125f, gridBorderPaint);
		
		canvas.drawLine(-mW * 0.405f, 0, mW * 0.405f, 0, gridPaint);
		canvas.drawLine(0, -mH * 0.405f, 0, mH * 0.405f, gridPaint);
		
		canvas.drawCircle(0, 0, mW * 0.405f, gridPaint);
		canvas.drawCircle(0, 0, mW * 0.27f, gridPaint);
		canvas.drawCircle(0, 0, mW * 0.135f, gridPaint);
		
		canvas.drawPath(northArrow, northPaint);
		
		canvas.drawTextOnPath(getContext().getString(R.string.value_N), labelPathN, 0, -labelPaint.descent(), labelPaint);
		canvas.drawTextOnPath(getContext().getString(R.string.value_S), labelPathS, 0, -labelPaint.descent(), labelPaint);
		canvas.drawTextOnPath(getContext().getString(R.string.value_E), labelPathE, 0, -labelPaint.descent(), labelPaint);
		canvas.drawTextOnPath(getContext().getString(R.string.value_W), labelPathW, 0, -labelPaint.descent(), labelPaint);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mW = w;
		mH = h;
		refreshGeometries();
	}
	
	public void refreshGeometries() {
		gridBorderPaint.setStrokeWidth(mW * 0.0625f);
		
		northArrow.reset();
		northArrow.moveTo(-8, -mH * 0.27f);
		northArrow.lineTo(8, -mH * 0.27f);
		northArrow.lineTo(0, -mH * 0.405f - gridStrokeWidth * 2);
		northArrow.close();

		labelPaint.setTextSize(mH * 0.045f);
		
		float offsetX = mW * 0.0275f * (float) Math.cos(Math.toRadians(mRotation + 90));
		float offsetY = mW * 0.0275f * (float) Math.sin(Math.toRadians(mRotation + 90));
		float relX = mW * (float) Math.cos(Math.toRadians(mRotation));
		float relY = mH * (float) Math.sin(Math.toRadians(mRotation));
		
		labelPathN.reset();
		labelPathN.moveTo(offsetX - relX, -mH * 0.4275f + offsetY - relY);
		labelPathN.rLineTo(2 * relX, 2 * relY);
		
		labelPathE.reset();
		labelPathE.moveTo(mW * 0.4275f + offsetX - relX, offsetY - relY);
		labelPathE.rLineTo(2 * relX, 2 * relY);
		
		labelPathS.reset();
		labelPathS.moveTo(offsetX - relX, mH * 0.4275f + offsetY - relY);
		labelPathS.rLineTo(2 * relX, 2 * relY);
		
		labelPathW.reset();
		labelPathW.moveTo(-mW * 0.4275f + offsetX - relX, offsetY - relY);
		labelPathW.rLineTo(2 * relX, 2 * relY);
	}
	
	public void setYaw(float yaw) {
		mYaw = yaw;
		mRotation = mYaw + zeroYaw[((Activity) getContext()).getWindowManager().getDefaultDisplay().getRotation()];
		refreshGeometries();
		invalidate();
	}

	public void setRelativeSize(float size) {
		mRelativeSize = size;
		invalidate();
	}
}
