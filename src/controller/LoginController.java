/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author HELIOS
 */

import model.User;
import view.MainFrame;
import javax.swing.JOptionPane;
import java.awt.CardLayout;

public class LoginController {

    private final MainFrame view;

    public LoginController(MainFrame view) {
        this.view = view;
    }

    public void login(String username, String password) {

        username = (username == null) ? "" : username.trim();
        password = (password == null) ? "" : password.trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Username and Password required.");
            return;
        }

        if (!User.authenticate(username, password)) {
            JOptionPane.showMessageDialog(view, "Invalid credentials.");
            return;
        }

        CardLayout cl = (CardLayout) view.getMainCardPanel().getLayout();
        cl.show(view.getMainCardPanel(), "card3");
    }
}
