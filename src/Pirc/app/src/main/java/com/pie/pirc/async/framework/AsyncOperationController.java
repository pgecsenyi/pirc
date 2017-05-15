package com.pie.pirc.async.framework;

import android.os.AsyncTask;

/**
 * Executes the specified asynchronous task and notifies the given entity through the {@link IAsyncOperationExecutor}
 * interface before the operation starts and after it finishes.
 *
 * Created by pgecsenyi on 2015.04.29..
 */
public class AsyncOperationController extends AsyncTask<Void, Void, Void>
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private IAsyncOperation asyncOperation = null;

    private IAsyncOperationResult asyncOperationResult = null;

    private IAsyncOperationExecutor executor = null;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public AsyncOperationController(IAsyncOperation asyncOperation, IAsyncOperationExecutor executor)
    {
        this.asyncOperation = asyncOperation;
        this.executor = executor;
    }

    /***************************************************************************************************************//**
     * Overridden AsyncTask methods.
     ******************************************************************************************************************/

    @Override
    protected Void doInBackground(Void... arg0)
    {
        try
        {
            if (asyncOperation != null)
                asyncOperationResult = asyncOperation.invoke();
        }
        catch (AsyncOperationException ex)
        {
            asyncOperationResult = new AsyncOperationResultAdapter();
            asyncOperationResult.setException(ex);
        }

        return null;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();

        if (executor != null)
            executor.operationStarting();
    }

    @Override
    protected void onPostExecute(Void result)
    {
        super.onPostExecute(result);

        if (executor != null)
            executor.operationEnding(asyncOperationResult);
    }
}
