package com.pie.pirc.async.framework;

/**
 * Interface that represents an asynchronous operation.
 *
 * Created by pgecsenyi on 2015.04.29..
 */
public interface IAsyncOperation
{
    IAsyncOperationResult invoke() throws AsyncOperationException;
}
