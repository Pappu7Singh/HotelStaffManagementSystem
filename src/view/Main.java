/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author HELIOS
 */

import controller.StaffController;
import model.StaffManager;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UITheme.install();

            StaffManager manager = new StaffManager();
            manager.preloadSampleData(); // at least 5 records loaded

            StaffController staffController = new StaffController(manager);

            HomeFrame frame = new HomeFrame(manager, staffController);
            frame.setVisible(true);
        });
    }
}
