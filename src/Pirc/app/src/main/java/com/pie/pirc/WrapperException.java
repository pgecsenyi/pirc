package com.pie.pirc;

/**
 * An exception that can contain an inner exception.
 *
 * Created by pgecsenyi on 2015.11.14..
 */
public class WrapperException extends Exception
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private Exception innerException;

    /***************************************************************************************************************//**
     * Constructors.
     ******************************************************************************************************************/

    public WrapperException()
    {
    }

    public WrapperException(String message)
    {
        super(message);
    }

    public WrapperException(String message, Exception innerException)
    {
        this(message);
        this.innerException = innerException;
    }

    /***************************************************************************************************************//**
     * Getters.
     ******************************************************************************************************************/

    public Exception getInnerException()
{
    return innerException;
}
}
