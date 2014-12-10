package com.bbvadata.timeseries;

import com.dtw.TimeWarpInfo;
import com.timeseries.TimeSeries;
import com.timeseries.TimeSeriesPoint;
import com.util.DistanceFunction;
import com.util.DistanceFunctionFactory;

import java.util.ArrayList;

/**
 * Created by Roberto Maestre on 03/12/14.
 */
public class DTW {

    public static double calculateDTW(double[] x, double[] y) {

        final ArrayList currentLineValues = new ArrayList();

        // Debug info
        //TimeSeries aux = new TimeSeries("/tmp/synthetic_norm.csv", new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, false, false, ' ');

        // Create two Time series from
        TimeSeries tsX = new TimeSeries(x);
        TimeSeries tsY = new TimeSeries(y);

        final DistanceFunction distFn;
        distFn = DistanceFunctionFactory.getDistFnByName("EuclideanDistance");

        final TimeWarpInfo info = com.dtw.FastDTW.getWarpInfoBetween(tsX, tsY, 1, distFn);

        //System.out.println("Warp Distance: " + info.getDistance());
        //System.out.println("Warp Path:     " + info.getPath());

        return info.getDistance();
    }
}
