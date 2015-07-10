package data;

import android.provider.BaseColumns;

/**
 * Created by Sajjad on 7/1/2015.
 */
public class WeatherContract {

    public static final class LocationEntry implements BaseColumns {
        public static final String TABLE_NAME ="location";

        public static final String COLUMN_LOCATION_SETTING="location_setting";
        public static final String COLUMN_CITY_NAME="city_name";
        public static final String COLUMN_COORD_LAT="coord_lat";
        public static final String COLUMN_COORD_LONG="coord_long";


    }

    public static final class WeatherEntry implements BaseColumns {
        public static final String TABLE_NAME = "weather";

        //Column with foreign key into Location Table

        public static final String COLUMN_LOC_KEY = "location_id";
        //Date Stored as text with format YYYY-MM-DD
        public static final String COLUMN_DATETEXT = "date";
        //weather id is return by API which is used to identify then icon
        public static final String COLUMN_WEATHER_ID = "weather_id";

        //short description as provided by API
        //eg "Clear" vs "Sky is Clear"
        public static final String COLUMN_SHORT_DESC = "short_desc";

        //max and min temperature of day (stored as float)
        public static final String COLUMN_MIN_TEMP = "min";
        public static final String COLUMN_MAX_TEMP = "max";

        //Humidity store as float representing Percentage value
        public static final String COLUMN_HUMIDITY = "humidity";

        public static final String COLUMN_PRESSURE = "pressure";

        public static final String COLUMN_WIND_SPEED = "wind";

        public static final String COLUMN_DEGREE = "degree";
    }

}
