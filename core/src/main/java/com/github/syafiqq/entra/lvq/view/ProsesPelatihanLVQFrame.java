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
import com.github.syafiqq.entra.lvq.model.database.pojo.DatasetPojo;
import com.github.syafiqq.entra.lvq.model.database.pojo.WeightPojo;
import com.github.syafiqq.entra.lvq.observable.java.lang.OStringBuilder;
import com.github.syafiqq.entra.lvq.util.Settings;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;

/**
 * @author Entra
 */
public class ProsesPelatihanLVQFrame extends ClosableInternalFrame
{
    InteractionListener listener;
    private Observer logObserver;
    private DebuggableLVQ1.OnWeightUpdateListener weightObserver;
    private DebuggableLVQ1.OnDistanceCalculationListener datasetObserver;
    private DebuggableLVQ1.OnPostCalculateAccuracyListener accuracyObserver;
    private DebuggableLVQ1.OnTrainingListener trainObserver;

    /**
     * Creates new form ProsesPelatihanLVQFrame
     */

    public ProsesPelatihanLVQFrame(InteractionListener listener)
    {
        this.listener = listener;
        initComponents();
        DefaultCaret caret = (DefaultCaret) this.jTextArea1.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        this.initializeDataObserver();
        this.initTable();
        this.initializeLVQ(this.listener.getLVQ());
        this.addInternalFrameListener(new COpenClosableInternalFrameListener()
        {
            @Override public void internalFrameOpened(InternalFrameEvent e)
            {
                ProsesPelatihanLVQFrame.this.listener.getOTrainingLog().addObserver(ProsesPelatihanLVQFrame.this.logObserver);
                ProsesPelatihanLVQFrame.this.listener.getLVQ().weightUpdateListeners.add(ProsesPelatihanLVQFrame.this.weightObserver);
                ProsesPelatihanLVQFrame.this.listener.getLVQ().distanceCalculationListener.add(ProsesPelatihanLVQFrame.this.datasetObserver);
                ProsesPelatihanLVQFrame.this.listener.getLVQ().accuracyListeners.add(ProsesPelatihanLVQFrame.this.accuracyObserver);
                ProsesPelatihanLVQFrame.this.listener.getLVQ().trainingListeners.add(ProsesPelatihanLVQFrame.this.trainObserver);
                ProsesPelatihanLVQFrame.this.callObserver();
            }

            @Override public void internalFrameClosed(InternalFrameEvent e)
            {
                ProsesPelatihanLVQFrame.this.listener.getOTrainingLog().deleteObserver(ProsesPelatihanLVQFrame.this.logObserver);
                ProsesPelatihanLVQFrame.this.listener.getLVQ().weightUpdateListeners.remove(ProsesPelatihanLVQFrame.this.weightObserver);
                ProsesPelatihanLVQFrame.this.listener.getLVQ().distanceCalculationListener.remove(ProsesPelatihanLVQFrame.this.datasetObserver);
                ProsesPelatihanLVQFrame.this.listener.getLVQ().accuracyListeners.remove(ProsesPelatihanLVQFrame.this.accuracyObserver);
                ProsesPelatihanLVQFrame.this.listener.getLVQ().trainingListeners.remove(ProsesPelatihanLVQFrame.this.trainObserver);
            }
        });
    }

    private void callObserver()
    {
        this.jTextArea1.setText(this.listener.getOTrainingLog().builder.toString());
        this.repopulateTable(this.listener.getLVQ().getDataset(), this.listener.getLVQ().getWeight());
        this.jTextField1.setText(String.format("%f", this.listener.getLVQ().calculateAccuracy(this.listener.getLVQ().getDataset())));
    }

