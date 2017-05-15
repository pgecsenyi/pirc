package com.pie.pirc.communication.protocols.rest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.pie.pirc.communication.data.VideoDetails;
import com.pie.pirc.communication.data.VideoFileDetails;
import com.pie.pirc.communication.data.VideoSubtitleDetails;

/**
 * Parses a string in JSON format containing information about video details.
 *
 * Created by pgecsenyi on 2015.04.28..
 */
public class JsonVideoDetailsParser
{
    public static VideoDetails Parse(String jsonString)
    {
        if (jsonString == null)
            return null;

        try
        {
            JSONObject jsonObject = new JSONObject(jsonString);

            // Get details.
            JSONObject jsonDetailsObject = jsonObject.getJSONObject("details");
            if (jsonDetailsObject == null)
                return null;

            // Get title from details.
            String title = jsonDetailsObject.getString("title");

            // Get file list from details.
            ArrayList<VideoFileDetails> files = new ArrayList<>();

            JSONArray jsonFileArray = jsonDetailsObject.getJSONArray("files");
            for (int i = 0; i < jsonFileArray.length(); i++)
            {
                JSONObject jsonFileEntry = jsonFileArray.getJSONObject(i);
                files.add(new VideoFileDetails(
                    jsonFileEntry.getInt("id"),
                    jsonFileEntry.getString("language"),
                    jsonFileEntry.getString("quality")));
            }

            // Get subtitle list from details.
            ArrayList<VideoSubtitleDetails> subtitles = new ArrayList<>();

            JSONArray jsonSubtitleArray = jsonDetailsObject.getJSONArray("subtitles");
            if (jsonSubtitleArray != null)
            {
                for (int i = 0; i < jsonSubtitleArray.length(); i++)
                {
                    JSONObject jsonSubtitleEntry = jsonSubtitleArray.getJSONObject(i);
                    subtitles.add(new VideoSubtitleDetails(
                        jsonSubtitleEntry.getInt("id"),
                        jsonSubtitleEntry.getInt("file"),
                        jsonSubtitleEntry.getString("language")));
                }
            }

            return new VideoDetails(
                title,
                files.toArray(new VideoFileDetails[files.size()]),
                subtitles.toArray(new VideoSubtitleDetails[subtitles.size()]));
        }
        catch (JSONException ex)
        {
            return null;
        }
    }
}
