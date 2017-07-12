package com.lp.draw.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice10HistogramView extends View {

    public Practice10HistogramView(Context context) {
        super(context);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        RectF rectf = new RectF();
        rectf.set(100, 100, 150, 150);
        Path path = new Path();
        path.moveTo(100, 50);
        path.lineTo(100, 400);
        path.lineTo(600, 400);
        canvas.drawPath(path, paint);
        String[] texts = {"Froyo", "GB", "ICS", "JB", "KitKat", "L", "M"};
        for (int i = 0; i < texts.length; i++) {
            canvas.drawText(texts[i], 100 + i * 50 + (i + 1) * 20, 420, paint);
        }
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GREEN);
        for (int i = 0; i < texts.length; i++) {
            rectf.set(100 + i * 50 + (i + 1) * 10, 400 - i * 10, 100 + (i + 1) * 50 + (i + 1) * 10, 400);
            canvas.drawRect(rectf, paint);
        }
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(40);
        canvas.drawText("直方图", 300, 450, paint);
    }
}
