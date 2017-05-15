package com.pie.pirc.gui.fragments.simple_list_fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.pie.pirc.R;
import com.pie.pirc.gui.interfaces.IAsyncOperationManager;
import com.pie.pirc.gui.interfaces.ICommunicatorProvider;
import com.pie.pirc.gui.interfaces.IFilterManager;
import com.pie.pirc.gui.interfaces.IFragmentManager;
import com.pie.pirc.gui.interfaces.ISettingsManager;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link SimpleListFragmentInteractionListener} interface.
 */
public class SimpleListFragment extends Fragment implements AbsListView.OnItemClickListener
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private static final String ARG_LIST_ID = "listId";

    /**
     * The Adapter which will be used to populate the ListView/GridView with Views.
     */
    private ListAdapter mAdapter;

    /**
     * The concrete fragment parameter.
     */
    private int mListId;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * Click event handler.
     */
    private SimpleListFragmentInteractionListener mListener;

    /***************************************************************************************************************//**
     * Constructors.
     ******************************************************************************************************************/

    public static SimpleListFragment newInstance(int listId)
    {
        // Create fragment.
        SimpleListFragment fragment = new SimpleListFragment();

        // Set arguments.
        Bundle args = new Bundle();
        args.putInt(ARG_LIST_ID, listId);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon screen orientation
     * changes).
     */
    public SimpleListFragment()
    {
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
            mListener = new com.pie.pirc.gui.interaction.SimpleListFragmentInteractionListener(
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

        if (savedInstanceState != null)
            mListId = savedInstanceState.getInt(ARG_LIST_ID);
        else if (getArguments() != null)
            mListId = getArguments().getInt(ARG_LIST_ID);

        mAdapter = new ArrayAdapter<>(
            getActivity(),
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            SimpleListContent.getList(mListId));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_simplelistitem, container, false);

        // Set the adapter
        mListView = (AbsListView)view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>)mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks.
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onDetach()
    {
        super.onDetach();

        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        if (null != mListener)
        {
            ArrayList<SimpleListItem> list = SimpleListContent.getList(mListId);
            if (list == null)
                return;

            // Notify the active callbacks interface (the activity, if the fragment is attached to one) that an item has
            // been selected.
            mListener.onSimpleListFragmentInteraction(list.get(position).id);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt(ARG_LIST_ID, mListId);
    }

    /***************************************************************************************************************//**
     * Public methods.
     ******************************************************************************************************************/

    /**
     * The default content for this Fragment has a TextView that is shown when the list is empty. If you would like to
     * change the text, call this method to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText)
    {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView)
            ((TextView)emptyView).setText(emptyText);
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
    public interface SimpleListFragmentInteractionListener
    {
        void onSimpleListFragmentInteraction(String id);
    }
}
