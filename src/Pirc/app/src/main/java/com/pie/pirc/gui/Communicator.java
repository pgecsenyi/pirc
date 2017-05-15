package com.pie.pirc.gui;

import com.pie.pirc.communication.interfaces.IAudioHandler;
import com.pie.pirc.communication.interfaces.IMaintenanceHandler;
import com.pie.pirc.communication.interfaces.IPlayerHandler;
import com.pie.pirc.communication.interfaces.IPlaylistHandler;
import com.pie.pirc.communication.interfaces.IVideoHandler;
import com.pie.pirc.communication.protocols.rest.RestAudioHandler;
import com.pie.pirc.communication.protocols.rest.RestMaintenanceHandler;
import com.pie.pirc.communication.protocols.rest.RestPlayerHandler;
import com.pie.pirc.communication.protocols.rest.RestPlaylistHandler;
import com.pie.pirc.communication.protocols.rest.RestVideoHandler;

/**
 * Created by pgecsenyi on 2016.11.03..
 */

public class Communicator
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private IAudioHandler audioHandler;

    private IMaintenanceHandler maintenanceHandler;

    private IPlayerHandler playerHandler;

    private IPlaylistHandler playlistHandler;

    private IVideoHandler videoHandler;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public Communicator(String baseAddress)
    {
        audioHandler = new RestAudioHandler(baseAddress);
        maintenanceHandler = new RestMaintenanceHandler(baseAddress);
        playerHandler = new RestPlayerHandler(baseAddress);
        playlistHandler = new RestPlaylistHandler(baseAddress);
        videoHandler = new RestVideoHandler(baseAddress);
    }

    /***************************************************************************************************************//**
     * Public methods -- getters.
     ******************************************************************************************************************/

    public IAudioHandler getAudioHandler()
    {
        return audioHandler;
    }

    public IMaintenanceHandler getMaintenanceHandler()
    {
        return maintenanceHandler;
    }

    public IPlayerHandler getPlayerHandler()
    {
        return playerHandler;
    }

    public IPlaylistHandler getPlaylistHandler()
    {
        return playlistHandler;
    }

    public IVideoHandler getVideoHandler()
    {
        return videoHandler;
    }
}
