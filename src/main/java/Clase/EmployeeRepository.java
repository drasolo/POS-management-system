package Clase;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    private List<Employee> employees;
    private static EmployeeRepository instance;

    private EmployeeRepository() {
        employees = new ArrayList<>();
    }

    public static EmployeeRepository getInstance() {
        if (instance == null) {
            instance = new EmployeeRepository();
        }
        return instance;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public Employee getEmployeeById(String id) {
        for (Employee employee : employees) {
            if (employee.getEmployeeID().equals(id)) {
                return employee;
            }
        }
        return null;
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    public void updateEmployee(Employee employee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getEmployeeID().equals(employee.getEmployeeID())) {
                employees.set(i, employee);
                break;
            }
        }
    }

    public void deleteEmployee(String id) {
        employees.removeIf(employee -> employee.getEmployeeID().equals(id));
    }
}
