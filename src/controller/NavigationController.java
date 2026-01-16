/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author HELIOS
 */
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class NavigationController {

    private final JPanel content;

    public NavigationController(JPanel content) {
        this.content = content;
    }

    public void show(String panel) {
        if (!(content.getLayout() instanceof CardLayout)) {
            JOptionPane.showMessageDialog(content, "Invalid layout.");
            return;
        }
        ((CardLayout) content.getLayout()).show(content, panel);
    }
}

