/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HELIOS
 */

public class Staff {

    private final int id;
    private final String name;
    private final String position;
    private final String shift;
    private final String contact;

    public Staff(int id, String name, String position, String shift, String contact) {

        if (id <= 0)
            throw new IllegalArgumentException("ID must be positive.");

        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Name cannot be empty.");

        if (position == null || position.trim().isEmpty())
            throw new IllegalArgumentException("Position cannot be empty.");

        if (!shift.equalsIgnoreCase("Morning")
                && !shift.equalsIgnoreCase("Day")
                && !shift.equalsIgnoreCase("Night"))
            throw new IllegalArgumentException("Shift must be Morning, Day, or Night.");

        if (contact == null || !contact.matches("\\d{9,15}"))
            throw new IllegalArgumentException("Invalid contact number.");

        this.id = id;
        this.name = name.trim();
        this.position = position.trim();
        this.shift = shift;
        this.contact = contact;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPosition() { return position; }
    public String getShift() { return shift; }
    public String getContact() { return contact; }
}
