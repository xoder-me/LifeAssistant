package com.alex.app.lifeassistant.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import com.alex.app.lifeassistant.utils.DensityUtils;

public class MyCircleView extends View {
	private Context mContext;

	private int mMax = 100;
	private int mProgress = 0;

	private int mScreenWidth;
	private int mScreenHeight;

	private Paint mRingPaint;
	private Paint mCirclePaint;
	private Paint mWavePaint;
	private Paint flowPaint;
	private Paint leftPaint;

	private int mRingSTROKEWidth = 15;
	private int mCircleSTROKEWidth = 2;

	private int mCircleColor = Color.WHITE;
	private int mRingColor = Color.WHITE;
	private int mWaveColor = Color.WHITE;

	private Handler mHandler;
	private long c = 0L;
	private boolean mStarted = false;
	private final float f = 0.033F;
	private int mAlpha = 50;// 透明度
	private float mAmplitude = 10.0F; // 振幅
	private float mWateLevel = 0.5F;// 水高(0~1)
	private Path mPath;

	private String flowNum = "未设置";
	private String flowLeft = "还剩余";

	private final static double[] PERCENT_TO_ARC = {
			0, 0.364413d, 0.4616d, 0.530831d, 0.586699d, 0.634474d, 0.676734d, 0.714958d, 0.750081d,
			0.782736d, 0.813377d, 0.842337d, 0.869872d, 0.896184d, 0.921432d, 0.945747d, 0.969237d,
			0.991993d, 1.01409d, 1.0356d, 1.05657d, 1.07706d, 1.0971d, 1.11674d, 1.13601d, 1.15494d,
			1.17356d, 1.19189d, 1.20996d, 1.22779d, 1.24539d, 1.26279d, 1.27999d, 1.29702d, 1.31389d,
			1.33061d, 1.3472d, 1.36366d, 1.38d, 1.39625d, 1.4124d, 1.42847d, 1.44446d, 1.46039d, 1.47627d,
			1.49209d, 1.50788d, 1.52364d, 1.53937d, 1.55509d, 0.5 * Math.PI, 1.58651d, 1.60222d, 1.61796d,
			1.63371d, 1.6495d, 1.66533d, 1.6812d, 1.69713d, 1.71313d, 1.72919d, 1.74535d, 1.76159d,
			1.77794d, 1.7944d, 1.81098d, 1.8277d, 1.84457d, 1.8616d, 1.8788d, 1.8962d, 1.9138d,
			1.93163d, 1.9497d, 1.96803d, 1.98665d, 2.00558d, 2.02485d, 2.04449d, 2.06454d, 2.08502d,
			2.10599d, 2.1275d, 2.1496d, 2.17236d, 2.19585d, 2.22016d, 2.24541d, 2.27172d, 2.29926d,
			2.32822d, 2.35886d, 2.39151d, 2.42663d, 2.46486d, 2.50712d, 2.55489d, 2.61076d, 2.67999d,
			2.77718d, Math.PI
	};

	/**
	 * when mFillMode=rising_water,the percent Mapping the angle
	 */
	private final static double[] PERCENT_TO_ANGLE = {
			0.0, 20.87932689970087, 26.447731823238804, 30.414375934709003, 33.61537654454588,
			36.352682410783395, 38.77400205300625, 40.964075929114315, 42.9764755929523,
			44.847469272952004, 46.60306925301236, 48.26235502771122, 49.83999431660394,
			51.34756086715217, 52.794164708298474, 54.18731158715907, 55.53318944792137,
			56.837012206521074, 58.103077046421646, 59.33550926374806, 60.53700176013739,
			61.710992282360436, 62.85919970380261, 63.984488813439555, 65.08857848465665,
			66.1731875908393, 67.24003500537289, 68.29026664384769, 69.32560137964909,
			70.34718512836734, 71.35559084779759, 72.35253741132523, 73.33802481895025,
			74.31377194405803, 75.28035174444373, 76.23833717790248, 77.1888741600245,
			78.13196269080984, 79.0681757280536, 79.99923214514119, 80.92455898427748,
			81.8453021610527, 82.7614616754669, 83.6741834431103, 84.58404042177803,
			85.49045965367499, 86.39516001218658, 87.29814149731276, 88.19940410905353,
			89.1000937629992, 90.0, 90.90032715530023, 91.80044385145077, 92.70227942098667,
			93.60468794831772, 94.50938830682928, 95.41638049652137, 96.325664517394,
			97.23838628503741, 98.15511875724673, 99.07528897622683, 100.00118877315823,
			100.9316722324507, 101.86845822748958, 102.81154675827493, 103.76151078260183,
			104.71949621606056, 105.68607601644626, 106.66182314155404, 107.64731054917908,
			108.64425711270671, 109.65266283213694, 110.67424658085521, 111.70958131665661,
			112.75981295513141, 113.82666036966499, 114.91126947584766, 116.01535914706473,
			117.1406482567017, 118.28942863593899, 119.46284620036691, 120.66433869675623,
			121.89677091408264, 123.16300764132176, 124.4670595830395, 125.81293744380183,
			127.20579784376484, 128.65251627647018, 130.1599682354594, 131.73789400324964,
			133.3971797779485, 135.15272246222935, 137.02342966333148, 139.03565743983094,
			141.2260750906161, 143.64739473283896, 146.3844141201789, 149.5855293215748,
			153.5521161372655, 159.12069294814196, 180.0
	};

