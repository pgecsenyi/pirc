package com.pie.pirc.gui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.pie.pirc.R;
import com.pie.pirc.gui.display_objects.KeyValuePair;
import com.pie.pirc.gui.display_objects.VideoFilterDataDo;
import com.pie.pirc.gui.interfaces.IAsyncOperationManager;
import com.pie.pirc.gui.interfaces.ICommunicatorProvider;
import com.pie.pirc.gui.interfaces.IFilterManager;
import com.pie.pirc.gui.interfaces.IFragmentManager;
import com.pie.pirc.gui.interfaces.ISettingsManager;

/**
 * Fragment that displays filtering options for video titles.
 *
 * Activities that contain this fragment must implement the {@link VideoFilterFragmentInteractionListener} interface to
 * handle interaction events. Use the {@link VideoFilterFragment#newInstance} factory method to create an instance of
 * this fragment.
 */
public class VideoFilterFragment extends Fragment
{
    /***************************************************************************************************************//**
     * Private constants.
     ******************************************************************************************************************/

    private static final String ARG_VIDEO_FILTER_DATA = "paramVideoFilterData";

    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private VideoFilterFragmentInteractionListener mListener;

    private VideoFilterDataDo mVideoFilterData;

    private String idSelectedLanguage, idSelectedQuality, idSelectedSubtitleLanguage, titleTextFilter;

    /***************************************************************************************************************//**
     * Constructors.
     ******************************************************************************************************************/

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param paramVideoFilterData Parameter for VideoFilterDataDo.
     * @return A new instance of fragment VideoFilterFragment.
     */
    public static VideoFilterFragment newInstance(VideoFilterDataDo paramVideoFilterData)
    {
        VideoFilterFragment fragment = new VideoFilterFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_VIDEO_FILTER_DATA, paramVideoFilterData);
        fragment.setArguments(args);
        return fragment;
    }

    public VideoFilterFragment()
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
            mListener = new com.pie.pirc.gui.interaction.VideoFilterFragmentInteractionListener(
                communicatorProvider,
                asyncOperationManager,
                filterManager,
                fragmentManager,
                settingsManager);
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
            mVideoFilterData = getArguments().getParcelable(ARG_VIDEO_FILTER_DATA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment.
        final View v = inflater.inflate(R.layout.fragment_video_filter, container, false);

        initializeLanguageSelector(v);
        initializeQualitySelector(v);
        initializeSubtitleSelector(v);
        initializeTitleEditor(v);

        Button btnSearch = (Button)v.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new BtnSearchClickListener());

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

    private ArrayList<KeyValuePair> convertHashMap(HashMap<String, String> hashMap)
    {
        if (hashMap == null)
            return null;

        ArrayList<KeyValuePair> arrayList = new ArrayList<>();
        arrayList.add(new KeyValuePair(null, getString(R.string.any)));
        for (Map.Entry pair : hashMap.entrySet())
            arrayList.add(new KeyValuePair(pair.getKey().toString(), pair.getValue().toString()));

        return arrayList;
    }

    private void initializeLanguageSelector(View view)
    {
        if (mVideoFilterData == null)
            return;

        Spinner spLanguages = (Spinner)view.findViewById(R.id.spLanguages);
        if (spLanguages == null)
            return;

        ArrayList<KeyValuePair> languageList = convertHashMap(mVideoFilterData.getLanguages());

        ArrayAdapter<KeyValuePair> adapter = new ArrayAdapter<>(
            getActivity(),
            //android.R.layout.simple_spinner_item,
            R.layout.fat_spinner_item,
            languageList);
        spLanguages.setAdapter(adapter);
        spLanguages.setOnItemSelectedListener(new SpLanguageItemChangeListener());
    }

    private void initializeQualitySelector(View view)
    {
        if (mVideoFilterData == null)
            return;

        Spinner spQualities = (Spinner)view.findViewById(R.id.spQualities);
        if (spQualities == null)
            return;

        ArrayList<KeyValuePair> qualityList = convertHashMap(mVideoFilterData.getQualities());

        ArrayAdapter<KeyValuePair> adapter = new ArrayAdapter<>(
            getActivity(),
            //android.R.layout.simple_spinner_item,
            R.layout.fat_spinner_item,
            qualityList);
        spQualities.setAdapter(adapter);
        spQualities.setOnItemSelectedListener(new SpQualityItemChangeListener());
    }

    private void initializeSubtitleSelector(View view)
    {
        if (mVideoFilterData == null)
            return;

        Spinner spSubtitles = (Spinner)view.findViewById(R.id.spSubtitles);
        if (spSubtitles == null)
            return;

        ArrayList<KeyValuePair> languageList = convertHashMap(mVideoFilterData.getLanguages());

        ArrayAdapter<KeyValuePair> adapter = new ArrayAdapter<>(
            getActivity(),
            //android.R.layout.simple_spinner_item,
            R.layout.fat_spinner_item,
            languageList);
        spSubtitles.setAdapter(adapter);
        spSubtitles.setOnItemSelectedListener(new SpSubtitleItemChangeListener());
    }

    private void initializeTitleEditor(View view)
    {
        EditText etTitle = (EditText)view.findViewById(R.id.etTitle);
        if (etTitle == null)
            return;

        etTitle.addTextChangedListener(new EtTitleTextChangeListener());
    }

    /***************************************************************************************************************//**
     * Nested types -- event handlers.
     ******************************************************************************************************************/

    private class BtnSearchClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            if (mListener == null)
                return;

            mListener.onVideoFilterFragmentInteraction(
                titleTextFilter,
                idSelectedLanguage,
                idSelectedQuality,
                idSelectedSubtitleLanguage);
        }
    }

    private class EtTitleTextChangeListener implements TextWatcher
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
        }

        @Override
        public void afterTextChanged(Editable s)
        {
            titleTextFilter = s.toString();
        }
    }

    private class SpLanguageItemChangeListener implements AdapterView.OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
        {
            idSelectedLanguage = ((KeyValuePair)parent.getItemAtPosition(pos)).getKey();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
            idSelectedLanguage = null;
        }
    }

    private class SpQualityItemChangeListener implements AdapterView.OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
        {
            idSelectedQuality = ((KeyValuePair)parent.getItemAtPosition(pos)).getKey();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
            idSelectedQuality = null;
        }
    }

    private class SpSubtitleItemChangeListener implements AdapterView.OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
        {
            idSelectedSubtitleLanguage = ((KeyValuePair)parent.getItemAtPosition(pos)).getKey();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
            idSelectedSubtitleLanguage = null;
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
    public interface VideoFilterFragmentInteractionListener
    {
        void onVideoFilterFragmentInteraction(
            String text,
            String languageId,
            String qualityId,
            String subtitleLanguageId);
    }
}
