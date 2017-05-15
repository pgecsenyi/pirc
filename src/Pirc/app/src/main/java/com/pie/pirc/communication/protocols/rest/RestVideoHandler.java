package com.pie.pirc.communication.protocols.rest;

import java.util.LinkedHashMap;

import com.pie.pirc.communication.data.VideoDetails;
import com.pie.pirc.communication.data.VideoTitleFilter;
import com.pie.pirc.communication.exceptions.CommunicationException;
import com.pie.pirc.communication.interfaces.IVideoHandler;
import com.pie.pirc.communication.interfaces.IVideoPlayerHandler;
import com.pie.pirc.communication.utilities.HttpClient;
import com.pie.pirc.communication.utilities.UrlHelper;

/**
 * Handles video data using the REST API.
 *
 * Created by pgecsenyi on 2015.04.26..
 */
public class RestVideoHandler implements IVideoHandler
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private String baseUrl;

    private RestVideoPlayerHandler playerHandler;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public RestVideoHandler(String baseUrl)
    {
        this.baseUrl = baseUrl;
        this.playerHandler = new RestVideoPlayerHandler(baseUrl);
    }

    /***************************************************************************************************************//**
     * IVideoHandler implementation.
     ******************************************************************************************************************/

    @Override
    public VideoDetails downloadDetails(String id) throws CommunicationException
    {
        // Get data from service.
        String jsonString = HttpClient.getContent(UrlHelper.buildUrl(this.baseUrl, "video/details", id), null);

        return JsonVideoDetailsParser.Parse(jsonString);
    }

    @Override
    public LinkedHashMap<String, String> downloadLanguages() throws CommunicationException
    {
        // Get data from service.
        String jsonString = HttpClient.getContent(UrlHelper.buildUrl(this.baseUrl, "video/languages"), null);

        return JsonListParser.ParseSpecific(jsonString, "languages", "id", "language");
    }

    @Override
    public LinkedHashMap<String, String> downloadQualities() throws CommunicationException
    {
        // Get data from service.
        String jsonString = HttpClient.getContent(UrlHelper.buildUrl(this.baseUrl, "video/qualities"), null);

        return JsonListParser.ParseSpecific(jsonString, "qualities", "id", "quality");
    }

    @Override
    public LinkedHashMap<String, String> downloadTitles(VideoTitleFilter filter) throws CommunicationException
    {
        // Prepare parameters.
        LinkedHashMap<String, String> parameters = prepareParameters(filter);

        // Get data from service.
        String jsonString = HttpClient.getContent(UrlHelper.buildUrl(this.baseUrl, "video/titles"), parameters);

        return JsonListParser.ParseSpecific(jsonString, "titles", "id", "title");
    }

    @Override
    public IVideoPlayerHandler getPlayer()
    {
        return playerHandler;
    }

    /***************************************************************************************************************//**
     * Private methods / Handling request parameters.
     ******************************************************************************************************************/

    private LinkedHashMap<String, String> prepareParameters(VideoTitleFilter filter)
    {
        if (filter == null)
            return null;

        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        if (filter.getLanguage() != null)
            parameters.put("language", filter.getLanguage());
        if (filter.getParent() != null)
            parameters.put("parent", filter.getParent());
        if (filter.getQuality() != null)
            parameters.put("quality", filter.getQuality());
        if (filter.getSubtitleLanguage() != null)
            parameters.put("subtitle", filter.getSubtitleLanguage());
        if (filter.getText() != null && !filter.getText().trim().equals(""))
            parameters.put("text", filter.getText());

        return parameters;
    }
}
