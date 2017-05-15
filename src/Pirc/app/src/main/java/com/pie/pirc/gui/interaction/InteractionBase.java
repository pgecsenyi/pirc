package com.pie.pirc.gui.interaction;

import com.pie.pirc.gui.interfaces.IAsyncOperationManager;
import com.pie.pirc.gui.interfaces.ICommunicatorProvider;
import com.pie.pirc.gui.interfaces.IFilterManager;
import com.pie.pirc.gui.interfaces.IFragmentManager;
import com.pie.pirc.gui.interfaces.ISettingsManager;

/**
 * Created by pgecsenyi on 2016.11.03..
 */
public class InteractionBase
{
    /***************************************************************************************************************//**
     * Protected fields.
     ******************************************************************************************************************/

    protected IAsyncOperationManager asyncOperationManager;

    protected ICommunicatorProvider communicatorProvider;

    protected IFilterManager filterManager;

    protected IFragmentManager fragmentManager;

    protected ISettingsManager settingsManager;

    /***************************************************************************************************************//**
     * Constructor.
     ******************************************************************************************************************/

    public InteractionBase(
        ICommunicatorProvider communicatorProvider,
        IAsyncOperationManager asyncOperationManager,
        IFilterManager filterManager,
        IFragmentManager fragmentManager,
        ISettingsManager settingsManager)
    {
        this.communicatorProvider = communicatorProvider;
        this.asyncOperationManager = asyncOperationManager;
        this.filterManager = filterManager;
        this.fragmentManager = fragmentManager;
        this.settingsManager = settingsManager;
    }
}
