package com.dtdream.nngovernment.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.dtdream.dtbase.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * 芝麻信用分
 * Created by yangle on 2016/9/26.
 */
public class CreditScoreView extends View {

    //数据个数
    private int dataCount = 5;
    //每个角的弧度
    private float radian = (float) (Math.PI * 2 / dataCount);
    //雷达图半径
    private float radius;
    //中心X坐标
    private int centerX;
    //中心Y坐标
    private int centerY;
    //各维度标题
    private String[] titles = {"身份特质", "经济能力", "经济活动", "遵纪守法", "社会公德"};
    //各维度分值
    private List<Integer> data = new ArrayList<>(5);
    //数据最大值
    private float maxValue = 198;
    //雷达图与标题的间距
    private int radarMargin = Tools.dp2px(10);
    //雷达区画笔
    private Paint mainPaint;
    //雷达区填充画笔
    private Paint fillPaint;
    //数据区画笔
    private Paint valuePaint;
    //分数画笔
    private Paint scorePaint;
    //标题画笔
    private Paint titlePaint;
    //分数大小
    private int scoreSize = Tools.dp2px(28);
    //标题文字大小
    private int titleSize = Tools.dp2px(14);

    public CreditScoreView(Context context) {
        this(context, null);
    }

    public CreditScoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CreditScoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mainPaint = new Paint();
        mainPaint.setAntiAlias(true);
        mainPaint.setStrokeWidth(1f);
        mainPaint.setColor(Color.argb(117, 255, 255, 255));
        mainPaint.setStyle(Paint.Style.STROKE);

        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setColor(Color.argb(56, 254, 254, 254));
        valuePaint.setAlpha(102);
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        scorePaint = new Paint();
        scorePaint.setAntiAlias(true);
        scorePaint.setTextSize(scoreSize);
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextAlign(Paint.Align.CENTER);
        scorePaint.setStyle(Paint.Style.FILL);

        titlePaint = new Paint();
        titlePaint.setAntiAlias(true);
        titlePaint.setTextSize(titleSize);
        titlePaint.setColor(Color.WHITE);
        titlePaint.setStyle(Paint.Style.FILL);

