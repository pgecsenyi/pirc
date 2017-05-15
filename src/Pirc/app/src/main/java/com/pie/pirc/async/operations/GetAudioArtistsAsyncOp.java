package com.pie.pirc.async.operations;

import java.util.LinkedHashMap;

import com.pie.pirc.async.framework.AsyncOperationException;
import com.pie.pirc.async.framework.IAsyncOperation;
import com.pie.pirc.async.framework.IAsyncOperationResult;
import com.pie.pirc.async.results.Results;
import com.pie.pirc.async.results.SimpleListResult;
import com.pie.pirc.communication.interfaces.IAudioHandler;

/**
 * Asynchronous operation that retrieves available audio artists from the remote server.
 *
 * Created by pgecsenyi on 2015.11.19..
 */
public class GetAudioArtistsAsyncOp implements IAsyncOperation
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private IAudioHandler audioHandler;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public GetAudioArtistsAsyncOp(IAudioHandler audioHandler)
    {
        this.audioHandler = audioHandler;
    }

    /***************************************************************************************************************//**
     * IAsyncOperation implementation.
     ******************************************************************************************************************/

    @Override
    public IAsyncOperationResult invoke() throws AsyncOperationException
    {
        try
        {
            LinkedHashMap<String, String> artistsResult = audioHandler.downloadArtists();

            return new SimpleListResult(Results.AUDIO_ARTISTS, artistsResult);
        }
        catch (Exception ex)
        {
            throw new AsyncOperationException("Downloading artists failed.", ex);
        }
    }
}
