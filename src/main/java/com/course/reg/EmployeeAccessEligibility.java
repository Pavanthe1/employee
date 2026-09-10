package com.course.reg;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAccessEligibility {

    public static class Employee {

        private String employeeId;
        private String name;
        private int age;
        private String department;
        private String employmentType;
        private int securityClearanceLevel;
        private boolean idValid;
        private int requestedAccessLevel;

        public Employee(String employeeId, String name, int age,
                        String department, String employmentType,
                        int securityClearanceLevel, boolean idValid,
                        int requestedAccessLevel) {

            if (employeeId == null || employeeId.trim().isEmpty()) {
                throw new IllegalArgumentException("Employee ID cannot be empty");
            }

            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Employee name cannot be empty");
            }

            if (age < 0) {
                throw new IllegalArgumentException("Age cannot be negative");
            }

            if (department == null || department.trim().isEmpty()) {
                throw new IllegalArgumentException("Department cannot be empty");
            }

            if (employmentType == null || employmentType.trim().isEmpty()) {
                throw new IllegalArgumentException("Employment type cannot be empty");
            }

            if (!employmentType.equalsIgnoreCase("Active")
                    && !employmentType.equalsIgnoreCase("Inactive")) {
                throw new IllegalArgumentException(
                        "Employment type must be Active or Inactive");
            }

            if (securityClearanceLevel < 0 || securityClearanceLevel > 5) {
                throw new IllegalArgumentException(
                        "Security clearance must be between 0 and 5");
            }

            if (requestedAccessLevel < 0 || requestedAccessLevel > 5) {
                throw new IllegalArgumentException(
                        "Requested access level must be between 0 and 5");
            }

            this.employeeId = employeeId;
            this.name = name;
            this.age = age;
            this.department = department;
            this.employmentType = employmentType;
            this.securityClearanceLevel = securityClearanceLevel;
            this.idValid = idValid;
            this.requestedAccessLevel = requestedAccessLevel;
        }
    }

    public static class EligibilityResult {

        private String status;
        private List<String> reasons;

        public EligibilityResult(String status, List<String> reasons) {
            this.status = status;
            this.reasons = reasons;
        }

        public String getStatus() {
            return status;
        }

        public List<String> getReasons() {
            return reasons;
        }
    }

    public static EligibilityResult checkEligibility(Employee employee) {

        List<String> reasons = new ArrayList<String>();

        if (employee.age < 21) {
            reasons.add("Employee must be at least 21 years old");
        }

        boolean authorizedDepartment =
                employee.department.equalsIgnoreCase("IT")
                || employee.department.equalsIgnoreCase("HR")
                || employee.department.equalsIgnoreCase("Finance")
                || employee.department.equalsIgnoreCase("Administration");

        if (!authorizedDepartment) {
            reasons.add("Department is not authorized");
        }

        if (!employee.employmentType.equalsIgnoreCase("Active")) {
            reasons.add("Employee is not actively employed");
        }

        if (!employee.idValid) {
            reasons.add("Employee ID is invalid");
        }

        boolean insufficientClearance =
                employee.securityClearanceLevel < employee.requestedAccessLevel;

        if (insufficientClearance) {
            reasons.add("Security clearance is insufficient for requested access level");
        }

        if (employee.age < 21
                || !authorizedDepartment
                || !employee.employmentType.equalsIgnoreCase("Active")
                || !employee.idValid) {

            return new EligibilityResult("Not Eligible", reasons);
        }

        if (insufficientClearance) {
            return new EligibilityResult("Conditionally Eligible", reasons);
        }

        return new EligibilityResult("Eligible", reasons);
    }

    public static void main(String[] args) {

        List<Employee> employees = new ArrayList<Employee>();

        employees.add(new Employee(
                "EMP101",
                "Rahul",
                25,
                "IT",
                "Active",
                4,
                true,
                3));

        employees.add(new Employee(
                "EMP102",
                "Priya",
                21,
                "HR",
                "Active",
                2,
                true,
                2));

        employees.add(new Employee(
                "EMP103",
                "Arun",
                19,
                "Sales",
                "Inactive",
                1,
                false,
                4));

        employees.add(new Employee(
                "EMP104",
                "Meena",
                30,
                "Finance",
                "Active",
                1,
                true,
                3));

        for (Employee employee : employees) {

            EligibilityResult result = checkEligibility(employee);

            System.out.println("----------------------------------------");
            System.out.println("Employee ID : " + employee.employeeId);
            System.out.println("Name        : " + employee.name);
            System.out.println("Department  : " + employee.department);
            System.out.println("Status      : " + result.getStatus());

            if (!result.getReasons().isEmpty()) {
                System.out.println("Reasons:");

                for (String reason : result.getReasons()) {
                    System.out.println("- " + reason);
                }
            }
        }

        System.out.println("----------------------------------------");
    }
}
