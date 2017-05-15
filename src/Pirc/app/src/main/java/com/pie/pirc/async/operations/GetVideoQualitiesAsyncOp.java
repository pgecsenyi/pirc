package com.pie.pirc.async.operations;

import java.util.LinkedHashMap;

import com.pie.pirc.async.framework.AsyncOperationException;
import com.pie.pirc.async.framework.IAsyncOperation;
import com.pie.pirc.async.framework.IAsyncOperationResult;
import com.pie.pirc.async.results.Results;
import com.pie.pirc.async.results.SimpleListResult;
import com.pie.pirc.communication.interfaces.IVideoHandler;

/**
 * Asynchronous operation that retrieves available video qualities from the remote server.
 *
 * Created by pgecsenyi on 2015.06.27..
 */
public class GetVideoQualitiesAsyncOp implements IAsyncOperation
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private IVideoHandler videoHandler;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public GetVideoQualitiesAsyncOp(IVideoHandler videoHandler)
    {
        this.videoHandler = videoHandler;
    }

    /***************************************************************************************************************//**
     * IAsyncOperation implementation.
     ******************************************************************************************************************/

    @Override
    public IAsyncOperationResult invoke() throws AsyncOperationException
    {
        try
        {
            LinkedHashMap<String, String> qualitiesResult = videoHandler.downloadQualities();

            return new SimpleListResult(Results.VIDEO_QUALITIES, qualitiesResult);
        }
        catch (Exception ex)
        {
            throw new AsyncOperationException("Downloading qualities failed.", ex);
        }
    }
}
