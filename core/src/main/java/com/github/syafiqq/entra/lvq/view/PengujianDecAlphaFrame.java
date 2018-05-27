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
import com.github.syafiqq.entra.lvq.util.Settings;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
public class PengujianDecAlphaFrame extends ClosableInternalFrame
{
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField accuracy;
    private javax.swing.JTextField epoch;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField learningrate;
    private javax.swing.JComboBox<String> lrdec;
    private javax.swing.JTextField lrmin;
    private javax.swing.JTextField sameclass;
    private javax.swing.JTable tbtraining;
    private javax.swing.JTable tbweight;
    private javax.swing.JComboBox<String> training;
    // End of variables declaration//GEN-END:variables

    final private DebuggableLVQ1 lvq = new DebuggableLVQ1(0, 0, 0, -1, new LinkedList<>(), new LinkedList<>());
    InteractionListener listener;
    private OStringBuilder testingLog = new OStringBuilder(new StringBuilder());
    private Observer logObserver;
    private DebuggableLVQ1.OnWeightUpdateListener weightObserver;
    private DebuggableLVQ1.OnWeightUpdateListener testWeightObserver;
    private DebuggableLVQ1.OnDistanceCalculationListener datasetObserver;
    private DebuggableLVQ1.OnPostCalculateAccuracyListener accuracyObserver;
    private DebuggableLVQ1.OnTrainingListener trainObserver;
    private DebuggableLVQ1.OnTestingListener testingObserver;
    private DebuggableLVQ1.OnPostInitializationListener lPostInitializationObserver;
    private DebuggableLVQ1.OnPostSatisfactionListener lPostSatisfactionObserver;
    private DebuggableLVQ1.OnDistanceCalculationListener lDistanceCalculationObserver;
    private DebuggableLVQ1.OnWeightUpdateListener lWeightUpdateObserver;
    private DebuggableLVQ1.OnPostReducedLearningRateListener lReducedLearningRateObserver;
    private DebuggableLVQ1.OnPostCalculateAccuracyListener lAccuracyObserver;
    private DebuggableLVQ1.OnPostSatisfactionEvaluationListener lSatisfactionEvaluationObserver;
    private DebuggableLVQ1.OnDistanceCalculationListener lTestDistanceCalculationObserver;
    private DebuggableLVQ1.OnWeightUpdateListener lTestWeightUpdateObserver;
    private DebuggableLVQ1.OnPostCalculateAccuracyListener lTestAccuracyObserver;