	public MyCircleView(Context context) {
		super(context);
		mContext = context;
		init(mContext);
	}

	public MyCircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init(mContext);
	}

	public MyCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		init(mContext);
	}

	private void init(Context mContext) {
		mRingPaint = new Paint();
		mRingPaint.setColor(mRingColor);
		mRingPaint.setAlpha(50);
		mRingPaint.setStyle(Paint.Style.STROKE);
		mRingPaint.setAntiAlias(true);
		mRingPaint.setStrokeWidth(mRingSTROKEWidth);

		mCirclePaint = new Paint();
		mCirclePaint.setColor(mCircleColor);
		mCirclePaint.setStyle(Paint.Style.STROKE);
		mCirclePaint.setAntiAlias(true);
		mCirclePaint.setStrokeWidth(mCircleSTROKEWidth);

		flowPaint = new Paint();
		flowPaint.setColor(mCircleColor);
		flowPaint.setStyle(Paint.Style.FILL);
		flowPaint.setAntiAlias(true);
		flowPaint.setTextSize(DensityUtils.sp2px(mContext, 18f));

		leftPaint = new Paint();
		leftPaint.setColor(mCircleColor);
		leftPaint.setStyle(Paint.Style.FILL);
		leftPaint.setAntiAlias(true);
		leftPaint.setTextSize(DensityUtils.sp2px(mContext, 14f));

		mWavePaint = new Paint();
		mWavePaint.setStrokeWidth(1.0F);
		mWavePaint.setColor(mWaveColor);
		mWavePaint.setAlpha(mAlpha);
		mPath = new Path();

		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0) {
					invalidate();
					if (mStarted) {
						// 不断发消息给自己，使自己不断被重绘
						mHandler.sendEmptyMessageDelayed(0, 60L);
					}
				}
			}
		};
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = measure(widthMeasureSpec, true);
		int height = measure(heightMeasureSpec, false);
		if (width < height) {
			setMeasuredDimension(width, width);
		} else {
			setMeasuredDimension(height, height);
		}

	}

	private int measure(int measureSpec, boolean isWidth) {
		int result;
		int mode = MeasureSpec.getMode(measureSpec);
		int size = MeasureSpec.getSize(measureSpec);
		int padding = isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();
		if (mode == MeasureSpec.EXACTLY) {
			result = size;
		} else {
			result = isWidth ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
			result += padding;
			if (mode == MeasureSpec.AT_MOST) {
				if (isWidth) {
					result = Math.max(result, size);
				} else {
					result = Math.min(result, size);
				}
			}
		}
		return result;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mScreenWidth = w;
		mScreenHeight = h;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 得到控件的宽高
//		int width = getWidth();
//		int height = getHeight();

		canvas.drawCircle(mScreenWidth / 2, mScreenHeight / 2, mScreenWidth / 4, mRingPaint);
		canvas.drawCircle(mScreenWidth / 2, mScreenHeight / 2, mScreenWidth / 4 - mRingSTROKEWidth / 2, mCirclePaint);
		float num = flowPaint.measureText(flowNum);
		canvas.drawText(flowNum, mScreenWidth * 4 / 8 - num / 2, mScreenHeight * 4 / 8, flowPaint);
		float left = leftPaint.measureText(flowLeft);
		canvas.drawText(flowLeft, mScreenWidth * 4 / 8 - left / 2, mScreenHeight * 3 / 8, leftPaint);

		int percent = getProgress() * 100 / getMax();
		float startAngle;
		Path path = new Path();
//		if (percent < 50) {
//			startAngle = (float) (90 - PERCENT_TO_ANGLE[percent]);
//			canvas.drawArc(mCircleRectF, startAngle, (float) (PERCENT_TO_ANGLE[percent] * 2), true, mSectorPaint);
//
//			float rSin = (float) (mCircleRadius * Math.sin(PERCENT_TO_ARC[percent]));
//			float rCos = (float) (mCircleRadius * Math.cos(PERCENT_TO_ARC[percent]));
//			path.moveTo(centerX, centerY);// triangle start
//			path.lineTo(centerX + rSin, centerY + rCos);
//			path.lineTo(centerX - rSin, centerY + rCos);
//			path.lineTo(centerX, centerY);
//			path.close(); // siege of triangle
//			canvas.drawPath(path, mCirclePaint);
//			mCirclePaint.setStyle(Paint.Style.STROKE);
//			mCirclePaint.setStrokeWidth(1.0f);
//			canvas.drawPath(path, mCirclePaint);
//		} else {
//			startAngle = (float) (450 - PERCENT_TO_ANGLE[percent]);
//			canvas.drawArc(mCircleRectF, startAngle, (float) (PERCENT_TO_ANGLE[percent] * 2), true, mSectorPaint);
//			float rSin = (float) (mCircleRadius * Math.sin(PERCENT_TO_ARC[percent]));
//			float rCos = (float) (mCircleRadius * Math.cos(PERCENT_TO_ARC[percent]));
//			path.moveTo(centerX, centerY);// triangle start
//			path.lineTo(centerX + rSin, centerY + rCos);
//			path.lineTo(centerX - rSin, centerY + rCos);
//			path.lineTo(centerX, centerY);
//			path.close(); // siege of triangle
//			canvas.drawPath(path, mSectorPaint);
//			mSectorPaint.setStyle(Paint.Style.STROKE);
//			mSectorPaint.setStrokeWidth(1.0f);
//			canvas.drawPath(path, mSectorPaint);
//		}


		// 如果未开始（未调用startWave方法）,绘制一个扇形
		// 设置个新的长方形，扫描测量
		if ((!mStarted) || (mScreenWidth == 0) || (mScreenHeight == 0)) {
			RectF oval = new RectF(
					mScreenWidth / 4 + mRingSTROKEWidth / 2,
					mScreenHeight / 4 + mRingSTROKEWidth / 2,
					mScreenWidth * 3 / 4 - mRingSTROKEWidth / 2,
					mScreenHeight * 3 / 4 - mRingSTROKEWidth / 2
			);
			canvas.drawArc(oval, 0, 180, true, mWavePaint);
			return;
		}

		// 绘制,即水面静止时的高度
		// 设置个新的长方形，扫描测量
		RectF oval = new RectF(
				mScreenWidth / 4 + mRingSTROKEWidth / 2,
				mScreenHeight / 4 + mRingSTROKEWidth / 2 + mAmplitude * 2,
				mScreenWidth * 3 / 4 - mRingSTROKEWidth / 2,
				mScreenHeight * 3 / 4 - mRingSTROKEWidth / 2
		);
		canvas.drawArc(oval, 0, 180, true, mWavePaint);

//		if (this.c >= 8388607L) {
//			this.c = 0L;
//		}
//
//		// 每次onDraw时c都会自增
//		c = (1L + c);
//		float f1 = mScreenHeight * (1.0F - mWateLevel);
//		int top = (int) (f1 + mAmplitude);
//		mPath.reset();
//		int startX = mScreenWidth / 2 - mScreenWidth / 4 + mRingSTROKEWidth / 2;
//
//		// 波浪效果
//		while (startX < mScreenWidth / 2 + mScreenWidth / 4 - mRingSTROKEWidth / 2) {
//			int startY = (int) (f1 - mAmplitude * Math.sin(Math.PI * (2.0F * (startX + this.c * width * this.f)) / width));
//			canvas.drawLine(startX, startY, startX, top, mWavePaint);
//			startX++;
//		}
//		canvas.restore();
	}

	public void setFlowNum(String flowNum) {
		this.flowNum = flowNum;
	}

	@Override
	public Parcelable onSaveInstanceState() {
		// Force our ancestor class to save its state
		Parcelable superState = super.onSaveInstanceState();
		SavedState ss = new SavedState(superState);
		ss.progress = (int) c;
		return ss;
	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {
		SavedState ss = (SavedState) state;
		super.onRestoreInstanceState(ss.getSuperState());
		c = ss.progress;
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		// 关闭硬件加速，防止异常unsupported operation exception
		//this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
	}

	/**
	 * 开始波动
	 */
	public void startWave() {
		if (!mStarted) {
			this.c = 0L;
			mStarted = true;
			this.mHandler.sendEmptyMessage(0);
		}
	}

	/**
	 * 停止波动
	 */
	public void stopWave() {
		if (mStarted) {
			this.c = 0L;
			mStarted = false;
			this.mHandler.removeMessages(0);
		}
	}

	public int getMax() {
		return mMax;
	}

	public void setMax(int Max) {
		if (Max > 0) {
			this.mMax = Max;
			invalidate();
		}
	}

	public int getProgress() {
		return mProgress;
	}

	public void setProgress(int Progress) {
		if (Progress <= getMax() && Progress >= 0) {
			this.mProgress = Progress;
			invalidate();
		}
	}

	/**
	 * 保存状态
	 */
	static class SavedState extends BaseSavedState {
		int progress;

		/**
		 * Constructor called from {@link ProgressBar#onSaveInstanceState()}
		 */
		SavedState(Parcelable superState) {
			super(superState);
		}

		/**
		 * Constructor called from {@link #CREATOR}
		 */
		private SavedState(Parcel in) {
			super(in);
			progress = in.readInt();
		}

		@Override
		public void writeToParcel(Parcel out, int flags) {
			super.writeToParcel(out, flags);
			out.writeInt(progress);
		}

		public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}

			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};
	}

}
