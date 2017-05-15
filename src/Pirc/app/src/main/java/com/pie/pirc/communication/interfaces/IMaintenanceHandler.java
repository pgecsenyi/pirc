package com.pie.pirc.communication.interfaces;

import com.pie.pirc.communication.exceptions.CommunicationException;

/**
 * The interface for common operations.
 *
 * Created by pgecsenyi on 2015.11.22..
 */
public interface IMaintenanceHandler
{
    void rebuildDatabase() throws CommunicationException;

    void synchronizeDatabase() throws CommunicationException;
}
