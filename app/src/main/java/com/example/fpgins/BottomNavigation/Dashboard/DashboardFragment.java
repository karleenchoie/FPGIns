package com.example.fpgins.BottomNavigation.Dashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.fpgins.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class DashboardFragment extends Fragment {

    private ImageView mNewsletters;
    private GraphView mProductionGraph, mLossesGraph;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mNewsletters = root.findViewById(R.id.img_news);

        mProductionGraph = (GraphView) root.findViewById(R.id.bar_graph);
        BarGraphSeries<DataPoint> barGraph_Data = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        mProductionGraph.addSeries(barGraph_Data);
        barGraph_Data.setColor(getResources().getColor(R.color.fpg_orange));
        barGraph_Data.setSpacing(5);


        mLossesGraph = root.findViewById(R.id.graphLosses);
        LineGraphSeries<DataPoint> losses = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        mLossesGraph.addSeries(losses);
        losses.setBackgroundColor(getResources().getColor(R.color.fpg_orange));
        losses.setColor(getResources().getColor(R.color.fpg_light_orange));

        return root;
    }
}