    /**
     * Creates new form PengujianDecAlphaFrame
     */
    public PengujianDecAlphaFrame(InteractionListener listener)
    {
        this.listener = listener;
        initComponents();
        DefaultCaret caret = (DefaultCaret) this.jTextArea1.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        this.initializeDataObserver();
        this.initTable();
        this.addInternalFrameListener(new COpenClosableInternalFrameListener()
        {
            @Override public void internalFrameOpened(InternalFrameEvent e)
            {
                PengujianDecAlphaFrame.this.testingLog.addObserver(PengujianDecAlphaFrame.this.logObserver);
                PengujianDecAlphaFrame.this.lvq.weightUpdateListeners.add(PengujianDecAlphaFrame.this.weightObserver);
                PengujianDecAlphaFrame.this.lvq.distanceCalculationListener.add(PengujianDecAlphaFrame.this.datasetObserver);
                PengujianDecAlphaFrame.this.lvq.accuracyListeners.add(PengujianDecAlphaFrame.this.accuracyObserver);
                PengujianDecAlphaFrame.this.lvq.testAccuracyListeners.add(PengujianDecAlphaFrame.this.accuracyObserver);
                PengujianDecAlphaFrame.this.lvq.trainingListeners.add(PengujianDecAlphaFrame.this.trainObserver);

                PengujianDecAlphaFrame.this.lvq.testingListeners.add(PengujianDecAlphaFrame.this.testingObserver);
                PengujianDecAlphaFrame.this.lvq.testWeightUpdateListeners.add(PengujianDecAlphaFrame.this.testWeightObserver);

                PengujianDecAlphaFrame.this.lvq.postInitializeListener.add(PengujianDecAlphaFrame.this.lPostInitializationObserver);
                PengujianDecAlphaFrame.this.lvq.postSatisfactionListeners.add(PengujianDecAlphaFrame.this.lPostSatisfactionObserver);
                PengujianDecAlphaFrame.this.lvq.distanceCalculationListener.add(PengujianDecAlphaFrame.this.lDistanceCalculationObserver);
                PengujianDecAlphaFrame.this.lvq.weightUpdateListeners.add(PengujianDecAlphaFrame.this.lWeightUpdateObserver);
                PengujianDecAlphaFrame.this.lvq.reducedLearningRateListener.add(PengujianDecAlphaFrame.this.lReducedLearningRateObserver);
                PengujianDecAlphaFrame.this.lvq.accuracyListeners.add(PengujianDecAlphaFrame.this.lAccuracyObserver);
                PengujianDecAlphaFrame.this.lvq.satisfactionEvaluationListeners.add(PengujianDecAlphaFrame.this.lSatisfactionEvaluationObserver);

                PengujianDecAlphaFrame.this.lvq.testDistanceCalculationListener.add(PengujianDecAlphaFrame.this.lTestDistanceCalculationObserver);
                PengujianDecAlphaFrame.this.lvq.testWeightUpdateListeners.add(PengujianDecAlphaFrame.this.lTestWeightUpdateObserver);
                PengujianDecAlphaFrame.this.lvq.testAccuracyListeners.add(PengujianDecAlphaFrame.this.lTestAccuracyObserver);
            }

            @Override public void internalFrameClosed(InternalFrameEvent e)
            {
                PengujianDecAlphaFrame.this.testingLog.deleteObserver(PengujianDecAlphaFrame.this.logObserver);
                PengujianDecAlphaFrame.this.lvq.weightUpdateListeners.remove(PengujianDecAlphaFrame.this.weightObserver);
                PengujianDecAlphaFrame.this.lvq.distanceCalculationListener.remove(PengujianDecAlphaFrame.this.datasetObserver);
                PengujianDecAlphaFrame.this.lvq.accuracyListeners.remove(PengujianDecAlphaFrame.this.accuracyObserver);
                PengujianDecAlphaFrame.this.lvq.testAccuracyListeners.remove(PengujianDecAlphaFrame.this.accuracyObserver);
                PengujianDecAlphaFrame.this.lvq.trainingListeners.remove(PengujianDecAlphaFrame.this.trainObserver);

                PengujianDecAlphaFrame.this.lvq.testingListeners.remove(PengujianDecAlphaFrame.this.testingObserver);
                PengujianDecAlphaFrame.this.lvq.testWeightUpdateListeners.add(PengujianDecAlphaFrame.this.testWeightObserver);

                PengujianDecAlphaFrame.this.lvq.postInitializeListener.remove(PengujianDecAlphaFrame.this.lPostInitializationObserver);
                PengujianDecAlphaFrame.this.lvq.postSatisfactionListeners.remove(PengujianDecAlphaFrame.this.lPostSatisfactionObserver);
                PengujianDecAlphaFrame.this.lvq.distanceCalculationListener.remove(PengujianDecAlphaFrame.this.lDistanceCalculationObserver);
                PengujianDecAlphaFrame.this.lvq.weightUpdateListeners.remove(PengujianDecAlphaFrame.this.lWeightUpdateObserver);
                PengujianDecAlphaFrame.this.lvq.reducedLearningRateListener.remove(PengujianDecAlphaFrame.this.lReducedLearningRateObserver);
                PengujianDecAlphaFrame.this.lvq.accuracyListeners.remove(PengujianDecAlphaFrame.this.lAccuracyObserver);
                PengujianDecAlphaFrame.this.lvq.satisfactionEvaluationListeners.remove(PengujianDecAlphaFrame.this.lSatisfactionEvaluationObserver);

                PengujianDecAlphaFrame.this.lvq.testDistanceCalculationListener.remove(PengujianDecAlphaFrame.this.lTestDistanceCalculationObserver);
                PengujianDecAlphaFrame.this.lvq.testWeightUpdateListeners.remove(PengujianDecAlphaFrame.this.lTestWeightUpdateObserver);
                PengujianDecAlphaFrame.this.lvq.testAccuracyListeners.remove(PengujianDecAlphaFrame.this.lTestAccuracyObserver);
            }
        });
    }


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int train = 0;
        double learningRate = 0;
        double decLR = 0;
        double minLR = 0;
        int epoch = 0;
        try
        {
            train = Integer.parseInt(Objects.requireNonNull(this.training.getSelectedItem()).toString());
        }
        catch(NumberFormatException ignored)
        {
        }
        try
        {
            learningRate = Double.parseDouble(Objects.requireNonNull(this.learningrate.getText()));
        }
        catch(NumberFormatException ignored)
        {
        }
        try
        {
            decLR = Double.parseDouble(Objects.requireNonNull(this.lrdec.getSelectedItem()).toString());
        }
        catch(NumberFormatException ignored)
        {
        }
        try
        {
            minLR = Double.parseDouble(this.lrmin.getText());
        }
        catch(NumberFormatException ignored)
        {
        }
        try
        {
            epoch = Integer.parseInt(this.epoch.getText());
        }
        catch(NumberFormatException ignored)
        {
        }

