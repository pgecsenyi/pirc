package com.pie.pirc.gui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.pie.pirc.R;
import com.pie.pirc.Settings;
import com.pie.pirc.communication.interfaces.AudioOutputs;
import com.pie.pirc.gui.display_objects.VideoDetailsDo;
import com.pie.pirc.gui.display_objects.VideoFileDetailsDo;
import com.pie.pirc.gui.display_objects.VideoSubtitleDetailsDo;
import com.pie.pirc.gui.interfaces.IAsyncOperationManager;
import com.pie.pirc.gui.interfaces.ICommunicatorProvider;
import com.pie.pirc.gui.interfaces.IFilterManager;
import com.pie.pirc.gui.interfaces.IFragmentManager;
import com.pie.pirc.gui.interfaces.ISettingsManager;

/**
 * Fragment that displays the details for a video file and allows the user to initiate video playing and select
 * corresponding parameters such as language, quality and subtitle.
 *
 * Activities that contain this fragment must implement the {@link VideoDetailsFragmentInteractionListener} interface to
 * handle interaction events. Use the {@link VideoDetailsFragment#newInstance} factory method to create an instance of
 * this fragment.
 */
public class VideoDetailsFragment extends Fragment
{
    /***************************************************************************************************************//**
     * Private constants.
     ******************************************************************************************************************/

    private static final String ARG_VIDEO_DETAILS = "paramVideoDetails";

    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private VideoDetailsFragmentInteractionListener mListener;

    private VideoDetailsDo mVideoDetails;

    private String selectedAudioOutput;

    private VideoFileDetailsDo selectedVideoFileDetails;

    private VideoSubtitleDetailsDo selectedVideoSubtitleDetails;

    private Spinner spSubtitles;

    private TextView tvSubtitle;

