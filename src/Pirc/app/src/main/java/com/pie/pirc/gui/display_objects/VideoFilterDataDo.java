package com.pie.pirc.gui.display_objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * Stores video filtering options to be displayed on the GUI.
 * Created by pgecsenyi on 2015.11.07..
 */
public class VideoFilterDataDo implements Parcelable
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private HashMap<String, String> languages;

    private HashMap<String, String> qualities;

    /***************************************************************************************************************//**
     * Public fields -- Parcelable creator.
     ******************************************************************************************************************/

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
        {
            public VideoFilterDataDo createFromParcel(Parcel in)
            {
                return new VideoFilterDataDo(in);
            }

            public VideoFilterDataDo[] newArray(int size)
            {
                return new VideoFilterDataDo[size];
            }
        };

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public VideoFilterDataDo(HashMap<String, String> languages, HashMap<String, String> qualities)
    {
        this.languages = languages;
        this.qualities = qualities;
    }

    public VideoFilterDataDo(Parcel in)
    {
        languages = in.readHashMap(HashMap.class.getClassLoader());
        qualities = in.readHashMap(HashMap.class.getClassLoader());
    }

    /***************************************************************************************************************//**
     * Getters.
     ******************************************************************************************************************/

    public HashMap<String, String> getLanguages()
    {
        return languages;
    }

    public HashMap<String, String> getQualities()
    {
        return qualities;
    }

    /***************************************************************************************************************//**
     * Parcelable implementation.
     ******************************************************************************************************************/

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeMap(languages);
        dest.writeMap(qualities);
    }
}
