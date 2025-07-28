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
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
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
                params.bottomMargin = (int) Utils.convertDpToPixel(4f);
                container.addView(label);
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
                    params.bottomMargin = (int) Utils.convertDpToPixel(4f);
                }

                View circle = new View(getContext());
                int size =(int) Utils.convertDpToPixel(6);
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
                List<Entry> matchEntry = item.getEntriesForXValue(e.getX());
                TextView label = new TextView(getContext());
                label.setTextSize(fontSize);
                label.setText(item.getLabel() + ": " + matchEntry.get(0).getY());
                label.setTextColor(0xFF262626);
                linearLayout.addView(circle);
                linearLayout.addView(label);
                container.addView(linearLayout);
            }
        }
        super.refreshContent(e, highlight);
    }
}