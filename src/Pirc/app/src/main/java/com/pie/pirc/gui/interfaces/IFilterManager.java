package com.pie.pirc.gui.interfaces;

import com.pie.pirc.communication.data.VideoTitleFilter;

/**
 * Created by pgecsenyi on 2016.11.04..
 */
public interface IFilterManager
{
    VideoTitleFilter buildVideoTitleFilter();

    int getCurrentPage();

    void resetFilter();

    void setFilter(String key, String value);
}
