package com.pie.pirc.communication.data;

/**
 * Created by pgecsenyi on 2016.02.21..
 */
public class PlaylistItem
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private String audioOutput;

    private String category;

    private String id;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public PlaylistItem(String audioOutput, String category, String id)
    {
        this.audioOutput = audioOutput;
        this.category = category;
        this.id = id;
    }

    /***************************************************************************************************************//**
     * Getters.
     ******************************************************************************************************************/

    public String getAudioOutput()
{
    return audioOutput;
}

    public String getCategory()
    {
        return category;
    }

    public String getId()
    {
        return id;
    }
}
