package com.course.reg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeAccessEligibilityTest {

    @Test
    public void testValidEmployee() {
        EmployeeAccessEligibility.Employee employee =
                new EmployeeAccessEligibility.Employee(
                        "EMP101", "Rahul", 25, "IT",
                        "Active", 4, true, 3);

        EmployeeAccessEligibility.EligibilityResult result =
                EmployeeAccessEligibility.checkEligibility(employee);

        assertEquals("Eligible", result.getStatus());
        assertTrue(result.getReasons().isEmpty());
    }

    @Test
    public void testAgeBoundary21() {
        EmployeeAccessEligibility.Employee employee =
                new EmployeeAccessEligibility.Employee(
                        "EMP102", "Priya", 21, "HR",
                        "Active", 3, true, 2);

        assertEquals("Eligible",
                EmployeeAccessEligibility.checkEligibility(employee).getStatus());
    }

    @Test
    public void testAgeBelow21() {
        EmployeeAccessEligibility.Employee employee =
                new EmployeeAccessEligibility.Employee(
                        "EMP103", "Arun", 20, "IT",
                        "Active", 3, true, 2);

        EmployeeAccessEligibility.EligibilityResult result =
                EmployeeAccessEligibility.checkEligibility(employee);

        assertEquals("Not Eligible", result.getStatus());
        assertTrue(result.getReasons().contains(
                "Employee must be at least 21 years old"));
    }

    @Test
    public void testITDepartment() {
        EmployeeAccessEligibility.Employee employee =
                new EmployeeAccessEligibility.Employee(
                        "EMP104", "A", 25, "IT",
                        "Active", 3, true, 2);

        assertEquals("Eligible",
                EmployeeAccessEligibility.checkEligibility(employee).getStatus());
    }

    @Test
    public void testHRDepartment() {
        EmployeeAccessEligibility.Employee employee =
                new EmployeeAccessEligibility.Employee(
                        "EMP105", "B", 25, "HR",
                        "Active", 3, true, 2);

        assertEquals("Eligible",
                EmployeeAccessEligibility.checkEligibility(employee).getStatus());
    }

    @Test
    public void testFinanceDepartment() {
        EmployeeAccessEligibility.Employee employee =
                new EmployeeAccessEligibility.Employee(
                        "EMP106", "C", 25, "Finance",
                        "Active", 3, true, 2);

        assertEquals("Eligible",
                EmployeeAccessEligibility.checkEligibility(employee).getStatus());
    }

    @Test
    public void testAdministrationDepartment() {
        EmployeeAccessEligibility.Employee employee =
                new EmployeeAccessEligibility.Employee(
                        "EMP107", "D", 25, "Administration",
                        "Active", 3, true, 2);

        assertEquals("Eligible",
                EmployeeAccessEligibility.checkEligibility(employee).getStatus());
    }

    @Test
    public void testUnauthorizedDepartment() {
        EmployeeAccessEligibility.Employee employee =
                new EmployeeAccessEligibility.Employee(
                        "EMP108", "E", 25, "Sales",
                        "Active", 3, true, 2);

        EmployeeAccessEligibility.EligibilityResult result =
                EmployeeAccessEligibility.checkEligibility(employee);

        assertEquals("Not Eligible", result.getStatus());
        assertTrue(result.getReasons().contains(
                "Department is not authorized"));
    }

    @Test
    public void testInactiveEmployee() {
        EmployeeAccessEligibility.Employee employee =
                new EmployeeAccessEligibility.Employee(
                        "EMP109", "F", 30, "IT",
                        "Inactive", 3, true, 2);

        EmployeeAccessEligibility.EligibilityResult result =
                EmployeeAccessEligibility.checkEligibility(employee);

        assertEquals("Not Eligible", result.getStatus());
        assertTrue(result.getReasons().contains(
                "Employee is not actively employed"));
    }

    @Test
    public void testInvalidID() {
        EmployeeAccessEligibility.Employee employee =
                new EmployeeAccessEligibility.Employee(
                        "EMP110", "G", 28, "Finance",
                        "Active", 3, false, 2);

        EmployeeAccessEligibility.EligibilityResult result =
                EmployeeAccessEligibility.checkEligibility(employee);

        assertEquals("Not Eligible", result.getStatus());
        assertTrue(result.getReasons().contains(
                "Employee ID is invalid"));
    }

    @Test
    public void testInsufficientClearance() {
        EmployeeAccessEligibility.Employee employee =
                new EmployeeAccessEligibility.Employee(
                        "EMP111", "H", 27, "IT",
                        "Active", 1, true, 3);

        EmployeeAccessEligibility.EligibilityResult result =
                EmployeeAccessEligibility.checkEligibility(employee);

        assertEquals("Conditionally Eligible", result.getStatus());
        assertTrue(result.getReasons().contains(
                "Security clearance is insufficient for requested access level"));
    }

    @Test
    public void testMaximumClearance() {
        EmployeeAccessEligibility.Employee employee =
                new EmployeeAccessEligibility.Employee(
                        "EMP112", "I", 27, "IT",
                        "Active", 5, true, 5);

        assertEquals("Eligible",
                EmployeeAccessEligibility.checkEligibility(employee).getStatus());
    }

    @Test
    public void testMultipleFailures() {
        EmployeeAccessEligibility.Employee employee =
                new EmployeeAccessEligibility.Employee(
                        "EMP113", "Vijay", 18, "Sales",
                        "Inactive", 1, false, 4);

        EmployeeAccessEligibility.EligibilityResult result =
                EmployeeAccessEligibility.checkEligibility(employee);

        assertEquals("Not Eligible", result.getStatus());

        assertEquals(5, result.getReasons().size());

        assertTrue(result.getReasons().contains(
                "Employee must be at least 21 years old"));

        assertTrue(result.getReasons().contains(
                "Department is not authorized"));

        assertTrue(result.getReasons().contains(
                "Employee is not actively employed"));

        assertTrue(result.getReasons().contains(
                "Employee ID is invalid"));

        assertTrue(result.getReasons().contains(
                "Security clearance is insufficient for requested access level"));
    }

    @Test
    public void testEmptyEmployeeID() {
        assertThrows(IllegalArgumentException.class, () ->
                new EmployeeAccessEligibility.Employee(
                        "", "Test", 25, "IT",
                        "Active", 3, true, 2));
    }

    @Test
    public void testEmptyName() {
        assertThrows(IllegalArgumentException.class, () ->
                new EmployeeAccessEligibility.Employee(
                        "EMP114", "", 25, "IT",
                        "Active", 3, true, 2));
    }

    @Test
    public void testNegativeAge() {
        assertThrows(IllegalArgumentException.class, () ->
                new EmployeeAccessEligibility.Employee(
                        "EMP115", "Test", -1, "IT",
                        "Active", 3, true, 2));
    }

    @Test
    public void testInvalidEmploymentType() {
        assertThrows(IllegalArgumentException.class, () ->
                new EmployeeAccessEligibility.Employee(
                        "EMP116", "Test", 25, "IT",
                        "Retired", 3, true, 2));
    }

    @Test
    public void testInvalidSecurityClearance() {
        assertThrows(IllegalArgumentException.class, () ->
                new EmployeeAccessEligibility.Employee(
                        "EMP117", "Test", 25, "IT",
                        "Active", 6, true, 2));
    }

    @Test
    public void testInvalidAccessLevel() {
        assertThrows(IllegalArgumentException.class, () ->
                new EmployeeAccessEligibility.Employee(
                        "EMP118", "Test", 25, "IT",
                        "Active", 3, true, 6));
    }

    @Test
    public void testMultipleEmployees() {
        EmployeeAccessEligibility.Employee employee1 =
                new EmployeeAccessEligibility.Employee(
                        "EMP119", "A", 25, "IT",
                        "Active", 3, true, 2);

        EmployeeAccessEligibility.Employee employee2 =
                new EmployeeAccessEligibility.Employee(
                        "EMP120", "B", 20, "HR",
                        "Active", 3, true, 2);

        EmployeeAccessEligibility.EligibilityResult result1 =
                EmployeeAccessEligibility.checkEligibility(employee1);

        EmployeeAccessEligibility.EligibilityResult result2 =
                EmployeeAccessEligibility.checkEligibility(employee2);

        assertEquals("Eligible", result1.getStatus());
        assertEquals("Not Eligible", result2.getStatus());
    }
}
