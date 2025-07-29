package com.github.wuxudong.rncharts.markers;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.wuxudong.rncharts.R;
import java.util.List;
import java.util.Map;

public class CustomMarker extends MarkerView {
    private final LinearLayout container;

    public CustomMarker(Context context) {
        super(context, R.layout.custom_marker);
        container = findViewById(R.id.custom_marker);
    }

    @Override
    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
        Chart chart = getChartView();
        if (chart == null) {
            return new MPPointF(0, 0);
        }
        
        // Get marker size
        float markerWidth = getWidth();
        float markerHeight = getHeight();
        
        // Get chart dimensions
        float chartWidth = chart.getWidth();
        float chartHeight = chart.getHeight();
        
        // Safety padding
        float padding = Utils.convertDpToPixel(8f);
        
        // 1. Calculate Y offset - center marker vertically in chart
        float chartCenterY = chartHeight / 2;
        float offsetY = chartCenterY - posY - markerHeight / 2;
        
        // 2. Default to showing on the right side
        float offsetX = Utils.convertDpToPixel(10f); // 10pt offset to the right
        
        // 3. Check if right side has enough space
        float markerRightEdge = posX + offsetX + markerWidth;
        if (markerRightEdge > chartWidth - padding) {
            // If right side doesn't have enough space, show on left side
            offsetX = -markerWidth - Utils.convertDpToPixel(10f); // 10pt offset to the left
        }
        
        // 4. Check if left side goes out of bounds (extreme case)
        float markerLeftEdge = posX + offsetX;
        if (markerLeftEdge < padding) {
            offsetX = -posX + padding; // Force right alignment
        }
        
        return new MPPointF(offsetX, offsetY);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        container.removeAllViews();
        int fontSize = 11;
        List<com.github.mikephil.charting.data.DataSet> dataSet = getChartView().getData().getDataSets();
        if (e.getData() instanceof Map) {
            Map<?, ?> dataMap = (Map<?, ?>) e.getData();
            if (dataMap.containsKey("marker")) {
                TextView label = new TextView(getContext());
                label.setLayoutParams(new ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                Object marker = dataMap.get("marker");
                label.setText(marker.toString());

                if (highlight.getStackIndex() != -1 && marker instanceof List) {
                    List<?> markerList = (List<?>) marker;
                    label.setText(markerList.get(highlight.getStackIndex()).toString());
                }
                label.setTextColor(0xFF8C8C8C);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) label.getLayoutParams();
                label.setTextSize(fontSize);
                params.bottomMargin = 4;
                container.addView(label);
            }
        }
        Chart chart = getChartView();
        boolean isBarChart = chart instanceof BarChart;
        
        // 对于BarChart，需要找到对应的数据索引
        int dataIndex = -1;
        if (isBarChart) {
            for (int i = 0; i < dataSet.size(); i++) {
                com.github.mikephil.charting.data.DataSet item = dataSet.get(i);
                int index = item.getEntryIndex(e);
                if (index != -1) {
                    dataIndex = i;
                    break;
                }
            }
        }
        
        for (int index = 0; index < dataSet.size(); index++) {
            com.github.mikephil.charting.data.DataSet item = dataSet.get(index);
            if (item.isVisible()) {
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setLayoutParams(new ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                linearLayout.setGravity(Gravity.CENTER_VERTICAL);
                if (index != dataSet.size() - 1) {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) linearLayout.getLayoutParams();
                    params.bottomMargin = 4;
                }

                View circle = new View(getContext());
                int size = (int) Utils.convertDpToPixel(6);
                circle.setLayoutParams(new ViewGroup.MarginLayoutParams(
                        size,
                        size
                ));
                GradientDrawable drawable = new GradientDrawable();
                drawable.setShape(GradientDrawable.OVAL);
                drawable.setColor(item.getColor());
                circle.setBackground(drawable);
                ViewGroup.MarginLayoutParams circleParams = (ViewGroup.MarginLayoutParams) circle.getLayoutParams();
                circleParams.rightMargin = (int) Utils.convertDpToPixel(4f);
                
                // 根据图表类型获取对应的Entry
                Entry matchEntry;
                if (isBarChart) {
                    matchEntry = item.getEntryForIndex(dataIndex);
                } else {
                    List<Entry> matchEntries = item.getEntriesForXValue(e.getX());
                    matchEntry = matchEntries.get(0);
                }
                
                TextView label = new TextView(getContext());
                label.setTextSize(fontSize);
                label.setText(item.getLabel() + ": " + matchEntry.getY());
                label.setTextColor(0xFF262626);
                linearLayout.addView(circle);
                linearLayout.addView(label);
                container.addView(linearLayout);
            }
        }
        super.refreshContent(e, highlight);
    }
}