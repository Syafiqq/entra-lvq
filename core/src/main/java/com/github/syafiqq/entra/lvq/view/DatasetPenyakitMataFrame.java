// @formatter:off
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.syafiqq.entra.lvq.view;

import com.github.syafiqq.entra.lvq.model.database.dao.DatasetDao;
import com.github.syafiqq.entra.lvq.model.database.pojo.DatasetPojo;
import com.github.syafiqq.entra.lvq.util.Settings;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Entra
 */
@SuppressWarnings("PointlessBooleanExpression") public class DatasetPenyakitMataFrame extends javax.swing.JInternalFrame {

    InteractionListener listener;
    int row;

    /**
     * Creates new form DatasetPenyakitMataFrame
     */
    public DatasetPenyakitMataFrame(@NotNull InteractionListener listener) {
        this.listener = listener;
        initComponents();
        readTable();

        datasetTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                row = datasetTable.getSelectedRow();
                if (row != -1) {
                    isiFormDataset();
                }
            }
        });
    }

    public List<DatasetPojo> list = new ArrayList<>();

    public void readTable() {
        list = DatasetDao.getAll(Settings.DB);
        if (!list.isEmpty()) {
            Object[][] data = new Object[this.list.size()][23];
            int i = 0;
            for (DatasetPojo dt : this.list) {
                int c = -1;
                data[i][++c] = dt.id;
                for(String idx : Settings.columns)
                {
                    data[i][++c] = dt.vector(idx);
                }
                data[i][++c] = dt.target;
                ++i;
            }
            this.datasetTable.setModel(new DefaultTableModel(data, new String[]{"No", "Sakit/Nyeri kepala hebat", "Penglihatan kabur perlahan", "Silau", "Merah", "Nyeri", "Perut mual", "Penglihatan berkabut(berasap)", "Lensa mata keruh", "Gatal", "Berair", "Belekan", "Kelopak bengkak", "Panas", "Mengganjal", "Lengket", "Merah jika terkena sinar matahari", "Tumbuh selaput pada mata", "Timbul bayangan", "Usia > 50 tahun", "Kelopak mata timbul benjolan", "Perih", "Target"}));
            this.jScrollPane1.setViewportView(this.datasetTable);
        } else {
            this.datasetTable.setEnabled(true);
        }
    }

    public void isiFormDataset() {
        DatasetPojo dt = list.get(row);
        noTextField.setText(dt.id.toString());

        //gejala 1
        if (dt.vector("g1") == 1) {
            this.g1_Ya_RadioButton.setSelected(true);
        } else if (dt.vector("g1") == 0) {
            this.g1_Tidak_RadioButton.setSelected(true);
        }

        //gejala 2
        if (dt.vector("g2") == 1) {
            this.g2_Ya_RadioButton.setSelected(true);
        } else if (dt.vector("g2") == 0) {
            this.g2_Tidak_RadioButton.setSelected(true);
        }

        //gejala 3
        if (dt.vector("g3") == 1) {
            this.g3_Ya_RadioButton.setSelected(true);
        } else if (dt.vector("g3") == 0) {
            this.g3_Tidak_RadioButton.setSelected(true);
        }

        //gejala 4
        if (dt.vector("g4") == 1) {
            this.g4_Ya_RadioButton.setSelected(true);
        } else if (dt.vector("g4") == 0) {
            this.g4_Tidak_RadioButton.setSelected(true);
        }

        //gejala 5
        if (dt.vector("g5") == 1) {
            this.g5_Ya_RadioButton.setSelected(true);
        } else if (dt.vector("g5") == 0) {
            this.g5_Tidak_RadioButton.setSelected(true);
        }

        //gejala 6
        if (dt.vector("g6") == 1) {
            this.g6_Ya_RadioButton.setSelected(true);
        } else if (dt.vector("g6") == 0) {
            this.g6_Tidak_RadioButton.setSelected(true);
        }

        //gejala 7
        if (dt.vector("g7") == 1) {
            this.g7_Ya_RadioButton.setSelected(true);
        } else if (dt.vector("g7") == 0) {
            this.g7_Tidak_RadioButton.setSelected(true);
        }

        //gejala 8
        if (dt.vector("g8") == 1) {
            this.g8_Ya_RadioButton.setSelected(true);
        } else if (dt.vector("g8") == 0) {
            this.g8_Tidak_RadioButton.setSelected(true);
        }

        //gejala 9
        if (dt.vector("g9") == 1) {
            this.g9_Ya_RadioButton.setSelected(true);
        } else if (dt.vector("g9") == 0) {
            this.g9_Tidak_RadioButton.setSelected(true);
        }

        //gejala 10
        if (dt.vector("g10") == 1) {
            this.g10_Ya_RadioButton.setSelected(true);
        } else if (dt.vector("g10") == 0) {
            this.g10_Tidak_RadioButton.setSelected(true);
        }

        //gejala 11
        if (dt.vector("g11") == 1) {
            this.g11_Ya_RadioButton.setSelected(true);
        } else if (dt.vector("g11") == 0) {
            this.g11_Tidak_RadioButton.setSelected(true);
        }

        //gejala 12
        if (dt.vector("g12") == 1) {
            this.g12_Ya_RadioButton.setSelected(true);
        } else if (dt.vector("g12") == 0) {
            this.g12_Tidak_RadioButton.setSelected(true);
        }

        //gejala 13
        if (dt.vector("g13") == 1) {
            this.g13_Ya_RadioButton.setSelected(true);
        } else if (dt.vector("g13") == 0) {
            this.g13_Tidak_RadioButton.setSelected(true);
        }

        //gejala 14
        if (dt.vector("g14") == 1) {
            this.g14_Ya_RadioButton.setSelected(true);
        } else if (dt.vector("g14") == 0) {
            this.g14_Tidak_RadioButton.setSelected(true);
        }

        //gejala 15
        if (dt.vector("g15") == 1) {
            this.g15_Ya_RadioButton.setSelected(true);
        } else if (dt.vector("g15") == 0) {
            this.g15_Tidak_RadioButton.setSelected(true);
        }

        //gejala 16
        if (dt.vector("g16") == 1) {
            this.g16_Ya_RadioButton.setSelected(true);
        } else if (dt.vector("g16") == 0) {
            this.g16_Tidak_RadioButton.setSelected(true);
        }

        //gejala 17
        if (dt.vector("g17") == 1) {
            this.g17_Ya_RadioButton.setSelected(true);
        } else if (dt.vector("g17") == 0) {
            this.g17_Tidak_RadioButton.setSelected(true);
        }

        //gejala 18
        if (dt.vector("g18") == 1) {
            this.g18_Ya_RadioButton.setSelected(true);
        } else if (dt.vector("g18") == 0) {
            this.g18_Tidak_RadioButton.setSelected(true);
        }

        //gejala 19
        if (dt.vector("g19") == 1) {
            this.g19_Ya_RadioButton.setSelected(true);
        } else if (dt.vector("g19") == 0) {
            this.g19_Tidak_RadioButton.setSelected(true);
        }

        //gejala 20
        if (dt.vector("g20") == 1) {
            this.g20_Ya_RadioButton.setSelected(true);
        } else if (dt.vector("g20") == 0) {
            this.g20_Tidak_RadioButton.setSelected(true);
        }

        //gejala 21
        if (dt.vector("g21") == 1) {
            this.g21_Ya_RadioButton.setSelected(true);
        } else if (dt.vector("g22") == 0) {
            this.g21_Tidak_RadioButton.setSelected(true);
        }

        //target
        switch(dt.target)
        {
            case 1 : this.penyakitComboBox.setSelectedItem("Glaukoma"); break;
            case 2 : this.penyakitComboBox.setSelectedItem("Katarak"); break;
            case 3 : this.penyakitComboBox.setSelectedItem("Konjungtivitis"); break;
            case 4 : this.penyakitComboBox.setSelectedItem("Keratitis"); break;
            case 5 : this.penyakitComboBox.setSelectedItem("Pterigium"); break;
            case 6 : this.penyakitComboBox.setSelectedItem("Uveitis"); break;
            case 7 : this.penyakitComboBox.setSelectedItem("Dry Eyes"); break;
            case 8 : this.penyakitComboBox.setSelectedItem("Bleparitis"); break;
            default : this.penyakitComboBox.setSelectedItem("Kalazion"); break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupG1 = new javax.swing.ButtonGroup();
        buttonGroupG2 = new javax.swing.ButtonGroup();
        buttonGroupG3 = new javax.swing.ButtonGroup();
        buttonGroupG4 = new javax.swing.ButtonGroup();
        buttonGroupG5 = new javax.swing.ButtonGroup();
        buttonGroupG6 = new javax.swing.ButtonGroup();
        buttonGroupG7 = new javax.swing.ButtonGroup();
        buttonGroupG8 = new javax.swing.ButtonGroup();
        buttonGroupG9 = new javax.swing.ButtonGroup();
        buttonGroupG10 = new javax.swing.ButtonGroup();
        buttonGroupG11 = new javax.swing.ButtonGroup();
        buttonGroupG12 = new javax.swing.ButtonGroup();
        buttonGroupG13 = new javax.swing.ButtonGroup();
        buttonGroupG14 = new javax.swing.ButtonGroup();
        buttonGroupG15 = new javax.swing.ButtonGroup();
        buttonGroupG16 = new javax.swing.ButtonGroup();
        buttonGroupG17 = new javax.swing.ButtonGroup();
        buttonGroupG18 = new javax.swing.ButtonGroup();
        buttonGroupG19 = new javax.swing.ButtonGroup();
        buttonGroupG20 = new javax.swing.ButtonGroup();
        buttonGroupG21 = new javax.swing.ButtonGroup();
        inputPanel = new javax.swing.JPanel();
        noTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        g1_Ya_RadioButton = new javax.swing.JRadioButton();
        g1_Tidak_RadioButton = new javax.swing.JRadioButton();
        g2_Ya_RadioButton = new javax.swing.JRadioButton();
        g2_Tidak_RadioButton = new javax.swing.JRadioButton();
        g3_Ya_RadioButton = new javax.swing.JRadioButton();
        g3_Tidak_RadioButton = new javax.swing.JRadioButton();
        g4_Ya_RadioButton = new javax.swing.JRadioButton();
        g4_Tidak_RadioButton = new javax.swing.JRadioButton();
        g5_Ya_RadioButton = new javax.swing.JRadioButton();
        g5_Tidak_RadioButton = new javax.swing.JRadioButton();
        g6_Ya_RadioButton = new javax.swing.JRadioButton();
        g6_Tidak_RadioButton = new javax.swing.JRadioButton();
        g7_Ya_RadioButton = new javax.swing.JRadioButton();
        g7_Tidak_RadioButton = new javax.swing.JRadioButton();
        g8_Ya_RadioButton = new javax.swing.JRadioButton();
        g8_Tidak_RadioButton = new javax.swing.JRadioButton();
        jLabel19 = new javax.swing.JLabel();
        ubahButton = new javax.swing.JButton();
        hapusButton = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        penyakitComboBox = new javax.swing.JComboBox<>();
        tambahButton = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        g9_Ya_RadioButton = new javax.swing.JRadioButton();
        g9_Tidak_RadioButton = new javax.swing.JRadioButton();
        g10_Ya_RadioButton = new javax.swing.JRadioButton();
        g10_Tidak_RadioButton = new javax.swing.JRadioButton();
        g11_Ya_RadioButton = new javax.swing.JRadioButton();
        g11_Tidak_RadioButton = new javax.swing.JRadioButton();
        g12_Ya_RadioButton = new javax.swing.JRadioButton();
        g12_Tidak_RadioButton = new javax.swing.JRadioButton();
        g13_Ya_RadioButton = new javax.swing.JRadioButton();
        g13_Tidak_RadioButton = new javax.swing.JRadioButton();
        g14_Ya_RadioButton = new javax.swing.JRadioButton();
        g14_Tidak_RadioButton = new javax.swing.JRadioButton();
        g15_Ya_RadioButton = new javax.swing.JRadioButton();
        g15_Tidak_RadioButton = new javax.swing.JRadioButton();
        g16_Ya_RadioButton = new javax.swing.JRadioButton();
        g16_Tidak_RadioButton = new javax.swing.JRadioButton();
        g17_Ya_RadioButton = new javax.swing.JRadioButton();
        g17_Tidak_RadioButton = new javax.swing.JRadioButton();
        g18_Ya_RadioButton = new javax.swing.JRadioButton();
        g18_Tidak_RadioButton = new javax.swing.JRadioButton();
        g19_Ya_RadioButton = new javax.swing.JRadioButton();
        g19_Tidak_RadioButton = new javax.swing.JRadioButton();
        g20_Ya_RadioButton = new javax.swing.JRadioButton();
        g20_Tidak_RadioButton = new javax.swing.JRadioButton();
        g21_Ya_RadioButton = new javax.swing.JRadioButton();
        g21_Tidak_RadioButton = new javax.swing.JRadioButton();
        datasetPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        datasetTable = new javax.swing.JTable();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximizable(true);

        inputPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Dataset Penyakit Mata"));

        jLabel1.setText("No");

        jLabel2.setText("Sakit/nyeri kepala hebat");

        jLabel3.setText("Penglihatan kabur perlahan");

        jLabel4.setText("Silau");

        jLabel5.setText("Merah");

        jLabel6.setText("Nyeri");

        jLabel7.setText("Perut Mual");

        jLabel8.setText("Penglihatan berkabut (berasap)");

        jLabel9.setText("Lensa mata keruh");

        jLabel10.setText("Gatal");

        jLabel11.setText("Berair");

        jLabel12.setText("Belekan");

        jLabel13.setText("Kelopak bengkak");

        jLabel14.setText("Panas");

        jLabel15.setText("Mengganjal");

        jLabel16.setText("Lengket");

        jLabel17.setText("Merah jika terkena sinar matahari");

        jLabel18.setText("Tumbuh selaput pada mata");

        buttonGroupG1.add(g1_Ya_RadioButton);
        g1_Ya_RadioButton.setText("Ya");

        buttonGroupG1.add(g1_Tidak_RadioButton);
        g1_Tidak_RadioButton.setText("Tidak");

        buttonGroupG2.add(g2_Ya_RadioButton);
        g2_Ya_RadioButton.setText("Ya");

        buttonGroupG2.add(g2_Tidak_RadioButton);
        g2_Tidak_RadioButton.setText("Tidak");

        buttonGroupG3.add(g3_Ya_RadioButton);
        g3_Ya_RadioButton.setText("Ya");

        buttonGroupG3.add(g3_Tidak_RadioButton);
        g3_Tidak_RadioButton.setText("Tidak");

        buttonGroupG4.add(g4_Ya_RadioButton);
        g4_Ya_RadioButton.setText("Ya");

        buttonGroupG4.add(g4_Tidak_RadioButton);
        g4_Tidak_RadioButton.setText("Tidak");

        buttonGroupG5.add(g5_Ya_RadioButton);
        g5_Ya_RadioButton.setText("Ya");

        buttonGroupG5.add(g5_Tidak_RadioButton);
        g5_Tidak_RadioButton.setText("Tidak");

        buttonGroupG6.add(g6_Ya_RadioButton);
        g6_Ya_RadioButton.setText("Ya");

        buttonGroupG6.add(g6_Tidak_RadioButton);
        g6_Tidak_RadioButton.setText("Tidak");

        buttonGroupG7.add(g7_Ya_RadioButton);
        g7_Ya_RadioButton.setText("Ya");

        buttonGroupG7.add(g7_Tidak_RadioButton);
        g7_Tidak_RadioButton.setText("Tidak");

        buttonGroupG8.add(g8_Ya_RadioButton);
        g8_Ya_RadioButton.setText("Ya");

        buttonGroupG8.add(g8_Tidak_RadioButton);
        g8_Tidak_RadioButton.setText("Tidak");

        jLabel19.setText("Timbul bayangan");

        ubahButton.setText("Ubah");
        ubahButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ubahButtonActionPerformed(evt);
            }
        });

        hapusButton.setText("Hapus");
        hapusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusButtonActionPerformed(evt);
            }
        });

        jLabel23.setText("Penyakit Mata");

        jLabel21.setText("Kelopak mata timbul benjolan");

        penyakitComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Glaukoma", "Katarak", "Konjungtivitis", "Keratitis", "Pterigium", "Uveitis", "Dry Eyes", "Bleparitis", "Kalazion" }));

        tambahButton.setText("Tambah");
        tambahButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahButtonActionPerformed(evt);
            }
        });

        jLabel22.setText("Perih");

        jLabel20.setText("Usia > 50 tahun");

        buttonGroupG9.add(g9_Ya_RadioButton);
        g9_Ya_RadioButton.setText("Ya");

        buttonGroupG9.add(g9_Tidak_RadioButton);
        g9_Tidak_RadioButton.setText("Tidak");

        buttonGroupG10.add(g10_Ya_RadioButton);
        g10_Ya_RadioButton.setText("Ya");

        buttonGroupG10.add(g10_Tidak_RadioButton);
        g10_Tidak_RadioButton.setText("Tidak");

        buttonGroupG11.add(g11_Ya_RadioButton);
        g11_Ya_RadioButton.setText("Ya");

        buttonGroupG11.add(g11_Tidak_RadioButton);
        g11_Tidak_RadioButton.setText("Tidak");

        buttonGroupG12.add(g12_Ya_RadioButton);
        g12_Ya_RadioButton.setText("Ya");

        buttonGroupG12.add(g12_Tidak_RadioButton);
        g12_Tidak_RadioButton.setText("Tidak");

        buttonGroupG13.add(g13_Ya_RadioButton);
        g13_Ya_RadioButton.setText("Ya");

        buttonGroupG13.add(g13_Tidak_RadioButton);
        g13_Tidak_RadioButton.setText("Tidak");

        buttonGroupG14.add(g14_Ya_RadioButton);
        g14_Ya_RadioButton.setText("Ya");

        buttonGroupG14.add(g14_Tidak_RadioButton);
        g14_Tidak_RadioButton.setText("Tidak");

        buttonGroupG15.add(g15_Ya_RadioButton);
        g15_Ya_RadioButton.setText("Ya");

        buttonGroupG15.add(g15_Tidak_RadioButton);
        g15_Tidak_RadioButton.setText("Tidak");

        buttonGroupG16.add(g16_Ya_RadioButton);
        g16_Ya_RadioButton.setText("Ya");

        buttonGroupG16.add(g16_Tidak_RadioButton);
        g16_Tidak_RadioButton.setText("Tidak");

        buttonGroupG17.add(g17_Ya_RadioButton);
        g17_Ya_RadioButton.setText("Ya");

        buttonGroupG17.add(g17_Tidak_RadioButton);
        g17_Tidak_RadioButton.setText("Tidak");

        buttonGroupG18.add(g18_Ya_RadioButton);
        g18_Ya_RadioButton.setText("Ya");

        buttonGroupG18.add(g18_Tidak_RadioButton);
        g18_Tidak_RadioButton.setText("Tidak");

        buttonGroupG19.add(g19_Ya_RadioButton);
        g19_Ya_RadioButton.setText("Ya");

        buttonGroupG19.add(g19_Tidak_RadioButton);
        g19_Tidak_RadioButton.setText("Tidak");

        buttonGroupG20.add(g20_Ya_RadioButton);
        g20_Ya_RadioButton.setText("Ya");
        g20_Ya_RadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                g20_Ya_RadioButtonActionPerformed(evt);
            }
        });

        buttonGroupG20.add(g20_Tidak_RadioButton);
        g20_Tidak_RadioButton.setText("Tidak");

        buttonGroupG21.add(g21_Ya_RadioButton);
        g21_Ya_RadioButton.setText("Ya");

        buttonGroupG21.add(g21_Tidak_RadioButton);
        g21_Tidak_RadioButton.setText("Tidak");

        javax.swing.GroupLayout inputPanelLayout = new javax.swing.GroupLayout(inputPanel);
        inputPanel.setLayout(inputPanelLayout);
        inputPanelLayout.setHorizontalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(noTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(g5_Ya_RadioButton)
                            .addComponent(g6_Ya_RadioButton)
                            .addComponent(g2_Ya_RadioButton)
                            .addComponent(g1_Ya_RadioButton)
                            .addComponent(g4_Ya_RadioButton)
                            .addComponent(g3_Ya_RadioButton)
                            .addComponent(g7_Ya_RadioButton)
                            .addComponent(g8_Ya_RadioButton))
                        .addGap(18, 18, 18)
                        .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(g2_Tidak_RadioButton)
                            .addComponent(g1_Tidak_RadioButton)
                            .addComponent(g4_Tidak_RadioButton)
                            .addComponent(g3_Tidak_RadioButton)
                            .addComponent(g6_Tidak_RadioButton)
                            .addComponent(g5_Tidak_RadioButton)
                            .addComponent(g7_Tidak_RadioButton)
                            .addComponent(g8_Tidak_RadioButton))))
                .addGap(32, 32, 32)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel13)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18))
                .addGap(18, 18, 18)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(g10_Ya_RadioButton)
                    .addComponent(g9_Ya_RadioButton)
                    .addComponent(g11_Ya_RadioButton)
                    .addComponent(g12_Ya_RadioButton)
                    .addComponent(g13_Ya_RadioButton)
                    .addComponent(g14_Ya_RadioButton)
                    .addComponent(g15_Ya_RadioButton)
                    .addComponent(g16_Ya_RadioButton)
                    .addComponent(g17_Ya_RadioButton))
                .addGap(18, 18, 18)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(g14_Tidak_RadioButton)
                    .addComponent(g15_Tidak_RadioButton)
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(g9_Tidak_RadioButton, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(g10_Tidak_RadioButton))
                            .addComponent(g11_Tidak_RadioButton)
                            .addComponent(g12_Tidak_RadioButton)
                            .addComponent(g13_Tidak_RadioButton))
                        .addGap(27, 27, 27)
                        .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inputPanelLayout.createSequentialGroup()
                                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel21))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(inputPanelLayout.createSequentialGroup()
                                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel23))
                                .addGap(71, 71, 71)))
                        .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(inputPanelLayout.createSequentialGroup()
                                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(g21_Ya_RadioButton)
                                    .addGroup(inputPanelLayout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(g19_Ya_RadioButton)
                                            .addComponent(g18_Ya_RadioButton)))
                                    .addComponent(g20_Ya_RadioButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(g18_Tidak_RadioButton)
                                    .addComponent(g19_Tidak_RadioButton)
                                    .addComponent(g20_Tidak_RadioButton)
                                    .addComponent(g21_Tidak_RadioButton)))
                            .addComponent(penyakitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(g16_Tidak_RadioButton)
                            .addComponent(g17_Tidak_RadioButton))
                        .addGap(18, 18, 18)
                        .addComponent(tambahButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ubahButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hapusButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        inputPanelLayout.setVerticalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(noTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(g9_Ya_RadioButton)
                    .addComponent(g9_Tidak_RadioButton)
                    .addComponent(jLabel19)
                    .addComponent(g18_Ya_RadioButton)
                    .addComponent(g18_Tidak_RadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(g1_Ya_RadioButton)
                        .addComponent(g1_Tidak_RadioButton)
                        .addComponent(jLabel2)
                        .addComponent(g10_Ya_RadioButton)
                        .addComponent(g10_Tidak_RadioButton)
                        .addComponent(jLabel20)
                        .addComponent(g19_Ya_RadioButton)
                        .addComponent(g19_Tidak_RadioButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(g2_Ya_RadioButton)
                    .addComponent(g2_Tidak_RadioButton)
                    .addComponent(jLabel3)
                    .addComponent(jLabel12)
                    .addComponent(g11_Ya_RadioButton)
                    .addComponent(g11_Tidak_RadioButton)
                    .addComponent(jLabel21)
                    .addComponent(g20_Ya_RadioButton)
                    .addComponent(g20_Tidak_RadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(g3_Ya_RadioButton)
                    .addComponent(g3_Tidak_RadioButton)
                    .addComponent(jLabel4)
                    .addComponent(jLabel13)
                    .addComponent(g12_Ya_RadioButton)
                    .addComponent(g12_Tidak_RadioButton)
                    .addComponent(jLabel22)
                    .addComponent(g21_Ya_RadioButton)
                    .addComponent(g21_Tidak_RadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(g4_Ya_RadioButton)
                    .addComponent(g4_Tidak_RadioButton)
                    .addComponent(jLabel5)
                    .addComponent(jLabel14)
                    .addComponent(g13_Ya_RadioButton)
                    .addComponent(g13_Tidak_RadioButton)
                    .addComponent(jLabel23)
                    .addComponent(penyakitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(g5_Ya_RadioButton)
                    .addComponent(g5_Tidak_RadioButton)
                    .addComponent(jLabel6)
                    .addComponent(jLabel15)
                    .addComponent(g14_Ya_RadioButton)
                    .addComponent(g14_Tidak_RadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(g6_Ya_RadioButton)
                    .addComponent(g6_Tidak_RadioButton)
                    .addComponent(jLabel7)
                    .addComponent(jLabel16)
                    .addComponent(g15_Ya_RadioButton)
                    .addComponent(g15_Tidak_RadioButton))
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(g7_Ya_RadioButton)
                            .addComponent(g7_Tidak_RadioButton)
                            .addComponent(jLabel8)
                            .addComponent(jLabel17)
                            .addComponent(g16_Ya_RadioButton)
                            .addComponent(g16_Tidak_RadioButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(g8_Ya_RadioButton)
                            .addComponent(g8_Tidak_RadioButton)
                            .addComponent(jLabel9)
                            .addComponent(jLabel18)
                            .addComponent(g17_Ya_RadioButton)
                            .addComponent(g17_Tidak_RadioButton)))
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tambahButton)
                            .addComponent(ubahButton)
                            .addComponent(hapusButton))))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        datasetPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabel Dataset Penyakit Mata"));

        datasetTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(datasetTable);

        javax.swing.GroupLayout datasetPanelLayout = new javax.swing.GroupLayout(datasetPanel);
        datasetPanel.setLayout(datasetPanelLayout);
        datasetPanelLayout.setHorizontalGroup(
            datasetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(datasetPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        datasetPanelLayout.setVerticalGroup(
            datasetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(datasetPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(inputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(datasetPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        inputPanel.getAccessibleContext().setAccessibleName("");
        datasetPanel.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tambahButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahButtonActionPerformed
        // TODO add your handling code here:
        DatasetPojo dt = new DatasetPojo();
        dt.id  = Integer.valueOf(noTextField.getText());

        //gejala1
        if (g1_Ya_RadioButton.isSelected() == true) {
            dt.vector("g1",1);
        }
        if (g1_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g1",0);
        }

        //gejala2
        if (g2_Ya_RadioButton.isSelected() == true) {
            dt.vector("g2",1);
        }
        if (g2_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g2",0);
        }

        //gejala3
        if (g3_Ya_RadioButton.isSelected() == true) {
            dt.vector("g3",1);
        }
        if (g3_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g3",0);
        }

        //gejala4
        if (g4_Ya_RadioButton.isSelected() == true) {
            dt.vector("g4",1);
        }
        if (g4_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g4",0);
        }

        //gejala5
        if (g5_Ya_RadioButton.isSelected() == true) {
            dt.vector("g5",1);
        }
        if (g5_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g5",0);
        }

        //gejala6
        if (g6_Ya_RadioButton.isSelected() == true) {
            dt.vector("g6",1);
        }
        if (g6_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g6",0);
        }

        //gejala7
        if (g7_Ya_RadioButton.isSelected() == true) {
            dt.vector("g7",1);
        }
        if (g7_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g7",0);
        }

        //gejala8
        if (g8_Ya_RadioButton.isSelected() == true) {
            dt.vector("g8",1);
        }
        if (g8_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g8",0);
        }

        //gejala9
        if (g9_Ya_RadioButton.isSelected() == true) {
            dt.vector("g9",1);
        }
        if (g9_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g9",0);
        }

        //gejala10
        if (g10_Ya_RadioButton.isSelected() == true) {
            dt.vector("g10",1);
        }
        if (g10_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g10",0);
        }

        //gejala11
        if (g11_Ya_RadioButton.isSelected() == true) {
            dt.vector("g11",1);
        }
        if (g11_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g11",0);
        }

        //gejala12
        if (g12_Ya_RadioButton.isSelected() == true) {
            dt.vector("g12",1);
        }
        if (g12_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g12",0);
        }

        //gejala13
        if (g13_Ya_RadioButton.isSelected() == true) {
            dt.vector("g13",1);
        }
        if (g13_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g13",0);
        }

        //gejala14
        if (g14_Ya_RadioButton.isSelected() == true) {
            dt.vector("g14",1);
        }
        if (g14_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g14",0);
        }

        //gejala15
        if (g15_Ya_RadioButton.isSelected() == true) {
            dt.vector("g15",1);
        }
        if (g15_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g15",0);
        }

        //gejala16
        if (g16_Ya_RadioButton.isSelected() == true) {
            dt.vector("g16",1);
        }
        if (g16_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g16",0);
        }

        //gejala17
        if (g17_Ya_RadioButton.isSelected() == true) {
            dt.vector("g17",1);
        }
        if (g17_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g17",0);
        }

        //gejala18
        if (g18_Ya_RadioButton.isSelected() == true) {
            dt.vector("g18",1);
        }
        if (g18_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g18",0);
        }

        //gejala19
        if (g19_Ya_RadioButton.isSelected() == true) {
            dt.vector("g19",1);
        }
        if (g19_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g19",0);
        }

        //gejala20
        if (g20_Ya_RadioButton.isSelected() == true) {
            dt.vector("g20",1);
        }
        if (g20_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g20",0);
        }

        //gejala21
        if (g21_Ya_RadioButton.isSelected() == true) {
            dt.vector("g21",1);
        }
        if (g21_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g21",0);
        }

        //target
        switch(penyakitComboBox.getSelectedItem().toString())
        {
            case "Glaukoma"             : dt.target = 1;break;
            case "Katarak"              : dt.target = 2;break;
            case "Konjungtivitis"       : dt.target = 3;break;
            case "Keratitis"            : dt.target = 4;break;
            case "Pterigium"            : dt.target = 5;break;
            case "Uveitis"              : dt.target = 6;break;
            case "Dry Eyes"             : dt.target = 7;break;
            case "Bleparitis"           : dt.target = 8;break;
            default                     : dt.target = 9;break;
        }

        //balasan
        int balasan = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menambahkan data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (balasan == JOptionPane.YES_OPTION) {
            DatasetDao.insert(Settings.DB, dt);
            JOptionPane.showMessageDialog(null, "Data Berhasil Ditambah");
            this.readTable();
        } else {
            JOptionPane.showMessageDialog(null, "Data Batal Ditambah");
            this.readTable();
        }
    }//GEN-LAST:event_tambahButtonActionPerformed

    private void hapusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusButtonActionPerformed
        // TODO add your handling code here:
        String no = noTextField.getText();
        
        //balasan
        int balasan = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (balasan == JOptionPane.YES_OPTION) {
            DatasetDao.delete(Settings.DB, Integer.valueOf(no));
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
            this.readTable();
        } else {
            JOptionPane.showMessageDialog(null, "Data Batal Dihapus");
            this.readTable();
        }
    }//GEN-LAST:event_hapusButtonActionPerformed


    private void ubahButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ubahButtonActionPerformed
        // TODO add your handling code here:
        DatasetPojo dt = new DatasetPojo();
        dt.id  = Integer.valueOf(noTextField.getText());

        //gejala1
        if (g1_Ya_RadioButton.isSelected() == true) {
            dt.vector("g1",1);
        }
        if (g1_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g1",0);
        }

        //gejala2
        if (g2_Ya_RadioButton.isSelected() == true) {
            dt.vector("g2",1);
        }
        if (g2_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g2",0);
        }

        //gejala3
        if (g3_Ya_RadioButton.isSelected() == true) {
            dt.vector("g3",1);
        }
        if (g3_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g3",0);
        }

        //gejala4
        if (g4_Ya_RadioButton.isSelected() == true) {
            dt.vector("g4",1);
        }
        if (g4_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g4",0);
        }

        //gejala5
        if (g5_Ya_RadioButton.isSelected() == true) {
            dt.vector("g5",1);
        }
        if (g5_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g5",0);
        }

        //gejala6
        if (g6_Ya_RadioButton.isSelected() == true) {
            dt.vector("g6",1);
        }
        if (g6_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g6",0);
        }

        //gejala7
        if (g7_Ya_RadioButton.isSelected() == true) {
            dt.vector("g7",1);
        }
        if (g7_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g7",0);
        }

        //gejala8
        if (g8_Ya_RadioButton.isSelected() == true) {
            dt.vector("g8",1);
        }
        if (g8_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g8",0);
        }

        //gejala9
        if (g9_Ya_RadioButton.isSelected() == true) {
            dt.vector("g9",1);
        }
        if (g9_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g9",0);
        }

        //gejala10
        if (g10_Ya_RadioButton.isSelected() == true) {
            dt.vector("g10",1);
        }
        if (g10_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g10",0);
        }

        //gejala11
        if (g11_Ya_RadioButton.isSelected() == true) {
            dt.vector("g11",1);
        }
        if (g11_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g11",0);
        }

        //gejala12
        if (g12_Ya_RadioButton.isSelected() == true) {
            dt.vector("g12",1);
        }
        if (g12_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g12",0);
        }

        //gejala13
        if (g13_Ya_RadioButton.isSelected() == true) {
            dt.vector("g13",1);
        }
        if (g13_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g13",0);
        }

        //gejala14
        if (g14_Ya_RadioButton.isSelected() == true) {
            dt.vector("g14",1);
        }
        if (g14_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g14",0);
        }

        //gejala15
        if (g15_Ya_RadioButton.isSelected() == true) {
            dt.vector("g15",1);
        }
        if (g15_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g15",0);
        }

        //gejala16
        if (g16_Ya_RadioButton.isSelected() == true) {
            dt.vector("g16",1);
        }
        if (g16_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g16",0);
        }

        //gejala17
        if (g17_Ya_RadioButton.isSelected() == true) {
            dt.vector("g17",1);
        }
        if (g17_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g17",0);
        }

        //gejala18
        if (g18_Ya_RadioButton.isSelected() == true) {
            dt.vector("g18",1);
        }
        if (g18_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g18",0);
        }

        //gejala19
        if (g19_Ya_RadioButton.isSelected() == true) {
            dt.vector("g19",1);
        }
        if (g19_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g19",0);
        }

        //gejala20
        if (g20_Ya_RadioButton.isSelected() == true) {
            dt.vector("g20",1);
        }
        if (g20_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g20",0);
        }

        //gejala21
        if (g21_Ya_RadioButton.isSelected() == true) {
            dt.vector("g21",1);
        }
        if (g21_Tidak_RadioButton.isSelected() == true) {
            dt.vector("g21",0);
        }

        //target
        switch(penyakitComboBox.getSelectedItem().toString())
        {
            case "Glaukoma"             : dt.target = 1;break;
            case "Katarak"              : dt.target = 2;break;
            case "Konjungtivitis"       : dt.target = 3;break;
            case "Keratitis"            : dt.target = 4;break;
            case "Pterigium"            : dt.target = 5;break;
            case "Uveitis"              : dt.target = 6;break;
            case "Dry Eyes"             : dt.target = 7;break;
            case "Bleparitis"           : dt.target = 8;break;
            default                     : dt.target = 9;break;
        }

        int balasan = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin mengubah data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (balasan == JOptionPane.YES_OPTION) {
             DatasetDao.insert(Settings.DB, dt);
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
            this.readTable();
        } else {
            JOptionPane.showMessageDialog(null, "Data Batal Diubah");
            this.readTable();
        }

    }//GEN-LAST:event_ubahButtonActionPerformed

    private void g20_Ya_RadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_g20_Ya_RadioButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_g20_Ya_RadioButtonActionPerformed


    public interface InteractionListener
    {

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupG1;
    private javax.swing.ButtonGroup buttonGroupG10;
    private javax.swing.ButtonGroup buttonGroupG11;
    private javax.swing.ButtonGroup buttonGroupG12;
    private javax.swing.ButtonGroup buttonGroupG13;
    private javax.swing.ButtonGroup buttonGroupG14;
    private javax.swing.ButtonGroup buttonGroupG15;
    private javax.swing.ButtonGroup buttonGroupG16;
    private javax.swing.ButtonGroup buttonGroupG17;
    private javax.swing.ButtonGroup buttonGroupG18;
    private javax.swing.ButtonGroup buttonGroupG19;
    private javax.swing.ButtonGroup buttonGroupG2;
    private javax.swing.ButtonGroup buttonGroupG20;
    private javax.swing.ButtonGroup buttonGroupG21;
    private javax.swing.ButtonGroup buttonGroupG3;
    private javax.swing.ButtonGroup buttonGroupG4;
    private javax.swing.ButtonGroup buttonGroupG5;
    private javax.swing.ButtonGroup buttonGroupG6;
    private javax.swing.ButtonGroup buttonGroupG7;
    private javax.swing.ButtonGroup buttonGroupG8;
    private javax.swing.ButtonGroup buttonGroupG9;
    private javax.swing.JPanel datasetPanel;
    private javax.swing.JTable datasetTable;
    private javax.swing.JRadioButton g10_Tidak_RadioButton;
    private javax.swing.JRadioButton g10_Ya_RadioButton;
    private javax.swing.JRadioButton g11_Tidak_RadioButton;
    private javax.swing.JRadioButton g11_Ya_RadioButton;
    private javax.swing.JRadioButton g12_Tidak_RadioButton;
    private javax.swing.JRadioButton g12_Ya_RadioButton;
    private javax.swing.JRadioButton g13_Tidak_RadioButton;
    private javax.swing.JRadioButton g13_Ya_RadioButton;
    private javax.swing.JRadioButton g14_Tidak_RadioButton;
    private javax.swing.JRadioButton g14_Ya_RadioButton;
    private javax.swing.JRadioButton g15_Tidak_RadioButton;
    private javax.swing.JRadioButton g15_Ya_RadioButton;
    private javax.swing.JRadioButton g16_Tidak_RadioButton;
    private javax.swing.JRadioButton g16_Ya_RadioButton;
    private javax.swing.JRadioButton g17_Tidak_RadioButton;
    private javax.swing.JRadioButton g17_Ya_RadioButton;
    private javax.swing.JRadioButton g18_Tidak_RadioButton;
    private javax.swing.JRadioButton g18_Ya_RadioButton;
    private javax.swing.JRadioButton g19_Tidak_RadioButton;
    private javax.swing.JRadioButton g19_Ya_RadioButton;
    private javax.swing.JRadioButton g1_Tidak_RadioButton;
    private javax.swing.JRadioButton g1_Ya_RadioButton;
    private javax.swing.JRadioButton g20_Tidak_RadioButton;
    private javax.swing.JRadioButton g20_Ya_RadioButton;
    private javax.swing.JRadioButton g21_Tidak_RadioButton;
    private javax.swing.JRadioButton g21_Ya_RadioButton;
    private javax.swing.JRadioButton g2_Tidak_RadioButton;
    private javax.swing.JRadioButton g2_Ya_RadioButton;
    private javax.swing.JRadioButton g3_Tidak_RadioButton;
    private javax.swing.JRadioButton g3_Ya_RadioButton;
    private javax.swing.JRadioButton g4_Tidak_RadioButton;
    private javax.swing.JRadioButton g4_Ya_RadioButton;
    private javax.swing.JRadioButton g5_Tidak_RadioButton;
    private javax.swing.JRadioButton g5_Ya_RadioButton;
    private javax.swing.JRadioButton g6_Tidak_RadioButton;
    private javax.swing.JRadioButton g6_Ya_RadioButton;
    private javax.swing.JRadioButton g7_Tidak_RadioButton;
    private javax.swing.JRadioButton g7_Ya_RadioButton;
    private javax.swing.JRadioButton g8_Tidak_RadioButton;
    private javax.swing.JRadioButton g8_Ya_RadioButton;
    private javax.swing.JRadioButton g9_Tidak_RadioButton;
    private javax.swing.JRadioButton g9_Ya_RadioButton;
    private javax.swing.JButton hapusButton;
    private javax.swing.JPanel inputPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField noTextField;
    private javax.swing.JComboBox<String> penyakitComboBox;
    private javax.swing.JButton tambahButton;
    private javax.swing.JButton ubahButton;
    // End of variables declaration//GEN-END:variables
}
