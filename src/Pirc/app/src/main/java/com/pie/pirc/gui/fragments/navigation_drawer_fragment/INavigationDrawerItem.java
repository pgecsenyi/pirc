package com.pie.pirc.gui.fragments.navigation_drawer_fragment;

/**
 * Created by pgecsenyi on 2015.11.17..
 */
public interface INavigationDrawerItem
{
    public int getId();

    public String getLabel();

    public int getType();

    public boolean isEnabled();

    public boolean updateActionBarTitle();
}
