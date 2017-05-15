package com.pie.pirc.gui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pie.pirc.R;
import com.pie.pirc.gui.interfaces.IAsyncOperationManager;
import com.pie.pirc.gui.interfaces.ICommunicatorProvider;
import com.pie.pirc.gui.interfaces.IFilterManager;
import com.pie.pirc.gui.interfaces.IFragmentManager;
import com.pie.pirc.gui.interfaces.ISettingsManager;

/**
 * Fragment that makes database maintenance possible.
 *
 * Activities that contain this fragment must implement the
 * {@link MaintenanceFragment.MaintenanceFragmentInteractionListener} interface to handle interaction events. Use the
 * {@link MaintenanceFragment#newInstance} factory method to create an instance of this fragment.
 */
public class MaintenanceFragment extends Fragment
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private MaintenanceFragmentInteractionListener mListener;

    /***************************************************************************************************************//**
     * Constructors.
     ******************************************************************************************************************/

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment MaintenanceFragment.
     */
    public static MaintenanceFragment newInstance()
    {
        return new MaintenanceFragment();
    }

    public MaintenanceFragment()
    {
        // Required empty public constructor
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
            mListener = new com.pie.pirc.gui.interaction.MaintenanceFragmentInteractionListener(
                communicatorProvider,
                asyncOperationManager,
                filterManager,
                fragmentManager,
                settingsManager);
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(
                activity.toString() + " must implement MaintenanceFragmentInteractionListener");
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
        final View v = inflater.inflate(R.layout.fragment_maintenance, container, false);

        Button btnReset = (Button)v.findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new BtnResetClickListener());
        Button btnSync = (Button)v.findViewById(R.id.btnSynchronize);
        btnSync.setOnClickListener(new BtnSynchronizeClickListener());

        return v;
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    /***************************************************************************************************************//**
     * Nested types -- event handlers.
     ******************************************************************************************************************/

    private class BtnResetClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            mListener.onSynchronizeDatabase(true);
        }
    }

    private class BtnSynchronizeClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            mListener.onSynchronizeDatabase(false);
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
    public interface MaintenanceFragmentInteractionListener
    {
        void onSynchronizeDatabase(boolean isCompleteRebuildRequested);
    }
}
