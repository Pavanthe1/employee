package com.course.reg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeAccessEligibilityTest {

    @Test
    public void testValidEmployee() {

        EmployeeAccessEligibility.Employee employee =
                new EmployeeAccessEligibility.Employee(
                        "EMP101",
                        "Rahul",
                        25,
                        "IT",
                        "Active",
                        4,
                        true,
                        3);

        EmployeeAccessEligibility.EligibilityResult result =
                EmployeeAccessEligibility.checkEligibility(employee);

        assertEquals("Eligible", result.getStatus());
        assertTrue(result.getReasons().isEmpty());
    }

    @Test
    public void testBoundaryAge21() {

        EmployeeAccessEligibility.Employee employee =
                new EmployeeAccessEligibility.Employee(
                        "EMP102",
                        "Priya",
                        21,
                        "HR",
                        "Active",
                        3,
                        true,
                        2);

        EmployeeAccessEligibility.EligibilityResult result =
                EmployeeAccessEligibility.checkEligibility(employee);

        assertEquals("Eligible", result.getStatus());
    }

    @Test
    public void testAgeBelow21() {

        EmployeeAccessEligibility.Employee employee =
                new EmployeeAccessEligibility.Employee(
                        "EMP103",
                        "Arun",
                        20,
                        "IT",
                        "Active",
                        3,
                        true,
                        2);

        EmployeeAccessEligibility.EligibilityResult result =
                EmployeeAccessEligibility.checkEligibility(employee);

        assertEquals("Not Eligible", result.getStatus());

        assertTrue(result.getReasons().contains(
                "Employee must be at least 21 years old"));
    }

    @Test
    public void testAuthorizedITDepartment() {

        EmployeeAccessEligibility.Employee employee =
                new EmployeeAccessEligibility.Employee(
                        "EMP104",
                        "Kiran",
                        25,
                        "IT",
                        "Active",
                        3,
                        true,
                        2);

        EmployeeAccessEligibility.EligibilityResult result =
                EmployeeAccessEligibility.checkEligibility(employee);

        assertEquals("Eligible", result.getStatus());
    }

    @Test
    public void testAuthorizedHRDepartment() {

        EmployeeAccessEligibility.Employee employee =
                new EmployeeAccessEligibility.Employee(
                        "EMP105",
                        "Sita",
                        25,
                        "HR",
                        "Active",
                        3,
                        true,
                        2);

        EmployeeAccessEligibility.EligibilityResult result =
                EmployeeAccessEligibility.checkEligibility(employee);

        assertEquals("Eligible", result.getStatus());
    }

    @Test
    public void testAuthorizedFinanceDepartment() {

        EmployeeAccessEligibility.Employee employee =
                new EmployeeAccessEligibility.Employee(
                        "EMP106",
                        "Ravi",
                        25,
                        "Finance",
                        "Active",
                        3,
                        true,
                        2);

        EmployeeAccessEligibility.EligibilityResult result =
                EmployeeAccessEligibility.checkEligibility(employee);

        assertEquals("Eligible", result.getStatus());
    }

    @Test
    public void testAuthorizedAdministrationDepartment() {

        EmployeeAccessEligibility.Employee employee =
                new EmployeeAccessEligibility.Employee(
                        "EMP107",
                        "Anu",
                        25,
                        "Administration",
                        "Active",
                        3,
                        true,
                        2);

        EmployeeAccessEligibility.EligibilityResult result =
                EmployeeAccessEligibility.checkEligibility(employee);

        assertEquals("Eligible", result.getStatus());
    }

    @Test
    public void testUnauthorizedDepartment() {

        EmployeeAccessEligibility.Employee employee =
                new EmployeeAccessEligibility.Employee(
                        "EMP108",
                        "Kiran",
                        25,
                        "Sales",
                        "Active",
                        3,
                        true,
                        2);

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
                        "EMP109",
                        "Ravi",
                        30,
                        "IT",
                        "Inactive",
                        3,
                        true,
                        2);

        EmployeeAccessEligibility.EligibilityResult result =
                EmployeeAccessEligibility.checkEligibility(employee);

        assertEquals("Not Eligible", result.getStatus());

        assertTrue(result.getReasons().contains(
                "Employee is not actively employed"));
    }

    @Test
    public void testInvalidEmployeeId() {

        EmployeeAccessEligibility.Employee employee =
                new EmployeeAccessEligibility.Employee(
                        "EMP110",
                        "Sita",
                        28,
                        "Finance",
                        "Active",
                        3,
                        false,
                        2);

        EmployeeAccessEligibility.EligibilityResult result =
                EmployeeAccessEligibility.checkEligibility(employee);

        assertEquals("Not Eligible", result.getStatus());

        assertTrue(result.getReasons().contains(
                "Employee ID is invalid"));
    }

    @Test
    public void testInsufficientSecurityClearance() {

        EmployeeAccessEligibility.Employee employee =
                new EmployeeAccessEligibility.Employee(
                        "EMP111",
                        "Manoj",
                        27,
                        "IT",
                        "Active",
                        1,
                        true,
                        3);

        EmployeeAccessEligibility.EligibilityResult result =
                EmployeeAccessEligibility.checkEligibility(employee);

        assertEquals("Conditionally Eligible", result.getStatus());

        assertTrue(result.getReasons().contains(
                "Security clearance is insufficient for requested access level"));
    }

    @Test
    public void testSufficientSecurityClearance() {

        EmployeeAccessEligibility.Employee employee =
                new EmployeeAccessEligibility.Employee(
                        "EMP112",
                        "Vijay",
                        27,
                        "IT",
                        "Active",
                        5,
                        true,
                        5);

        EmployeeAccessEligibility.EligibilityResult result =
                EmployeeAccessEligibility.checkEligibility(employee);

        assertEquals("Eligible", result.getStatus());
    }

    @Test
    public void testMultipleFailures() {

        EmployeeAccessEligibility.Employee employee =
                new EmployeeAccessEligibility.Employee(
                        "EMP113",
                        "Vijay",
                        18,
                        "Sales",
                        "Inactive",
                        1,
                        false,
                        4);

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
    public void testEmptyEmployeeId() {

        assertThrows(IllegalArgumentException.class, () ->
                new EmployeeAccessEligibility.Employee(
                        "",
                        "Test",
                        25,
                        "IT",
                        "Active",
                        3,
                        true,
                        2));
    }

    @Test
    public void testEmptyEmployeeName() {

        assertThrows(IllegalArgumentException.class, () ->
                new EmployeeAccessEligibility.Employee(
                        "EMP114",
                        "",
                        25,
                        "IT",
                        "Active",
                        3,
                        true,
                        2));
    }

    @Test
    public void testNegativeAge() {

        assertThrows(IllegalArgumentException.class, () ->
                new EmployeeAccessEligibility.Employee(
                        "EMP115",
                        "Test",
                        -1,
                        "IT",
                        "Active",
                        3,
                        true,
                        2));
    }

    @Test
    public void testInvalidEmploymentType() {

        assertThrows(IllegalArgumentException.class, () ->
                new EmployeeAccessEligibility.Employee(
                        "EMP116",
                        "Test",
                        25,
                        "IT",
                        "Retired",
                        3,
                        true,
                        2));
    }

    @Test
    public void testInvalidSecurityClearance() {

        assertThrows(IllegalArgumentException.class, () ->
                new EmployeeAccessEligibility.Employee(
                        "EMP117",
                        "Test",
                        25,
                        "IT",
                        "Active",
                        6,
                        true,
                        2));
    }

    @Test
    public void testInvalidRequestedAccessLevel() {

        assertThrows(IllegalArgumentException.class, () ->
                new EmployeeAccessEligibility.Employee(
                        "EMP118",
                        "Test",
                        25,
                        "IT",
                        "Active",
                        3,
                        true,
                        6));
    }

    @Test
    public void testMultipleEmployees() {

        EmployeeAccessEligibility.Employee employee1 =
                new EmployeeAccessEligibility.Employee(
                        "EMP119",
                        "A",
                        25,
                        "IT",
                        "Active",
                        3,
                        true,
                        2);

        EmployeeAccessEligibility.Employee employee2 =
                new EmployeeAccessEligibility.Employee(
                        "EMP120",
                        "B",
                        20,
                        "HR",
                        "Active",
                        3,
                        true,
                        2);

        EmployeeAccessEligibility.EligibilityResult result1 =
                EmployeeAccessEligibility.checkEligibility(employee1);

        EmployeeAccessEligibility.EligibilityResult result2 =
                EmployeeAccessEligibility.checkEligibility(employee2);

        assertEquals("Eligible", result1.getStatus());
        assertEquals("Not Eligible", result2.getStatus());
    }
}
