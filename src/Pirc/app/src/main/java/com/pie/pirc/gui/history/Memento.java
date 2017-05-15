package com.pie.pirc.gui.history;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Stores a piece of history: a visited page and some related parameters.
 *
 * Created by pgecsenyi on 2015.06.28..
 */
public class Memento implements Parcelable
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    // The name of the filtering parameters set when navigated to this page.
    private String[] filters;

    // The ID of the list in the repository to be displayed.
    private int listId;

    // The visited page.
    private int page;

    /***************************************************************************************************************//**
     * Public fields -- Parcelable creator.
     ******************************************************************************************************************/

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
        {
            public Memento createFromParcel(Parcel in)
            {
                return new Memento(in);
            }

            public Memento[] newArray(int size)
            {
                return new Memento[size];
            }
        };

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public Memento(int page)
    {
        this(page, -1, null);
    }

    public Memento(int page, int listId, String[] filters)
    {
        this.page = page;
        this.listId = listId;
        this.filters = filters;
    }

    public Memento(Parcel in)
    {
        page = in.readInt();
        listId = in.readInt();
        in.readStringArray(filters);
    }

    /***************************************************************************************************************//**
     * Getters.
     ******************************************************************************************************************/

    public String[] getFilters()
{
    return filters;
}

    public int getListId()
    {
        return listId;
    }

    public int getPage()
    {
        return page;
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
        dest.writeInt(page);
        dest.writeInt(listId);
        dest.writeStringArray(filters);
    }
}
