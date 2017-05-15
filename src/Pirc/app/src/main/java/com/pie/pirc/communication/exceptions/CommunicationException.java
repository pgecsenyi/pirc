package com.pie.pirc.communication.exceptions;

import com.pie.pirc.WrapperException;

/**
 * Represents exceptions produced by the network protocol towards higher level layers.
 *
 * Created by pgecsenyi on 2015.11.14..
 */
public class CommunicationException extends WrapperException
{
    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public CommunicationException(String message, Exception innerException)
    {
        super(message, innerException);
    }
}
