package com.pie.pirc.async.operations;

import com.pie.pirc.async.framework.AsyncOperationException;
import com.pie.pirc.async.framework.IAsyncOperation;
import com.pie.pirc.async.framework.IAsyncOperationResult;
import com.pie.pirc.async.results.EmptyResult;
import com.pie.pirc.communication.interfaces.IMaintenanceHandler;

/**
 * Asynchronous operation that initiates a database synchronization.
 *
 * Created by pgecsenyi on 2015.11.22..
 */
public class SynchronizeDatabaseAsyncOp implements IAsyncOperation
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private IMaintenanceHandler maintenanceHandler;

    private boolean isCompleteRebuildRequested;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public SynchronizeDatabaseAsyncOp(IMaintenanceHandler maintenanceHandler, boolean isCompleteRebuildRequested)
    {
        this.maintenanceHandler = maintenanceHandler;
        this.isCompleteRebuildRequested = isCompleteRebuildRequested;
    }

    /***************************************************************************************************************//**
     * IAsyncOperation implementation.
     ******************************************************************************************************************/

    @Override
    public IAsyncOperationResult invoke() throws AsyncOperationException
    {
        try
        {
            if (isCompleteRebuildRequested)
                maintenanceHandler.rebuildDatabase();
            else
                maintenanceHandler.synchronizeDatabase();

            return new EmptyResult();
        }
        catch (Exception ex)
        {
            throw new AsyncOperationException("Synchronizing database failed.", ex);
        }
    }
}
