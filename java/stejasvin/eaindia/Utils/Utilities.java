package stejasvin.eaindia.Utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import stejasvin.eaindia.databases.SkillDatabaseHandler;

/**
 * Created by stejasvin on 7/12/2014.
 */
public class Utilities {

    public static String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        String currentTime = (c.getTime()).toString();
        return currentTime;
    }

    public static String getDate(String input) {
        if (input != null) {
            SimpleDateFormat dateFormat = null;
            Date date = null;
            SimpleDateFormat dateFormatOutput = new SimpleDateFormat(
                    "dd/MM/yyyy");
            try {
                dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                date = dateFormat.parse(input);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                try {
                    dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
                    date = dateFormat.parse(input);
                } catch (ParseException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    try {
                        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        date = dateFormat.parse(input);
                    } catch (ParseException e2) {
                        // TODO Auto-generated catch block
                        e2.printStackTrace();
                    }
                }
            }

            if (date != null) {
                Log.i("TIME-DATE", date.toString());
                String output = dateFormatOutput.format(date);
                Log.i("TIME-DATE", output);
                return output;
            }
        }
        return null;
    }

    public static HashMap<String,String> extractjson(JSONArray jsonArray) throws JSONException{
        HashMap<String,String> map = new HashMap<String,String>();
        for(int i=0;i<jsonArray.length();i++){
            map.put((String) jsonArray.getJSONObject(i).get(SkillDatabaseHandler.KEY_LOCAL_ID),
                    (String) jsonArray.getJSONObject(i).get(SkillDatabaseHandler.KEY_CREATION_DATE));
        }
        return map;
    }
}