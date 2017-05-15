package com.pie.pirc.async.operations;

import java.util.LinkedHashMap;

import com.pie.pirc.async.framework.AsyncOperationException;
import com.pie.pirc.async.framework.IAsyncOperation;
import com.pie.pirc.async.framework.IAsyncOperationResult;
import com.pie.pirc.async.results.VideoFilterDataResult;
import com.pie.pirc.communication.data.VideoFilterData;
import com.pie.pirc.communication.interfaces.IVideoHandler;

/**
 * Asynchronous operation that retrieves options for video title filtering from remote server.
 *
 * Created by pgecsenyi on 2015.06.27..
 */
public class GetVideoFilterDataAsyncOp implements IAsyncOperation
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private IVideoHandler videoHandler;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public GetVideoFilterDataAsyncOp(IVideoHandler videoHandler)
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
            LinkedHashMap<String, String> languages = videoHandler.downloadLanguages();
            LinkedHashMap<String, String> qualities = videoHandler.downloadQualities();

            return new VideoFilterDataResult(new VideoFilterData(languages, qualities));
        }
        catch (Exception ex)
        {
            throw new AsyncOperationException("Downloading data failed.", ex);
        }
    }
}
