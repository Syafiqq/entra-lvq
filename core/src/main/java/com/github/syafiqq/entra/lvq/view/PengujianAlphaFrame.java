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
import java.awt.event.ActionEvent;
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
public class PengujianAlphaFrame extends ClosableInternalFrame
{
    InteractionListener listener;
    final private DebuggableLVQ1 lvq = new DebuggableLVQ1(0, 0, 0, -1, new LinkedList<>(), new LinkedList<>());
    private OStringBuilder testingLog = new OStringBuilder(new StringBuilder());
    private Observer logObserver;
    private DebuggableLVQ1.OnWeightUpdateListener weightObserver;
    private DebuggableLVQ1.OnDistanceCalculationListener datasetObserver;
    private DebuggableLVQ1.OnPostCalculateAccuracyListener accuracyObserver;
    private DebuggableLVQ1.OnTrainingListener trainObserver;
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
     * Creates new form PengujianAlphaFrame
     */
    public PengujianAlphaFrame(InteractionListener listener)
    {
        this.listener = listener;
        initComponents();
        this.process.addActionListener(this::processButtonClicked);
        DefaultCaret caret = (DefaultCaret) this.jTextArea1.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        this.initializeDataObserver();
        this.initTable();
        this.addInternalFrameListener(new COpenClosableInternalFrameListener()
        {
            @Override public void internalFrameOpened(InternalFrameEvent e)
            {
                PengujianAlphaFrame.this.testingLog.addObserver(PengujianAlphaFrame.this.logObserver);
                PengujianAlphaFrame.this.lvq.weightUpdateListeners.add(PengujianAlphaFrame.this.weightObserver);
                PengujianAlphaFrame.this.lvq.distanceCalculationListener.add(PengujianAlphaFrame.this.datasetObserver);
                PengujianAlphaFrame.this.lvq.accuracyListeners.add(PengujianAlphaFrame.this.accuracyObserver);
                PengujianAlphaFrame.this.lvq.trainingListeners.add(PengujianAlphaFrame.this.trainObserver);

                PengujianAlphaFrame.this.lvq.postInitializeListener.add(PengujianAlphaFrame.this.lPostInitializationObserver);
                PengujianAlphaFrame.this.lvq.postSatisfactionListeners.add(PengujianAlphaFrame.this.lPostSatisfactionObserver);
                PengujianAlphaFrame.this.lvq.distanceCalculationListener.add(PengujianAlphaFrame.this.lDistanceCalculationObserver);
                PengujianAlphaFrame.this.lvq.weightUpdateListeners.add(PengujianAlphaFrame.this.lWeightUpdateObserver);
                PengujianAlphaFrame.this.lvq.reducedLearningRateListener.add(PengujianAlphaFrame.this.lReducedLearningRateObserver);
                PengujianAlphaFrame.this.lvq.accuracyListeners.add(PengujianAlphaFrame.this.lAccuracyObserver);
                PengujianAlphaFrame.this.lvq.satisfactionEvaluationListeners.add(PengujianAlphaFrame.this.lSatisfactionEvaluationObserver);

                PengujianAlphaFrame.this.lvq.testDistanceCalculationListener.add(PengujianAlphaFrame.this.lTestDistanceCalculationObserver);
                PengujianAlphaFrame.this.lvq.testWeightUpdateListeners.add(PengujianAlphaFrame.this.lTestWeightUpdateObserver);
                PengujianAlphaFrame.this.lvq.testAccuracyListeners.add(PengujianAlphaFrame.this.lTestAccuracyObserver);
            }

            @Override public void internalFrameClosed(InternalFrameEvent e)
            {
                PengujianAlphaFrame.this.testingLog.deleteObserver(PengujianAlphaFrame.this.logObserver);
                PengujianAlphaFrame.this.lvq.weightUpdateListeners.remove(PengujianAlphaFrame.this.weightObserver);
                PengujianAlphaFrame.this.lvq.distanceCalculationListener.remove(PengujianAlphaFrame.this.datasetObserver);
                PengujianAlphaFrame.this.lvq.accuracyListeners.remove(PengujianAlphaFrame.this.accuracyObserver);
                PengujianAlphaFrame.this.lvq.trainingListeners.remove(PengujianAlphaFrame.this.trainObserver);

                PengujianAlphaFrame.this.lvq.postInitializeListener.remove(PengujianAlphaFrame.this.lPostInitializationObserver);
                PengujianAlphaFrame.this.lvq.postSatisfactionListeners.remove(PengujianAlphaFrame.this.lPostSatisfactionObserver);
                PengujianAlphaFrame.this.lvq.distanceCalculationListener.remove(PengujianAlphaFrame.this.lDistanceCalculationObserver);
                PengujianAlphaFrame.this.lvq.weightUpdateListeners.remove(PengujianAlphaFrame.this.lWeightUpdateObserver);
                PengujianAlphaFrame.this.lvq.reducedLearningRateListener.remove(PengujianAlphaFrame.this.lReducedLearningRateObserver);
                PengujianAlphaFrame.this.lvq.accuracyListeners.remove(PengujianAlphaFrame.this.lAccuracyObserver);
                PengujianAlphaFrame.this.lvq.satisfactionEvaluationListeners.remove(PengujianAlphaFrame.this.lSatisfactionEvaluationObserver);

                PengujianAlphaFrame.this.lvq.testDistanceCalculationListener.remove(PengujianAlphaFrame.this.lTestDistanceCalculationObserver);
                PengujianAlphaFrame.this.lvq.testWeightUpdateListeners.remove(PengujianAlphaFrame.this.lTestWeightUpdateObserver);
                PengujianAlphaFrame.this.lvq.testAccuracyListeners.remove(PengujianAlphaFrame.this.lTestAccuracyObserver);
            }
        });
    }

