/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.syafiqq.entra.lvq.view;

import com.github.syafiqq.entra.lvq.function.DebuggableLVQ1;
import com.github.syafiqq.entra.lvq.function.model.EuclideanWeightPojo;
import com.github.syafiqq.entra.lvq.function.model.ProcessedDatasetPojo;
import com.github.syafiqq.entra.lvq.function.model.ProcessedWeightPojo;
import com.github.syafiqq.entra.lvq.model.database.dao.DatasetDao;
import com.github.syafiqq.entra.lvq.model.database.dao.WeightDao;
import com.github.syafiqq.entra.lvq.model.database.pojo.DatasetPojo;
import com.github.syafiqq.entra.lvq.model.database.pojo.WeightPojo;
import com.github.syafiqq.entra.lvq.observable.java.lang.OStringBuilder;
import com.github.syafiqq.entra.lvq.observable.java.util.OList;
import com.github.syafiqq.entra.lvq.util.Settings;
import java.beans.PropertyVetoException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JInternalFrame;

/**
 * @author Entra
 */
public class FormUtama extends javax.swing.JFrame implements DatasetPenyakitMataFrame.InteractionListener, ProsesPelatihanLVQFrame.InteractionListener, ProsesPengujianLVQFrame.InteractionListener, JarakTrainingFrame.InteractionListener, BobotTrainingFrame.InteractionListener, JarakTestingFrame.InteractionListener, PengujianAlphaFrame.InteractionListener, PengujianDecAlphaFrame.InteractionListener, PengujianMinAlphaFrame.InteractionListener, PengujianIterasiMaxFrame.InteractionListener, PengujianDatasetFrame.InteractionListener
{

    private ClosableInternalFrame fDataset;
    private ClosableInternalFrame fTraining;
    private ClosableInternalFrame fTesting;
    private ClosableInternalFrame fJarakTraining;
    private ClosableInternalFrame fBobotTraining;
    private ClosableInternalFrame fJarakTesting;
    private ClosableInternalFrame fTestLearningRate;
    private ClosableInternalFrame fTestDecLearningRate;
    private ClosableInternalFrame fTestMinLearningRate;
    private ClosableInternalFrame fTestMaxEpoch;
    private ClosableInternalFrame fTestDataset;
    private OList<DatasetPojo> dataset;
    private OList<WeightPojo> weight;
    private OList<ProcessedDatasetPojo> oDataset;
    private OList<ProcessedWeightPojo<Double>> oWeight;
    public OStringBuilder trainingLog = new OStringBuilder(new StringBuilder());
    public OStringBuilder testingLog = new OStringBuilder(new StringBuilder());
    public OStringBuilder trainingCompleteDistanceLog = new OStringBuilder(new StringBuilder());
    public OStringBuilder trainingCompleteWeightLog = new OStringBuilder(new StringBuilder());
    public OStringBuilder testingCompleteDistanceLog = new OStringBuilder(new StringBuilder());
    private DebuggableLVQ1 lvq;

    /**
     * Creates new form FormUtama
     */
    public FormUtama()
    {
        this.dataset = new OList<>(new LinkedList<>(DatasetDao.getAll(Settings.DB)));
        this.weight = new OList<>(new LinkedList<>(WeightDao.getAll(Settings.DB)));
        this.oDataset = new OList<>(new LinkedList<>(this.dataset.lists.stream().map(ProcessedDatasetPojo::new).collect(Collectors.toList())));
        this.oWeight = new OList<>(new LinkedList<>(this.weight.lists.stream().map(EuclideanWeightPojo::new).collect(Collectors.toList())));
        this.lvq = new DebuggableLVQ1(0.5, 0.01, 1e-11, 5, this.oDataset.lists, this.oWeight.lists);
        this.addLVQListener();
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
    }

