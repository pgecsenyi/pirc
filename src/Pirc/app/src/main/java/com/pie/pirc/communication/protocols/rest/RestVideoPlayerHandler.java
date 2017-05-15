package com.pie.pirc.communication.protocols.rest;

import java.util.LinkedHashMap;

import com.pie.pirc.communication.exceptions.CommunicationException;
import com.pie.pirc.communication.interfaces.AudioOutputs;
import com.pie.pirc.communication.interfaces.IVideoPlayerHandler;
import com.pie.pirc.communication.utilities.HttpClient;
import com.pie.pirc.communication.utilities.UrlHelper;

/**
 * Controls a video player using the REST API.
 *
 * Created by pgecsenyi on 2015.06.20..
 */
public class RestVideoPlayerHandler
    extends RestPlayerHandler
    implements IVideoPlayerHandler
{
    public RestVideoPlayerHandler(String baseUrl)
    {
        super(baseUrl, "video");
    }

    /***************************************************************************************************************//**
     * IVideoPlayerHandler implementation.
     ******************************************************************************************************************/

    @Override
    public void sendPlay(String audioOutput, String idVideo, String idSubtitle) throws CommunicationException
    {
        if (audioOutput == null || idVideo == null)
            return;

        // Select audio output.
        String effectiveAudioOutput = AudioOutputs.DIGITAL;
        if (audioOutput.equals(AudioOutputs.ANALOG))
            effectiveAudioOutput = AudioOutputs.ANALOG;

        // Collect arguments.
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("audioout", effectiveAudioOutput);
        parameters.put("id", idVideo);
        if (idSubtitle != null)
            parameters.put("subtitle", idSubtitle);

        // Send query.
        HttpClient.getContent(UrlHelper.buildUrl(super.baseUrl, "player/play"), parameters);
    }
}
