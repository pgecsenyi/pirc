package com.pie.pirc.gui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.pie.pirc.R;
import com.pie.pirc.Settings;
import com.pie.pirc.async.framework.AsyncOperationController;
import com.pie.pirc.async.framework.AsyncOperationException;
import com.pie.pirc.async.framework.IAsyncOperation;
import com.pie.pirc.async.framework.IAsyncOperationExecutor;
import com.pie.pirc.async.framework.IAsyncOperationResult;
import com.pie.pirc.async.operations.ControlPlayerAsyncOp;
import com.pie.pirc.async.operations.GetAudioAlbumsAsyncOp;
import com.pie.pirc.async.operations.GetAudioArtistsAsyncOp;
import com.pie.pirc.async.operations.GetVideoFilterDataAsyncOp;
import com.pie.pirc.async.operations.GetVideoLanguagesAsyncOp;
import com.pie.pirc.async.operations.GetVideoQualitiesAsyncOp;
import com.pie.pirc.async.operations.GetVideoTitlesAsyncOp;
import com.pie.pirc.async.results.Results;
import com.pie.pirc.communication.data.VideoDetails;
import com.pie.pirc.communication.data.VideoFilterData;
import com.pie.pirc.communication.data.VideoTitleFilter;
import com.pie.pirc.communication.interfaces.IPlayerHandler;
import com.pie.pirc.gui.Communicator;
import com.pie.pirc.gui.Pages;
import com.pie.pirc.gui.display_objects.VideoDetailsConverter;
import com.pie.pirc.gui.display_objects.VideoFilterDataDo;
import com.pie.pirc.gui.fragments.MaintenanceFragment;
import com.pie.pirc.gui.fragments.PlayerFragment;
import com.pie.pirc.gui.fragments.VideoDetailsFragment;
import com.pie.pirc.gui.fragments.VideoFilterFragment;
import com.pie.pirc.gui.fragments.navigation_drawer_fragment.NavigationDrawerFragment;
import com.pie.pirc.gui.fragments.simple_list_fragment.SimpleListContent;
import com.pie.pirc.gui.fragments.simple_list_fragment.SimpleListFragment;
import com.pie.pirc.gui.fragments.simple_list_fragment.SimpleListItem;
import com.pie.pirc.gui.history.FilterState;
import com.pie.pirc.gui.interfaces.IAsyncOperationManager;
import com.pie.pirc.gui.interfaces.ICommunicatorProvider;
import com.pie.pirc.gui.interfaces.IFilterManager;
import com.pie.pirc.gui.interfaces.IFragmentManager;
import com.pie.pirc.gui.interfaces.ISettingsManager;

/**
 * Main GUI module.
 *
 * Created by pie.
 */
