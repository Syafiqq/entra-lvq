/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.syafiqq.entra.lvq.view;

import com.github.syafiqq.entra.lvq.function.DebuggableLVQ1;
import com.github.syafiqq.entra.lvq.function.model.ProcessedDatasetPojo;
import com.github.syafiqq.entra.lvq.function.model.ProcessedWeightPojo;
import com.github.syafiqq.entra.lvq.observable.java.lang.OStringBuilder;
import com.github.syafiqq.entra.lvq.util.Settings;
import java.util.List;
import java.util.Observer;
import javax.swing.JTable;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;

/**
 * @author Entra
 */
public class ProsesPengujianLVQFrame extends ClosableInternalFrame
{
    InteractionListener listener;
    private Observer logObserver;
    private DebuggableLVQ1.OnDistanceCalculationListener datasetObserver;
    private DebuggableLVQ1.OnPostCalculateAccuracyListener accuracyObserver;
    private DebuggableLVQ1.OnTestingListener testObserver;


    /**
     * Creates new form ProsesPengujianLVQFrame
     */
    public ProsesPengujianLVQFrame(InteractionListener listener)
    {
        this.listener = listener;
        initComponents();
        DefaultCaret caret = (DefaultCaret) this.testinglvqTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        this.initializeDataObserver();
        this.initTable();
        this.addInternalFrameListener(new COpenClosableInternalFrameListener()
        {
            @Override public void internalFrameOpened(InternalFrameEvent e)
            {
                ProsesPengujianLVQFrame.this.listener.getOTestingLog().addObserver(ProsesPengujianLVQFrame.this.logObserver);
                ProsesPengujianLVQFrame.this.listener.getLVQ().testDistanceCalculationListener.add(ProsesPengujianLVQFrame.this.datasetObserver);
                ProsesPengujianLVQFrame.this.listener.getLVQ().testAccuracyListeners.add(ProsesPengujianLVQFrame.this.accuracyObserver);
                ProsesPengujianLVQFrame.this.listener.getLVQ().testingListeners.add(ProsesPengujianLVQFrame.this.testObserver);
                ProsesPengujianLVQFrame.this.callObserver();
            }

            @Override public void internalFrameClosed(InternalFrameEvent e)
            {
                ProsesPengujianLVQFrame.this.listener.getOTestingLog().deleteObserver(ProsesPengujianLVQFrame.this.logObserver);
                ProsesPengujianLVQFrame.this.listener.getLVQ().testDistanceCalculationListener.remove(ProsesPengujianLVQFrame.this.datasetObserver);
                ProsesPengujianLVQFrame.this.listener.getLVQ().testAccuracyListeners.remove(ProsesPengujianLVQFrame.this.accuracyObserver);
                ProsesPengujianLVQFrame.this.listener.getLVQ().testingListeners.remove(ProsesPengujianLVQFrame.this.testObserver);
            }
        });
    }

    private void callObserver()
    {
        this.testinglvqTextArea.setText(this.listener.getOTestingLog().builder.toString());
        this.repopulateTable(this.listener.getLVQ().getTesting(), this.listener.getLVQ().getWeight());
        this.jTextField2.setText(String.format("%f", this.listener.getLVQ().calculateAccuracy(this.listener.getLVQ().getTesting())));
        this.jTextField1.setText(String.format("%d", this.listener.getLVQ().getTesting().stream().filter(ProcessedDatasetPojo::isSameClass).count()));
    }