        train = (int) (train / 100.0 * DatasetDao.getAll(Settings.DB).size());

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
        else
        {
            this.doProses(train, learningRate, decLR, minLR, epoch);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void doProses(int train, double learningRate, double decLR, double minLR, int epoch)
    {
        final List<DatasetPojo> dataset = DatasetDao.getAll(Settings.DB);
        final Map<Integer, List<DatasetPojo>> groupedDataset = dataset.stream().collect(Collectors.groupingBy(DatasetPojo::getTarget));

        // Generating Dataset
        groupedDataset.values().forEach(d -> d.sort(Comparator.comparingInt(o -> o.target + (o.id == null ? 0 : o.id))));
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
        final List<WeightPojo> weight = WeightDao.getAll(Settings.DB);
        final Map<Integer, List<WeightPojo>> groupedWeight = weight.stream().collect(Collectors.groupingBy(WeightPojo::getTarget));
        final List<WeightPojo> selectedWeight = new LinkedList<>();
        selectedWeight.addAll(weight);

        List<DatasetPojo> selectedTesting = dataset.stream().filter(d -> !selectedDataset.contains(d)).collect(Collectors.toList());
        lvq.setSetting(learningRate, decLR, minLR, epoch);
        lvq.setData(selectedDataset.stream().map(ProcessedDatasetPojo::new).collect(Collectors.toList()), selectedWeight.stream().map(EuclideanWeightPojo::new).collect(Collectors.toList()), selectedTesting.stream().map(ProcessedDatasetPojo::new).collect(Collectors.toList()));
        new Thread(lvq::trainingAndTesting).start();
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
                final DefaultTableModel weightTable = (DefaultTableModel) PengujianDecAlphaFrame.this.tbweight.getModel();
                int index = lvq.getWeight().indexOf(weight);
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
                final DefaultTableModel datasetTable = (DefaultTableModel) PengujianDecAlphaFrame.this.tbtraining.getModel();
                int index = lvq.getDataset().indexOf(data);
                int i = 0;
                int c = 22;
                datasetTable.setValueAt(data.actualTarget, index, ++c);
                ++i;
                datasetTable.fireTableDataChanged();
            }
        };
        this.accuracyObserver = (same, size, accuracy) -> {
            this.accuracy.setText(String.format("%d of %d [%g%%]", same, size, accuracy));
            this.sameclass.setText(Integer.toString(same));
        };
        this.trainObserver = this::resetOperation;

