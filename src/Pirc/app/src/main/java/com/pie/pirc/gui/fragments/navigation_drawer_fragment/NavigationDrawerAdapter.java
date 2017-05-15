package com.pie.pirc.gui.fragments.navigation_drawer_fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pie.pirc.R;

/**
 * List adapter for Navigation Drawer.
 *
 * Created by pgecsenyi on 2015.11.17..
 */
public class NavigationDrawerAdapter extends ArrayAdapter<INavigationDrawerItem>
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private LayoutInflater inflater;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public NavigationDrawerAdapter(Context context, int textViewResourceId, INavigationDrawerItem[] objects)
    {
        super(context, textViewResourceId, objects);

        this.inflater = LayoutInflater.from(context);
    }

    /***************************************************************************************************************//**
     * Overridden ArrayAdapter methods.
     ******************************************************************************************************************/

    @Override
    public long getItemId(int position)
    {
        return this.getItem(position).getId();
    }

    @Override
    public int getItemViewType(int position)
    {
        return this.getItem(position).getType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view;
        INavigationDrawerItem menuItem = this.getItem(position);

        if (menuItem.getType() == NavigationDrawerListItem.ITEM_TYPE)
            view = getItemView(convertView, parent, menuItem );
        else
            view = getSectionView(convertView, parent, menuItem);

        return view;
    }

    @Override
    public int getViewTypeCount()
    {
        return 2;
    }

    @Override
    public boolean isEnabled(int position)
    {
        return getItem(position).isEnabled();
    }

    /***************************************************************************************************************//**
     * Public methods.
     ******************************************************************************************************************/

    public View getItemView(View convertView, ViewGroup parentView, INavigationDrawerItem navDrawerItem)
    {
        NavigationDrawerListItem menuItem = (NavigationDrawerListItem) navDrawerItem;
        NavigationDrawerListItemHolder navMenuItemHolder = null;

        if (convertView == null)
        {
            convertView = inflater.inflate( R.layout.navigation_drawer_list_item, parentView, false);
            TextView labelView = (TextView)convertView.findViewById(R.id.navmenuitem_label);
            ImageView iconView = (ImageView)convertView.findViewById(R.id.navmenuitem_icon);

            navMenuItemHolder = new NavigationDrawerListItemHolder();
            navMenuItemHolder.labelView = labelView;
            navMenuItemHolder.iconView = iconView;

            convertView.setTag(navMenuItemHolder);
        }

        if (navMenuItemHolder == null)
            navMenuItemHolder = (NavigationDrawerListItemHolder)convertView.getTag();

        navMenuItemHolder.labelView.setText(menuItem.getLabel());
        navMenuItemHolder.iconView.setImageResource(menuItem.getIcon());

        return convertView;
    }

    public View getSectionView(View convertView, ViewGroup parentView, INavigationDrawerItem navDrawerItem)
    {
        NavigationDrawerSectionItem menuSection = (NavigationDrawerSectionItem) navDrawerItem;
        NavigationDrawerSectionItemHolder navMenuItemHolder = null;

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.navigation_drawer_section_item, parentView, false);
            TextView labelView = (TextView)convertView.findViewById(R.id.navmenusection_label);

            navMenuItemHolder = new NavigationDrawerSectionItemHolder();
            navMenuItemHolder.labelView = labelView;
            convertView.setTag(navMenuItemHolder);
        }

        if (navMenuItemHolder == null)
            navMenuItemHolder = (NavigationDrawerSectionItemHolder) convertView.getTag();

        navMenuItemHolder.labelView.setText(menuSection.getLabel());

        return convertView;
    }

    /***************************************************************************************************************//**
     * Nested types.
     ******************************************************************************************************************/

    private class NavigationDrawerListItemHolder
    {
        private TextView labelView;
        private ImageView iconView;
    }

    private class NavigationDrawerSectionItemHolder
    {
        private TextView labelView;
    }
}
