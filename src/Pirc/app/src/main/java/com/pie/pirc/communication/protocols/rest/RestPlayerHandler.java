package com.pie.pirc.communication.protocols.rest;

import java.util.LinkedHashMap;

import com.pie.pirc.communication.exceptions.CommunicationException;
import com.pie.pirc.communication.interfaces.AudioOutputs;
import com.pie.pirc.communication.interfaces.IPlayerHandler;
import com.pie.pirc.communication.utilities.HttpClient;
import com.pie.pirc.communication.utilities.UrlHelper;

/**
 * Controls a multimedia player using the REST API.
 *
 * Created by pgecsenyi on 2015.04.26..
 */
public class RestPlayerHandler implements IPlayerHandler
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    protected String baseUrl;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public RestPlayerHandler(String baseUrl)
    {
        this.baseUrl = UrlHelper.buildUrl(baseUrl, "");
    }

    public RestPlayerHandler(String baseUrl, String category)
    {
        this.baseUrl = UrlHelper.buildUrl(baseUrl, category);
    }

    /***************************************************************************************************************//**
     * IPlayerHandler implementation.
     ******************************************************************************************************************/

    @Override
    public void sendFaster() throws CommunicationException
    {
        HttpClient.getContent(UrlHelper.buildUrl(this.baseUrl, "player/faster"));
    }

    @Override
    public void sendFastForward() throws CommunicationException
    {
        HttpClient.getContent(UrlHelper.buildUrl(this.baseUrl, "player/fastforward"));
    }

    @Override
    public void sendFastRewind() throws CommunicationException
    {
        HttpClient.getContent(UrlHelper.buildUrl(this.baseUrl, "player/fastrewind"));
    }

    @Override
    public void sendForward() throws CommunicationException
    {
        HttpClient.getContent(UrlHelper.buildUrl(this.baseUrl, "player/forward"));
    }

    @Override
    public void sendPause() throws CommunicationException
    {
        HttpClient.getContent(UrlHelper.buildUrl(this.baseUrl, "player/pause"));
    }

    @Override
    public void sendPlay(String id) throws CommunicationException
    {
        if (id != null)
            HttpClient.getContent(UrlHelper.buildUrl(this.baseUrl, "player/play/", id));
    }

    @Override
    public void sendPlay(String audioOutput, String id) throws CommunicationException
    {
        if (audioOutput == null || id == null)
            return;

        // Select audio output.
        String effectiveAudioOutput = AudioOutputs.DIGITAL;
        if (audioOutput.equals(AudioOutputs.ANALOG))
            effectiveAudioOutput = AudioOutputs.ANALOG;

        // Collect arguments.
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("audioout", effectiveAudioOutput);
        parameters.put("id", id);

        // Send query.
        HttpClient.getContent(UrlHelper.buildUrl(this.baseUrl, "player/play"), parameters);
    }

    @Override
    public void sendRewind() throws CommunicationException
    {
        HttpClient.getContent(UrlHelper.buildUrl(this.baseUrl, "player/rewind"));
    }

    @Override
    public void sendSlower() throws CommunicationException
    {
        HttpClient.getContent(UrlHelper.buildUrl(this.baseUrl, "player/slower"));
    }

    @Override
    public void sendStop() throws CommunicationException
    {
        HttpClient.getContent(UrlHelper.buildUrl(this.baseUrl, "player/stop"));
    }

    @Override
    public void sendVolumeDown() throws CommunicationException
    {
        HttpClient.getContent(UrlHelper.buildUrl(this.baseUrl, "player/volumedown"));
    }

    @Override
    public void sendVolumeUp() throws CommunicationException
    {
        HttpClient.getContent(UrlHelper.buildUrl(this.baseUrl + "/player/volumeup"));
    }
}
