package com.pie.pirc.gui.interfaces;

import com.pie.pirc.async.framework.IAsyncOperation;

/**
 * Created by pgecsenyi on 2016.11.03..
 */
public interface IAsyncOperationManager
{
    void executeAsyncOperation(IAsyncOperation asyncOperation);
}
