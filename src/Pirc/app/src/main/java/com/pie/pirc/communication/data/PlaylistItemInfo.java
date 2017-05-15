package com.pie.pirc.communication.data;

/**
 * Created by pgecsenyi on 2016.02.21..
 */
public class PlaylistItemInfo
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private String category;

    private String id;

    private String title;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public PlaylistItemInfo(String category, String id, String title)
    {
        this.category = category;
        this.id = id;
        this.title = title;
    }

    /***************************************************************************************************************//**
     * Getters.
     ******************************************************************************************************************/

    public String getCategory()
    {
        return category;
    }

    public String getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }
}
