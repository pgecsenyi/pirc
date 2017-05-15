package com.pie.pirc.communication.data;

/**
 * Contains video title filtering options (from the GUI to the network).
 *
 * Created by pgecsenyi on 2015.04.26..
 */
public class VideoTitleFilter
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private String language, parent, quality, subtitleLanguage, text;

    /***************************************************************************************************************//**
     * Constructors.
     ******************************************************************************************************************/

    public VideoTitleFilter()
    {
    }

    /***************************************************************************************************************//**
     * Getters and setters.
     ******************************************************************************************************************/

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public String getParent()
    {
        return parent;
    }

    public void setParent(String parent)
    {
        this.parent = parent;
    }

    public String getQuality()
    {
        return quality;
    }

    public void setQuality(String quality)
    {
        this.quality = quality;
    }

    public String getSubtitleLanguage()
    {
        return subtitleLanguage;
    }

    public void setSubtitleLanguage(String subtitleLanguage)
    {
        this.subtitleLanguage = subtitleLanguage;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
}
