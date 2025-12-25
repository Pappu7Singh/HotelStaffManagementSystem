/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author HELIOS
 */

import model.Shift;
import model.Staff;
import model.StaffManager;

import java.util.LinkedList;
import java.util.List;

public class StaffController {
    private final StaffManager manager;

    public StaffController(StaffManager manager) {
        this.manager = manager;
    }

    // ---------- Validation ----------
    public String validateStaff(String idText, String name, String position, Shift shift, String contact, boolean isEdit, Integer editingOldId) {
        if (idText == null || idText.trim().isEmpty()) return "ID is required.";
        int id;
        try {
            id = Integer.parseInt(idText.trim());
        } catch (NumberFormatException e) {
            return "ID must be a number.";
        }
        if (id <= 0) return "ID must be positive.";

        if (name == null || name.trim().isEmpty()) return "Name is required.";
        if (position == null || position.trim().isEmpty()) return "Position is required.";
        if (shift == null) return "Shift must be selected.";

        if (contact == null || contact.trim().isEmpty()) return "Contact is required.";
        String c = contact.trim();
        if (!c.matches("\\d+")) return "Contact must contain only digits.";
        if (c.length() < 7 || c.length() > 15) return "Contact must be 7 to 15 digits long.";

        // Unique ID check
        for (Staff s : manager.getAll()) {
            if (s.getId() == id) {
                if (isEdit && editingOldId != null && editingOldId == id) {
                    // same record, ok
                } else {
                    return "ID already exists. Please use a unique ID.";
                }
            }
        }
        return null; // valid
    }

    // ---------- CRUD ----------
    public void addStaff(Staff s) {
        manager.addStaffInternal(s);
    }

    public boolean updateStaff(int oldId, Staff updated) {
        for (Staff s : manager.getAll()) {
            if (s.getId() == oldId) {
                s.setId(updated.getId());
                s.setName(updated.getName());
                s.setPosition(updated.getPosition());
                s.setShift(updated.getShift());
                s.setContact(updated.getContact());
                manager.enqueueRecent(s); // treat edit as recent activity
                return true;
            }
        }
        return false;
    }

    public Staff deleteStaffById(int id) {
        List<Staff> list = manager.getAll();
        for (int i = 0; i < list.size(); i++) {
            Staff s = list.get(i);
            if (s.getId() == id) {
                list.remove(i);
                manager.pushDeleted(s);
                return s;
            }
        }
        return null;
    }

    public Staff undoLastDelete() {
        Staff s = manager.popDeletedOrNull();
        if (s != null) {
            manager.addStaffInternal(s);
        }
        return s;
    }

    // ---------- Sorting (manual algorithms) ----------
    // Selection Sort by ID
    public void sortById(boolean ascending) {
        List<Staff> list = manager.getAll();
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            int bestIdx = i;
            for (int j = i + 1; j < n; j++) {
                int a = list.get(j).getId();
                int b = list.get(bestIdx).getId();
                boolean better = ascending ? (a < b) : (a > b);
                if (better) bestIdx = j;
            }
            if (bestIdx != i) {
                Staff tmp = list.get(i);
                list.set(i, list.get(bestIdx));
                list.set(bestIdx, tmp);
            }
        }
    }

    // Insertion Sort by Name
    public void sortByName(boolean ascending) {
        List<Staff> list = manager.getAll();
        for (int i = 1; i < list.size(); i++) {
            Staff key = list.get(i);
            int j = i - 1;
            while (j >= 0) {
                String left = list.get(j).getName().toLowerCase();
                String right = key.getName().toLowerCase();
                int cmp = left.compareTo(right);
                boolean shouldMove = ascending ? (cmp > 0) : (cmp < 0);
                if (!shouldMove) break;
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    // ---------- Searching ----------
    // Binary Search by ID (requires list sorted by ID ascending)
    public Staff binarySearchById(int id) {
        List<Staff> list = manager.getAll();
        int low = 0;
        int high = list.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int midId = list.get(mid).getId();
            if (midId == id) return list.get(mid);
            if (midId < id) low = mid + 1;
            else high = mid - 1;
        }
        return null;
    }

    // Linear search (partial) by Name/Position/Contact/Shift
    public LinkedList<Staff> linearSearch(String namePart, String positionPart, String contactPart, Shift shiftOrNull) {
        LinkedList<Staff> results = new LinkedList<>(); // LinkedList requirement
        String n = safeLower(namePart);
        String p = safeLower(positionPart);
        String c = safeLower(contactPart);

        for (Staff s : manager.getAll()) {
            boolean ok = true;

            if (!n.isEmpty() && !s.getName().toLowerCase().contains(n)) ok = false;
            if (!p.isEmpty() && !s.getPosition().toLowerCase().contains(p)) ok = false;
            if (!c.isEmpty() && !s.getContact().toLowerCase().contains(c)) ok = false;
            if (shiftOrNull != null && s.getShift() != shiftOrNull) ok = false;

            if (ok) results.add(s);
        }
        return results;
    }

    private String safeLower(String x) {
        if (x == null) return "";
        return x.trim().toLowerCase();
    }

    // ---------- Dashboard stats ----------
    public int totalStaff() {
        return manager.getAll().size();
    }

    public int countByShift(Shift shift) {
        int count = 0;
        for (Staff s : manager.getAll()) {
            if (s.getShift() == shift) count++;
        }
        return count;
    }
}
