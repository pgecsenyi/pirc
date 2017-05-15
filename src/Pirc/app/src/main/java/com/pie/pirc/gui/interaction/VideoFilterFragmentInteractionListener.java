package com.pie.pirc.gui.interaction;

import com.pie.pirc.async.operations.GetVideoTitlesAsyncOp;
import com.pie.pirc.communication.data.VideoTitleFilter;
import com.pie.pirc.gui.fragments.VideoFilterFragment;
import com.pie.pirc.gui.interfaces.IAsyncOperationManager;
import com.pie.pirc.gui.interfaces.ICommunicatorProvider;
import com.pie.pirc.gui.interfaces.IFilterManager;
import com.pie.pirc.gui.interfaces.IFragmentManager;
import com.pie.pirc.gui.interfaces.ISettingsManager;

/**
 * Created by pgecsenyi on 2016.11.04..
 */
public class VideoFilterFragmentInteractionListener
    extends InteractionBase
    implements VideoFilterFragment.VideoFilterFragmentInteractionListener
{
    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public VideoFilterFragmentInteractionListener(
        ICommunicatorProvider communicatorProvider,
        IAsyncOperationManager asyncOperationManager,
        IFilterManager filterManager,
        IFragmentManager fragmentManager,
        ISettingsManager settingsManager)
    {
        super(communicatorProvider, asyncOperationManager, filterManager, fragmentManager, settingsManager);
    }

    /***************************************************************************************************************//**
     * VideoFilterFragment.VideoFilterFragmentInteractionListener implementation.
     ******************************************************************************************************************/

    @Override
    public void onVideoFilterFragmentInteraction(
        String text,
        String idLanguage,
        String idQuality,
        String idSubtitleLanguage)
    {
        filterManager.setFilter("videoLanguage", idLanguage);
        filterManager.setFilter("videoQuality", idQuality);
        filterManager.setFilter("videoSubtitleLanguage", idSubtitleLanguage);
        filterManager.setFilter("videoText", text);

        VideoTitleFilter filter = filterManager.buildVideoTitleFilter();
        asyncOperationManager.executeAsyncOperation(new GetVideoTitlesAsyncOp(
            communicatorProvider.getCommunicator().getVideoHandler(),
            filter));
    }
}
