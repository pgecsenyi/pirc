package com.pie.pirc.communication.data;

/**
 * A Data Transfer Object for details about a particular subtitle file. (Here a Data Transfer Object actually transfers
 * data from the mid-level network protocol implementation to the GUI. Objects of this class are not used for
 * transferring data through the network.)
 *
 * Created by pgecsenyi on 2015.05.23..
 */
public class VideoSubtitleDetails
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private int id;

    private int idVideo;

    private String language;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public VideoSubtitleDetails(int id, int idVideo, String language)
    {
        this.id = id;
        this.idVideo = idVideo;
        this.language = language;
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
}
