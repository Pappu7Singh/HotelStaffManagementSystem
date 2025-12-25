/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HELIOS
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Stack;

public class StaffManager {
    private final ArrayList<Staff> staffList = new ArrayList<>();
    private final Deque<Staff> recentQueue = new ArrayDeque<>(); // Queue for last 5 added
    private final Stack<Staff> deletedStack = new Stack<>();     // Undo last delete

    public List<Staff> getAll() {
        return staffList;
    }

    public Deque<Staff> getRecentQueue() {
        return recentQueue;
    }

    public Stack<Staff> getDeletedStack() {
        return deletedStack;
    }

    public void preloadSampleData() {
        // Minimum 5 records on open
        addStaffInternal(new Staff(101, "Asha Shrestha", "Receptionist", Shift.MORNING, "9800000001"));
        addStaffInternal(new Staff(102, "Bibek Rai", "Cleaner", Shift.DAY, "9800000002"));
        addStaffInternal(new Staff(103, "Sita Gurung", "Chef", Shift.NIGHT, "9800000003"));
        addStaffInternal(new Staff(104, "Nischal Thapa", "Guard", Shift.NIGHT, "9800000004"));
        addStaffInternal(new Staff(105, "Pooja Karki", "Manager", Shift.DAY, "9800000005"));
    }

    // Used internally for preload and for controller after validation
    public void addStaffInternal(Staff s) {
        staffList.add(s);
        enqueueRecent(s);
    }

    public void enqueueRecent(Staff s) {
        recentQueue.addLast(s);
        while (recentQueue.size() > 5) {
            recentQueue.removeFirst();
        }
    }

    public void pushDeleted(Staff s) {
        deletedStack.push(s);
    }

    public Staff popDeletedOrNull() {
        if (deletedStack.isEmpty()) return null;
        return deletedStack.pop();
    }
}
