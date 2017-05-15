package com.pie.pirc.communication.interfaces;

import com.pie.pirc.communication.exceptions.CommunicationException;

/**
 * The interface for controlling the remote multimedia player.
 *
 * Created by pgecsenyi on 2015.04.26..
 */
public interface IPlayerHandler
{
    void sendFaster() throws CommunicationException;

    void sendFastForward() throws CommunicationException;

    void sendFastRewind() throws CommunicationException;

    void sendForward() throws CommunicationException;

    void sendPause() throws CommunicationException;

    void sendPlay(String id) throws CommunicationException;

    void sendPlay(String audioOutput, String id) throws CommunicationException;

    void sendRewind() throws CommunicationException;

    void sendSlower() throws CommunicationException;

    void sendStop() throws CommunicationException;

    void sendVolumeDown() throws CommunicationException;

    void sendVolumeUp() throws CommunicationException;
}
