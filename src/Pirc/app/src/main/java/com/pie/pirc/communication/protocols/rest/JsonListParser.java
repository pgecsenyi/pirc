package com.pie.pirc.communication.protocols.rest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

/**
 * Parses a string in JSON format containing a list of key-value pairs.
 *
 * Created by pgecsenyi on 2015.04.28..
 */
public class JsonListParser
{
    public static LinkedHashMap<String, String> Parse(String jsonString, String arrayName)
    {
        if (jsonString == null)
            return null;

        try
        {
            JSONObject jsonObject = new JSONObject(jsonString);

            JSONArray jsonArray = jsonObject.getJSONArray(arrayName);
            if (jsonArray == null || jsonArray.length() <= 0)
                return null;

            LinkedHashMap<String, String> result = new LinkedHashMap<>();

            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONArray jsonArrayEntry = jsonArray.getJSONArray(i);
                result.put(jsonArrayEntry.getString(0), jsonArrayEntry.getString(1));
            }

            return result;
        }
        catch (JSONException ex)
        {
            return null;
        }
    }

    public static LinkedHashMap<String, String> ParseSpecific(
        String jsonString,
        String arrayName,
        String key,
        String value)
    {
        if (jsonString == null)
            return null;

        try
        {
            JSONObject jsonObject = new JSONObject(jsonString);

            JSONArray jsonArray = jsonObject.getJSONArray(arrayName);
            if (jsonArray == null || jsonArray.length() <= 0)
                return null;

            LinkedHashMap<String, String> result = new LinkedHashMap<>();

            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonArrayEntry = jsonArray.getJSONObject(i);
                result.put(jsonArrayEntry.getString(key), jsonArrayEntry.getString(value));
            }

            return result;
        }
        catch (JSONException ex)
        {
            return null;
        }
    }
}
