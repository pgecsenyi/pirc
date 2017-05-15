package com.pie.pirc.gui.history;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pgecsenyi on 2015.06.28..
 */
public class FilterState implements Parcelable
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private int currentListId = 0;

    private ArrayList<String> activeFilters = new ArrayList<>();

    private HashMap<String, String> filters = new HashMap<>();

    private History history = new History();

    /***************************************************************************************************************//**
     * Public fields -- Parcelable creator.
     ******************************************************************************************************************/

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
        {
            public FilterState createFromParcel(Parcel in)
            {
                return new FilterState(in);
            }

            public FilterState[] newArray(int size)
            {
                return new FilterState[size];
            }
        };

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public FilterState()
    {
    }

    public FilterState(Parcel in)
    {
        history = in.readParcelable(History.class.getClassLoader());
        currentListId = in.readInt();
        filters = in.readHashMap(HashMap.class.getClassLoader());
    }

    /***************************************************************************************************************//**
     * Getters and setters.
     ******************************************************************************************************************/

    public int getCurrentListId()
    {
        return currentListId;
    }

    public int getCurrentPage()
    {
        if (history.isEmpty())
            return -1;

        return history.peek().getPage();
    }

    public String getFilter(String key)
    {
        if (filters.containsKey(key))
            return filters.get(key);

        return null;
    }

    public void setFilter(String key, String value)
    {
        filters.put(key, value);
        activeFilters.add(key);
    }

    public boolean isHistoryEmpty()
    {
        return history.isEmpty();
    }

    /***************************************************************************************************************//**
     * Parcelable implementation.
     ******************************************************************************************************************/

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeParcelable(history, 0);
        dest.writeInt(currentListId);
        dest.writeMap(filters);
    }

    /***************************************************************************************************************//**
     * Public methods.
     ******************************************************************************************************************/

    public void resetFilter()
    {
        filters.clear();
    }

    public void stepBack()
    {
        // The history is empty, there is no place to go.
        if (history.isEmpty())
            return;

        // Go back in history, set the appropriate variables.
        Memento memento = history.pop();
        currentListId = memento.getListId();

        // Reset filters if needed.
        String[] filters = memento.getFilters();
        if (filters != null)
        {
            for (String filter : filters)
                this.filters.remove(filter);
        }
    }

    public void stepForward(int page)
    {
        // Convert active filters to an array.
        String[] activeFilterArray = new String[activeFilters.size()];
        activeFilters.toArray(activeFilterArray);

        // Update history.
        history.push(new Memento(page, currentListId, activeFilterArray));
        currentListId++;
        activeFilters.clear();
    }
}
