package com.pie.pirc.communication.data;

import java.util.HashMap;

/**
 * A Data Transfer Object for video title filtering options (from the network to the GUI). (Here a Data Transfer Object
 * actually transfers data from the mid-level network protocol implementation to the GUI. Objects of this class are not
 * used for transferring data through the network.)
 *
 * Created by pgecsenyi on 2015.11.07..
 */
public class VideoFilterData
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private HashMap<String, String> languages;

    private HashMap<String, String> qualities;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public VideoFilterData(HashMap<String, String> languages, HashMap<String, String> qualities)
    {
        this.languages = languages;
        this.qualities = qualities;
    }

    /***************************************************************************************************************//**
     * Getters.
     ******************************************************************************************************************/

    public HashMap<String, String> getLanguages()
    {
        return languages;
    }

    public HashMap<String, String> getQualities()
    {
        return qualities;
    }
}
