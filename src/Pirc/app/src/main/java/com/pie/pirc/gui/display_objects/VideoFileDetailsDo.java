package com.pie.pirc.gui.display_objects;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.HashMap;

/**
 * A Display Object for video file details.
 *
 * Created by pgecsenyi on 2015.06.14..
 */
public class VideoFileDetailsDo implements Comparable<VideoFileDetailsDo>, Parcelable
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private int id;

    private String language;

    private String quality;

    private static HashMap<String, Integer> qualityRanks;

    /***************************************************************************************************************//**
     * Public fields.
     ******************************************************************************************************************/

    public static String preferredLanguage;

    /***************************************************************************************************************//**
     * Public fields -- Parcelable creator.
     ******************************************************************************************************************/

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
        {
            public VideoFileDetailsDo createFromParcel(Parcel in)
            {
                return new VideoFileDetailsDo(in);
            }

            public VideoFileDetailsDo[] newArray(int size)
            {
                return new VideoFileDetailsDo[size];
            }
        };

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    static
    {
        qualityRanks = new HashMap<>();
        qualityRanks.put("LQ", 1);
        qualityRanks.put("HQ", 2);
        qualityRanks.put("DVD", 3);
        qualityRanks.put("HD (720p)", 4);
        qualityRanks.put("HD (1080p)", 5);
    }

    public VideoFileDetailsDo(int id, String language, String quality)
    {
        this.id = id;
        this.language = language;
        this.quality = quality;
    }

    public VideoFileDetailsDo(Parcel in)
    {
        id = in.readInt();
        language = in.readString();
        quality = in.readString();
    }

    /***************************************************************************************************************//**
     * Getters.
     ******************************************************************************************************************/

    public int getId()
    {
        return id;
    }

    public String getLanguage()
    {
        return language;
    }

    public String getQuality()
    {
        return quality;
    }

    /***************************************************************************************************************//**
     * Overridden methods.
     ******************************************************************************************************************/

    @Override
    public String toString()
    {
        return language + ", " + quality;
    }

    /***************************************************************************************************************//**
     * Comparable implementation.
     ******************************************************************************************************************/

    @Override
    public int compareTo(@NonNull VideoFileDetailsDo videoFileDetailsDo)
    {
        if (language.equals(preferredLanguage) && videoFileDetailsDo.language.equals(preferredLanguage))
        {
            if (qualityRanks.get(quality) > qualityRanks.get(videoFileDetailsDo.quality))
                return 1;
            if (qualityRanks.get(quality).equals(qualityRanks.get(videoFileDetailsDo.quality)))
                return 0;
        }
        else if (language.equals(preferredLanguage))
        {
            return 1;
        }
        else if (qualityRanks.get(quality) > qualityRanks.get(videoFileDetailsDo.quality))
        {
            return 1;
        }

        return -1;
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
        dest.writeInt(id);
        dest.writeString(language);
        dest.writeString(quality);
    }
}
