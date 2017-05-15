package com.pie.pirc.async.framework;

import com.pie.pirc.WrapperException;

/**
 * Represents exceptions produced by asynchronous operations towards higher level layers.
 *
 * Created by pgecsenyi on 2015.11.14..
 */
public class AsyncOperationException extends WrapperException
{
    /***************************************************************************************************************//**
     * Constructors.
     ******************************************************************************************************************/

    public AsyncOperationException()
    {
    }

    public AsyncOperationException(String message)
    {
        super(message);
    }

    public AsyncOperationException(String message, Exception innerException)
    {
        super(message, innerException);
    }
}
