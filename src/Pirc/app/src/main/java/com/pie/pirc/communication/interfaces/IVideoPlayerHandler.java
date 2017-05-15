package com.pie.pirc.communication.interfaces;

import com.pie.pirc.communication.exceptions.CommunicationException;

/**
 * The interface for controlling the remote video player.
 *
 * Created by pgecsenyi on 2015.04.26..
 */
public interface IVideoPlayerHandler extends IPlayerHandler
{
    void sendPlay(String audioOutput, String idVideo, String idSubtitle) throws CommunicationException;
}
