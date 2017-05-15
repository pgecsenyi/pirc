package com.pie.pirc.async.results;

import com.pie.pirc.async.framework.AsyncOperationResultAdapter;
import com.pie.pirc.communication.data.VideoFilterData;

/**
 * Stores video filter options.
 *
 * Created by pgecsenyi on 2015.05.23..
 */
public class VideoFilterDataResult extends AsyncOperationResultAdapter
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private VideoFilterData content;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public VideoFilterDataResult(VideoFilterData content)
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
        return Results.VIDEO_FILTER_DATA;
    }
}
