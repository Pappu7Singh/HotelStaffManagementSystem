/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author HELIOS
 */

import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class StaffController {

    private final StaffModel model;

    public StaffController(StaffModel model) {
        this.model = model;
    }

    public void loadTable(JTable table, List<Staff> list) {
        DefaultTableModel tm = (DefaultTableModel) table.getModel();
        tm.setRowCount(0);

        for (Staff s : list) {
            tm.addRow(new Object[]{
                s.getId(), s.getName(), s.getPosition(),
                s.getShift(), s.getContact()
            });
        }
    }

    public void addStaffAuto(String name, String position, String shift, String contact, JTable table) {
        try {
            int id = model.getNextId();
            model.addStaff(new Staff(id, name, position, shift, contact));
            loadTable(table, model.getStaffList());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(table, e.getMessage());
        }
    }

    public void editStaff(JTable table, String name, String position, String shift, String contact) {

        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(table, "Select a record to edit.");
            return;
        }

        try {
            int id = (int) table.getValueAt(row, 0);
            model.updateStaffById(id, new Staff(id, name, position, shift, contact));
            loadTable(table, model.getStaffList());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(table, e.getMessage());
        }
    }

    //  CLEAR = DELETE SELECTED 
    public void deleteSelected(JTable table) {

        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(table, "Select a record to delete.");
            return;
        }

        int id = (int) table.getValueAt(row, 0);
        model.deleteStaffById(id);
        loadTable(table, model.getStaffList());
    }

    public void undoDelete(JTable table) {
        model.undoDelete();
        loadTable(table, model.getStaffList());
    }
}