    private void processButtonClicked(ActionEvent actionEvent)
    {
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
            learningRate = Double.parseDouble(Objects.requireNonNull(this.learningrate.getSelectedItem()).toString());
        }
        catch(NumberFormatException ignored)
        {
        }
        try
        {
            decLR = Double.parseDouble(this.lrdec.getText());
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
    }

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
                final DefaultTableModel weightTable = (DefaultTableModel) PengujianAlphaFrame.this.tbweight.getModel();
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
                final DefaultTableModel datasetTable = (DefaultTableModel) PengujianAlphaFrame.this.tbtraining.getModel();
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

        final DefaultTableModel datasetTable = (DefaultTableModel) this.tbtraining.getModel();
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField accuracy;
    private javax.swing.JTextField epoch;
    private javax.swing.JComboBox<String> learningrate;
    private javax.swing.JTextField lrdec;
    private javax.swing.JTextField lrmin;
    private javax.swing.JButton process;
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
    private javax.swing.JTextField sameclass;
    private javax.swing.JTable tbtraining;
    private javax.swing.JTable tbweight;
    private javax.swing.JComboBox<String> training;

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
        learningrate = new javax.swing.JComboBox<>();
        lrdec = new javax.swing.JTextField();
        lrmin = new javax.swing.JTextField();
        epoch = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        process = new javax.swing.JButton();
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
        setTitle("Proses Pengujian Parameter Learning Rate (α)");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Parameter Pengujian"));

        training.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"10", "20", "30", "40", "50", "60", "70", "80", "90"}));

        learningrate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1"}));

        jLabel1.setText("Data Latih (%)");

        jLabel2.setText("Learning Rate (α)");

        jLabel3.setText("Pengurangan Learning Rate (Dec α)");

        jLabel4.setText("Minimal Learning Rate (Min α)");

        jLabel5.setText("Iterasi Maksimum (MaxEpoh)");

        process.setText("Proses");

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
                                                                                                                         .addComponent(jLabel2)
                                                                                                                         .addComponent(jLabel3)
                                                                                                                         .addComponent(jLabel4)
                                                                                                                         .addComponent(jLabel5))
                                                                                                  .addGap(18, 18, 18)
                                                                                                  .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                                                                         .addComponent(lrmin)
                                                                                                                         .addComponent(lrdec, javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                         .addComponent(learningrate, javax.swing.GroupLayout.Alignment.LEADING, 0, 69, Short.MAX_VALUE)
                                                                                                                         .addComponent(training, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                                         .addComponent(epoch)))
                                                                           .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                                  .addComponent(process, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                  .addGap(42, 42, 42)
                                                                                                  .addComponent(jButton2)))
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
                                                                           .addComponent(learningrate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                           .addComponent(jLabel2))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                           .addComponent(lrdec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                           .addComponent(jLabel3))
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
                                                                           .addComponent(process)
                                                                           .addComponent(jButton2))
                                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                                                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(143, 143, 143))
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
                                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                                      .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                      .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                      .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // End of variables declaration//GEN-END:variables
}
