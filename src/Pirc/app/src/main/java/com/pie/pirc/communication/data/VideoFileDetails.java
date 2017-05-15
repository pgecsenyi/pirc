package com.pie.pirc.communication.data;

/**
 * A Data Transfer Object for details about a particular video file. (Here a Data Transfer Object actually transfers
 * data from the mid-level network protocol implementation to the GUI. Objects of this class are not used for
 * transferring data through the network.)
 *
 * Created by pgecsenyi on 2015.04.26..
 */
public class VideoFileDetails
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private int id;

    private String language;

    private String quality;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public VideoFileDetails(int id, String language, String quality)
    {
        this.id = id;
        this.language = language;
        this.quality = quality;
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
}
