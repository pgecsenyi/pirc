package com.pie.pirc.communication.interfaces;

import java.util.LinkedHashMap;

import com.pie.pirc.communication.exceptions.CommunicationException;

/**
 * The interface for handling audio data.
 *
 * Created by pgecsenyi on 2015.11.19..
 */
public interface IAudioHandler
{
    LinkedHashMap<String, String> downloadAlbums() throws CommunicationException;

    LinkedHashMap<String, String> downloadAlbums(String artist) throws CommunicationException;

    LinkedHashMap<String, String> downloadArtists() throws CommunicationException;

    LinkedHashMap<String, String> downloadTracks(String album) throws CommunicationException;

    IPlayerHandler getPlayer();
}
