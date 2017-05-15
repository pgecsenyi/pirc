package com.pie.pirc.communication.protocols.rest;

import java.util.LinkedHashMap;

import com.pie.pirc.communication.exceptions.CommunicationException;
import com.pie.pirc.communication.interfaces.IAudioHandler;
import com.pie.pirc.communication.interfaces.IPlayerHandler;
import com.pie.pirc.communication.utilities.HttpClient;
import com.pie.pirc.communication.utilities.UrlHelper;

/**
 * Handles audio data using the REST API.
 *
 * Created by pgecsenyi on 2015.11.19..
 */
public class RestAudioHandler implements IAudioHandler
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private String baseUrl;

    private RestPlayerHandler playerHandler;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public RestAudioHandler(String baseUrl)
    {
        this.baseUrl = baseUrl;
        this.playerHandler = new RestPlayerHandler(baseUrl, "audio");
    }

    /***************************************************************************************************************//**
     * IAudioHandler implementation.
     ******************************************************************************************************************/

    @Override
    public LinkedHashMap<String, String> downloadAlbums() throws CommunicationException
    {
        return downloadAlbums(null);
    }

    @Override
    public LinkedHashMap<String, String> downloadAlbums(String artist) throws CommunicationException
    {
        // Prepare parameters.
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        if (artist != null && !artist.equals(""))
            parameters.put("artist", artist);

        // Get data from service.
        String jsonString = HttpClient.getContent(UrlHelper.buildUrl(this.baseUrl, "audio/albums"), parameters);

        return JsonListParser.ParseSpecific(jsonString, "albums", "id", "album");
    }

    @Override
    public LinkedHashMap<String, String> downloadArtists() throws CommunicationException
    {
        // Get data from service.
        String jsonString = HttpClient.getContent(UrlHelper.buildUrl(this.baseUrl, "audio/artists"));

        return JsonListParser.ParseSpecific(jsonString, "artists", "id", "artist");
    }

    @Override
    public LinkedHashMap<String, String> downloadTracks(String album) throws CommunicationException
    {
        // Prepare parameters.
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        if (album != null && !album.equals(""))
            parameters.put("album", album);

        // Get data from service.
        String jsonString = HttpClient.getContent(UrlHelper.buildUrl(this.baseUrl, "audio/tracks"), parameters);

        return JsonListParser.ParseSpecific(jsonString, "tracks", "id", "title");
    }

    @Override
    public IPlayerHandler getPlayer()
    {
        return playerHandler;
    }
}
