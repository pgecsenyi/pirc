package com.pie.pirc.communication.interfaces;

import com.pie.pirc.communication.data.PlaylistItem;
import com.pie.pirc.communication.data.PlaylistItemInfo;
import com.pie.pirc.communication.exceptions.CommunicationException;

/**
 * Created by pgecsenyi on 2016.02.21..
 */
public interface IPlaylistHandler
{
    void add(PlaylistItem item) throws CommunicationException;

    void clear() throws CommunicationException;

    void remove(String id) throws CommunicationException;

    PlaylistItemInfo[] view() throws CommunicationException;
}
