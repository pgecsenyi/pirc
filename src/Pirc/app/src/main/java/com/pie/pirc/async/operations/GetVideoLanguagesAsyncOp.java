package com.pie.pirc.async.operations;

import java.util.LinkedHashMap;

import com.pie.pirc.async.framework.AsyncOperationException;
import com.pie.pirc.async.framework.IAsyncOperation;
import com.pie.pirc.async.framework.IAsyncOperationResult;
import com.pie.pirc.async.results.Results;
import com.pie.pirc.async.results.SimpleListResult;
import com.pie.pirc.communication.interfaces.IVideoHandler;

/**
 * Asynchronous operation that retrieves available video languages from the remote server.
 *
 * Created by pgecsenyi on 2015.06.27..
 */
public class GetVideoLanguagesAsyncOp implements IAsyncOperation
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private IVideoHandler videoHandler;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public GetVideoLanguagesAsyncOp(IVideoHandler videoHandler)
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
            LinkedHashMap<String, String> languagesResult = videoHandler.downloadLanguages();

            return new SimpleListResult(Results.VIDEO_LANGUAGES, languagesResult);
        }
        catch (Exception ex)
        {
            throw new AsyncOperationException("Downloading languages failed.", ex);
        }
    }
}
