/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import static model.Shift.values;

/**
 *
 * @author HELIOS
 */
public enum Shift {
    MORNING("Morning (4 AM - 12 PM)"),
    DAY("Day (12 PM - 8 PM)"),
    NIGHT("Night (8 PM - 4 AM)");

    private final String label;

    Shift(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static Shift fromLabel(String label) {
        for (Shift s : values()) {
            if (s.getLabel().equals(label)) return s;
        }
        throw new IllegalArgumentException("Invalid shift label: " + label);
    }
}
