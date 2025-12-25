package view;

import controller.StaffController;
import model.Shift;
import model.Staff;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author HELIOS
 */
public class StaffFormDialog extends JDialog {
    private JTextField idField;
    private JTextField nameField;
    private JTextField positionField;
    private JComboBox<Shift> shiftCombo;
    private JTextField contactField;
    private JButton saveButton;
    private JButton cancelButton;
    
    private StaffController controller;
    private Staff editingStaff = null;
    private boolean saved = false;
    
    public StaffFormDialog(Frame parent, StaffController controller, Staff editStaff) {
        super(parent, editStaff == null ? "Add Staff" : "Edit Staff", true);
        this.controller = controller;
        this.editingStaff = editStaff;
        
        initComponents();
        setLocationRelativeTo(parent);
        
        if (editingStaff != null) {
            loadStaffData();
        }
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        formPanel.add(new JLabel("ID:"));
        idField = new JTextField(15);
        formPanel.add(idField);
        
        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField(15);
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("Position:"));
        positionField = new JTextField(15);
        formPanel.add(positionField);
        
        formPanel.add(new JLabel("Shift:"));
        shiftCombo = new JComboBox<>(Shift.values());
        formPanel.add(shiftCombo);
        
        formPanel.add(new JLabel("Contact:"));
        contactField = new JTextField(15);
        formPanel.add(contactField);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(e -> onSave());
        cancelButton.addActionListener(e -> onCancel());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        pack();
    }
    
    private void loadStaffData() {
        idField.setText(String.valueOf(editingStaff.getId()));
        nameField.setText(editingStaff.getName());
        positionField.setText(editingStaff.getPosition());
        shiftCombo.setSelectedItem(editingStaff.getShift());
        contactField.setText(editingStaff.getContact());
    }
    
    private void onSave() {
        String idText = idField.getText();
        String name = nameField.getText();
        String position = positionField.getText();
        Shift shift = (Shift) shiftCombo.getSelectedItem();
        String contact = contactField.getText();
        
        Integer oldId = editingStaff != null ? editingStaff.getId() : null;
        String error = controller.validateStaff(idText, name, position, shift, contact, 
                                                 editingStaff != null, oldId);
        
        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "Validation Error", 
                                         JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int id = Integer.parseInt(idText.trim());
        Staff staff = new Staff(id, name.trim(), position.trim(), shift, contact.trim());
        
        if (editingStaff != null) {
            controller.updateStaff(editingStaff.getId(), staff);
        } else {
            controller.addStaff(staff);
        }
        
        saved = true;
        dispose();
    }
    
    private void onCancel() {
        saved = false;
        dispose();
    }
    
    public boolean wasSaved() {
        return saved;
    }
}

