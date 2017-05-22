package com.dtdream.nngovernment.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.dtdream.dtbase.utils.Tools;
import com.dtdream.nngovernment.R;

public class OldCreditSesameView extends View {

    // 最外层圆环渐变色环颜色
    private final int[] mColors = new int[]{
            Color.parseColor("#8dcf13"),
            Color.rgb(78, 205, 82),
            Color.parseColor("#0ecc93"),
    };

    // 宽度
    private int width;

    // 高度
    private int height;

    // 半径
    private float radius;

    // 指针图片
    private Bitmap mBitmap;

    // 外层进度圆环画笔
    private Paint mGradientProgressPaint;

    // 最外层圆环画笔
    private Paint mGradientRingPaint;

    // 内虚线圆环画笔
    private Paint mInnerRingPaint;

    //内虚线渐变圆环画笔
    private Paint mInnerProgressPaint;

    // 指针画笔
    private Paint mPointerBitmapPaint;

    // 信用积分画笔
    private Paint mPointerTextPaint;

    //信用等级画笔
    private Paint mLevelTextPaint;

    // 绘制外层圆环的矩形
    private RectF mOuterArc;

    // 绘制内层圆环的矩形
    private RectF mInnerArc;

    // 圆环起始角度
    private static final float mStartAngle = 0f;

    // 圆环结束角度
    private static final float mEndAngle = 180f;

    // 指针全部进度
    private float mTotalAngle = 180f;

    // 指针当前进度
    private float mCurrentAngle = 0f;

    // 最小数字
    private int mMinNum = 0;

    // 最大数字
    private int mMaxNum = 990;

    //信用分数
    private String sesamePoints = "暂无评分";

    //信用等级
    private String sesameLevel = "暂无信用";

    private PaintFlagsDrawFilter mPaintFlagsDrawFilter;


    public OldCreditSesameView(Context context) {

        this(context, null);
    }


