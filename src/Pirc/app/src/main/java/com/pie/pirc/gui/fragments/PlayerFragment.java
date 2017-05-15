package com.pie.pirc.gui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.pie.pirc.R;
import com.pie.pirc.async.operations.ControlPlayerAsyncOp;
import com.pie.pirc.gui.interfaces.IAsyncOperationManager;
import com.pie.pirc.gui.interfaces.ICommunicatorProvider;
import com.pie.pirc.gui.interfaces.IFilterManager;
import com.pie.pirc.gui.interfaces.IFragmentManager;
import com.pie.pirc.gui.interfaces.ISettingsManager;

/**
 * Fragment that allows the user to control the player.
 *
 * Activities that contain this fragment must implement the {@link PlayerFragmentInteractionListener} interface to
 * handle interaction events. Use the {@link VideoDetailsFragment#newInstance} factory method to create an instance of
 * this fragment.
 */
public class PlayerFragment extends Fragment
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private PlayerFragmentInteractionListener mListener;

    private int selectedPlayer;

    /***************************************************************************************************************//**
     * Constructors.
     ******************************************************************************************************************/

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment PlayerFragment.
     */
    public static PlayerFragment newInstance()
    {
        return new PlayerFragment();
    }

    public PlayerFragment()
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
            mListener = new com.pie.pirc.gui.interaction.PlayerFragmentInteractionListener(
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment.
        View view = inflater.inflate(R.layout.fragment_player, container, false);

        view.findViewById(R.id.btnFaster).setOnClickListener(new BtnFasterClickListener());
        view.findViewById(R.id.btnFastForward).setOnClickListener(new BtnFastForwardClickListener());
        view.findViewById(R.id.btnFastRewind).setOnClickListener(new BtnFastRewindClickListener());
        view.findViewById(R.id.btnForward).setOnClickListener(new BtnForwardClickListener());
        view.findViewById(R.id.btnPause).setOnClickListener(new BtnPauseClickListener());
        view.findViewById(R.id.btnRewind).setOnClickListener(new BtnRewindClickListener());
        view.findViewById(R.id.btnSlower).setOnClickListener(new BtnSlowerClickListener());
        view.findViewById(R.id.btnStop).setOnClickListener(new BtnStopClickListener());
        view.findViewById(R.id.btnVolumeDown).setOnClickListener(new BtnVolumeDownClickListener());
        view.findViewById(R.id.btnVolumeUp).setOnClickListener(new BtnVolumeUpClickListener());
        view.findViewById(R.id.rbAll).setOnClickListener(new RadioGroupPlayersListener());
        view.findViewById(R.id.rbAudio).setOnClickListener(new RadioGroupPlayersListener());
        view.findViewById(R.id.rbVideo).setOnClickListener(new RadioGroupPlayersListener());

        return view;
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

    private void invokeInteractionHandler(int action)
    {
        if (mListener != null)
            mListener.onPlayerFragmentInteraction(selectedPlayer, action);
    }

    /***************************************************************************************************************//**
     * Nested types -- event handlers.
     ******************************************************************************************************************/

    private class BtnFasterClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            invokeInteractionHandler(ControlPlayerAsyncOp.FASTER);
        }
    }

    private class BtnFastForwardClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            invokeInteractionHandler(ControlPlayerAsyncOp.FAST_FORWARD);
        }
    }

    private class BtnFastRewindClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            invokeInteractionHandler(ControlPlayerAsyncOp.FAST_REWIND);
        }
    }

    private class BtnForwardClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            invokeInteractionHandler(ControlPlayerAsyncOp.FORWARD);
        }
    }

    private class BtnPauseClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            invokeInteractionHandler(ControlPlayerAsyncOp.PAUSE);
        }
    }

    private class BtnRewindClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            invokeInteractionHandler(ControlPlayerAsyncOp.REWIND);
        }
    }

    private class BtnSlowerClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            invokeInteractionHandler(ControlPlayerAsyncOp.SLOWER);
        }
    }

    private class BtnStopClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            invokeInteractionHandler(ControlPlayerAsyncOp.STOP);
        }
    }

    private class BtnVolumeDownClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            invokeInteractionHandler(ControlPlayerAsyncOp.VOLUME_DOWN);
        }
    }

    private class BtnVolumeUpClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            invokeInteractionHandler(ControlPlayerAsyncOp.VOLUME_UP);
        }
    }

    private class RadioGroupPlayersListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            // Is the button now checked?
            boolean checked = ((RadioButton)view).isChecked();

            // Check which radio button was clicked.
            switch (view.getId())
            {
                case R.id.rbAudio:
                    if (checked)
                        selectedPlayer = Players.AUDIO;
                        break;
                case R.id.rbVideo:
                    if (checked)
                        selectedPlayer = Players.VIDEO;
                        break;
                default:
                    if (checked)
                        selectedPlayer = Players.ALL;
                        break;
            }
        }
    }

    /***************************************************************************************************************//**
     * Nested types -- players.
     ******************************************************************************************************************/

    public class Players
    {
        public final static int ALL = 0;
        public final static int AUDIO = 1;
        public final static int VIDEO = 2;
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
    public interface PlayerFragmentInteractionListener
    {
        void onPlayerFragmentInteraction(int player, int action);
    }
}
