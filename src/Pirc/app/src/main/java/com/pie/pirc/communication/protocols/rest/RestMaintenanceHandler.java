package com.pie.pirc.communication.protocols.rest;

import com.pie.pirc.communication.exceptions.CommunicationException;
import com.pie.pirc.communication.interfaces.IMaintenanceHandler;
import com.pie.pirc.communication.utilities.HttpClient;
import com.pie.pirc.communication.utilities.UrlHelper;

/**
 * Performs common operations using the REST API.
 *
 * Created by pgecsenyi on 2015.11.22..
 */
public class RestMaintenanceHandler implements IMaintenanceHandler
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    protected String baseUrl;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public RestMaintenanceHandler(String baseUrl)
    {
        this.baseUrl = baseUrl;
    }

    /***************************************************************************************************************//**
     * ICommonHandler implementation.
     ******************************************************************************************************************/

    @Override
    public void rebuildDatabase() throws CommunicationException
    {
        HttpClient.getContent(UrlHelper.buildUrl(this.baseUrl, "rebuild"));
    }

    @Override
    public void synchronizeDatabase() throws CommunicationException
    {
        HttpClient.getContent(UrlHelper.buildUrl(this.baseUrl, "sync"));
    }
}
