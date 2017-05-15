package com.pie.pirc.gui.display_objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A Display Object for video subtitle details.
 *
 * Created by pgecsenyi on 2015.06.14..
 */
public class VideoSubtitleDetailsDo implements Parcelable
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private int id;

    private int idVideo;

    private String language;

    /***************************************************************************************************************//**
     * Public fields -- Parcelable creator.
     ******************************************************************************************************************/

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
        {
            public VideoSubtitleDetailsDo createFromParcel(Parcel in)
            {
                return new VideoSubtitleDetailsDo(in);
            }

            public VideoSubtitleDetailsDo[] newArray(int size)
            {
                return new VideoSubtitleDetailsDo[size];
            }
        };

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public VideoSubtitleDetailsDo(int id, int idVideo, String language)
    {
        this.id = id;
        this.idVideo = idVideo;
        this.language = language;
    }

    public VideoSubtitleDetailsDo(Parcel in)
    {
        id = in.readInt();
        idVideo = in.readInt();
        language = in.readString();
    }

    /***************************************************************************************************************//**
     * Getters.
     ******************************************************************************************************************/

    public int getId()
    {
        return id;
    }

    public int getIdVideo()
    {
        return idVideo;
    }

    public String getLanguage()
    {
        return language;
    }

    /***************************************************************************************************************//**
     * Overridden methods.
     ******************************************************************************************************************/

    @Override
    public String toString()
    {
        return language;
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
        dest.writeInt(idVideo);
        dest.writeString(language);
    }
}