    /***************************************************************************************************************//**
     * Constructors.
     ******************************************************************************************************************/

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param paramVideoDetails Parameter for VideoDetails.
     * @return A new instance of fragment VideoDetailsFragment.
     */
    public static VideoDetailsFragment newInstance(VideoDetailsDo paramVideoDetails)
    {
        VideoDetailsFragment fragment = new VideoDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_VIDEO_DETAILS, paramVideoDetails);
        fragment.setArguments(args);
        return fragment;
    }

    public VideoDetailsFragment()
    {
        // Required empty public constructor.
    }

    /***************************************************************************************************************//**
     * Overridden Fragment methods.
     ******************************************************************************************************************/

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        try
        {
            IAsyncOperationManager asyncOperationManager = (IAsyncOperationManager)activity;
            ICommunicatorProvider communicatorProvider = (ICommunicatorProvider)activity;
            IFilterManager filterManager = (IFilterManager)activity;
            IFragmentManager fragmentManager = (IFragmentManager)activity;
            ISettingsManager settingsManager = (ISettingsManager)activity;
            mListener = new com.pie.pirc.gui.interaction.VideoDetailsFragmentInteractionListener(
                communicatorProvider,
                asyncOperationManager,
                filterManager,
                fragmentManager,
                settingsManager);
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement VideoDetailsFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
            mVideoDetails = getArguments().getParcelable(ARG_VIDEO_DETAILS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment.
        final View v = inflater.inflate(R.layout.fragment_video_details, container, false);

        initializeVideoSelector(v);
        initializeSubtitleSelector(v);
        initializeAudioOutputSelector(v);
        initializeTitle(v);

        Button btnPlay = (Button)v.findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new BtnPlayClickListener());
        Button btnAddToPlaylist = (Button)v.findViewById(R.id.btnAddToPlaylist);
        btnAddToPlaylist.setOnClickListener(new BtnAddToPlaylistClickListener());

        return v;
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    /***************************************************************************************************************//**
     * Private methods.
     ******************************************************************************************************************/

    private String getConfigurationString(String id, String defaultValue)
    {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(
            getActivity().getPackageName() + "_preferences",
            Context.MODE_PRIVATE);

        return sharedPreferences.getString(id, defaultValue);
    }

    private VideoSubtitleDetailsDo[] getSubtitlesForSelectedFile()
    {
        ArrayList<VideoSubtitleDetailsDo> subtitlesForVideo = new ArrayList<>();

        // Get settings.
        String preferredSubtitleLanguage = getConfigurationString(
            Settings.VIDEO_PREFERRED_SUBTITLE_LANGUAGE,
            Settings.VIDEO_PREFERRED_SUBTITLE_LANGUAGE_DEFAULT);

        // Get the subtitles that belong to this video. Preferred languages first.
        VideoSubtitleDetailsDo[] subtitles = mVideoDetails.getSubtitles();
        for (VideoSubtitleDetailsDo subtitle : subtitles)
        {
            if (selectedVideoFileDetails == null || subtitle.getIdVideo() == selectedVideoFileDetails.getId())
            {
                if (subtitle.getLanguage().equals(preferredSubtitleLanguage))
                    subtitlesForVideo.add(0, subtitle);
                else
                    subtitlesForVideo.add(subtitle);
            }
        }

        // Determine index of "None".
        int resultLength = subtitlesForVideo.size() + 1;
        int indexOfNone = 0, startIndex = 1;
        if (!preferredSubtitleLanguage.equals("None"))
        {
            indexOfNone = resultLength - 1;
            startIndex = 0;
        }

        // Create an array from the list. Add "None" to the result array.
        VideoSubtitleDetailsDo[] subtitlesForVideoArray = new VideoSubtitleDetailsDo[resultLength];
        for (int i = 0; i < subtitlesForVideo.size(); i++)
            subtitlesForVideoArray[i + startIndex] = subtitlesForVideo.get(i);
        subtitlesForVideoArray[indexOfNone] = new VideoSubtitleDetailsDo(
            -1,
            -1,
            getResources().getString(R.string.none));

        return subtitlesForVideoArray;
    }

    private void initializeAudioOutputSelector(View view)
    {
        Spinner spAudioOutputs = (Spinner)view.findViewById(R.id.spAudioOutputs);
        if (spAudioOutputs == null)
            return;

        // Configure control.
        ArrayAdapter audioOutputsAdapter = ArrayAdapter.createFromResource(
            getActivity(),
            //android.R.layout.simple_spinner_item,
            R.array.audio_outputs,
            R.layout.fat_spinner_item);
        spAudioOutputs.setAdapter(audioOutputsAdapter);
        spAudioOutputs.setOnItemSelectedListener(new SpAudioOutputItemChangeListener());

        // Get settings.
        String preferredAudioOutput = getConfigurationString(
            Settings.VIDEO_PREFERRED_AUDIO_OUTPUT,
            Settings.VIDEO_PREFERRED_AUDIO_OUTPUT_DEFAULT);

        // Set default item.
        selectedAudioOutput = preferredAudioOutput;
        if (preferredAudioOutput.equals(AudioOutputs.DIGITAL))
            spAudioOutputs.setSelection(1);
    }

    private void initializeSubtitleSelector(View view)
    {
        if (mVideoDetails == null)
            return;

        tvSubtitle = (TextView)view.findViewById(R.id.tvSubtitle);
        spSubtitles = (Spinner)view.findViewById(R.id.spSubtitles);
        if (tvSubtitle == null || spSubtitles == null)
            return;

        updateSubtitles();
        spSubtitles.setOnItemSelectedListener(new SpSubtitleItemChangeListener());
    }

    private void initializeTitle(View view)
    {
        if (mVideoDetails == null || mVideoDetails.getTitle() == null)
            return;

        TextView tvTitle = (TextView)view.findViewById(R.id.tvTitle);
        tvTitle.setText(mVideoDetails.getTitle());
    }

    private void initializeVideoSelector(View view)
    {
        if (mVideoDetails == null || mVideoDetails.getFiles() == null || mVideoDetails.getFiles().length <= 0)
            return;

        Spinner spLanguageAndQuality = (Spinner)view.findViewById(R.id.spLanguageAndQuality);
        if (spLanguageAndQuality == null)
            return;

        // Get preferred language from settings, then sort the video list based on this value.
        VideoFileDetailsDo.preferredLanguage = getConfigurationString(
            Settings.VIDEO_PREFERRED_LANGUAGE,
            Settings.VIDEO_PREFERRED_LANGUAGE_DEFAULT);
        VideoFileDetailsDo[] videoFileDetailsDo = mVideoDetails.getFiles();
        Arrays.sort(videoFileDetailsDo, Collections.reverseOrder());
        selectedVideoFileDetails = videoFileDetailsDo[0];

        // Configure control.
        ArrayAdapter<VideoFileDetailsDo> adapter = new ArrayAdapter<>(
            getActivity(),
            //android.R.layout.simple_spinner_item,
            R.layout.fat_spinner_item,
            videoFileDetailsDo);
        spLanguageAndQuality.setAdapter(adapter);
        spLanguageAndQuality.setOnItemSelectedListener(new SpLanguageAndQualityItemChangeListener());
    }

    private void playOrAdd(boolean addToPlaylist)
    {
        if (mListener == null || selectedVideoFileDetails == null)
            return;

        if (spSubtitles.isEnabled() && selectedVideoSubtitleDetails != null)
        {
            mListener.onVideoDetailsFragmentInteraction(
                selectedAudioOutput,
                selectedVideoFileDetails.getId(),
                selectedVideoSubtitleDetails.getId(),
                addToPlaylist);
        }
        else
        {
            mListener.onVideoDetailsFragmentInteraction(
                selectedAudioOutput,
                selectedVideoFileDetails.getId(),
                -1,
                addToPlaylist);
        }
    }

    private void setSubtitlesForSelectedFile()
    {
        VideoSubtitleDetailsDo[] videoSubtitleDetailsDo = getSubtitlesForSelectedFile();

        if (videoSubtitleDetailsDo == null || videoSubtitleDetailsDo.length <= 1)
        {
            tvSubtitle.setVisibility(View.GONE);
            spSubtitles.setVisibility(View.GONE);
        }
        else
        {
            tvSubtitle.setVisibility(View.VISIBLE);
            spSubtitles.setVisibility(View.VISIBLE);

            ArrayAdapter<VideoSubtitleDetailsDo> adapter = new ArrayAdapter<>(
                getActivity(),
                //android.R.layout.simple_spinner_item,
                R.layout.fat_spinner_item,
                videoSubtitleDetailsDo);
            spSubtitles.setAdapter(adapter);
        }
    }

    private void updateSubtitles()
    {
        if (mVideoDetails.getSubtitles() == null || mVideoDetails.getSubtitles().length <= 0)
        {
            tvSubtitle.setVisibility(View.GONE);
            spSubtitles.setVisibility(View.GONE);
        }
        else
        {
            tvSubtitle.setVisibility(View.VISIBLE);
            spSubtitles.setVisibility(View.VISIBLE);
            setSubtitlesForSelectedFile();
        }
    }

    /***************************************************************************************************************//**
     * Nested types -- event handlers.
     ******************************************************************************************************************/

    private class BtnAddToPlaylistClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            playOrAdd(true);
        }
    }

    private class BtnPlayClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            playOrAdd(false);
        }
    }

    private class SpAudioOutputItemChangeListener implements AdapterView.OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
        {
            if (pos == 0)
                selectedAudioOutput = AudioOutputs.ANALOG;
            else
                selectedAudioOutput = AudioOutputs.DIGITAL;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
            selectedVideoFileDetails = null;
        }
    }

    private class SpLanguageAndQualityItemChangeListener implements AdapterView.OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
        {
            selectedVideoFileDetails = (VideoFileDetailsDo)parent.getItemAtPosition(pos);
            updateSubtitles();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            selectedVideoFileDetails = null;
            updateSubtitles();
        }
    }

    private class SpSubtitleItemChangeListener implements AdapterView.OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
        {
            selectedVideoSubtitleDetails = (VideoSubtitleDetailsDo)parent.getItemAtPosition(pos);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
            selectedVideoSubtitleDetails = null;
        }
    }

    /***************************************************************************************************************//**
     * Nested types -- fragment interaction.
     ******************************************************************************************************************/

    /**
     * This interface must be implemented by activities that contain this fragment to allow an interaction in this
     * fragment to be communicated to the activity and potentially other fragments contained in that activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface VideoDetailsFragmentInteractionListener
    {
        void onVideoDetailsFragmentInteraction(
            String audioOutput,
            int videoDetailsId,
            int subtitleId,
            boolean addToPlaylist);
    }
}
