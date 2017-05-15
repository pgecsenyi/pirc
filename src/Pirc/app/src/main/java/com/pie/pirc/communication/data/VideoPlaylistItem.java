package com.pie.pirc.communication.data;

/**
 * Created by pgecsenyi on 2016.02.21..
 */
public class VideoPlaylistItem extends PlaylistItem
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private String subtitleId;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public VideoPlaylistItem(String audioOutput, String category, String id, String subtitleId)
    {
        super(audioOutput, category, id);
        this.subtitleId = subtitleId;
    }

    /***************************************************************************************************************//**
     * Getters.
     ******************************************************************************************************************/

    public String getSubtitleId()
    {
        return subtitleId;
    }
}