    private void repopulateTable(List<ProcessedDatasetPojo> testing, List<ProcessedWeightPojo<Double>> weight)
    {
        final DefaultTableModel datasetTable = (DefaultTableModel) this.dataujiTable.getModel();
        datasetTable.setRowCount(0);
        testing.forEach(dt -> {
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
        datasetTable.fireTableDataChanged();
        final DefaultTableModel weightTable = (DefaultTableModel) this.bobotakhirTable.getModel();
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
        this.dataujiTable.setModel(new DefaultTableModel(null, new String[] {"No", "Sakit/Nyeri kepala hebat", "Penglihatan kabur perlahan", "Silau", "Merah", "Nyeri", "Perut mual", "Penglihatan berkabut(berasap)", "Lensa mata keruh", "Gatal", "Berair", "Belekan", "Kelopak bengkak", "Panas", "Mengganjal", "Lengket", "Merah jika terkena sinar matahari", "Tumbuh selaput pada mata", "Timbul bayangan", "Usia > 50 tahun", "Kelopak mata timbul benjolan", "Perih", "Target", "Aktual-Target"}));
        this.dataujiTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.jScrollPane1.setViewportView(this.dataujiTable);
        this.bobotakhirTable.setModel(new DefaultTableModel(null, new String[] {"No", "Sakit/Nyeri kepala hebat", "Penglihatan kabur perlahan", "Silau", "Merah", "Nyeri", "Perut mual", "Penglihatan berkabut(berasap)", "Lensa mata keruh", "Gatal", "Berair", "Belekan", "Kelopak bengkak", "Panas", "Mengganjal", "Lengket", "Merah jika terkena sinar matahari", "Tumbuh selaput pada mata", "Timbul bayangan", "Usia > 50 tahun", "Kelopak mata timbul benjolan", "Perih", "Target"}));
        this.bobotakhirTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.jScrollPane2.setViewportView(this.bobotakhirTable);
    }

    private void initializeDataObserver()
    {
        this.logObserver = (o, arg) -> this.testinglvqTextArea.setText(arg.toString());
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
                final DefaultTableModel datasetTable = (DefaultTableModel) ProsesPengujianLVQFrame.this.dataujiTable.getModel();
                int index = ProsesPengujianLVQFrame.this.listener.getLVQ().getTesting().indexOf(data);
                int i = 0;
                int c = 22;
                datasetTable.setValueAt(data.actualTarget, index, ++c);
                ++i;
                datasetTable.fireTableDataChanged();
            }
        };
        this.accuracyObserver = (same, size, accuracy) -> {
            this.jTextField2.setText(String.format("%d of %d [%g%%]", same, size, accuracy));
            this.jTextField1.setText(Integer.toString(same));
        };
        this.testObserver = this::repopulateTable;
    }

    public interface InteractionListener
    {
        DebuggableLVQ1 getLVQ();

        OStringBuilder getOTestingLog();
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dataujiTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        bobotakhirTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        testinglvqTextArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();

        setClosable(true);
        setMaximizable(true);
        setTitle("Proses Pengujian (Testing) dengan Learning Vector Quantization (LVQ)");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabel Data Uji"));

        dataujiTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(dataujiTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                             .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                                                    .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                             .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                                                    .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabel Bobot Akhir"));

        bobotakhirTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(bobotakhirTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                             .addGroup(jPanel2Layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                                                    .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                             .addGroup(jPanel2Layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                    .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Hasil Proses Pengujian LVQ"));

        testinglvqTextArea.setColumns(20);
        testinglvqTextArea.setRows(5);
        jScrollPane3.setViewportView(testinglvqTextArea);

        jLabel1.setText("Jumlah Prediksi Kelas yang Sesuai : ");

        jLabel2.setText("Akurasi : ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                             .addGroup(jPanel3Layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                           .addComponent(jScrollPane3)
                                                                           .addGroup(jPanel3Layout.createSequentialGroup()
                                                                                                  .addComponent(jLabel1)
                                                                                                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                  .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 332, Short.MAX_VALUE)
                                                                                                  .addComponent(jLabel2)
                                                                                                  .addGap(18, 18, 18)
                                                                                                  .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                    .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                             .addGroup(jPanel3Layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                           .addComponent(jLabel1)
                                                                           .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                           .addComponent(jLabel2)
                                                                           .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                      .addGroup(layout.createSequentialGroup()
                                      .addContainerGap()
                                      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                      .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                      .addGroup(layout.createSequentialGroup()
                                                                      .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                      .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                      .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                      .addGroup(layout.createSequentialGroup()
                                      .addContainerGap()
                                      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                      .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                      .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                      .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                      .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable bobotakhirTable;
    private javax.swing.JTable dataujiTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextArea testinglvqTextArea;
    // End of variables declaration//GEN-END:variables
}