        data.add(10);
        data.add(50);
        data.add(100);
        data.add(150);
        data.add(190);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //雷达图半径
        radius = w / 2 * 0.44f;
        //中心坐标
        centerX = w / 2;
        centerY = h / 2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawPolygon(canvas);
        drawRegion(canvas);
        drawScore(canvas);
        drawTitle(canvas);
    }

    /**
     * 绘制网格
     *
     * @param canvas 画布
     */
    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        //1度=1*PI/180   360度=2*PI   那么我们每旋转一次的角度为2*PI/内角个数
        //每个蛛丝之间的间距
        float r = radius / 8;
        for (int i = 0; i < 9; i++) {
            //当前半径
            float curR = r * i;
            path.moveTo((float) (centerX + curR * Math.sin(radian)), (float) (centerY - curR * Math.cos(radian)));
            path.lineTo((float) (centerX + curR * Math.sin(radian / 2)), (float) (centerY + curR * Math.cos(radian / 2)));
            path.lineTo((float) (centerX - curR * Math.sin(radian / 2)), (float) (centerY + curR * Math.cos(radian / 2)));
            path.lineTo((float) (centerX - curR * Math.sin(radian)), (float) (centerY - curR * Math.cos(radian)));
            path.lineTo(centerX, (float) (centerY - curR));
            path.close();
            if (i == 8) {
                //绘制填充区域
                fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                fillPaint.setColor(Color.argb(40, 255, 255, 255));
                fillPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                canvas.drawPath(path, fillPaint);
            }
        }
        canvas.drawPath(path, mainPaint);

    }

    /**
     * 绘制覆盖区域
     *
     * @param canvas 画布
     */
    private void drawRegion(Canvas canvas) {
        Path path = new Path();

        for (int i = 0; i < dataCount; i++) {
            //计算百分比
            float percent = data.get(i) / maxValue;

            if (i == 0) {
                int x = getPoint(4, 0, percent).x;
                int y = getPoint(4, 0, percent).y;
                path.moveTo(x, y);
            } else {
                int x = getPoint(i - 1, 0, percent).x;
                int y = getPoint(i - 1, 0, percent).y;
                path.lineTo(x, y);
            }
        }
        path.close();
        //绘制填充区域
        canvas.drawPath(path, valuePaint);
    }

    /**
     * 绘制分数
     *
     * @param canvas 画布
     */
    private void drawScore(Canvas canvas) {
        int score = 0;
        //计算总分
        for (int i = 0; i < dataCount; i++) {
            score += data.get(i);
        }
        canvas.drawText(score + "", centerX, centerY + scoreSize / 2, scorePaint);
    }

    /**
     * 绘制标题
     *
     * @param canvas 画布
     */
    private void drawTitle(Canvas canvas) {
        for (int i = 0; i < dataCount; i++) {
            int x = 0;
            int y = 0;

//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), icons[i]);
            int titleHeight = getTextHeight(titlePaint);
            float titleWidth = titlePaint.measureText(titles[i]);

            //底下两个角的坐标需要向下移动半个图片的位置（1、2）
            if (i == 0) {
                int margin = Tools.dp2px(5);
                x = getPoint(4, margin, 1).x;
                y = getPoint(4, margin, 1).y;
                x -= titleWidth / 2;
            } else if (i == 1) {
//                y += (iconHeight / 2);
                x = getPoint(0, radarMargin, 1).x;
                y = getPoint(0, radarMargin, 1).y;
                y += titleHeight / 2;
            } else if (i == 2) {
                int margin = Tools.dp2px(15);
                x = getPoint(1, 0, 1).x;
                y = getPoint(1, margin, 1).y;
//                x += titleWidth;
                y += (titleHeight / 2);
            } else if (i == 3) {
                int margin = Tools.dp2px(15);
                x = getPoint(2, 0, 1).x;
                y = getPoint(2, margin, 1).y;
                x -= titleWidth;
                y += (titleHeight / 2);
            } else if (i == 4) {
                x = getPoint(3, radarMargin, 1).x;
                y = getPoint(3, radarMargin, 1).y;
                x -= titleWidth;
                y += titleHeight / 2;
            }
            canvas.drawText(titles[i], x, y, titlePaint);
        }
    }

    /**
     * 获取雷达图上各个点的坐标
     *
     * @param position 坐标位置（右上角为0，顺时针递增）
     * @return 坐标
     */
    private Point getPoint(int position) {
        return getPoint(position, 0, 1);
    }

    /**
     * 获取雷达图上各个点的坐标（包括维度标题与图标的坐标）
     *
     * @param position    坐标位置
     * @param radarMargin 雷达图与维度标题的间距
     * @param percent     覆盖区的的百分比
     * @return 坐标
     */
    private Point getPoint(int position, int radarMargin, float percent) {
        int x = 0;
        int y = 0;

        if (position == 0) {
            x = (int) (centerX + (radius + radarMargin) * Math.sin(radian) * percent);
            y = (int) (centerY - (radius + radarMargin) * Math.cos(radian) * percent);

        } else if (position == 1) {
            x = (int) (centerX + (radius + radarMargin) * Math.sin(radian / 2) * percent);
            y = (int) (centerY + (radius + radarMargin) * Math.cos(radian / 2) * percent);

        } else if (position == 2) {
            x = (int) (centerX - (radius + radarMargin) * Math.sin(radian / 2) * percent);
            y = (int) (centerY + (radius + radarMargin) * Math.cos(radian / 2) * percent);

        } else if (position == 3) {
            x = (int) (centerX - (radius + radarMargin) * Math.sin(radian) * percent);
            y = (int) (centerY - (radius + radarMargin) * Math.cos(radian) * percent);

        } else if (position == 4) {
            x = centerX;
            y = (int) (centerY - (radius + radarMargin) * percent);
        }

        return new Point(x, y);
    }

    /**
     * 获取文本的高度
     *
     * @param paint 文本绘制的画笔
     * @return 文本高度
     */
    private int getTextHeight(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (int) (fontMetrics.descent - fontMetrics.ascent);
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }
}
