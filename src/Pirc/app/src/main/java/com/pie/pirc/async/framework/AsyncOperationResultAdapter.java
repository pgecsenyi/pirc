package com.pie.pirc.async.framework;

/**
 * Implements {@link IAsyncOperationResult} interface, handles exception related stuff.
 *
 * Created by pgecsenyi on 2015.11.14..
 */
public class AsyncOperationResultAdapter implements IAsyncOperationResult
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private AsyncOperationException exception;

    /***************************************************************************************************************//**
     * IAsyncOperationResult implementation.
     ******************************************************************************************************************/

    @Override
    public Object getContent()
    {
        return null;
    }

    @Override
    public AsyncOperationException getException()
    {
        return exception;
    }

    @Override
    public void setException(AsyncOperationException exception)
    {
        this.exception = exception;
    }

    @Override
    public int getId()
    {
        return -1;
    }
}
