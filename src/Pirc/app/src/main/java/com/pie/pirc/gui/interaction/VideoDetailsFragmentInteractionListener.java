package com.pie.pirc.gui.interaction;

import com.pie.pirc.async.framework.IAsyncOperation;
import com.pie.pirc.async.operations.AddToPlaylistAsyncOp;
import com.pie.pirc.async.operations.ControlVideoPlayerPlayAsyncOp;
import com.pie.pirc.communication.data.VideoPlaylistItem;
import com.pie.pirc.gui.Communicator;
import com.pie.pirc.gui.fragments.VideoDetailsFragment;
import com.pie.pirc.gui.interfaces.IAsyncOperationManager;
import com.pie.pirc.gui.interfaces.ICommunicatorProvider;
import com.pie.pirc.gui.interfaces.IFilterManager;
import com.pie.pirc.gui.interfaces.IFragmentManager;
import com.pie.pirc.gui.interfaces.ISettingsManager;

/**
 * Created by pgecsenyi on 2016.11.03..
 */
public class VideoDetailsFragmentInteractionListener
    extends InteractionBase
    implements VideoDetailsFragment.VideoDetailsFragmentInteractionListener
{
    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public VideoDetailsFragmentInteractionListener(
        ICommunicatorProvider communicatorProvider,
        IAsyncOperationManager asyncOperationManager,
        IFilterManager filterManager,
        IFragmentManager fragmentManager,
        ISettingsManager settingsManager)
    {
        super(communicatorProvider, asyncOperationManager, filterManager, fragmentManager, settingsManager);
    }

    /***************************************************************************************************************//**
     * VideoDetailsFragment.VideoDetailsFragmentInteractionListener implementation.
     ******************************************************************************************************************/

    @Override
    public void onVideoDetailsFragmentInteraction(String audioOutput, int idVideo, int idSubtitle, boolean addToPlaylist)
    {
        if (idVideo == 0)
            return;

        filterManager.setFilter("currentCategory", "video");

        Communicator communicator = communicatorProvider.getCommunicator();

        // Get the subtitle data.
        String idSubtitleString = null;
        if (idSubtitle == -1)
            idSubtitleString = String.valueOf(idSubtitle);

        // Create asynchronous operation.
        IAsyncOperation operation;

        if (addToPlaylist)
        {
            VideoPlaylistItem playlistItem = new VideoPlaylistItem(
                audioOutput,
                "video",
                String.valueOf(idVideo),
                idSubtitleString);
            operation = new AddToPlaylistAsyncOp(communicator.getPlaylistHandler(), playlistItem);
        }
        else
        {
            operation = new ControlVideoPlayerPlayAsyncOp(
                communicator.getVideoHandler().getPlayer(),
                audioOutput,
                String.valueOf(idVideo),
                idSubtitleString);
        }

        // Execute operation.
        asyncOperationManager.executeAsyncOperation(operation);
    }
}
