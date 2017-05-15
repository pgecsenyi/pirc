package com.pie.pirc.async.results;

import java.util.LinkedHashMap;

import com.pie.pirc.async.framework.AsyncOperationResultAdapter;

/**
 * Stores a simple list of key-value pairs.
 *
 * Created by pgecsenyi on 2015.04.30..
 */
public class SimpleListResult extends AsyncOperationResultAdapter
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private LinkedHashMap<String, String> content;

    private int id;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public SimpleListResult(int id, LinkedHashMap<String, String> content)
    {
        this.content = content;
        this.id = id;
    }

    /***************************************************************************************************************//**
     * Overridden AsyncOperationResultAdapter methods.
     ******************************************************************************************************************/

    @Override
    public Object getContent()
    {
        return content;
    }

    @Override
    public int getId()
    {
        return id;
    }
}
