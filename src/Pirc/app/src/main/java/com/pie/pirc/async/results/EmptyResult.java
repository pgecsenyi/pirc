package com.pie.pirc.async.results;

import com.pie.pirc.async.framework.AsyncOperationResultAdapter;

/**
 * To be used by asynchronous operation that does not have a result.
 *
 * Created by pgecsenyi on 2015.06.21..
 */
public class EmptyResult extends AsyncOperationResultAdapter
{
    /***************************************************************************************************************//**
     * Overridden AsyncOperationResultAdapter methods.
     ******************************************************************************************************************/

    @Override
    public int getId()
    {
        return Results.EMPTY;
    }
}
