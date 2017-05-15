package com.pie.pirc.communication.protocols.rest;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pie.pirc.communication.data.PlaylistItem;
import com.pie.pirc.communication.data.PlaylistItemInfo;
import com.pie.pirc.communication.exceptions.CommunicationException;
import com.pie.pirc.communication.interfaces.AudioOutputs;
import com.pie.pirc.communication.interfaces.IPlaylistHandler;
import com.pie.pirc.communication.utilities.HttpClient;
import com.pie.pirc.communication.utilities.UrlHelper;

/**
 * Created by pgecsenyi on 2016.02.21..
 */
public class RestPlaylistHandler implements IPlaylistHandler
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private String baseUrl;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public RestPlaylistHandler(String baseUrl)
    {
        this.baseUrl = baseUrl;
    }

    /***************************************************************************************************************//**
     * IPlaylistHandler implementation.
     ******************************************************************************************************************/

    @Override
    public void add(PlaylistItem item) throws CommunicationException
    {
        if (item == null)
            return;

        // Select audio output.
        String effectiveAudioOutput = AudioOutputs.DIGITAL;
        if (item.getAudioOutput().equals(AudioOutputs.ANALOG))
            effectiveAudioOutput = AudioOutputs.ANALOG;

        // Prepare parameters.
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("category", item.getCategory());
        parameters.put("id", item.getId());
        parameters.put("audioout", effectiveAudioOutput);

        // Send query.
        HttpClient.getContent(UrlHelper.buildUrl(this.baseUrl, "playlist/add"), parameters, HttpClient.POST);
    }

    @Override
    public void clear() throws CommunicationException
    {
        // Send query.
        HttpClient.getContent(UrlHelper.buildUrl(this.baseUrl, "playlist/clear"), HttpClient.DELETE);
    }

    @Override
    public void remove(String id) throws CommunicationException
    {
        if (id != null && !id.equals(""))
            return;

        // Prepare parameters.
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("id", id);

        // Send query.
        HttpClient.getContent(UrlHelper.buildUrl(this.baseUrl, "playlist/remove"), parameters, HttpClient.DELETE);
    }

    @Override
    public PlaylistItemInfo[] view() throws CommunicationException
    {
        // Get data from service.
        String jsonString = HttpClient.getContent(UrlHelper.buildUrl(this.baseUrl, "playlist/view"));

        // Parse data.
        LinkedHashMap<String, String> response = JsonListParser.ParseSpecific(jsonString, "playlist", "id", "title");
        PlaylistItemInfo[] playlist = new PlaylistItemInfo[response.size()];

        int i = 0;
        for (Map.Entry<String, String> entry : response.entrySet())
        {
            playlist[i] = new PlaylistItemInfo(null, entry.getKey(), entry.getValue());
            i++;
        }

        return playlist;
    }
}
