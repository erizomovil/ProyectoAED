/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.sistemagestioninventario;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ancor
 */
public class View extends javax.swing.JFrame {

    /**
     * Creates new form View
     */
    private DefaultTableModel tableModel;
    File loadedFile;
    ProductList productList = new ProductList();
    TransformData transformData = new TransformData();

    public View() {
        initComponents();
        MainPanel.setVisible(false);
        LoadTable();
        this.setResizable(false);
    }

    public void AddProduct(Product product) {
        productList.addProduct(product);
        this.setEnabled(true);
        LoadTable();
    }

    public void UpdateProduct(Product editedProduct, int id) {
        productList.updateProductAt(id, editedProduct);
        this.setEnabled(true);
        LoadTable();
    }

    public void RemoveProduct(int x) {
        productList.getProducts().remove(x);
        LoadTable();
    }

    private void ClearTable() {
        if (jTable2.getModel() instanceof DefaultTableModel) {
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);
            productList.getProducts().clear();
        }
    }

    private void LoadTable() {

        Object[][] data = convertProductListToData(productList);

        String[] columnNames = {"Product Name", "Quantity", "Price", "Category", "Configuration", "Delete"};

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 4 || column == 5) {
                    return true;
                }
                return false;
            }
        };

        jTable2.setModel(tableModel);

        jTable2.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        jTable2.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(jTable2, this));

        jTable2.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        jTable2.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(jTable2, this));
    }

    private Object[][] convertProductListToData(ProductList productList) {

        int productCount = productList.getProducts().size();
        Object[][] data = new Object[productCount][6];

        for (int i = 0; i < productCount; i++) {
            Product product = productList.getProducts().get(i);
            data[i][0] = product.getName();
            data[i][1] = product.getQuantity();
            data[i][2] = product.getPrice();
            data[i][3] = product.getCategory();
            data[i][4] = "Configure";
            data[i][5] = "Delete";

        }
        return data;
    }

    private void LoadFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load");

        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos (.dat, .csv, .xml, .json)", "dat", "csv", "xml", "json"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            String filePath = fileToLoad.getAbsolutePath();
            String fileExtension = "";

            String[] acceptedExtensions = {"dat", "csv", "xml", "json"};
            boolean hasValidExtension = false;

            for (String ext : acceptedExtensions) {
                if (filePath.toLowerCase().endsWith("." + ext)) {
                    fileExtension = ext;
                    hasValidExtension = true;
                    break;
                }
            }

            MainPanel.setVisible(true);
            jLabel3.setVisible(false);
            switch (fileExtension) {
                case "dat":
                    productList.getProducts().clear();
                    productList.addList(transformData.DatToObject(filePath));
                    break;
                case "csv":
                    productList.getProducts().clear();
                    productList.addList(transformData.CSVToObject(filePath));
                    break;
                case "xml":
                    productList.getProducts().clear();
                    productList.addList(transformData.XMLToObject(filePath));
                    break;
                case "json":
                    productList.getProducts().clear();
                    productList.addList(transformData.JsonToObject(filePath));
                    break;
                default:
                    new ErrorHandler("Extension not supported", null);
            }
            FileName.setText(fileToLoad.getName());
            LoadTable();
            loadedFile = fileToLoad;
        }
    }

    private void SaveFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save as");

        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos (.dat, .csv, .xml, .json)", "dat", "csv", "xml", "json"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            String fileExtension = "";

            String[] acceptedExtensions = {"dat", "csv", "xml", "json"};
            boolean hasValidExtension = false;
            boolean hasExtension = filePath.contains(".");

            if (hasExtension) {
                for (String ext : acceptedExtensions) {
                    if (filePath.toLowerCase().endsWith("." + ext)) {
                        fileExtension = ext;
                        hasValidExtension = true;
                        break;
                    }
                }
                if (!hasValidExtension) {
                    new ErrorHandler("Extension not supported", null);
                }
            } else {
                filePath += ".dat";
                fileExtension = "dat";
            }
            switch (fileExtension) {
                case "dat":
                    transformData.ObjectTODat(productList, filePath);
                    break;
                case "csv":
                    transformData.ObjectToCSV(productList, filePath);
                    break;
                case "xml":
                    transformData.ObjectToXML(productList, filePath);
                    break;
                case "json":
                    transformData.ObjectToJSON(productList, filePath);
                    break;
                default:
                    new ErrorHandler("Extension not supported", null);
            }
            loadedFile = fileToSave;
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

        jPanel3 = new javax.swing.JPanel();
        NewFileBtn = new javax.swing.JButton();
        SaveBtn = new javax.swing.JButton();
        LoadBtn = new javax.swing.JButton();
        ExitBtn = new javax.swing.JButton();
        DeleteBtn = new javax.swing.JButton();
        MainPanel = new javax.swing.JPanel();
        FileName = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        AddBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(217, 217, 217));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        NewFileBtn.setText("New File");
        NewFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewFileBtnActionPerformed(evt);
            }
        });

        SaveBtn.setText("Save");
        SaveBtn.setEnabled(false);
        SaveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveBtnActionPerformed(evt);
            }
        });

        LoadBtn.setText("Load");
        LoadBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoadBtnActionPerformed(evt);
            }
        });

        ExitBtn.setText("Exit");
        ExitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitBtnActionPerformed(evt);
            }
        });

        DeleteBtn.setText("Delete File");
        DeleteBtn.setEnabled(false);
        DeleteBtn.setMaximumSize(new java.awt.Dimension(72, 23));
        DeleteBtn.setMinimumSize(new java.awt.Dimension(72, 23));
        DeleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LoadBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(NewFileBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SaveBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ExitBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(DeleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(NewFileBtn)
                .addGap(18, 18, 18)
                .addComponent(LoadBtn)
                .addGap(18, 18, 18)
                .addComponent(SaveBtn)
                .addGap(18, 18, 18)
                .addComponent(DeleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(408, 408, 408)
                .addComponent(ExitBtn)
                .addContainerGap())
        );

        MainPanel.setBackground(new java.awt.Color(255, 255, 255));
        MainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        FileName.setFont(new java.awt.Font("Microsoft JhengHei Light", 0, 18)); // NOI18N
        FileName.setText("File Name . . .");

        jTable2.setFont(new java.awt.Font("Microsoft YaHei Light", 0, 12)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Name", "Quantity", "Price", "Category", "Configure"
            }
        ));
        jTable2.setFocusable(false);
        jScrollPane1.setViewportView(jTable2);

        AddBtn.setText("Add Product");
        AddBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MainPanelLayout = new javax.swing.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(MainPanelLayout.createSequentialGroup()
                        .addComponent(FileName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(AddBtn))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 748, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );
        MainPanelLayout.setVerticalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(FileName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AddBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Microsoft JhengHei Light", 0, 12)); // NOI18N
        jLabel3.setText("Cree un archivo nuevo o importe un fichero de objetos, JSON, CSV o XML");
        jLabel3.setToolTipText("");
        jLabel3.setAutoscrolls(true);
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(MainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(242, 242, 242))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NewFileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewFileBtnActionPerformed
        ClearTable();
        FileName.setText("New File");
        SaveBtn.setEnabled(true);
        MainPanel.setVisible(true);
        jLabel3.setVisible(false);
    }//GEN-LAST:event_NewFileBtnActionPerformed

    private void DeleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteBtnActionPerformed
        ClearTable();
        loadedFile.delete();
        MainPanel.setVisible(false);
        jLabel3.setVisible(true);
        SaveBtn.setEnabled(false);
        DeleteBtn.setEnabled(false);
    }//GEN-LAST:event_DeleteBtnActionPerformed

    private void ExitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitBtnActionPerformed
        ClearTable();
        SaveBtn.setEnabled(false);
        MainPanel.setVisible(false);
        jLabel3.setVisible(true);
        DeleteBtn.setEnabled(false);
    }//GEN-LAST:event_ExitBtnActionPerformed

    private void LoadBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoadBtnActionPerformed
        LoadFile();
        DeleteBtn.setEnabled(true);
        SaveBtn.setEnabled(true);
    }//GEN-LAST:event_LoadBtnActionPerformed

    private void SaveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveBtnActionPerformed
        SaveFile();
        DeleteBtn.setEnabled(true);
    }//GEN-LAST:event_SaveBtnActionPerformed

    private void AddBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddBtnActionPerformed
        AddProduct createProductFrame = new AddProduct(true, this);
        createProductFrame.setVisible(true);
        createProductFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createProductFrame.setLocationRelativeTo(null);
        this.setEnabled(false);
    }//GEN-LAST:event_AddBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo look : looks) {
            System.out.println(look.getName() + " : " + look.getClassName());
        }
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                View view = new View();
                view.setVisible(true);
                view.setLocationRelativeTo(null);
            }
        });
    }

    public JTable getjTable2() {
        return jTable2;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddBtn;
    private javax.swing.JButton DeleteBtn;
    private javax.swing.JButton ExitBtn;
    private javax.swing.JLabel FileName;
    private javax.swing.JButton LoadBtn;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JButton NewFileBtn;
    private javax.swing.JButton SaveBtn;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
