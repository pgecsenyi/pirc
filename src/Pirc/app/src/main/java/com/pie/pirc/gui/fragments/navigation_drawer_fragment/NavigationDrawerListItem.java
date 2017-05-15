package com.pie.pirc.gui.fragments.navigation_drawer_fragment;

import android.content.Context;

/**
 * Created by pgecsenyi on 2015.11.17..
 */
public class NavigationDrawerListItem implements INavigationDrawerItem
{
    /***************************************************************************************************************//**
     * Public constants.
     ******************************************************************************************************************/

    public static final int ITEM_TYPE = 1;

    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private int icon;

    private int id;

    private String label;

    private boolean updateActionBarTitle;

    /***************************************************************************************************************//**
     * Constructors.
     ******************************************************************************************************************/

    private NavigationDrawerListItem()
    {
    }

    public static NavigationDrawerListItem create(int id, String label, int icon)
    {
        NavigationDrawerListItem item = new NavigationDrawerListItem();
        item.setId(id);
        item.setLabel(label);
        item.setIcon(icon);
        item.setUpdateActionBarTitle(false);

        return item;
    }

    public static NavigationDrawerListItem create(
        int id,
        String label,
        String icon,
        boolean updateActionBarTitle,
        Context context)
    {
        NavigationDrawerListItem item = new NavigationDrawerListItem();
        item.setId(id);
        item.setLabel(label);
        item.setIcon(context.getResources().getIdentifier(icon, "drawable", context.getPackageName()));
        item.setUpdateActionBarTitle(updateActionBarTitle);

        return item;
    }

    /***************************************************************************************************************//**
     * INavigationDrawerItem implementation.
     ******************************************************************************************************************/

    @Override
    public int getType()
    {
        return ITEM_TYPE;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }

    @Override
    public boolean updateActionBarTitle()
    {
        return this.updateActionBarTitle;
    }

    /***************************************************************************************************************//**
     * Public methods.
     ******************************************************************************************************************/

    public int getIcon()
    {
        return icon;
    }

    public void setIcon(int icon)
    {
        this.icon = icon;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public void setUpdateActionBarTitle(boolean updateActionBarTitle)
    {
        this.updateActionBarTitle = updateActionBarTitle;
    }
}
