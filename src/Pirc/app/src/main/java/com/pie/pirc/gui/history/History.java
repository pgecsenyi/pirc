package com.pie.pirc.gui.history;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Stack;

import com.pie.pirc.gui.activities.MainActivity;

/**
 * Stores navigation history for {@link MainActivity} in a stack.
 *
 * Created by pgecsenyi on 2015.06.27..
 */
public class History
    extends Stack<Memento>
    implements Parcelable
{
    /***************************************************************************************************************//**
     * Public fields -- Parcelable creator.
     ******************************************************************************************************************/

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
        {
            public History createFromParcel(Parcel in)
            {
                return new History(in);
            }

            public History[] newArray(int size)
            {
                return new History[size];
            }
        };

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public History()
    {
        super();
    }

    public History(Parcel in)
    {
        Memento[] serializedArray = null;
        in.readTypedArray(serializedArray, Memento.CREATOR);
        for (Memento memento : serializedArray)
            super.push(memento);
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
        Memento[] serializableArray = new Memento[super.elementCount];
        for (int i = 0; i < super.elementCount; i++)
            serializableArray[i] = super.get(i);
        dest.writeTypedArray(serializableArray, 0);
    }
}
