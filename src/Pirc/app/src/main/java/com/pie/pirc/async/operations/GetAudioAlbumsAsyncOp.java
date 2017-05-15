package com.pie.pirc.async.operations;

import java.util.LinkedHashMap;

import com.pie.pirc.async.framework.AsyncOperationException;
import com.pie.pirc.async.framework.IAsyncOperation;
import com.pie.pirc.async.framework.IAsyncOperationResult;
import com.pie.pirc.async.results.Results;
import com.pie.pirc.async.results.SimpleListResult;
import com.pie.pirc.communication.interfaces.IAudioHandler;

/**
 * Asynchronous operation that retrieves available audio albums from the remote server.
 *
 * Created by pgecsenyi on 2015.11.19..
 */
public class GetAudioAlbumsAsyncOp implements IAsyncOperation
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private String artist;

    private IAudioHandler audioHandler;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public GetAudioAlbumsAsyncOp(IAudioHandler audioHandler)
    {
        this(audioHandler, null);
    }

    public GetAudioAlbumsAsyncOp(IAudioHandler audioHandler, String artist)
    {
        this.audioHandler = audioHandler;
        this.artist = artist;
    }

    /***************************************************************************************************************//**
     * IAsyncOperation implementation.
     ******************************************************************************************************************/

    @Override
    public IAsyncOperationResult invoke() throws AsyncOperationException
    {
        try
        {
            LinkedHashMap<String, String> albumsResult = audioHandler.downloadAlbums(artist);

            return new SimpleListResult(Results.AUDIO_ALBUMS, albumsResult);
        }
        catch (Exception ex)
        {
            throw new AsyncOperationException("Downloading albums failed.", ex);
        }
    }
}
