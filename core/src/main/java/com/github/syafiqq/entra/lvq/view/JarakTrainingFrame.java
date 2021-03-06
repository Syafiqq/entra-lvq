/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.syafiqq.entra.lvq.view;

import com.github.syafiqq.entra.lvq.observable.java.lang.OStringBuilder;
import java.util.Observer;
import javax.swing.event.InternalFrameEvent;
import javax.swing.text.DefaultCaret;

/**
 * @author Entra
 */
public class JarakTrainingFrame extends ClosableInternalFrame
{
    InteractionListener listener;
    private Observer logObserver;

    /**
     * Creates new form JarakTrainingFrame
     */
    public JarakTrainingFrame(InteractionListener listener)
    {
        this.listener = listener;
        initComponents();
        DefaultCaret caret = (DefaultCaret) this.jTextArea2.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        this.initializeDataObserver();
        this.addInternalFrameListener(new COpenClosableInternalFrameListener()
        {
            @Override public void internalFrameOpened(InternalFrameEvent e)
            {
                JarakTrainingFrame.this.listener.getJarakTrainingLog().addObserver(JarakTrainingFrame.this.logObserver);
                JarakTrainingFrame.this.callObserver();
            }

            @Override public void internalFrameClosed(InternalFrameEvent e)
            {
                JarakTrainingFrame.this.listener.getJarakTrainingLog().deleteObserver(JarakTrainingFrame.this.logObserver);
            }
        });
    }

    private void callObserver()
    {
        this.jTextArea2.setText(this.listener.getJarakTrainingLog().builder.toString());
    }

    private void initializeDataObserver()
    {
        this.logObserver = (o, arg) -> this.jTextArea2.setText(arg.toString());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();

        setClosable(true);
        setMaximizable(true);
        setTitle("Proses Perhitungan Jarak Data Latih (Training)");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                      .addGroup(layout.createSequentialGroup()
                                      .addContainerGap()
                                      .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
                                      .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                      .addGroup(layout.createSequentialGroup()
                                      .addContainerGap()
                                      .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                                      .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public interface InteractionListener
    {
        OStringBuilder getJarakTrainingLog();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}
