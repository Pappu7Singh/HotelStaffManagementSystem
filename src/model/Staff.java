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
    private int id;
    private String name;
    private String position;
    private Shift shift;
    private String contact;

    public Staff(int id, String name, String position, Shift shift, String contact) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.shift = shift;
        this.contact = contact;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPosition() { return position; }
    public Shift getShift() { return shift; }
    public String getContact() { return contact; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPosition(String position) { this.position = position; }
    public void setShift(Shift shift) { this.shift = shift; }
    public void setContact(String contact) { this.contact = contact; }
}
