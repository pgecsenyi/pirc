package com.pie.pirc.gui.interaction;

import com.pie.pirc.Settings;
import com.pie.pirc.async.operations.ControlPlayerPlayAsyncOp;
import com.pie.pirc.async.operations.GetAudioAlbumsAsyncOp;
import com.pie.pirc.async.operations.GetAudioTracksAsyncOp;
import com.pie.pirc.async.operations.GetVideoTitlesAsyncOp;
import com.pie.pirc.communication.data.VideoTitleFilter;
import com.pie.pirc.gui.Pages;
import com.pie.pirc.gui.fragments.simple_list_fragment.SimpleListFragment;
import com.pie.pirc.gui.interfaces.IAsyncOperationManager;
import com.pie.pirc.gui.interfaces.ICommunicatorProvider;
import com.pie.pirc.gui.interfaces.IFilterManager;
import com.pie.pirc.gui.interfaces.IFragmentManager;
import com.pie.pirc.gui.interfaces.ISettingsManager;

/**
 * Created by pgecsenyi on 2016.11.04..
 */
public class SimpleListFragmentInteractionListener
    extends InteractionBase
    implements SimpleListFragment.SimpleListFragmentInteractionListener
{
    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public SimpleListFragmentInteractionListener(
            ICommunicatorProvider communicatorProvider,
            IAsyncOperationManager asyncOperationManager,
            IFilterManager filterManager,
            IFragmentManager fragmentManager,
            ISettingsManager settingsManager)
    {
        super(communicatorProvider, asyncOperationManager, filterManager, fragmentManager, settingsManager);
    }

    /***************************************************************************************************************//**
     * SimpleListFragment.SimpleListFragmentInteractionListener implementation.
     ******************************************************************************************************************/

    @Override
    public void onSimpleListFragmentInteraction(String id)
    {
        int page = filterManager.getCurrentPage();

        VideoTitleFilter filter = filterManager.buildVideoTitleFilter();

        switch (page)
        {
            case Pages.AUDIO_ALBUMS:
                asyncOperationManager.executeAsyncOperation(new GetAudioTracksAsyncOp(
                    communicatorProvider.getCommunicator().getAudioHandler(),
                    id));
                break;
            case Pages.AUDIO_ARTISTS:
                asyncOperationManager.executeAsyncOperation(new GetAudioAlbumsAsyncOp(
                    communicatorProvider.getCommunicator().getAudioHandler(),
                    id));
                break;
            case Pages.AUDIO_TRACKS:
                filterManager.setFilter("currentCategory", "audio");
                asyncOperationManager.executeAsyncOperation(new ControlPlayerPlayAsyncOp(
                    communicatorProvider.getCommunicator().getAudioHandler().getPlayer(),
                    settingsManager.getSetting(Settings.AUDIO_PREFERRED_AUDIO_OUTPUT),
                    id));
                break;
            case Pages.VIDEO_LANGUAGES:
                filterManager.setFilter("videoLanguage", id);
                filter.setLanguage(id);
                asyncOperationManager.executeAsyncOperation(new GetVideoTitlesAsyncOp(
                    communicatorProvider.getCommunicator().getVideoHandler(),
                    filter));
                break;
            case Pages.VIDEO_QUALITIES:
                filterManager.setFilter("videoQuality", id);
                filter.setQuality(id);
                asyncOperationManager.executeAsyncOperation(new GetVideoTitlesAsyncOp(
                    communicatorProvider.getCommunicator().getVideoHandler(),
                    filter));
                break;
            case Pages.VIDEO_TITLES:
                filter.setParent(id);
                asyncOperationManager.executeAsyncOperation(new GetVideoTitlesAsyncOp(
                    communicatorProvider.getCommunicator().getVideoHandler(),
                    filter));
                break;
        }
    }
}
