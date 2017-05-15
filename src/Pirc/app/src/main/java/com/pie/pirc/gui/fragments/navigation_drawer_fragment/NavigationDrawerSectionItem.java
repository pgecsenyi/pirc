package com.pie.pirc.gui.fragments.navigation_drawer_fragment;

/**
 * Created by pgecsenyi on 2015.11.17..
 */
public class NavigationDrawerSectionItem implements INavigationDrawerItem
{
    /***************************************************************************************************************//**
     * Public constants.
     ******************************************************************************************************************/

    public static final int SECTION_TYPE = 0;

    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private int id;

    private String label;

    /***************************************************************************************************************//**
     * Constructors.
     ******************************************************************************************************************/

    private NavigationDrawerSectionItem()
    {
    }

    public static NavigationDrawerSectionItem create(int id, String label)
    {
        NavigationDrawerSectionItem section = new NavigationDrawerSectionItem();
        section.setLabel(label);
        return section;
    }

    /***************************************************************************************************************//**
     * INavigationDrawerItem implementation.
     ******************************************************************************************************************/

    @Override
    public int getType()
    {
        return SECTION_TYPE;
    }

    @Override
    public boolean isEnabled()
    {
        return false;
    }

    @Override
    public boolean updateActionBarTitle()
    {
        return false;
    }

    /***************************************************************************************************************//**
     * Public methods.
     ******************************************************************************************************************/

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
}
