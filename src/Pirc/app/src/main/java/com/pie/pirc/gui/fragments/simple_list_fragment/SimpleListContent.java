package com.pie.pirc.gui.fragments.simple_list_fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pgecsenyi on 2015.05.02..
 */
public class SimpleListContent
{
    /***************************************************************************************************************//**
     * Public fields.
     ******************************************************************************************************************/

    /**
     * An array of sample (dummy) items.
     */
    private static Map<Integer, ArrayList<SimpleListItem>> items = new HashMap<>();

    /***************************************************************************************************************//**
     * Public methods.
     ******************************************************************************************************************/

    public static void addItem(int id, SimpleListItem item)
    {
        if (!items.containsKey(id))
            items.put(id, new ArrayList<SimpleListItem>());

        items.get(id).add(item);
    }

    public static void addItems(int id, ArrayList<SimpleListItem> list)
    {
        items.put(id, list);
    }

    public static void clear()
    {
        items.clear();
    }

    public static void clear(int id)
    {
        if (items.containsKey(id))
            items.get(id).clear();
    }

    public static ArrayList<SimpleListItem> getList(int id)
    {
        if (!items.containsKey(id))
            return null;

        return items.get(id);
    }
}
