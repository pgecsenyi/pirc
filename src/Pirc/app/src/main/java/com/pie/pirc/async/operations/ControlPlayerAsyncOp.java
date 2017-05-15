package com.pie.pirc.async.operations;

import com.pie.pirc.async.framework.AsyncOperationException;
import com.pie.pirc.async.framework.IAsyncOperation;
import com.pie.pirc.async.framework.IAsyncOperationResult;
import com.pie.pirc.async.results.EmptyResult;
import com.pie.pirc.communication.interfaces.IPlayerHandler;

/**
 * Asynchronous operation for controlling the remote video player.
 *
 * Created by pgecsenyi on 2015.05.01..
 */
public class ControlPlayerAsyncOp implements IAsyncOperation
{
    /***************************************************************************************************************//**
     * Public constants.
     ******************************************************************************************************************/

    public static final int FAST_FORWARD = 1;
    public static final int FAST_REWIND = 2;
    public static final int FASTER = 3;
    public static final int FORWARD = 4;
    public static final int PAUSE = 5;
    public static final int SLOWER = 6;
    public static final int STOP = 7;
    public static final int REWIND = 8;
    public static final int VOLUME_DOWN = 9;
    public static final int VOLUME_UP = 10;

    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private int action;

    private IPlayerHandler playerHandler;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public ControlPlayerAsyncOp(IPlayerHandler playerHandler, int action)
    {
        this.playerHandler = playerHandler;
        this.action = action;
    }

    /***************************************************************************************************************//**
     * IAsyncOperation implementation.
     ******************************************************************************************************************/

    @Override
    public IAsyncOperationResult invoke() throws AsyncOperationException
    {
        if (playerHandler == null)
            return null;

        try
        {
            switch (action)
            {
                case FASTER:
                    playerHandler.sendFaster();
                    break;
                case FAST_FORWARD:
                    playerHandler.sendFastForward();
                    break;
                case FAST_REWIND:
                    playerHandler.sendFastRewind();
                    break;
                case FORWARD:
                    playerHandler.sendForward();
                    break;
                case PAUSE:
                    playerHandler.sendPause();
                    break;
                case REWIND:
                    playerHandler.sendRewind();
                    break;
                case SLOWER:
                    playerHandler.sendSlower();
                    break;
                case STOP:
                    playerHandler.sendStop();
                    break;
                case VOLUME_DOWN:
                    playerHandler.sendVolumeDown();
                    break;
                case VOLUME_UP:
                    playerHandler.sendVolumeUp();
                    break;
            }
        }
        catch (Exception ex)
        {
            throw new AsyncOperationException("Asynchronous operation failed.", ex);
        }

        return new EmptyResult();
    }
}