public class MainActivity
    extends ActionBarActivity
    implements IAsyncOperationExecutor,
               IAsyncOperationManager,
               ICommunicatorProvider,
               IFilterManager,
               IFragmentManager,
               ISettingsManager,
               NavigationDrawerFragment.NavigationDrawerCallbacks,
               SharedPreferences.OnSharedPreferenceChangeListener
{
    /***************************************************************************************************************//**
     * Private constants.
     ******************************************************************************************************************/

    private static final String ARG_STATE = "state";

    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private Communicator communicator;

    /**
     * Do not handle events from outside until the constructor has run.
     */
    private boolean isLoaded;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private ProgressDialog progressDialog;

    private FilterState state = new FilterState();

    /***************************************************************************************************************//**
     * Overridden ActionBarActivity methods.
     ******************************************************************************************************************/

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        state.stepBack();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null)
            state = savedInstanceState.getParcelable(ARG_STATE);

        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager()
            .findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
            R.id.navigation_drawer,
            (DrawerLayout)findViewById(R.id.drawer_layout));

        // Initialize progress dialog.
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setCancelable(false);

        // Listen to changes in configuration.
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        // Instantiate new video handler and start downloading video information.
        initializeServiceHandlers(sharedPreferences);
        startContentDownload();
        isLoaded = true;
    }

    /**
     * Initialize the contents of the Activity's standard options menu. Custom menu items should be placed in to menu
     * here. This is only called once, the first time the options menu is displayed. To update the menu every time it is
     * displayed, see {@link #onPrepareOptionsMenu}. You can safely hold a reference on menu (and any items created from
     * it), making modifications to it as desired, until the next time this method is called.
     *
     * @param menu The options menu in which custom items can be placed.
     * @return Returns true if the menu should be displayed; returns false when drawer is visible (therefore the
     *     menu is not).
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        if (!mNavigationDrawerFragment.isDrawerOpen())
        {
            // Only show items in the action bar relevant to this screen if the drawer is not showing. Otherwise, let
            // the drawer decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so
     * long as you specify a parent activity in AndroidManifest.xml.
     *
     * @param item The menu item that was selected.
     * @return Return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        IPlayerHandler playerHandler = getPlayerHandler();

        if (id == R.id.action_maintenance)
        {
            replaceFragment(MaintenanceFragment.newInstance(), Pages.MAINTENANCE);
            return true;
        }
        else if (id == R.id.action_pause)
        {
            executeAsyncOperation(new ControlPlayerAsyncOp(playerHandler, ControlPlayerAsyncOp.PAUSE));
            return true;
        }
        else if (id == R.id.action_stop)
        {
            executeAsyncOperation(new ControlPlayerAsyncOp(playerHandler, ControlPlayerAsyncOp.STOP));
            return true;
        }
        else if (id == R.id.action_player)
        {
            if (state.isHistoryEmpty() || state.getCurrentPage() != Pages.PLAYER)
                replaceFragment(PlayerFragment.newInstance(), Pages.PLAYER);
            return true;
        }
        else if (id == R.id.action_settings)
        {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putParcelable(ARG_STATE, state);
    }

    /***************************************************************************************************************//**
    * IAsyncOperationExecutor implementation.
     ******************************************************************************************************************/

    @Override
    public void operationStarting()
    {
        progressDialog.show();
    }

    @Override
    public void operationEnding(IAsyncOperationResult result)
    {
        processAsyncOperationResult(result);

        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    /***************************************************************************************************************//**
     * IAsyncOperationManager implementation.
     ******************************************************************************************************************/

    @Override
    public void executeAsyncOperation(IAsyncOperation asyncOperation)
    {
        if (asyncOperation == null)
            return;

        AsyncOperationController asyncOperationController = new AsyncOperationController(asyncOperation, this);
        asyncOperationController.execute();
    }

    /***************************************************************************************************************//**
     * ICommunicatorProvider implementation.
     ******************************************************************************************************************/

    @Override
    public Communicator getCommunicator()
    {
        return communicator;
    }

    /***************************************************************************************************************//**
     * IFilterManager implementation.
     ******************************************************************************************************************/

    @Override
    public VideoTitleFilter buildVideoTitleFilter()
    {
        VideoTitleFilter filter = new VideoTitleFilter();
        filter.setLanguage(state.getFilter("videoLanguage"));
        filter.setQuality(state.getFilter("videoQuality"));
        filter.setSubtitleLanguage(state.getFilter("videoSubtitleLanguage"));
        filter.setText(state.getFilter("videoText"));

        return filter;
    }

    @Override
    public int getCurrentPage()
    {
        int page = Pages.VIDEO_TITLES;
        if (!state.isHistoryEmpty())
            page = state.getCurrentPage();

        return page;
    }

    @Override
    public void resetFilter()
    {
        state.resetFilter();
    }

    @Override
    public void setFilter(String key, String value)
    {
        state.setFilter(key, value);
    }

    /***************************************************************************************************************//**
     * IFragmentManager implementation.
     ******************************************************************************************************************/

    @Override
    public void replaceFragment(Fragment fragment, int page)
    {
        // Update the main content by replacing fragments.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.container, fragment);

        // If this isn't the first page to load, add the transaction to the back stack.
        // Do not insert the first transition (from the placeholder Fragment to the first useful Fragment) to the back
        // stack, so there will not be an empty screen before exit.
        if (!state.isHistoryEmpty())
            transaction.addToBackStack(fragment.toString());

        // Commit and set the isInitialized flag to true.
        transaction.commit();
        state.stepForward(page);
    }

    /***************************************************************************************************************//**
     * ISettingsManager implementation.
     ******************************************************************************************************************/

    @Override
    public String getSetting(String id)
    {
        if (id == Settings.AUDIO_PREFERRED_AUDIO_OUTPUT)
        {
            return PreferenceManager.getDefaultSharedPreferences(this).getString(
                id,
                Settings.AUDIO_PREFERRED_AUDIO_OUTPUT_DEFAULT);
        }

        return "";
    }

    /***************************************************************************************************************//**
     * NavigationDrawerFragment.NavigationDrawerCallbacks implementation.
     ******************************************************************************************************************/

    @Override
    public void onNavigationDrawerItemSelected(long id)
    {
        if (!isLoaded)
            return;

        state.resetFilter();

        if (id == 1)
            executeAsyncOperation(new GetAudioArtistsAsyncOp(communicator.getAudioHandler()));
        else if (id == 2)
            executeAsyncOperation(new GetAudioAlbumsAsyncOp(communicator.getAudioHandler()));
        else if (id == 3)
            executeAsyncOperation(new GetVideoFilterDataAsyncOp(communicator.getVideoHandler()));
        else if (id == 4)
            executeAsyncOperation(new GetVideoTitlesAsyncOp(communicator.getVideoHandler()));
        else if (id == 5)
            executeAsyncOperation(new GetVideoLanguagesAsyncOp(communicator.getVideoHandler()));
        else if (id == 6)
            executeAsyncOperation(new GetVideoQualitiesAsyncOp(communicator.getVideoHandler()));
        else
            replaceFragment(PlaceholderFragment.newInstance(id + 1), Pages.EMPTY);
    }

    /***************************************************************************************************************//**
     * SharedPreferences.OnSharedPreferenceChangeListener implementation.
     ******************************************************************************************************************/

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        if (key.equals(Settings.CONNECTION_ADDRESS) || key.equals(Settings.CONNECTION_PROTOCOL))
            initializeServiceHandlers(sharedPreferences);
    }

    /***************************************************************************************************************//**
     * Private methods.
     ******************************************************************************************************************/

    private boolean checkIfResultIsEmpty(IAsyncOperationResult result)
    {
        // No data retrieved.
        if (result == null)
        {
            displayErrorMessage(R.string.no_results);
            return true;
        }

        // An error occurred.
        AsyncOperationException exception = result.getException();
        if (exception != null)
        {
            String message = exception.getMessage();
            if (exception.getInnerException() != null)
                message = exception.getInnerException().getMessage();
            displayErrorMessage(getResources().getString(R.string.failed_to_get_data) + " " + message);
            return true;
        }

        // Result supposed to empty anyway.
        if (result.getId() == Results.EMPTY)
            return true;

        // Something strange is going on, no data retrieved.
        if (result.getContent() == null)
        {
            displayErrorMessage(R.string.no_results);
            return true;
        }

        return false;
    }

    private void displayErrorMessage(int resId)
    {
        Toast
            .makeText(getApplicationContext(), resId, Toast.LENGTH_SHORT)
            .show();
    }

    private void displayErrorMessage(String text)
    {
        Toast
            .makeText(getApplicationContext(), text, Toast.LENGTH_SHORT)
            .show();
    }

    private IPlayerHandler getPlayerHandler()
    {
        if (state.getFilter("currentCategory") == "audio")
            return communicator.getAudioHandler().getPlayer();

        return communicator.getVideoHandler().getPlayer();
    }

    private void initializeServiceHandlers(SharedPreferences sharedPreferences)
    {
        // Get server address and protocol information from settings.
        String connectionAddress = sharedPreferences.getString(
            Settings.CONNECTION_ADDRESS,
            Settings.CONNECTION_ADDRESS_DEFAULT);
        // String connectionProtocol = sharedPreferences.getString(
            // Settings.CONNECTION_PROTOCOL,
            // Settings.CONNECTION_PROTOCOL_DEFAULT);

        // Initialize protocol handlers.
        String baseAddress = "http://" + connectionAddress;
        communicator = new Communicator(baseAddress);
    }

    private void onSectionAttached(int number)
    {
        switch (number)
        {
            case 1:
                mTitle = getString(R.string.title_section_by_titles);
                break;
            case 2:
                mTitle = getString(R.string.title_section_by_languages);
                break;
            case 3:
                mTitle = getString(R.string.title_section_by_qualities);
                break;
        }
    }

    private void processAsyncOperationResult(IAsyncOperationResult result)
    {
        if (checkIfResultIsEmpty(result))
            return;

        switch (result.getId())
        {
            case Results.AUDIO_ALBUMS:
                processResultSimpleListAndSwitchPage(result, Pages.AUDIO_ALBUMS);
                break;
            case Results.AUDIO_ARTISTS:
                processResultSimpleListAndSwitchPage(result, Pages.AUDIO_ARTISTS);
                break;
            case Results.AUDIO_TRACKS:
                processResultSimpleListAndSwitchPage(result, Pages.AUDIO_TRACKS);
                break;
            case Results.VIDEO_DETAILS:
                replaceFragment(
                    VideoDetailsFragment.newInstance(
                        VideoDetailsConverter.Convert((VideoDetails)(result.getContent()))),
                    Pages.VIDEO_DETAILS);
                break;
            case Results.VIDEO_FILTER_DATA:
                VideoFilterData data = (VideoFilterData)(result.getContent());
                replaceFragment(
                    VideoFilterFragment.newInstance(new VideoFilterDataDo(data.getLanguages(), data.getQualities())),
                    Pages.VIDEO_FILTER);
                break;
            case Results.VIDEO_LANGUAGES:
                processResultSimpleListAndSwitchPage(result, Pages.VIDEO_LANGUAGES);
                break;
            case Results.VIDEO_QUALITIES:
                processResultSimpleListAndSwitchPage(result, Pages.VIDEO_QUALITIES);
                break;
            case Results.VIDEO_TITLES:
                processResultSimpleListAndSwitchPage(result, Pages.VIDEO_TITLES);
                break;
        }
    }

    private boolean processResultSimpleList(LinkedHashMap<String, String> result)
    {
        SimpleListContent.clear(state.getCurrentListId());

        if (result.size() <= 0)
            return false;

        ArrayList<SimpleListItem> items = new ArrayList<>();
        for (Map.Entry pair : result.entrySet())
            items.add(new SimpleListItem(pair.getKey().toString(), pair.getValue().toString()));

        SimpleListContent.addItems(state.getCurrentListId(), items);

        return true;
    }

    private void processResultSimpleListAndSwitchPage(IAsyncOperationResult result, int page)
    {
        if (processResultSimpleList((LinkedHashMap<String, String>)result.getContent()))
            replaceFragment(SimpleListFragment.newInstance(state.getCurrentListId()), page);
    }

    private void restoreActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    private void startContentDownload()
    {
        if (!state.isHistoryEmpty())
            return;

        GetVideoTitlesAsyncOp getVideoTitlesAsyncOp = new GetVideoTitlesAsyncOp(communicator.getVideoHandler());
        executeAsyncOperation(getVideoTitlesAsyncOp);
    }

    /***************************************************************************************************************//**
     * Nested types.
     ******************************************************************************************************************/

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment
    {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(long sectionNumber)
        {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putLong(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);

            return fragment;
        }

        public PlaceholderFragment()
        {
        }

        @Override
        public void onAttach(Activity activity)
        {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            return inflater.inflate(R.layout.fragment_main, container, false);
        }
    }
}
