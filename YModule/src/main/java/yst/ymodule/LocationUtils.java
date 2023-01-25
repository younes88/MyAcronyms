package yst.ymodule;

import android.location.Location;

/**
 * Created by Kasper on 12/3/2015.
 */
public class LocationUtils {

    /**
     * using WSG84
     * using the Metric system
     */
    public static float getDistance(double startLati, double startLongi, double goalLati, double goalLongi) {
        try {
            float[] resultArray = new float[99];
            Location.distanceBetween(startLati, startLongi, goalLati, goalLongi, resultArray);
            return resultArray[0];
        } catch (Exception e) {
            return 0;
        }
    }

}