    private void addLVQListener()
    {
        this.lvq.postInitializeListener.add((learningRate, lrReduction, lrThreshold, maxIteration, weight, dataset) -> {
            trainingLog.append("\n==Begin Initialization Phase==\n");
            trainingLog.append(String.format("Learning Rate           = %f\n", learningRate));
            trainingLog.append(String.format("Learning Rate Reduction = %f\n", lrReduction));
            trainingLog.append(String.format("Learning Rate Threshold = %g\n", lrThreshold));
            trainingLog.append(String.format("Iteration               = %d\n", maxIteration));
            trainingLog.append("=Dataset=\n");
            dataset.forEach(d -> trainingLog.append(String.format("%s\n", d)));
            trainingLog.append("=Bobot=\n");
            weight.forEach(w -> trainingLog.append(String.format("%s\n", w)));
            trainingLog.append("==End Initialization Phase==\n");

            trainingCompleteDistanceLog.append("==Initialization Phase==\n");
            trainingCompleteWeightLog.append("==Initialization Phase==\n");
        });
        this.lvq.postSatisfactionListeners.add((epoch, maxEpoch, learningRate, maxLearningRate, result) -> {
            trainingLog.append(String.format("\n==Begin Epoch [%d] ==\n", epoch));
            trainingLog.append("===Begin Check Satisfaction===\n");
            trainingLog.append(String.format("Iteration %d of %d\n", epoch, maxEpoch));
            trainingLog.append(String.format("LearningRate %g of %g\n", learningRate, maxLearningRate));
            trainingLog.append(String.format("Result %s\n", result ? "Satisfied" : "Not Satisfied"));
            trainingLog.append("===End Check Satisfaction===\n");

            trainingCompleteDistanceLog.append(String.format("\n==Begin Epoch [%d] ==\n", epoch));
            trainingCompleteWeightLog.append(String.format("\n==Begin Epoch [%d] ==\n", epoch));
        });
        this.lvq.distanceCalculationListener.add(new DebuggableLVQ1.OnDistanceCalculationListener()
        {
            @Override public void preCalculated(ProcessedDatasetPojo data)
            {
                trainingLog.append("===Begin Calculate Distance===\n");

                trainingCompleteDistanceLog.append("===Begin Calculate Distance===\n");
                trainingCompleteWeightLog.append("===Process Calculate Distance===\n");
            }

            @Override public void calculated(ProcessedDatasetPojo data, ProcessedWeightPojo<Double> weight)
            {
                trainingLog.append(String.format("Distance [%d, %d] against [%d, %d] resulting %f \n", data.dataset.id, data.dataset.target, weight.weight.id, weight.weight.target, weight.getDistance()));

                trainingCompleteDistanceLog.append(String.format("Distance %s against %s resulting %f \n", data.toString(), weight.toString(), weight.getDistance()));
            }

            @Override public void postCalculated(ProcessedDatasetPojo data)
            {
                trainingLog.append("===End Calculate Distance===\n");

                trainingCompleteDistanceLog.append("===End Calculate Distance===\n");
            }
        });
        this.lvq.weightUpdateListeners.add(new DebuggableLVQ1.OnWeightUpdateListener()
        {
            @Override public void preUpdate(ProcessedDatasetPojo data, ProcessedWeightPojo<Double> weight, boolean sameSignature)
            {
                trainingLog.append("===Begin Update Weight===\n");
                trainingLog.append(String.format("Minimum weight distance is [%d, %d, %g] which are %s with data [%d %d] so the weight is move %s the data\n", weight.weight.id, weight.weight.target, weight.getDistance(), sameSignature ? "Same Class" : "Different Class", data.dataset.id, data.dataset.target, sameSignature ? "Closer to" : "Away from"));

                trainingCompleteDistanceLog.append("===Process Update Weight===\n");
                trainingCompleteWeightLog.append("===Begin Update Weight===\n");
                trainingCompleteWeightLog.append(String.format("Minimum weight distance is %s which are %s with data %s so the weight is move %s the data\n", weight.toString(), sameSignature ? "Same Class" : "Different Class", data.toString(), sameSignature ? "Closer to" : "Away from"));
            }

            @Override public void update(ProcessedWeightPojo<Double> weight)
            {
                trainingLog.append(String.format("Resulting Weight is : %s\n", weight.toString()));

                trainingCompleteWeightLog.append(String.format("Resulting Weight is : %s\n", weight.toString()));
            }

            @Override public void postUpdate(ProcessedWeightPojo<Double> weight)
            {
                trainingLog.append("===End Update Weight===\n");

                trainingCompleteWeightLog.append("===End Update Weight===\n");
            }
        });
        this.lvq.reducedLearningRateListener.add((lr, lrr, r) -> {
            trainingLog.append("===Begin Reduce Learning Rate===\n");
            trainingLog.append(String.format("Reduce Learning rate [%g] with learning rate reduction component [%f] resulting [%f]\n", lr, lrr, r));
            trainingLog.append("===End Reduce Learning Rate===\n");

            trainingCompleteDistanceLog.append("===Process Reduce Learning Rate===\n");
            trainingCompleteWeightLog.append("===Process Reduce Learning Rate===\n");
        });
        this.lvq.accuracyListeners.add((s, t, acc) -> {
            trainingLog.append("===Begin Calculate Accuracy===\n");
            trainingLog.append(String.format("The Correct Class is %d of %d resulting %f%%\n", s, t, acc));
            trainingLog.append("===End Calculate Accuracy===\n");

            trainingCompleteDistanceLog.append("===Process Calculate Accuracy===\n");
            trainingCompleteWeightLog.append("===Process Calculate Accuracy===\n");
        });
        this.lvq.satisfactionEvaluationListeners.add(e -> {
            trainingLog.append("===Begin Satisfaction Evaluation===\n");
            trainingLog.append(String.format("Update Epoch to %d\n", e));
            trainingLog.append("===End Satisfaction Evaluation===\n");
            trainingLog.append(String.format("==End Epoch [%d]==\n\n", e - 1));

            trainingCompleteDistanceLog.append("===Process Satisfaction Evaluation===\n");
            trainingCompleteDistanceLog.append(String.format("==End Epoch [%d]==\n\n", e - 1));
            trainingCompleteWeightLog.append("===Process Satisfaction Evaluation===\n");
            trainingCompleteWeightLog.append(String.format("==End Epoch [%d]==\n\n", e - 1));
        });


        this.lvq.testDistanceCalculationListener.add(new DebuggableLVQ1.OnDistanceCalculationListener()
        {
            @Override public void preCalculated(ProcessedDatasetPojo data)
            {
                testingLog.append("==Begin Testing==\n");
                testingLog.append("===Begin Calculate Distance===\n");

                testingCompleteDistanceLog.append("==Begin Testing==\n");
                testingCompleteDistanceLog.append("===Begin Calculate Distance===\n");
            }

            @Override public void calculated(ProcessedDatasetPojo data, ProcessedWeightPojo<Double> weight)
            {
                testingLog.append(String.format("Distance [%d, %d] against [%d, %d] resulting %f \n", data.dataset.id, data.dataset.target, weight.weight.id, weight.weight.target, weight.getDistance()));

                testingCompleteDistanceLog.append(String.format("Distance %s against %s resulting %f \n", data.toString(), weight.toString(), weight.getDistance()));
            }

            @Override public void postCalculated(ProcessedDatasetPojo data)
            {
                testingLog.append("===End Calculate Distance===\n");

                testingCompleteDistanceLog.append("===End Calculate Distance===\n");
            }
        });
        this.lvq.testWeightUpdateListeners.add(new DebuggableLVQ1.OnWeightUpdateListener()
        {
            @Override public void preUpdate(ProcessedDatasetPojo data, ProcessedWeightPojo<Double> weight, boolean sameSignature)
            {
                testingLog.append("===Begin Classify===\n");
                testingLog.append(String.format("The closest distance of [%d, %d] is [%d, %d] which are [%g] so the data can be classified as Class [%d]\n", data.dataset.id, data.dataset.target, weight.weight.id, weight.weight.target, weight.getDistance(), data.actualTarget));

                testingCompleteDistanceLog.append("===Process Classify===\n");
            }

            @Override public void update(ProcessedWeightPojo<Double> weight)
            {
            }

            @Override public void postUpdate(ProcessedWeightPojo<Double> weight)
            {
                testingLog.append("===End Classify===\n");
            }
        });
        this.lvq.testAccuracyListeners.add((s, t, acc) -> {
            testingLog.append("===Begin Calculate Accuracy===\n");
            testingLog.append(String.format("The Correct Class is %d of %d resulting %f%%\n", s, t, acc));
            testingLog.append("===End Calculate Accuracy===\n");
            testingLog.append("==End Testing==\n");

            testingCompleteDistanceLog.append("===Process Calculate Accuracy===\n");
            testingCompleteDistanceLog.append("==End Testing==\n");
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try
        {
            for(javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch(ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(FormUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch(InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(FormUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch(IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(FormUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch(javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(FormUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new FormUtama().setVisible(true);
            }
        });
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

        jDesktopPane1 = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenu = new javax.swing.JMenuItem();
        lvqMenu = new javax.swing.JMenu();
        datasetMenu = new javax.swing.JMenuItem();
        trainingMenu = new javax.swing.JMenuItem();
        testingMenu = new javax.swing.JMenuItem();
        viewMenu = new javax.swing.JMenu();
        jaraktrainingMenu = new javax.swing.JMenuItem();
        bobottrainingMenu = new javax.swing.JMenuItem();
        jaraktestingMenu = new javax.swing.JMenuItem();
        testMenu = new javax.swing.JMenu();
        testalphaMenu = new javax.swing.JMenuItem();
        testdecalphaMenu = new javax.swing.JMenuItem();
        testminalphaMenu = new javax.swing.JMenuItem();
        testmaxepohMenu = new javax.swing.JMenuItem();
        testdataMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
                jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                   .addGap(0, 586, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
                jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                   .addGap(0, 348, Short.MAX_VALUE)
        );

        fileMenu.setText("File");

        exitMenu.setText("Exit");
        exitMenu.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                exitMenuActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenu);

        jMenuBar1.add(fileMenu);

        lvqMenu.setText("Menu LVQ");

        datasetMenu.setText("Dataset Penyakit Mata");
        datasetMenu.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                datasetMenuActionPerformed(evt);
            }
        });
        lvqMenu.add(datasetMenu);

        trainingMenu.setText("Proses Pelatihan LVQ");
        trainingMenu.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                trainingMenuActionPerformed(evt);
            }
        });
        lvqMenu.add(trainingMenu);

        testingMenu.setText("Proses Pengujian LVQ");
        testingMenu.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                testingMenuActionPerformed(evt);
            }
        });
        lvqMenu.add(testingMenu);

