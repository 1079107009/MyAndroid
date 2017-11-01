package com.lp.itemdecoration.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.lp.itemdecoration.R;

import java.util.Arrays;
import java.util.List;

/**
 * @author LiPin
 * @date 2017/11/1 8:49
 * 描述：
 */

public class IndexBar extends View {

    private int textSize;
    private int textColor;
    private int mPressedBackground;
    /**
     * #在最后面（默认的数据源）
     */
    public static String[] INDEX_STRING = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    /**
     * 索引数据源
     */
    private List<String> mIndexDatas;
    /**
     * 每个index区域的高度
     */
    private int mGapHeight;

    private Paint mPaint;

    private int mWidth;
    private int mHeight;


    private IndexPressedListener mIndexPressedListener;

    public void setIndexPressedListener(IndexPressedListener indexPressedListener) {
        this.mIndexPressedListener = indexPressedListener;
    }

    public IndexBar(Context context) {
        this(context, null);
    }

    public IndexBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mIndexDatas = Arrays.asList(INDEX_STRING);
        //默认字体大小为16sp
        textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16,
                getResources().getDisplayMetrics());
        //默认按下背景为黑色
        mPressedBackground = Color.BLACK;
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.IndexBar,
                defStyleAttr, 0);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.IndexBar_textSize:
                    textSize = typedArray.getDimensionPixelSize(attr, textSize);
                    break;
                case R.styleable.IndexBar_pressBackground:
                    mPressedBackground = typedArray.getColor(attr, mPressedBackground);
                    break;
                case R.styleable.IndexBar_textColor:
                    textColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                default:
                    break;
            }
        }
        mPaint.setTextSize(textSize);
        mPaint.setColor(textColor);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //取出宽高的MeasureSpec  Mode 和Size
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        //最终测量出来的宽高
        int measureWidth = 0;
        int measureHeight = 0;
        //存放index的区域
        Rect indexBound = new Rect();
        int size = mIndexDatas.size();
        for (int i = 0; i < size; i++) {
            mPaint.getTextBounds(mIndexDatas.get(i), 0, 1, indexBound);
            measureWidth = Math.max(measureWidth, indexBound.width());
            measureHeight = Math.max(measureHeight, indexBound.height());
        }
        measureHeight *= size;
        switch (wMode) {
            case MeasureSpec.EXACTLY:
                measureWidth = wSize;
                break;
            case MeasureSpec.AT_MOST:
                measureWidth = Math.min(measureWidth, wSize);
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                break;
        }

        switch (hMode) {
            case MeasureSpec.EXACTLY:
                measureHeight = hSize;
                break;
            case MeasureSpec.AT_MOST:
                measureHeight = Math.min(measureHeight, hSize);
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                break;
        }
        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mGapHeight = (mHeight - getPaddingTop() - getPaddingBottom()) / mIndexDatas.size();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int top = getPaddingTop();
        Rect indexBound = new Rect();
        int size = mIndexDatas.size();
        for (int i = 0; i < size; i++) {
            String index = mIndexDatas.get(i);
            mPaint.getTextBounds(index, 0, 1, indexBound);
            //获得画笔的FontMetrics，用来计算baseLine。因为drawText的y坐标，代表的是绘制的文字的baseLine的位置
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            //计算出在每格index区域，竖直居中的baseLine值
            int baseline = (int) ((mGapHeight - fontMetrics.bottom - fontMetrics.top) / 2);
            int x = mWidth / 2 - indexBound.width() / 2;
            int y = top + i * mGapHeight + baseline;
            canvas.drawText(index, x, y, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setBackgroundColor(mPressedBackground);
                //break; 这里不需要break,因为down时，也要计算落点回调监听器
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                int index = (int) ((y - getPaddingTop())) / mGapHeight;
                //边界处理
                if (index < 0) {
                    index = 0;
                } else if (index >= mIndexDatas.size()) {
                    index = mIndexDatas.size() - 1;
                }
                //回调监听器
                if (null != mIndexPressedListener) {
                    mIndexPressedListener.onIndexPressed(index, mIndexDatas.get(index));
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            default:
                //手指抬起时背景恢复透明
                setBackgroundResource(android.R.color.transparent);
                //回调监听器
                if (null != mIndexPressedListener) {
                    mIndexPressedListener.onMotionEventEnd();
                }
                break;
        }
        return true;
    }

    /**
     * 当前被按下的index的监听器
     */
    public interface IndexPressedListener {
        /**
         * 当某个Index被按下
         *
         * @param index
         * @param text
         */
        void onIndexPressed(int index, String text);

        /**
         * 当触摸事件结束（UP CANCEL）
         */
        void onMotionEventEnd();
    }

}
