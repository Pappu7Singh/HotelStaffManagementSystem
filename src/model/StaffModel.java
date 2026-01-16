/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HELIOS
 */

import java.util.*;

public class StaffModel {

    private final List<Staff> staffList = new ArrayList<>();
    private final Deque<Staff> recentQueue = new ArrayDeque<>();
    private final Stack<Staff> deletedStack = new Stack<>();

    public StaffModel() {
        addStaffInternal(new Staff(101, "John Doe", "Receptionist", "Morning", "980000001"));
        addStaffInternal(new Staff(102, "Alice Smith", "Housekeeping", "Day", "980000002"));
        addStaffInternal(new Staff(103, "Robert Brown", "Security", "Night", "980000003"));
        addStaffInternal(new Staff(104, "Emily Davis", "Manager", "Day", "980000004"));
        addStaffInternal(new Staff(105, "Michael Lee", "Chef", "Morning", "980000005"));
    }

    // ========= ADD =========
    public void addStaff(Staff s) {
        if (isDuplicateId(s.getId()))
            throw new IllegalArgumentException("Duplicate Staff ID.");
        addStaffInternal(s);
    }

    private void addStaffInternal(Staff s) {
        staffList.add(s);
        recentQueue.addLast(s);
        if (recentQueue.size() > 5)
            recentQueue.removeFirst();
    }

    // ========= UPDATE =========
    public boolean updateStaffById(int id, Staff updated) {
        for (int i = 0; i < staffList.size(); i++) {
            if (staffList.get(i).getId() == id) {
                staffList.set(i, updated);
                return true;
            }
        }
        return false;
    }

    // ========= DELETE (SELECTED ONLY) =========
    public boolean deleteStaffById(int id) {
        Iterator<Staff> it = staffList.iterator();
        while (it.hasNext()) {
            Staff s = it.next();
            if (s.getId() == id) {
                deletedStack.push(s);
                it.remove();
                return true;
            }
        }
        return false;
    }

    // ========= UNDO =========
    public void undoDelete() {
        if (!deletedStack.isEmpty()) {
            Staff s = deletedStack.pop();
            staffList.add(s);
            recentQueue.addLast(s);
            if (recentQueue.size() > 5)
                recentQueue.removeFirst();
        }
    }

    // ========= GETTERS =========
    public List<Staff> getStaffList() {
        return new ArrayList<>(staffList);
    }

    public List<Staff> getRecentStaff() {
        return new ArrayList<>(recentQueue);
    }

    public int getTotalCount() {
        return staffList.size();
    }

    public int getShiftCount(String shift) {
        int count = 0;
        for (Staff s : staffList)
            if (s.getShift().equalsIgnoreCase(shift)) count++;
        return count;
    }

    public int getNextId() {
        int max = 100;
        for (Staff s : staffList)
            max = Math.max(max, s.getId());
        return max + 1;
    }

    private boolean isDuplicateId(int id) {
        for (Staff s : staffList)
            if (s.getId() == id) return true;
        return false;
    }

    // ========= SORT =========
    public void sortByIdAsc() {
        staffList.sort(Comparator.comparingInt(Staff::getId));
    }

    public void sortByIdDesc() {
        staffList.sort((a, b) -> b.getId() - a.getId());
    }

    public void sortByNameAsc() {
        staffList.sort(Comparator.comparing(Staff::getName, String.CASE_INSENSITIVE_ORDER));
    }

    public void sortByNameDesc() {
        staffList.sort((a, b) -> b.getName().compareToIgnoreCase(a.getName()));
    }

    // ========= SEARCH =========
    public Staff binarySearchById(int id) {
        sortByIdAsc();
        int low = 0, high = staffList.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int midId = staffList.get(mid).getId();

            if (midId == id) return staffList.get(mid);
            if (midId < id) low = mid + 1;
            else high = mid - 1;
        }
        return null;
    }

    public List<Staff> linearSearch(String name, String position, String shift) {
        List<Staff> result = new ArrayList<>();

        for (Staff s : staffList) {
            boolean match =
                (name.isEmpty() || s.getName().toLowerCase().contains(name.toLowerCase())) &&
                (position.isEmpty() || s.getPosition().toLowerCase().contains(position.toLowerCase())) &&
                (shift.equalsIgnoreCase("Any") || s.getShift().equalsIgnoreCase(shift));

            if (match) result.add(s);
        }
        return result;
    }
}