        jMenuBar1.add(lvqMenu);

        viewMenu.setText("View");

        jaraktrainingMenu.setText("Proses Perhitungan Jarak Data Latih (Training)");
        jaraktrainingMenu.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jaraktrainingMenuActionPerformed(evt);
            }
        });
        viewMenu.add(jaraktrainingMenu);

        bobottrainingMenu.setText("Proses Perhitungan Bobot Data Latih (Training)");
        bobottrainingMenu.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bobottrainingMenuActionPerformed(evt);
            }
        });
        viewMenu.add(bobottrainingMenu);

        jaraktestingMenu.setText("Proses Perhitungan Jarak Data Uji (Testing)");
        jaraktestingMenu.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jaraktestingMenuActionPerformed(evt);
            }
        });
        viewMenu.add(jaraktestingMenu);

        jMenuBar1.add(viewMenu);

        testMenu.setText("Test Parameters");

        testalphaMenu.setText("Pengujian Learning Rate (α)");
        testalphaMenu.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                testalphaMenuActionPerformed(evt);
            }
        });
        testMenu.add(testalphaMenu);

        testdecalphaMenu.setText("Pengujian Pengurangan Learning Rate (Dec α)");
        testdecalphaMenu.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                testdecalphaMenuActionPerformed(evt);
            }
        });
        testMenu.add(testdecalphaMenu);

        testminalphaMenu.setText("Pengujian Minimal Learning Rate (Min α)");
        testminalphaMenu.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                testminalphaMenuActionPerformed(evt);
            }
        });
        testMenu.add(testminalphaMenu);

        testmaxepohMenu.setText("Pengujian Iterasi Maksimum (MaxEpoh)");
        testmaxepohMenu.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                testmaxepohMenuActionPerformed(evt);
            }
        });
        testMenu.add(testmaxepohMenu);

        testdataMenu.setText("Perbandingan Data Latih : Data Uji");
        testdataMenu.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                testdataMenuActionPerformed(evt);
            }
        });
        testMenu.add(testdataMenu);

        jMenuBar1.add(testMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                      .addComponent(jDesktopPane1)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                      .addComponent(jDesktopPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_exitMenuActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_exitMenuActionPerformed

    private void datasetMenuActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_datasetMenuActionPerformed
        // TODO add your handling code here:
        try
        {
            if(this.fDataset == null)
            {
                this.fDataset = new DatasetPenyakitMataFrame(this);
                this.jDesktopPane1.add(fDataset);
                this.fDataset.setMaximum(true);
                this.fDataset.addInternalFrameListener(e -> this.fDataset = null);
            }
            setTopAndActivate(this.fDataset);
        }
        catch(PropertyVetoException ex)
        {
            Logger.getLogger(FormUtama.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_datasetMenuActionPerformed

    private void trainingMenuActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_trainingMenuActionPerformed
        // TODO add your handling code here:
        try
        {
            if(this.fTraining == null)
            {
                this.fTraining = new ProsesPelatihanLVQFrame(this);
                this.jDesktopPane1.add(fTraining);
                this.fTraining.setMaximum(true);
                this.fTraining.addInternalFrameListener(e -> this.fTraining = null);
            }
            setTopAndActivate(this.fTraining);
        }
        catch(PropertyVetoException ex)
        {
            Logger.getLogger(FormUtama.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_trainingMenuActionPerformed

    private void testingMenuActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_testingMenuActionPerformed
        // TODO add your handling code here:
        try
        {
            if(this.fTesting == null)
            {
                this.fTesting = new ProsesPengujianLVQFrame(this);
                this.jDesktopPane1.add(fTesting);
                this.fTesting.setMaximum(true);
                this.fTesting.addInternalFrameListener(e -> this.fTesting = null);
            }
            setTopAndActivate(this.fTesting);
        }
        catch(PropertyVetoException ex)
        {
            Logger.getLogger(FormUtama.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_testingMenuActionPerformed

    private void jaraktrainingMenuActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jaraktrainingMenuActionPerformed
        // TODO add your handling code here:
        try
        {
            if(this.fJarakTraining == null)
            {
                this.fJarakTraining = new JarakTrainingFrame(this);
                this.jDesktopPane1.add(fJarakTraining);
                this.fJarakTraining.setMaximum(true);
                this.fJarakTraining.addInternalFrameListener(e -> this.fJarakTraining = null);
            }
            setTopAndActivate(this.fJarakTraining);
        }
        catch(PropertyVetoException ex)
        {
            Logger.getLogger(FormUtama.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jaraktrainingMenuActionPerformed

    private void bobottrainingMenuActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_bobottrainingMenuActionPerformed
        // TODO add your handling code here:
        try
        {
            if(this.fBobotTraining == null)
            {
                this.fBobotTraining = new BobotTrainingFrame(this);
                this.jDesktopPane1.add(fBobotTraining);
                this.fBobotTraining.setMaximum(true);
                this.fBobotTraining.addInternalFrameListener(e -> this.fBobotTraining = null);
            }
            setTopAndActivate(this.fBobotTraining);
        }
        catch(PropertyVetoException ex)
        {
            Logger.getLogger(FormUtama.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bobottrainingMenuActionPerformed

    private void jaraktestingMenuActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jaraktestingMenuActionPerformed
        // TODO add your handling code here:
        try
        {
            if(this.fJarakTesting == null)
            {
                this.fJarakTesting = new JarakTestingFrame(this);
                this.jDesktopPane1.add(fJarakTesting);
                this.fJarakTesting.setMaximum(true);
                this.fJarakTesting.addInternalFrameListener(e -> this.fJarakTesting = null);
            }
            setTopAndActivate(this.fJarakTesting);
        }
        catch(PropertyVetoException ex)
        {
            Logger.getLogger(FormUtama.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jaraktestingMenuActionPerformed

    private void testalphaMenuActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_testalphaMenuActionPerformed
        // TODO add your handling code here:
        try
        {
            if(this.fTestLearningRate == null)
            {
                this.fTestLearningRate = new PengujianAlphaFrame(this);
                this.jDesktopPane1.add(fTestLearningRate);
                this.fTestLearningRate.setMaximum(true);
                this.fTestLearningRate.addInternalFrameListener(e -> this.fTestLearningRate = null);
            }
            setTopAndActivate(this.fTestLearningRate);
        }
        catch(PropertyVetoException ex)
        {
            Logger.getLogger(FormUtama.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_testalphaMenuActionPerformed

    private void testdecalphaMenuActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_testdecalphaMenuActionPerformed
        // TODO add your handling code here:
        try
        {
            if(this.fTestDecLearningRate == null)
            {
                this.fTestDecLearningRate = new PengujianDecAlphaFrame(this);
                this.jDesktopPane1.add(fTestDecLearningRate);
                this.fTestDecLearningRate.setMaximum(true);
                this.fTestDecLearningRate.addInternalFrameListener(e -> this.fTestDecLearningRate = null);
            }
            setTopAndActivate(this.fTestDecLearningRate);
        }
        catch(PropertyVetoException ex)
        {
            Logger.getLogger(FormUtama.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_testdecalphaMenuActionPerformed

    private void testminalphaMenuActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_testminalphaMenuActionPerformed
        // TODO add your handling code here:
        try
        {
            if(this.fTestMinLearningRate == null)
            {
                this.fTestMinLearningRate = new PengujianMinAlphaFrame(this);
                this.jDesktopPane1.add(fTestMinLearningRate);
                this.fTestMinLearningRate.setMaximum(true);
                this.fTestMinLearningRate.addInternalFrameListener(e -> this.fTestMinLearningRate = null);
            }
            setTopAndActivate(this.fTestMinLearningRate);
        }
        catch(PropertyVetoException ex)
        {
            Logger.getLogger(FormUtama.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_testminalphaMenuActionPerformed

    private void testmaxepohMenuActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_testmaxepohMenuActionPerformed
        // TODO add your handling code here:
        try
        {
            if(this.fTestMaxEpoch == null)
            {
                this.fTestMaxEpoch = new PengujianIterasiMaxFrame(this);
                this.jDesktopPane1.add(fTestMaxEpoch);
                this.fTestMaxEpoch.setMaximum(true);
                this.fTestMaxEpoch.addInternalFrameListener(e -> this.fTestMaxEpoch = null);
            }
            setTopAndActivate(this.fTestMaxEpoch);
        }
        catch(PropertyVetoException ex)
        {
            Logger.getLogger(FormUtama.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_testmaxepohMenuActionPerformed

    private void testdataMenuActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_testdataMenuActionPerformed
        // TODO add your handling code here:
        try
        {
            if(this.fTestDataset == null)
            {
                this.fTestDataset = new PengujianDatasetFrame(this);
                this.jDesktopPane1.add(fTestDataset);
                this.fTestDataset.setMaximum(true);
                this.fTestDataset.addInternalFrameListener(e -> this.fTestDataset = null);
            }
            setTopAndActivate(this.fTestDataset);
        }
        catch(PropertyVetoException ex)
        {
            Logger.getLogger(FormUtama.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_testdataMenuActionPerformed

    @Override public List<DatasetPojo> getDataset()
    {
        return this.dataset.lists;
    }

    @Override public void refreshDataset(List<DatasetPojo> all)
    {
        this.dataset.refresh(all);
    }

    @Override public DebuggableLVQ1 getLVQ()
    {
        return this.lvq;
    }

    @Override public OStringBuilder getOTestingLog()
    {
        return this.testingLog;
    }

    @Override public List<WeightPojo> getWeight()
    {
        return this.weight.lists;
    }

    @Override public void beginTraining()
    {
        this.trainingLog.setLength(0);
        this.testingLog.setLength(0);
        new Thread(lvq::trainingAndTesting).start();
    }

    @Override public OStringBuilder getOTrainingLog()
    {
        return trainingLog;
    }

    @Override public OStringBuilder getJarakTrainingLog()
    {
        return this.trainingCompleteDistanceLog;
    }

    @Override public OStringBuilder getBobotTrainingLog()
    {
        return trainingCompleteWeightLog;
    }

    @Override public OStringBuilder getJarakTesting()
    {
        return this.testingCompleteDistanceLog;
    }

    private void setTopAndActivate(JInternalFrame frame) throws PropertyVetoException
    {
        Arrays.stream(this.jDesktopPane1.getAllFrames()).filter(t -> t != frame).forEach(JInternalFrame::toBack);
        frame.toFront();
        frame.show();
        frame.requestFocus();
        frame.setSelected(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem bobottrainingMenu;
    private javax.swing.JMenuItem datasetMenu;
    private javax.swing.JMenuItem exitMenu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jaraktestingMenu;
    private javax.swing.JMenuItem jaraktrainingMenu;
    private javax.swing.JMenu lvqMenu;
    private javax.swing.JMenu testMenu;
    private javax.swing.JMenuItem testalphaMenu;
    private javax.swing.JMenuItem testdataMenu;
    private javax.swing.JMenuItem testdecalphaMenu;
    private javax.swing.JMenuItem testingMenu;
    private javax.swing.JMenuItem testmaxepohMenu;
    private javax.swing.JMenuItem testminalphaMenu;
    private javax.swing.JMenuItem trainingMenu;
    private javax.swing.JMenu viewMenu;
    // End of variables declaration//GEN-END:variables
}
