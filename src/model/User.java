/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HELIOS
 */

public class User {
    public static boolean authenticate(String username, String password) {
        if (username == null || password == null) return false;
        return username.equals("admin") && password.equals("admin123");
    }
}


