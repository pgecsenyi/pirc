package com.pie.pirc.async.operations;

import com.pie.pirc.async.framework.AsyncOperationException;
import com.pie.pirc.async.framework.IAsyncOperation;
import com.pie.pirc.async.framework.IAsyncOperationResult;
import com.pie.pirc.async.results.EmptyResult;
import com.pie.pirc.communication.interfaces.AudioOutputs;
import com.pie.pirc.communication.interfaces.IPlayerHandler;

/**
 * Asynchronous operation that initiates playing on the remote player.
 *
 * Created by pgecsenyi on 2015.11.19..
 */
public class ControlPlayerPlayAsyncOp implements IAsyncOperation
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private String audioOutput = AudioOutputs.DIGITAL;

    private String id;

    private IPlayerHandler playerHandler;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public ControlPlayerPlayAsyncOp(IPlayerHandler playerHandler, String id)
    {
        this.playerHandler = playerHandler;
        this.id = id;
    }

    public ControlPlayerPlayAsyncOp(IPlayerHandler playerHandler, String audioOutput, String id)
    {
        this(playerHandler, id);
        this.audioOutput = audioOutput;
    }

    /***************************************************************************************************************//**
     * IAsyncOperation implementation.
     ******************************************************************************************************************/

    @Override
    public IAsyncOperationResult invoke() throws AsyncOperationException
    {
        try
        {
            if (playerHandler != null)
                playerHandler.sendPlay(audioOutput, id);
        }
        catch (Exception ex)
        {
            throw new AsyncOperationException("Playing file failed.", ex);
        }

        return new EmptyResult();
    }
}
