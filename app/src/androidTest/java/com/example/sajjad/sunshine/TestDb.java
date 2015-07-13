package com.example.sajjad.sunshine;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import java.util.Map;
import java.util.Set;

import data.WeatherContract;
import data.WeatherDbHelper;


public class TestDb extends AndroidTestCase {
    public static final String LOG_TAG = TestDb.class.getSimpleName();

    public void testCreateDb() throws Throwable{
        mContext.deleteDatabase(WeatherDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new  WeatherDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true,db.isOpen());
        db.close();
    }
    static public String TEST_CITY_NAME="North Pole";
    ContentValues getLocationContentVales()
    {
        //String testName="North Pole";
        String testLocationSetting="99705";
        double testLatitude=64.772;
        double testLongitude=-147.355;

        ContentValues values = new ContentValues();
        values.put(WeatherContract.LocationEntry.COLUMN_CITY_NAME, TEST_CITY_NAME);
        values.put(WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING, testLocationSetting);
        values.put(WeatherContract.LocationEntry.COLUMN_COORD_LAT, testLatitude);
        values.put(WeatherContract.LocationEntry.COLUMN_COORD_LONG, testLongitude);

        return values;
    }

    public static ContentValues getWeatherContentValues(long locationRowId)
    {
        ContentValues weatherValues = new ContentValues();
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_LOC_KEY, locationRowId);
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_DATETEXT, "20141205");
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_DEGREE, 1.2);
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_HUMIDITY, 1.2);
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_PRESSURE, 1.3);
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP, 75);
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP, 65);
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_SHORT_DESC, "Asteroids");
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_WIND_SPEED, 5.5);
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_WEATHER_ID, 321);
        return weatherValues;
    }

    public static void validateCursor(ContentValues expectedValues, Cursor valueCursor)
    {
        Set<Map.Entry<String, Object>> valueSet= expectedValues.valueSet();
        for(Map.Entry<String,Object> entry : valueSet)
        {
            String columnName=entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse(-1 == idx);

            String expectedValue= entry.getValue().toString();

            assertEquals(expectedValue, valueCursor.getString(idx));
        }

    }

    public void testInsertReadDb()
    {


        WeatherDbHelper dbHelper = new WeatherDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = getLocationContentVales();

        long locationRowId;
        locationRowId = db.insert(WeatherContract.LocationEntry.TABLE_NAME, null, values);
        assertTrue(locationRowId!=-1);
        Log.d(LOG_TAG,"New Row Id "+locationRowId);

        /*String[] columns = {
                WeatherContract.LocationEntry._ID,
                WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING,
                WeatherContract.LocationEntry.COLUMN_CITY_NAME,
                WeatherContract.LocationEntry.COLUMN_COORD_LAT,
                WeatherContract.LocationEntry.COLUMN_COORD_LONG,
        };*/

        Cursor cursor = db.query(
                WeatherContract.LocationEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if(cursor.moveToFirst())
        {
            /*int locationIndex = cursor.getColumnIndex(WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING);
            String location =cursor.getString(locationIndex);

            int nameIndex = cursor.getColumnIndex(WeatherContract.LocationEntry.COLUMN_CITY_NAME);
            String name =cursor.getString(nameIndex);


            int latIndex = cursor.getColumnIndex(WeatherContract.LocationEntry.COLUMN_COORD_LAT);
            String latitude =cursor.getString(latIndex);


            int longIndex = cursor.getColumnIndex(WeatherContract.LocationEntry.COLUMN_COORD_LONG);
            String longitude =cursor.getString(longIndex);

            assertEquals(testName, name);
            assertEquals(testLocationSetting, location);
            assertEquals(testLatitude, latitude);
            assertEquals(testLongitude, longitude);
            */
            validateCursor(values,cursor);


            ContentValues weatherValues =getWeatherContentValues(locationRowId);
            long weatherRowId;
            weatherRowId = db.insert(WeatherContract.WeatherEntry.TABLE_NAME, null,weatherValues);
            assertTrue(weatherRowId!=-1);

            Cursor weatherCursor =db.query(
                    WeatherContract.WeatherEntry.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            if(weatherCursor.moveToFirst())
            {
                validateCursor(weatherValues, weatherCursor);

                /*int dateIndex= weatherCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_DATETEXT);
                String date = weatherCursor.getString(dateIndex);

                int degreesIndex= weatherCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_DEGREE);
                String degrees = weatherCursor.getString(degreesIndex);

                int humidityIndex= weatherCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_HUMIDITY);
                String humidity = weatherCursor.getString(humidityIndex);

                int pressureIndex= weatherCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_PRESSURE);
                String pressure = weatherCursor.getString(pressureIndex);

                int maxIndex= weatherCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP);
                double max = weatherCursor.getDouble(maxIndex);

                int minIndex= weatherCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP);
                double min = weatherCursor.getDouble(minIndex);

                int descIndex= weatherCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_SHORT_DESC);
                String desc = weatherCursor.getString(descIndex);

                int windIndex= weatherCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_WIND_SPEED);
                double wind = weatherCursor.getDouble(windIndex);

                int weatherIdIndex= weatherCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_WEATHER_ID);
                int weatherId = weatherCursor.getInt(weatherIdIndex);
                */

            }
            else
            {
                fail("no  values returned :( ");
            }
        }
        else
        {
            fail("no  values returned :( ");
        }
    }
}