    public OldCreditSesameView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }


    public OldCreditSesameView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        init();
    }


    /**
     * 初始化
     */
    private void init() {
        //设置图片线条的抗锯齿
        mPaintFlagsDrawFilter = new PaintFlagsDrawFilter
                (0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

        //最外层圆环画笔设置
        mGradientRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGradientRingPaint.setColor(Color.parseColor("#b6efdd"));
        mGradientRingPaint.setStrokeCap(Paint.Cap.ROUND);
        mGradientRingPaint.setStyle(Paint.Style.STROKE);
        mGradientRingPaint.setStrokeWidth(5);

        //最外层圆环渐变画笔设置
        mGradientProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGradientProgressPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        float position[] = {0, 0.5f, 1f};
        Shader mShader = new SweepGradient(width / 2, radius, mColors, position);
        mGradientProgressPaint.setShader(mShader);
        mGradientProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        mGradientProgressPaint.setStyle(Paint.Style.STROKE);
        mGradientProgressPaint.setStrokeWidth(5);

        //内层圆环画笔设置
        mInnerRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerRingPaint.setStyle(Paint.Style.STROKE);
        mInnerRingPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerRingPaint.setStrokeWidth(5);
        mInnerRingPaint.setColor(Color.parseColor("#b6efdd"));
        PathEffect mPathEffect = new DashPathEffect(new float[]{1, 19}, 14);
        mInnerRingPaint.setPathEffect(mPathEffect);

        //内层圆环渐变
        mInnerProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerProgressPaint.setStyle(Paint.Style.STROKE);
        mInnerProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerProgressPaint.setStrokeWidth(5);
        mInnerProgressPaint.setPathEffect(mPathEffect);
        mInnerProgressPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        mInnerProgressPaint.setShader(mShader);

        //信用分数文字画笔设置
        mPointerTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointerTextPaint.setColor(Color.parseColor("#00c099"));
        mPointerTextPaint.setTextSize(Tools.dp2px(28));
        mPointerTextPaint.setTextAlign(Paint.Align.CENTER);

        //信用等级画笔设置
        mLevelTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLevelTextPaint.setColor(Color.parseColor("#20cd82"));
        mLevelTextPaint.setTextSize(Tools.dp2px(14));
        mLevelTextPaint.setTextAlign(Paint.Align.CENTER);

        //指针图片画笔
        mPointerBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //获取指针图片
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_pointer);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);

        //确定View宽高
        width = w;
        height = h;

        //圆环半径
        radius = Tools.dp2px(120);

        //外层圆环
        float oval1 = radius;
        mOuterArc = new RectF(-oval1, -oval1, oval1, oval1);

        //内层圆环
        float oval2 = radius - Tools.dp2px(10);
        mInnerArc = new RectF(-oval2, -oval2, oval2, oval2);

    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        //设置画布绘图无锯齿
        canvas.setDrawFilter(mPaintFlagsDrawFilter);
        drawArc(canvas);
        drawCenterText(canvas);
        drawBitmapProgress(canvas);
    }


    /**
     * 绘制指针图片和渐变进度
     */
    private void drawBitmapProgress(Canvas canvas) {
        if ("暂无评分".equals(sesamePoints)) {
            return;
        }
        canvas.save();
        canvas.translate(width / 2, height);
        canvas.rotate(178);
        canvas.rotate(mCurrentAngle);
        canvas.drawBitmap(mBitmap, radius - Tools.dp2px(22), 0, mPointerBitmapPaint);
        canvas.restore();
    }


    /**
     * 绘制中间文本内容
     */
    private void drawCenterText(Canvas canvas) {
        int levelMargin = Tools.dp2px(66);
        float x1 = width / 2;
        int y1 = height - levelMargin;
        //绘制信用等级
        canvas.drawText(sesameLevel, x1, y1, mLevelTextPaint);

        int pointMargin = Tools.dp2px(16);
        int y2 = height - pointMargin;
        //绘制信用分数
        canvas.drawText(sesamePoints, x1, y2, mPointerTextPaint);

    }

    /**
     * 分别绘制外层 中间 内层圆环
     */
    private void drawArc(Canvas canvas) {
        canvas.save();
        canvas.translate(width / 2, height);
        canvas.rotate(180);
        //画最外层的圆环
        canvas.drawArc(mOuterArc, mStartAngle, mEndAngle, false, mGradientRingPaint);
        //绘制内圈圆形
        canvas.drawArc(mInnerArc, mStartAngle, mEndAngle, false, mInnerRingPaint);
        //绘制渐变圆环
        canvas.drawArc(mOuterArc, mStartAngle, mCurrentAngle, false, mGradientProgressPaint);
        canvas.drawArc(mInnerArc, mStartAngle, mCurrentAngle, false, mInnerProgressPaint);
        canvas.restore();
    }


    /**
     * 设置芝麻信用数据
     */
    public void setSesameValues(int num) {
        if (num <= 550) {
            sesameLevel = "信用较差";
        } else if (num <= 600) {
            sesameLevel = "信用中等";
        } else if (num <= 650) {
            sesameLevel = "信用良好";
        } else if (num <= 700) {
            sesameLevel = "信用优秀";
        } else if (num <= 990) {
            sesameLevel = "信用极好";
        }
        mCurrentAngle = (float) num / (float) mMaxNum * 180f;
        sesamePoints = String.valueOf(num);
//        startRotateAnim();
    }


    /**
     * 开始指针旋转动画
     */
    public void startRotateAnim() {

        ValueAnimator mAngleAnim = ValueAnimator.ofFloat(0, mTotalAngle);
        mAngleAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        mAngleAnim.setDuration(3000);
        mAngleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                postInvalidate();
            }
        });
        mAngleAnim.start();

        ValueAnimator mNumAnim = ValueAnimator.ofInt(mMinNum, mMaxNum);
        mNumAnim.setDuration(3000);
        mNumAnim.setInterpolator(new LinearInterpolator());
        mNumAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                postInvalidate();
            }
        });
        mNumAnim.start();
    }

}
