package com.pie.pirc.async.results;

import com.pie.pirc.async.framework.AsyncOperationResultAdapter;
import com.pie.pirc.communication.data.VideoDetails;

/**
 * Stores details for a video title.
 *
 * Created by pgecsenyi on 2015.05.23..
 */
public class VideoDetailsResult extends AsyncOperationResultAdapter
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private VideoDetails content;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public VideoDetailsResult(VideoDetails content)
    {
        this.content = content;
    }

    /***************************************************************************************************************//**
     * Overridden AsyncOperationResultAdapter methods.
     ******************************************************************************************************************/

    @Override
    public Object getContent()
{
    return content;
}

    @Override
    public int getId()
    {
        return Results.VIDEO_DETAILS;
    }
}
