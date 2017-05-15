package com.pie.pirc.async.operations;

import com.pie.pirc.async.framework.AsyncOperationException;
import com.pie.pirc.async.framework.IAsyncOperation;
import com.pie.pirc.async.framework.IAsyncOperationResult;
import com.pie.pirc.async.results.EmptyResult;
import com.pie.pirc.communication.interfaces.AudioOutputs;
import com.pie.pirc.communication.interfaces.IVideoPlayerHandler;

/**
 * Asynchronous operation that initiates video playing on the remote player.
 *
 * Created by pgecsenyi on 2015.05.01..
 */
public class ControlVideoPlayerPlayAsyncOp implements IAsyncOperation
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private String audioOutput = AudioOutputs.DIGITAL;

    private String idSubtitle;

    private String idVideo;

    private IVideoPlayerHandler videoPlayerHandler;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public ControlVideoPlayerPlayAsyncOp(IVideoPlayerHandler videoPlayerHandler, String idVideo)
    {
        this.videoPlayerHandler = videoPlayerHandler;
        this.idVideo = idVideo;
    }

    public ControlVideoPlayerPlayAsyncOp(IVideoPlayerHandler videoPlayerHandler, String idVideo, String idSubtitle)
    {
        this(videoPlayerHandler, idVideo);
        this.idSubtitle = idSubtitle;
    }

    public ControlVideoPlayerPlayAsyncOp(
        IVideoPlayerHandler videoPlayerHandler,
        String audioOutput,
        String idVideo,
        String idSubtitle)
    {
        this(videoPlayerHandler, idVideo, idSubtitle);
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
            if (videoPlayerHandler != null)
                videoPlayerHandler.sendPlay(audioOutput, idVideo, idSubtitle);
        }
        catch (Exception ex)
        {
            throw new AsyncOperationException("Playing video failed.", ex);
        }

        return new EmptyResult();
    }
}
