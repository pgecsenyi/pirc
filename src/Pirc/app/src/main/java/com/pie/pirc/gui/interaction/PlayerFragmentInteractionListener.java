package com.pie.pirc.gui.interaction;

import com.pie.pirc.async.framework.IAsyncOperation;
import com.pie.pirc.async.operations.ControlPlayerAsyncOp;
import com.pie.pirc.gui.Communicator;
import com.pie.pirc.gui.fragments.PlayerFragment;
import com.pie.pirc.gui.interfaces.IAsyncOperationManager;
import com.pie.pirc.gui.interfaces.ICommunicatorProvider;
import com.pie.pirc.gui.interfaces.IFilterManager;
import com.pie.pirc.gui.interfaces.IFragmentManager;
import com.pie.pirc.gui.interfaces.ISettingsManager;

/**
 * Created by pgecsenyi on 2016.11.03..
 */

public class PlayerFragmentInteractionListener
    extends InteractionBase
    implements PlayerFragment.PlayerFragmentInteractionListener
{
    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public PlayerFragmentInteractionListener(
        ICommunicatorProvider communicatorProvider,
        IAsyncOperationManager asyncOperationManager,
        IFilterManager filterManager,
        IFragmentManager fragmentManager,
        ISettingsManager settingsManager)
    {
        super(communicatorProvider, asyncOperationManager, filterManager, fragmentManager, settingsManager);
    }

    /***************************************************************************************************************//**
     * PlayerFragment.PlayerFragmentInteractionListener implementation.
     ******************************************************************************************************************/

    @Override
    public void onPlayerFragmentInteraction(int player, int action)
    {
        Communicator communicator = communicatorProvider.getCommunicator();
        IAsyncOperation operation = null;

        if (player == PlayerFragment.Players.AUDIO)
            operation = new ControlPlayerAsyncOp(communicator.getAudioHandler().getPlayer(), action);
        else if (player == PlayerFragment.Players.VIDEO)
            operation = new ControlPlayerAsyncOp(communicator.getVideoHandler().getPlayer(), action);
        else
            operation = new ControlPlayerAsyncOp(communicator.getPlayerHandler(), action);

        asyncOperationManager.executeAsyncOperation(operation);
    }
}
