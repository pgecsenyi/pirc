package com.pie.pirc.async.framework;

/**
 * Interface that represents the result of an asynchronous operation.
 *
 * Created by pgecsenyi on 2015.04.30..
 */
public interface IAsyncOperationResult
{
    Object getContent();

    AsyncOperationException getException();

    void setException(AsyncOperationException exception);

    int getId();
}
