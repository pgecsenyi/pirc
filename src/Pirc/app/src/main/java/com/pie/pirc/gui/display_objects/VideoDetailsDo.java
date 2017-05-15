package com.pie.pirc.gui.display_objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A Display Object for video details.
 *
 * Created by pgecsenyi on 2015.06.14..
 */
public class VideoDetailsDo implements Parcelable
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private VideoFileDetailsDo[] files;

    private VideoSubtitleDetailsDo[] subtitles;

    private String title;

    /***************************************************************************************************************//**
     * Public fields -- Parcelable creator.
     ******************************************************************************************************************/

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
        {
            public VideoDetailsDo createFromParcel(Parcel in)
            {
                return new VideoDetailsDo(in);
            }

            public VideoDetailsDo[] newArray(int size)
            {
                return new VideoDetailsDo[size];
            }
        };

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public VideoDetailsDo(String title, VideoFileDetailsDo[] files)
    {
        this.title = title;
        this.files = files;
    }

    public VideoDetailsDo(String title, VideoFileDetailsDo[] files, VideoSubtitleDetailsDo[] subtitles)
    {
        this(title, files);
        this.subtitles = subtitles;
    }

    public VideoDetailsDo(Parcel in)
    {
        files = (VideoFileDetailsDo[])in.readParcelableArray(VideoFileDetailsDo.class.getClassLoader());
        subtitles = (VideoSubtitleDetailsDo[])in.readParcelableArray(VideoFileDetailsDo.class.getClassLoader());
        title = in.readString();
    }

    /***************************************************************************************************************//**
     * Getters.
     ******************************************************************************************************************/

    public VideoFileDetailsDo[] getFiles()
    {
        return files;
    }

    public VideoSubtitleDetailsDo[] getSubtitles()
    {
        return subtitles;
    }

    public String getTitle()
    {
        return title;
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
        dest.writeParcelableArray(files, flags);
        dest.writeParcelableArray(subtitles, flags);
        dest.writeString(title);
    }
}
