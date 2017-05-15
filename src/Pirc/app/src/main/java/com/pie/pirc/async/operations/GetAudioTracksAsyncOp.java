package com.pie.pirc.async.operations;

import java.util.LinkedHashMap;

import com.pie.pirc.async.framework.AsyncOperationException;
import com.pie.pirc.async.framework.IAsyncOperation;
import com.pie.pirc.async.framework.IAsyncOperationResult;
import com.pie.pirc.async.results.Results;
import com.pie.pirc.async.results.SimpleListResult;
import com.pie.pirc.communication.interfaces.IAudioHandler;

/**
 * Asynchronous operation that retrieves available audio tracks from the remote server.
 *
 * Created by pgecsenyi on 2015.11.19..
 */
public class GetAudioTracksAsyncOp implements IAsyncOperation
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private String album;

    private IAudioHandler audioHandler;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public GetAudioTracksAsyncOp(IAudioHandler audioHandler)
    {
        this(audioHandler, null);
    }

    public GetAudioTracksAsyncOp(IAudioHandler audioHandler, String album)
    {
        this.audioHandler = audioHandler;
        this.album = album;
    }

    /***************************************************************************************************************//**
     * IAsyncOperation implementation.
     ******************************************************************************************************************/

    @Override
    public IAsyncOperationResult invoke() throws AsyncOperationException
    {
        try
        {
            LinkedHashMap<String, String> tracksResult = audioHandler.downloadTracks(album);

            return new SimpleListResult(Results.AUDIO_TRACKS, tracksResult);
        }
        catch (Exception ex)
        {
            throw new AsyncOperationException("Downloading tracks failed.", ex);
        }
    }
}
