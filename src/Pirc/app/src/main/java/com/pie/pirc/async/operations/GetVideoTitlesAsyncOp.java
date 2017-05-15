package com.pie.pirc.async.operations;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pie.pirc.async.framework.AsyncOperationException;
import com.pie.pirc.async.framework.IAsyncOperation;
import com.pie.pirc.async.framework.IAsyncOperationResult;
import com.pie.pirc.async.results.Results;
import com.pie.pirc.async.results.SimpleListResult;
import com.pie.pirc.async.results.VideoDetailsResult;
import com.pie.pirc.communication.data.VideoDetails;
import com.pie.pirc.communication.data.VideoTitleFilter;
import com.pie.pirc.communication.interfaces.IVideoHandler;

/**
 * Asynchronous operation that retrieves available video titles from the remote server.
 *
 * Created by pgecsenyi on 2015.04.30..
 */
public class GetVideoTitlesAsyncOp implements IAsyncOperation
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private VideoTitleFilter filter;

    private IVideoHandler videoHandler;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public GetVideoTitlesAsyncOp(IVideoHandler videoHandler)
    {
        this(videoHandler, null);
    }

    public GetVideoTitlesAsyncOp(IVideoHandler videoHandler, VideoTitleFilter filter)
    {
        this.videoHandler = videoHandler;
        this.filter = filter;
    }

    /***************************************************************************************************************//**
     * IAsyncOperation implementation.
     ******************************************************************************************************************/

    @Override
    public IAsyncOperationResult invoke() throws AsyncOperationException
    {
        try
        {
            LinkedHashMap<String, String> titlesResponse = videoHandler.downloadTitles(filter);

            if (filter != null && filter.getParent() != null)
            {
                // If the response is NULL, then try to get details for the last title ID.
                if (titlesResponse == null)
                {
                    VideoDetails detailsResponse = videoHandler.downloadDetails(filter.getParent());
                    return new VideoDetailsResult(detailsResponse);
                }
                // If there is only one title in the response, then get the details for it immediately.
                else if (titlesResponse.size() == 1)
                {
                    String id = null;
                    for (Map.Entry pair : titlesResponse.entrySet())
                        id = pair.getKey().toString();

                    if (id != null)
                    {
                        VideoDetails detailsResponse = videoHandler.downloadDetails(id);

                        // Maybe we should not get the details yet. Maybe this is just a long unknown title hierarchy
                        // representing a folder structure with only a single directory in some places: return the original
                        // result in this case.
                        if (detailsResponse != null
                            && detailsResponse.getFiles() != null
                            && detailsResponse.getFiles().length > 0)
                            return new VideoDetailsResult(detailsResponse);
                    }
                }
            }

            // If a title list is available for the given ID, then create the result object from it.
            return new SimpleListResult(Results.VIDEO_TITLES, titlesResponse);
        }
        catch (Exception ex)
        {
            throw new AsyncOperationException("Downloading titles failed.", ex);
        }
    }
}
