package com.lp.practicedraw8.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lp.practicedraw8.R;

/**
 * Created by LiPin on 2017/10/13 16:08.
 * 描述：点赞
 */

public class LikeView extends View {

    private Paint mPaint;
    private int num = 4309;

    public LikeView(Context context) {
        super(context, null);
    }

    public LikeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public LikeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;

        Bitmap bitmap = BitmapFactory
                .decodeResource(getResources(), R.drawable.ic_messages_like_unselected);

        canvas.drawBitmap(bitmap, centerX - bitmap.getWidth() - 10, centerY - bitmap.getHeight() / 2, mPaint);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(30);
        for (int i = 0; i < 4; i++) {

            canvas.drawText(String.valueOf(num), centerX + i * 10, centerY + 10, paint);
        }
    }
}
