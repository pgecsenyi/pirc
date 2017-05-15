package com.pie.pirc.communication.data;

/**
 * A Data Transfer Object for video details. (A Data Transfer Object here actually transfers data from the mid-level
 * network protocol implementation to the GUI. Objects of this class are not used for transferring data through the
 * network.)
 *
 * Created by pgecsenyi on 2015.04.26..
 */
public class VideoDetails
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private VideoFileDetails[] files;

    private VideoSubtitleDetails[] subtitles;

    private String title;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public VideoDetails(String title, VideoFileDetails[] files)
    {
        this.title = title;
        this.files = files;
    }

    public VideoDetails(String title, VideoFileDetails[] files, VideoSubtitleDetails[] subtitles)
    {
        this(title, files);
        this.subtitles = subtitles;
    }

    /***************************************************************************************************************//**
     * Getters.
     ******************************************************************************************************************/

    public VideoFileDetails[] getFiles()
    {
        return files;
    }

    public VideoSubtitleDetails[] getSubtitles()
    {
        return subtitles;
    }

    public String getTitle()
    {
        return title;
    }
}
