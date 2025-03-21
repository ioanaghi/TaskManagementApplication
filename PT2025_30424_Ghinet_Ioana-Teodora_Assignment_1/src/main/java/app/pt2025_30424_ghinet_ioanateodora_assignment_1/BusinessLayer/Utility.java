package app.pt2025_30424_ghinet_ioanateodora_assignment_1.BusinessLayer;

import app.pt2025_30424_ghinet_ioanateodora_assignment_1.DataModel.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Utility {
    public static List<Employee> filterEmployeesByWorkHours(TaskManagement taskManagement, int threshold) {
        return taskManagement.getMap().keySet().stream()
                .filter(emp -> taskManagement.calculateEmployeeWorkDuration(emp.getIdEmployee()) > threshold)
                .sorted(Comparator.comparingInt(emp -> -taskManagement.calculateEmployeeWorkDuration(emp.getIdEmployee()))) // Descending order
                .collect(Collectors.toList());
    }
}
