package com.android.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author someone
 * @date 2018/4/16
 */
public class LuckyWheel extends SurfaceView implements SurfaceHolder.Callback, Runnable {


    private Paint mPicPaint;
    private int verticalPadding;
    private int horizontalPadding;
    private boolean isDrawing;
    private SurfaceHolder mHolder;
    private Canvas mCanvas;

    public LuckyWheel(Context context) {
        this(context, null);
    }

    public LuckyWheel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDrawing = true;
        AsyncTask.execute(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDrawing = false;
        mHolder.removeCallback(this);
        mHolder = null;
        mCanvas = null;
    }

    @Override
    public void run() {
        while (isDrawing) {
            mCanvas = mHolder.lockCanvas();
            draw();
            mHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    void draw() {
        //绘制的内容
    }
}
