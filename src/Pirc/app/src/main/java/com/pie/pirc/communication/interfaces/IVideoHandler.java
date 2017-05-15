package com.pie.pirc.communication.interfaces;

import java.util.LinkedHashMap;

import com.pie.pirc.communication.data.VideoDetails;
import com.pie.pirc.communication.data.VideoTitleFilter;
import com.pie.pirc.communication.exceptions.CommunicationException;

/**
 * The interface for handling video data.
 *
 * Created by pgecsenyi on 2015.04.26..
 */
public interface IVideoHandler
{
    VideoDetails downloadDetails(String id) throws CommunicationException;

    LinkedHashMap<String, String> downloadLanguages() throws CommunicationException;

    LinkedHashMap<String, String> downloadQualities() throws CommunicationException;

    LinkedHashMap<String, String> downloadTitles(VideoTitleFilter filter) throws CommunicationException;

    IVideoPlayerHandler getPlayer();
}
