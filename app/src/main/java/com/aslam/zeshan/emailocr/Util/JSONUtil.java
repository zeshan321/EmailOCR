package com.aslam.zeshan.emailocr.Util;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtil {

    public String getFileID(String JSON) {
        String ID = null;

        try {
            JSONObject jsonObject = new JSONObject(JSON);

            ID = jsonObject.getJSONObject("data").getString("file_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ID;
    }

    public String getText(String JSON) {
        String text = null;

        try {
            JSONObject jsonObject = new JSONObject(JSON);

            text = jsonObject.getJSONObject("data").getString("text");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return text;
    }
}