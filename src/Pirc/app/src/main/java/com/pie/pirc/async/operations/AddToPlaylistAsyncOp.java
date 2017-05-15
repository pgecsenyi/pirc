package com.pie.pirc.async.operations;

import com.pie.pirc.async.framework.AsyncOperationException;
import com.pie.pirc.async.framework.IAsyncOperation;
import com.pie.pirc.async.framework.IAsyncOperationResult;
import com.pie.pirc.async.results.EmptyResult;
import com.pie.pirc.communication.data.PlaylistItem;
import com.pie.pirc.communication.interfaces.IPlaylistHandler;

/**
 * Created by pgecsenyi on 2016.02.21..
 */
public class AddToPlaylistAsyncOp implements IAsyncOperation
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private IPlaylistHandler playlistHandler;

    private PlaylistItem playlistItem;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public AddToPlaylistAsyncOp(IPlaylistHandler playlistHandler, PlaylistItem playlistItem)
    {
        this.playlistHandler = playlistHandler;
        this.playlistItem = playlistItem;
    }

    /***************************************************************************************************************//**
     * IAsyncOperation implementation.
     ******************************************************************************************************************/

    @Override
    public IAsyncOperationResult invoke() throws AsyncOperationException
    {
        try
        {
            playlistHandler.add(playlistItem);

            return new EmptyResult();
        }
        catch (Exception ex)
        {
            throw new AsyncOperationException("Downloading albums failed.", ex);
        }
    }
}