    private void initializeDataObserver()
    {
        this.logObserver = (o, arg) -> this.jTextArea1.setText(arg.toString());
        this.weightObserver = new DebuggableLVQ1.OnWeightUpdateListener()
        {
            @Override public void preUpdate(ProcessedDatasetPojo data, ProcessedWeightPojo<Double> weight, boolean sameSignature)
            {

            }

            @Override public void update(ProcessedWeightPojo<Double> weight)
            {
                final DefaultTableModel weightTable = (DefaultTableModel) ProsesPelatihanLVQFrame.this.bobotawalTable.getModel();
                int index = ProsesPelatihanLVQFrame.this.listener.getLVQ().getWeight().indexOf(weight);
                int i = 0;
                int c = 0;
                for(String idx : Settings.columns)
                {
                    weightTable.setValueAt(weight.vector(idx), index, ++c);
                }
                ++i;
                weightTable.fireTableDataChanged();
            }

            @Override public void postUpdate(ProcessedWeightPojo<Double> weight)
            {

            }
        };
        this.datasetObserver = new DebuggableLVQ1.OnDistanceCalculationListener()
        {

            @Override public void preCalculated(ProcessedDatasetPojo data)
            {

            }

            @Override public void calculated(ProcessedDatasetPojo data, ProcessedWeightPojo<Double> weight)
            {

            }

            @Override public void postCalculated(ProcessedDatasetPojo data)
            {
                final DefaultTableModel datasetTable = (DefaultTableModel) ProsesPelatihanLVQFrame.this.datalatihTable.getModel();
                int index = ProsesPelatihanLVQFrame.this.listener.getLVQ().getDataset().indexOf(data);
                int i = 0;
                int c = 22;
                datasetTable.setValueAt(data.actualTarget, index, ++c);
                ++i;
                datasetTable.fireTableDataChanged();
            }
        };
        this.accuracyObserver = (same, size, accuracy) -> this.jTextField1.setText(String.format("%d of %d [%g%%]", same, size, accuracy));
        this.trainObserver = this::repopulateTable;
    }

    private void repopulateTable(List<ProcessedDatasetPojo> train, List<ProcessedWeightPojo<Double>> weight)
    {
        final DefaultTableModel datasetTable = (DefaultTableModel) this.datalatihTable.getModel();
        datasetTable.setRowCount(0);
        train.forEach(dt -> {
            Object[] data = new Object[24];
            int i = 0;
            int c = -1;
            data[++c] = dt.dataset.id;
            for(String idx : Settings.columns)
            {
                data[++c] = dt.vector(idx);
            }
            data[++c] = dt.dataset.target;
            data[++c] = dt.actualTarget;
            ++i;
            datasetTable.addRow(data);
        });
        final DefaultTableModel weightTable = (DefaultTableModel) this.bobotawalTable.getModel();
        weightTable.setRowCount(0);
        weight.forEach(dt -> {
            Object[] data = new Object[23];
            int i = 0;
            int c = -1;
            data[++c] = dt.weight.id;
            for(String idx : Settings.columns)
            {
                data[++c] = dt.vector(idx);
            }
            data[++c] = dt.weight.target;
            ++i;
            weightTable.addRow(data);
        });
        weightTable.fireTableDataChanged();
    }

    private void initTable()
    {
        this.datalatihTable.setModel(new DefaultTableModel(null, new String[] {"No", "Sakit/Nyeri kepala hebat", "Penglihatan kabur perlahan", "Silau", "Merah", "Nyeri", "Perut mual", "Penglihatan berkabut(berasap)", "Lensa mata keruh", "Gatal", "Berair", "Belekan", "Kelopak bengkak", "Panas", "Mengganjal", "Lengket", "Merah jika terkena sinar matahari", "Tumbuh selaput pada mata", "Timbul bayangan", "Usia > 50 tahun", "Kelopak mata timbul benjolan", "Perih", "Target", "Aktual-Target"}));
        this.datalatihTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.jScrollPane2.setViewportView(this.datalatihTable);
        this.bobotawalTable.setModel(new DefaultTableModel(null, new String[] {"No", "Sakit/Nyeri kepala hebat", "Penglihatan kabur perlahan", "Silau", "Merah", "Nyeri", "Perut mual", "Penglihatan berkabut(berasap)", "Lensa mata keruh", "Gatal", "Berair", "Belekan", "Kelopak bengkak", "Panas", "Mengganjal", "Lengket", "Merah jika terkena sinar matahari", "Tumbuh selaput pada mata", "Timbul bayangan", "Usia > 50 tahun", "Kelopak mata timbul benjolan", "Perih", "Target"}));
        this.bobotawalTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.jScrollPane1.setViewportView(this.bobotawalTable);
    }

