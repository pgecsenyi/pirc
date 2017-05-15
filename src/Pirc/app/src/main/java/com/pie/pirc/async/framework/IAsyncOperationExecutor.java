package com.pie.pirc.async.framework;

/**
 * Interface that represents an entity which can execute asynchronous operations.
 *
 * Created by pgecsenyi on 2015.04.30..
 */
public interface IAsyncOperationExecutor
{
    void operationStarting();

    void operationEnding(IAsyncOperationResult result);
}
