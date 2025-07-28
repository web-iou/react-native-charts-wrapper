package com.github.wuxudong.rncharts.charts;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Path;
import android.graphics.RectF;


import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class BaseBarChartRenderer extends BarChartRenderer {

    private Transformer transformer;
    private static float radius = 0;
    private RectF mBarShadowRectBuffer = new RectF();
    public BaseBarChartRenderer(
            BarDataProvider chart,
            ChartAnimator animator,
            ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    public static void setRadius(float radius) {
        BaseBarChartRenderer.radius = radius;
    }

    @Override
    protected void prepareBarHighlight(float x, float y1, float y2, float barWidthHalf, Transformer trans) {
        transformer = trans;
        BarData barData = mChart.getBarData();
        int barCountInGroup = barData.getDataSetCount(); // Group 内的 bar 数量
        int groupIndex = (int) x; // 如果 x 是 group 索引（如 0, 1, 2...）
        // 计算 group 的中心点和宽度
        float barCenterX = (float) (barData.getBarWidth()*barCountInGroup/2 + 0.1);
        float groupCenterX =  groupIndex+barCenterX;
        
        // 设置高亮范围为整个 group 的宽度
        float highlightBarWidthHalf = barWidthHalf*barCountInGroup;
        super.prepareBarHighlight(barCountInGroup>1?groupCenterX:x, mChart.getYChartMax(), y2, (float) (highlightBarWidthHalf), trans);
    }

    @Override
    protected void setHighlightDrawPos(Highlight high, RectF bar) {
        if (transformer != null) {
            //还原真实的点击数据
            float barWidthHalf = mChart.getBarData().getBarWidth() / 2f;
            float x = high.getX();
            RectF rectF = new RectF();
            rectF.set(x - barWidthHalf, high.getY(), x + barWidthHalf, 0f);
            transformer.rectToPixelPhase(rectF, mAnimator.getPhaseY());
            super.setHighlightDrawPos(high, rectF);
        } else {
            super.setHighlightDrawPos(high, bar);
        }
    }

    @Override
    protected void drawDataSet(Canvas c, IBarDataSet dataSet, int index) {
        Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());
        mBarBorderPaint.setColor(dataSet.getBarBorderColor());
        mBarBorderPaint.setStrokeWidth(Utils.convertDpToPixel(dataSet.getBarBorderWidth()));
        final boolean drawBorder = dataSet.getBarBorderWidth() > 0.f;
        float phaseX = mAnimator.getPhaseX();
        float phaseY = mAnimator.getPhaseY();
        // draw the bar shadow before the values
        if (mChart.isDrawBarShadowEnabled()) {
            mShadowPaint.setColor(dataSet.getBarShadowColor());
            BarData barData = mChart.getBarData();
            final float barWidth = barData.getBarWidth();
            final float barWidthHalf = barWidth / 2.0f;
            float x;
            for (int i = 0, count = Math.min((int)(Math.ceil((float)(dataSet.getEntryCount()) * phaseX)), dataSet.getEntryCount());
                 i < count;
                 i++) {
                BarEntry e = dataSet.getEntryForIndex(i);
                x = e.getX();
                mBarShadowRectBuffer.left = x - barWidthHalf;
                mBarShadowRectBuffer.right = x + barWidthHalf;
                trans.rectValueToPixel(mBarShadowRectBuffer);
                if (!mViewPortHandler.isInBoundsLeft(mBarShadowRectBuffer.right))
                    continue;
                if (!mViewPortHandler.isInBoundsRight(mBarShadowRectBuffer.left))
                    break;
                mBarShadowRectBuffer.top = mViewPortHandler.contentTop();
                mBarShadowRectBuffer.bottom = mViewPortHandler.contentBottom();
                c.drawRect(mBarShadowRectBuffer, mShadowPaint);
            }
        }
        // initialize the buffer
        BarBuffer buffer = mBarBuffers[index];
        buffer.setPhases(phaseX, phaseY);
        buffer.setDataSet(index);
        buffer.setInverted(mChart.isInverted(dataSet.getAxisDependency()));
        buffer.setBarWidth(mChart.getBarData().getBarWidth());
        
        buffer.feed(dataSet);
        trans.pointValuesToPixel(buffer.buffer);
        
        final boolean isSingleColor = dataSet.getColors().size() == 1;
        if (isSingleColor) {
            mRenderPaint.setColor(dataSet.getColor());
        }
        float radiusPx = Utils.convertDpToPixel(radius);
        for (int j = 0; j < buffer.size(); j += 4) {
            if (!mViewPortHandler.isInBoundsLeft(buffer.buffer[j + 2]))
                continue;
            if (!mViewPortHandler.isInBoundsRight(buffer.buffer[j]))
                break;
            // 1. 获取柱子的矩形区域
            float left = buffer.buffer[j];
            float top = buffer.buffer[j + 1];
            float right = buffer.buffer[j + 2];
            float bottom = buffer.buffer[j + 3];
            // 2. 创建带圆角的 Path（仅顶部圆角）
            Path path = new Path();
            RectF rect = new RectF(left, top, right, bottom);
            float[] radii = {radiusPx, radiusPx, radiusPx, radiusPx, 0, 0, 0, 0}; // 仅左上、右上圆角
            path.addRoundRect(rect, radii, Path.Direction.CW);
            int saveCount = c.save();
            // 4. 裁剪画布为圆角 Path
            c.clipPath(path);
            if (!isSingleColor) {
                // Set the color for the currently drawn value. If the index
                // is out of bounds, reuse colors.
                mRenderPaint.setColor(dataSet.getColor(j / 4));
            }
            
            if (dataSet.getGradientColor() != null) {
                GradientColor gradientColor = dataSet.getGradientColor();
                mRenderPaint.setShader(
                        new LinearGradient(
                                buffer.buffer[j],
                                buffer.buffer[j + 3],
                                buffer.buffer[j],
                                buffer.buffer[j + 1],
                                gradientColor.getStartColor(),
                                gradientColor.getEndColor(),
                                android.graphics.Shader.TileMode.MIRROR));
            }
            if (dataSet.getGradientColors() != null) {
                mRenderPaint.setShader(
                        new LinearGradient(
                                buffer.buffer[j],
                                buffer.buffer[j + 3],
                                buffer.buffer[j],
                                buffer.buffer[j + 1],
                                dataSet.getGradientColor(j / 4).getStartColor(),
                                dataSet.getGradientColor(j / 4).getEndColor(),
                                android.graphics.Shader.TileMode.MIRROR));
                }
            c.drawRect(rect, mRenderPaint);
            if (drawBorder) {
                Path borderPath = new Path();
                borderPath.addRoundRect(rect, radii, Path.Direction.CW);
                c.drawPath(borderPath, mBarBorderPaint);
            }
            c.restoreToCount(saveCount);
        }
    }
}