    private void initializeLVQ(DebuggableLVQ1 lvq)
    {
        this.alphaTextField.setText(String.format("%f", lvq.learningRate));
        this.decalphaTextField.setText(String.format("%f", lvq.lrReduction));
        this.minalphaTextField.setText(String.format("%g", lvq.lrThreshold));
        this.maxepohTextField.setText(String.format("%d", lvq.maxIteration));
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        datalatihTextField = new javax.swing.JTextField();
        alphaTextField = new javax.swing.JTextField();
        decalphaTextField = new javax.swing.JTextField();
        minalphaTextField = new javax.swing.JTextField();
        maxepohTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        prosesButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        RadioButton_urut = new javax.swing.JRadioButton();
        RadioButton_acak_dataset = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        RadioButton_disesuaikan = new javax.swing.JRadioButton();
        RadioButton_acak_bobot = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        bobotawalTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        datalatihTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        setClosable(true);
        setMaximizable(true);
        setTitle("Proses Pelatihan (Training) dengan Learning Vector Quantization (LVQ)");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Proses Training LVQ"));

        jLabel1.setText("Data Latih (%)");

        jLabel2.setText("Learning Rate (α)");

        jLabel3.setText("Pengurangan Learning Rate (Dec α)");

        jLabel4.setText("Minimal Learning Rate (Min α)");

        jLabel5.setText("Iterasi Maksimum (MaxEpoh)");

        prosesButton.setText("Proses");
        prosesButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                prosesButtonActionPerformed(evt);
            }
        });

        resetButton.setText("Ulang Proses");

        jLabel7.setText("Dataset");

        buttonGroup1.add(RadioButton_urut);
        RadioButton_urut.setText("Urut");
        RadioButton_urut.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                RadioButton_urutActionPerformed(evt);
            }
        });

        buttonGroup1.add(RadioButton_acak_dataset);
        RadioButton_acak_dataset.setText("Acak");

        jLabel8.setText("Bobot");

        buttonGroup2.add(RadioButton_disesuaikan);
        RadioButton_disesuaikan.setText("Disesuaikan");

        buttonGroup2.add(RadioButton_acak_bobot);
        RadioButton_acak_bobot.setText("Acak");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                             .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                           .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                                  .addComponent(prosesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                  .addGap(39, 39, 39)
                                                                                                  .addComponent(resetButton)
                                                                                                  .addGap(20, 20, 20))
                                                                           .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                                  .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                         .addComponent(jLabel1)
                                                                                                                         .addComponent(jLabel2)
                                                                                                                         .addComponent(jLabel3)
                                                                                                                         .addComponent(jLabel4)
                                                                                                                         .addComponent(jLabel5))
                                                                                                  .addGap(27, 27, 27)
                                                                                                  .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                                                                         .addComponent(minalphaTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                                                                                                                         .addComponent(decalphaTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                         .addComponent(alphaTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                         .addComponent(datalatihTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                         .addComponent(maxepohTextField))
                                                                                                  .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                         .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                       .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                                                                                                                              .addGap(31, 31, 31)
                                                                                                                                                                                              .addComponent(RadioButton_urut))
                                                                                                                                                                       .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                                                                                                                              .addGap(22, 22, 22)
                                                                                                                                                                                              .addComponent(RadioButton_disesuaikan)
                                                                                                                                                                                              .addGap(18, 18, 18)
                                                                                                                                                                                              .addComponent(RadioButton_acak_bobot)))
                                                                                                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                                                                                         .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                                                                                                                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                                                                                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                                                                   .addComponent(jLabel8)
                                                                                                                                                                                                                   .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                                                                                          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                                                                                                                                                                                                                                                                             .addComponent(jLabel7)
                                                                                                                                                                                                                                                                                                             .addGap(68, 68, 68))
                                                                                                                                                                                                                                          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                                                                                                                                                                                                                                                                             .addComponent(RadioButton_acak_dataset)
                                                                                                                                                                                                                                                                                                             .addGap(20, 20, 20)))))))))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                             .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                           .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                                                                                                              .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                                                     .addComponent(datalatihTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                     .addComponent(jLabel1))
                                                                                                                                              .addGap(11, 11, 11))
                                                                           .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                                                                                                              .addComponent(jLabel7)
                                                                                                                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                           .addComponent(alphaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                           .addComponent(jLabel2)
                                                                           .addComponent(RadioButton_urut)
                                                                           .addComponent(RadioButton_acak_dataset))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                           .addComponent(decalphaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                           .addComponent(jLabel3))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                           .addComponent(minalphaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                           .addComponent(jLabel4)
                                                                           .addComponent(jLabel8))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                           .addComponent(maxepohTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                           .addComponent(jLabel5)
                                                                           .addComponent(RadioButton_disesuaikan)
                                                                           .addComponent(RadioButton_acak_bobot))
                                                    .addGap(18, 18, 18)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                           .addComponent(prosesButton)
                                                                           .addComponent(resetButton))
                                                    .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabel Bobot Awal"));

        bobotawalTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String[] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        jScrollPane1.setViewportView(bobotawalTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                             .addGroup(jPanel2Layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                    .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                             .addGroup(jPanel2Layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                    .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabel Data Latih"));

        datalatihTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String[] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        jScrollPane2.setViewportView(datalatihTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                             .addGroup(jPanel3Layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(jScrollPane2)
                                                    .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                             .addGroup(jPanel3Layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                    .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Hasil Proses Pelatihan LVQ"));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        jLabel6.setText("Jumlah Prediksi Kelas yang Sesuai : ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                             .addGroup(jPanel4Layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                           .addComponent(jScrollPane3)
                                                                           .addGroup(jPanel4Layout.createSequentialGroup()
                                                                                                  .addComponent(jLabel6)
                                                                                                  .addGap(18, 18, 18)
                                                                                                  .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                  .addGap(0, 137, Short.MAX_VALUE)))
                                                    .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                             .addGroup(jPanel4Layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                           .addComponent(jLabel6)
                                                                           .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                      .addGroup(layout.createSequentialGroup()
                                      .addContainerGap()
                                      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                      .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                      .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                      .addGap(18, 18, 18)
                                      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                      .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                      .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                      .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                      .addGroup(layout.createSequentialGroup()
                                      .addContainerGap()
                                      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                      .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                      .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                      .addGap(18, 18, 18)
                                      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                      .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                      .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                      .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void RadioButton_urutActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_RadioButton_urutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RadioButton_urutActionPerformed

    private void prosesButtonActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_prosesButtonActionPerformed
        int train = 0;
        double learningRate = 0;
        double decLR = 0;
        double minLR = 0;
        int epoch = 0;
        try
        {
            train = Integer.parseInt(this.datalatihTextField.getText());
        }
        catch(NumberFormatException ignored)
        {
        }
        try
        {
            learningRate = Double.parseDouble(this.alphaTextField.getText());
        }
        catch(NumberFormatException ignored)
        {
        }
        try
        {
            decLR = Double.parseDouble(this.decalphaTextField.getText());
        }
        catch(NumberFormatException ignored)
        {
        }
        try
        {
            minLR = Double.parseDouble(this.minalphaTextField.getText());
        }
        catch(NumberFormatException ignored)
        {
        }
        try
        {
            epoch = Integer.parseInt(this.maxepohTextField.getText());
        }
        catch(NumberFormatException ignored)
        {
        }


        train = (int) (train / 100.0 * this.listener.getDataset().size());

        if(train <= 9)
        {
            JOptionPane.showMessageDialog(this, "Field Data Latih tidak Valid atau kurang besar", "Perhatian", JOptionPane.INFORMATION_MESSAGE, null);
        }
        else if(learningRate <= 0.0)
        {
            JOptionPane.showMessageDialog(this, "Field Learning Rate tidak Valid", "Perhatian", JOptionPane.INFORMATION_MESSAGE, null);
        }
        else if(decLR <= 0.0)
        {
            JOptionPane.showMessageDialog(this, "Field Learning Reduction tidak Valid", "Perhatian", JOptionPane.INFORMATION_MESSAGE, null);
        }
        else if(minLR <= 0.0)
        {
            JOptionPane.showMessageDialog(this, "Field Learning Rate Treshold tidak Valid", "Perhatian", JOptionPane.INFORMATION_MESSAGE, null);
        }
        else if(epoch <= 0)
        {
            JOptionPane.showMessageDialog(this, "Field Epoch tidak Valid", "Perhatian", JOptionPane.INFORMATION_MESSAGE, null);
        }
        else if(buttonGroup1.getSelection() == null)
        {
            JOptionPane.showMessageDialog(this, "Tipe Dataset Harus dipilih", "Perhatian", JOptionPane.INFORMATION_MESSAGE, null);
        }
        else if(buttonGroup2.getSelection() == null)
        {
            JOptionPane.showMessageDialog(this, "Tipe Bobot Harus dipilih", "Perhatian", JOptionPane.INFORMATION_MESSAGE, null);
        }
        else
        {
            this.doProses(train, learningRate, decLR, minLR, epoch, RadioButton_urut.isSelected(), RadioButton_disesuaikan.isSelected());
        }
    }//GEN-LAST:event_prosesButtonActionPerformed

    private void doProses(int train, double learningRate, double decLR, double minLR, int epoch, boolean urut, boolean disesuaikan)
    {
        final List<DatasetPojo> dataset = this.listener.getDataset();
        final Map<Integer, List<DatasetPojo>> groupedDataset = dataset.stream().collect(Collectors.groupingBy(DatasetPojo::getTarget));

        // Generating Dataset
        if(urut)
        {
            groupedDataset.values().forEach(d -> d.sort(Comparator.comparingInt(o -> o.target + (o.id == null ? 0 : o.id))));
        }
        else
        {
            groupedDataset.values().forEach(Collections::shuffle);
        }
        final List<DatasetPojo> selectedDataset = new LinkedList<>();
        int base = -1;
        while(train > 0)
        {
            ++base;
            for(List<DatasetPojo> l : groupedDataset.values())
            {
                if(--train >= 0)
                {
                    selectedDataset.add(l.get(base));
                }
            }
        }
        selectedDataset.sort(Comparator.comparingInt(o -> o.target + (o.id == null ? 0 : o.id)));

        //Generating Weight
        final List<WeightPojo> weight = this.listener.getWeight();
        final Map<Integer, List<WeightPojo>> groupedWeight = weight.stream().collect(Collectors.groupingBy(WeightPojo::getTarget));
        if(!disesuaikan)
        {
            groupedDataset.values().forEach(Collections::shuffle);
        }
        final List<WeightPojo> selectedWeight = new LinkedList<>();
        if(disesuaikan)
        {
            selectedWeight.addAll(weight);
        }
        else
        {
            train = 9;
            while(train > 0)
            {
                for(List<DatasetPojo> l : groupedDataset.values())
                {
                    if(--train >= 0)
                    {
                        final DatasetPojo selection = l.get(0);
                        WeightPojo w = new WeightPojo();
                        w.id = selection.id;
                        w.target = selection.target;
                        selection.vector.forEach(w.vector::put);
                        selectedWeight.add(w);
                    }
                }
            }
            selectedWeight.sort(Comparator.comparingInt(o -> o.target + (o.id == null ? 0 : o.id)));
        }

        List<DatasetPojo> selectedTesting = dataset.stream().filter(d -> !selectedDataset.contains(d)).collect(Collectors.toList());
        final DebuggableLVQ1 lvq = this.listener.getLVQ();
        lvq.setSetting(learningRate, decLR, minLR, epoch);
        lvq.setData(selectedDataset.stream().map(ProcessedDatasetPojo::new).collect(Collectors.toList()), selectedWeight.stream().map(EuclideanWeightPojo::new).collect(Collectors.toList()), selectedTesting.stream().map(ProcessedDatasetPojo::new).collect(Collectors.toList()));
        this.listener.beginTraining();
    }


    public interface InteractionListener
    {
        List<DatasetPojo> getDataset();

        void refreshDataset(List<DatasetPojo> all);

        DebuggableLVQ1 getLVQ();

        List<WeightPojo> getWeight();

        void beginTraining();

        OStringBuilder getOTrainingLog();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton RadioButton_acak_bobot;
    private javax.swing.JRadioButton RadioButton_acak_dataset;
    private javax.swing.JRadioButton RadioButton_disesuaikan;
    private javax.swing.JRadioButton RadioButton_urut;
    private javax.swing.JTextField alphaTextField;
    private javax.swing.JTable bobotawalTable;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JTable datalatihTable;
    private javax.swing.JTextField datalatihTextField;
    private javax.swing.JTextField decalphaTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField maxepohTextField;
    private javax.swing.JTextField minalphaTextField;
    private javax.swing.JButton prosesButton;
    private javax.swing.JButton resetButton;
    // End of variables declaration//GEN-END:variables
}
