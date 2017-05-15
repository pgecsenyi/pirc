package com.pie.pirc.gui.interaction;

import com.pie.pirc.async.operations.SynchronizeDatabaseAsyncOp;
import com.pie.pirc.gui.fragments.MaintenanceFragment;
import com.pie.pirc.gui.interfaces.IAsyncOperationManager;
import com.pie.pirc.gui.interfaces.ICommunicatorProvider;
import com.pie.pirc.gui.interfaces.IFilterManager;
import com.pie.pirc.gui.interfaces.IFragmentManager;
import com.pie.pirc.gui.interfaces.ISettingsManager;

/**
 * Created by pgecsenyi on 2016. 11. 03..
 */

public class MaintenanceFragmentInteractionListener
    extends InteractionBase
    implements MaintenanceFragment.MaintenanceFragmentInteractionListener
{
    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public MaintenanceFragmentInteractionListener(
        ICommunicatorProvider communicatorProvider,
        IAsyncOperationManager asyncOperationManager,
        IFilterManager filterManager,
        IFragmentManager fragmentManager,
        ISettingsManager settingsManager)
    {
        super(communicatorProvider, asyncOperationManager, filterManager, fragmentManager, settingsManager);
    }

    /***************************************************************************************************************//**
     * MaintenanceFragment.MaintenanceFragmentInteractionListener implementation.
     ******************************************************************************************************************/

    @Override
    public void onSynchronizeDatabase(boolean isCompleteRebuildRequested)
    {
        asyncOperationManager.executeAsyncOperation(new SynchronizeDatabaseAsyncOp(
            communicatorProvider.getCommunicator().getMaintenanceHandler(),
            isCompleteRebuildRequested));
    }
}
