/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemagestioninventario;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.TableCellEditor;
/**
 *
 * @author Ancor
 */

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;

    public ButtonEditor(JTable table, View v) {
        button = new JButton();
        button.setOpaque(true);
        button.setFont(new Font("Microsoft YaHei Light", Font.PLAIN, 12));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                fireEditingStopped();
                if (label.equals("Configure")) {
                    AddProduct createProductFrame = new AddProduct(false, v);
                    v.setEnabled(false);
                } else if (label.equals("Delete")) {
                    v.RemoveProduct(table.getSelectedRow());
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    public void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
