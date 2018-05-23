package com.github.syafiqq.entra.lvq.view;

import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

interface CClosableInternalFrameListener extends InternalFrameListener
{
    default @Override void internalFrameOpened(InternalFrameEvent e)
    {

    }

    default @Override void internalFrameClosing(InternalFrameEvent e)
    {

    }

    default @Override void internalFrameIconified(InternalFrameEvent e)
    {

    }

    default @Override void internalFrameDeiconified(InternalFrameEvent e)
    {

    }

    default @Override void internalFrameActivated(InternalFrameEvent e)
    {

    }

    default @Override void internalFrameDeactivated(InternalFrameEvent e)
    {

    }
}

/**
 * This entra-lvq project created by :
 * Name         : IT Dev
 * Date / Time  : 23 Mei 2018, 12:28.
 * Email        : jasuindo.co.id
 */
public class ClosableInternalFrame extends javax.swing.JInternalFrame
{
    public void addInternalFrameListener(CClosableInternalFrameListener l)
    {
        super.addInternalFrameListener(l);
    }
}