        this.testingObserver = this::repopulateTable;
        this.testWeightObserver = new DebuggableLVQ1.OnWeightUpdateListener()
        {
            @Override public void preUpdate(ProcessedDatasetPojo data, ProcessedWeightPojo<Double> weight, boolean sameSignature)
            {
                final DefaultTableModel datasetTable = (DefaultTableModel) PengujianDecAlphaFrame.this.tbtraining.getModel();
                int index = PengujianDecAlphaFrame.this.lvq.getTesting().indexOf(data);
                int i = 0;
                int c = 22;
                datasetTable.setValueAt(data.actualTarget, index, ++c);
                ++i;
                datasetTable.fireTableDataChanged();
            }

            @Override public void update(ProcessedWeightPojo<Double> weight)
            {

            }

            @Override public void postUpdate(ProcessedWeightPojo<Double> weight)
            {

            }
        };

        this.lPostInitializationObserver = (learningRate, lrReduction, lrThreshold, maxIteration, weight, dataset) -> {
            testingLog.append("\n==Begin Initialization Phase==\n");
            testingLog.append(String.format("Learning Rate           = %f\n", learningRate));
            testingLog.append(String.format("Learning Rate Reduction = %f\n", lrReduction));
            testingLog.append(String.format("Learning Rate Threshold = %g\n", lrThreshold));
            testingLog.append(String.format("Iteration               = %d\n", maxIteration));
            testingLog.append("=Dataset=\n");
            dataset.forEach(d -> testingLog.append(String.format("%s\n", d)));
            testingLog.append("=Bobot=\n");
            weight.forEach(w -> testingLog.append(String.format("%s\n", w)));
            testingLog.append("==End Initialization Phase==\n");
        };
        this.lPostSatisfactionObserver = ((epoch, maxEpoch, learningRate, maxLearningRate, result) -> {
            testingLog.append(String.format("\n==Begin Epoch [%d] ==\n", epoch));
            testingLog.append("===Begin Check Satisfaction===\n");
            testingLog.append(String.format("Iteration %d of %d\n", epoch, maxEpoch));
            testingLog.append(String.format("LearningRate %g of %g\n", learningRate, maxLearningRate));
            testingLog.append(String.format("Result %s\n", result ? "Satisfied" : "Not Satisfied"));
            testingLog.append("===End Check Satisfaction===\n");
        });
        this.lDistanceCalculationObserver = new DebuggableLVQ1.OnDistanceCalculationListener()
        {
            @Override public void preCalculated(ProcessedDatasetPojo data)
            {
                testingLog.append("===Begin Calculate Distance===\n");
            }

            @Override public void calculated(ProcessedDatasetPojo data, ProcessedWeightPojo<Double> weight)
            {
                testingLog.append(String.format("Distance [%d, %d] against [%d, %d] resulting %f \n", data.dataset.id, data.dataset.target, weight.weight.id, weight.weight.target, weight.getDistance()));
            }

            @Override public void postCalculated(ProcessedDatasetPojo data)
            {
                testingLog.append("===End Calculate Distance===\n");
            }
        };
        this.lWeightUpdateObserver = new DebuggableLVQ1.OnWeightUpdateListener()
        {
            @Override public void preUpdate(ProcessedDatasetPojo data, ProcessedWeightPojo<Double> weight, boolean sameSignature)
            {
                testingLog.append("===Begin Update Weight===\n");
                testingLog.append(String.format("Minimum weight distance is [%d, %d, %g] which are %s with data [%d %d] so the weight is move %s the data\n", weight.weight.id, weight.weight.target, weight.getDistance(), sameSignature ? "Same Class" : "Different Class", data.dataset.id, data.dataset.target, sameSignature ? "Closer to" : "Away from"));
            }

            @Override public void update(ProcessedWeightPojo<Double> weight)
            {
                testingLog.append(String.format("Resulting Weight is : %s\n", weight.toString()));
            }

            @Override public void postUpdate(ProcessedWeightPojo<Double> weight)
            {
                testingLog.append("===End Update Weight===\n");
            }
        };
        this.lReducedLearningRateObserver = (lr, lrr, r) -> {
            testingLog.append("===Begin Reduce Learning Rate===\n");
            testingLog.append(String.format("Reduce Learning rate [%g] with learning rate reduction component [%f] resulting [%f]\n", lr, lrr, r));
            testingLog.append("===End Reduce Learning Rate===\n");
        };
        this.lAccuracyObserver = (s, t, acc) -> {
            testingLog.append("===Begin Calculate Accuracy===\n");
            testingLog.append(String.format("The Correct Class is %d of %d resulting %f%%\n", s, t, acc));
            testingLog.append("===End Calculate Accuracy===\n");
        };
        this.lSatisfactionEvaluationObserver = e -> {
            testingLog.append("===Begin Satisfaction Evaluation===\n");
            testingLog.append(String.format("Update Epoch to %d\n", e));
            testingLog.append("===End Satisfaction Evaluation===\n");
            testingLog.append(String.format("==End Epoch [%d]==\n\n", e - 1));
        };


        this.lTestDistanceCalculationObserver = new DebuggableLVQ1.OnDistanceCalculationListener()
        {
            @Override public void preCalculated(ProcessedDatasetPojo data)
            {
                testingLog.append("==Begin Testing==\n");
                testingLog.append("===Begin Calculate Distance===\n");
            }

            @Override public void calculated(ProcessedDatasetPojo data, ProcessedWeightPojo<Double> weight)
            {
                testingLog.append(String.format("Distance [%d, %d] against [%d, %d] resulting %f \n", data.dataset.id, data.dataset.target, weight.weight.id, weight.weight.target, weight.getDistance()));
            }

            @Override public void postCalculated(ProcessedDatasetPojo data)
            {
                testingLog.append("===End Calculate Distance===\n");
            }
        };
        this.lTestWeightUpdateObserver = new DebuggableLVQ1.OnWeightUpdateListener()
        {
            @Override public void preUpdate(ProcessedDatasetPojo data, ProcessedWeightPojo<Double> weight, boolean sameSignature)
            {
                testingLog.append("===Begin Classify===\n");
                testingLog.append(String.format("The closest distance of [%d, %d] is [%d, %d] which are [%g] so the data can be classified as Class [%d]\n", data.dataset.id, data.dataset.target, weight.weight.id, weight.weight.target, weight.getDistance(), data.actualTarget));
            }

            @Override public void update(ProcessedWeightPojo<Double> weight)
            {
            }

            @Override public void postUpdate(ProcessedWeightPojo<Double> weight)
            {
                testingLog.append("===End Classify===\n");
            }
        };
        this.lTestAccuracyObserver = (s, t, acc) -> {
            testingLog.append("===Begin Calculate Accuracy===\n");
            testingLog.append(String.format("The Correct Class is %d of %d resulting %f%%\n", s, t, acc));
            testingLog.append("===End Calculate Accuracy===\n");
            testingLog.append("==End Testing==\n");
        };
    }

    private void resetOperation(List<ProcessedDatasetPojo> train, List<ProcessedWeightPojo<Double>> weight)
    {
        this.testingLog.setLength(0);
        this.repopulateTable(train, weight);
    }

    private void repopulateTable(List<ProcessedDatasetPojo> list, List<ProcessedWeightPojo<Double>> weight)
    {
        final DefaultTableModel datasetTable = (DefaultTableModel) this.tbtraining.getModel();
        datasetTable.setRowCount(0);
        list.forEach(dt -> {
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

        final DefaultTableModel weightTable = (DefaultTableModel) this.tbweight.getModel();
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
        this.tbtraining.setModel(new DefaultTableModel(null, new String[] {"No", "Sakit/Nyeri kepala hebat", "Penglihatan kabur perlahan", "Silau", "Merah", "Nyeri", "Perut mual", "Penglihatan berkabut(berasap)", "Lensa mata keruh", "Gatal", "Berair", "Belekan", "Kelopak bengkak", "Panas", "Mengganjal", "Lengket", "Merah jika terkena sinar matahari", "Tumbuh selaput pada mata", "Timbul bayangan", "Usia > 50 tahun", "Kelopak mata timbul benjolan", "Perih", "Target", "Aktual-Target"}));
        this.tbtraining.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.jScrollPane2.setViewportView(this.tbtraining);
        this.tbweight.setModel(new DefaultTableModel(null, new String[] {"No", "Sakit/Nyeri kepala hebat", "Penglihatan kabur perlahan", "Silau", "Merah", "Nyeri", "Perut mual", "Penglihatan berkabut(berasap)", "Lensa mata keruh", "Gatal", "Berair", "Belekan", "Kelopak bengkak", "Panas", "Mengganjal", "Lengket", "Merah jika terkena sinar matahari", "Tumbuh selaput pada mata", "Timbul bayangan", "Usia > 50 tahun", "Kelopak mata timbul benjolan", "Perih", "Target"}));
        this.tbweight.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.jScrollPane3.setViewportView(this.tbweight);
    }

    public interface InteractionListener
    {
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
        training = new javax.swing.JComboBox<>();
        lrdec = new javax.swing.JComboBox<>();
        learningrate = new javax.swing.JTextField();
        lrmin = new javax.swing.JTextField();
        epoch = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        sameclass = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        accuracy = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbtraining = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbweight = new javax.swing.JTable();

        setClosable(true);
        setMaximizable(true);
        setTitle("Proses Pengujian Parameter Pengurangan nilai Learning Rate (Dec α)");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Parameter Pengujian"));

        training.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"10", "20", "30", "40", "50", "60", "70", "80", "90"}));

        lrdec.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9"}));

        jLabel1.setText("Data Latih (%)");

        jLabel2.setText("Learning Rate (α)");

        jLabel3.setText("Pengurangan Learning Rate (Dec α)");

        jLabel4.setText("Minimal Learning Rate (Min α)");

        jLabel5.setText("Iterasi Maksimum (MaxEpoh)");

        jButton1.setText("Proses");
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Ulang Proses");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                             .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                           .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                                  .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                         .addComponent(jLabel1)
                                                                                                                         .addComponent(jLabel3)
                                                                                                                         .addComponent(jLabel4)
                                                                                                                         .addComponent(jLabel5))
                                                                                                  .addGap(18, 18, 18)
                                                                                                  .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                                                                         .addComponent(lrmin)
                                                                                                                         .addComponent(learningrate, javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                         .addComponent(training, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                                         .addComponent(epoch)
                                                                                                                         .addComponent(lrdec, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                           .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                                  .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                  .addGap(42, 42, 42)
                                                                                                  .addComponent(jButton2))
                                                                           .addComponent(jLabel2))
                                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                             .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                           .addComponent(training, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                           .addComponent(jLabel1))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                           .addComponent(jLabel2)
                                                                           .addComponent(learningrate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                           .addComponent(jLabel3)
                                                                           .addComponent(lrdec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                           .addComponent(lrmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                           .addComponent(jLabel4))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                           .addComponent(epoch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                           .addComponent(jLabel5))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                           .addComponent(jButton1)
                                                                           .addComponent(jButton2))
                                                    .addContainerGap(53, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Hasil Pengujian"));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel6.setText("Jumlah Prediksi Kelas yang Sesuai : ");

        jLabel7.setText("Akurasi : ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                             .addGroup(jPanel2Layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                           .addComponent(jScrollPane1)
                                                                           .addGroup(jPanel2Layout.createSequentialGroup()
                                                                                                  .addComponent(jLabel6)
                                                                                                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                  .addComponent(sameclass, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                  .addComponent(jLabel7)
                                                                                                  .addGap(18, 18, 18)
                                                                                                  .addComponent(accuracy, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                    .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                             .addGroup(jPanel2Layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                           .addComponent(jLabel6)
                                                                           .addComponent(sameclass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                           .addComponent(jLabel7)
                                                                           .addComponent(accuracy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabel Data"));

        tbtraining.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tbtraining);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                             .addGroup(jPanel3Layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                             .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(181, 181, 181))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabel Bobot"));

        tbweight.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tbweight);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                             .addGroup(jPanel4Layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                             .addGroup(jPanel4Layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                      .addGroup(layout.createSequentialGroup()
                                      .addContainerGap()
                                      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                      .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                      .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                      .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                      .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                      .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                      .addGroup(layout.createSequentialGroup()
                                      .addContainerGap()
                                      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                      .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                      .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                      .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                      .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 303, Short.MAX_VALUE))
                                      .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
}
