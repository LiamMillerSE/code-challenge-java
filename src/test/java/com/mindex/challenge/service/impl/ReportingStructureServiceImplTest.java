package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.ReportingStructureService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String employeeUrl;
    private String reportUrl;

    @Autowired
    private ReportingStructureService reportService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        reportUrl = "http://localhost:" + port + "/report";
    }

    @Test
    public void testCreateStructure() {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        //Test that employee creation works
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();
        assertNotNull(createdEmployee.getEmployeeId());

        //assert created structure exists and has no reports
        ReportingStructure createdStructure = restTemplate.postForEntity(reportUrl, createdEmployee, ReportingStructure.class).getBody();
        assertNotNull(createdStructure);
        assertEquals(0, createdStructure.getNumberOfReports());
    }

    @Test
    public void testReportStructureWithOneDependant() {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        Employee testManager = new Employee();
        testManager.setFirstName("James");
        testManager.setLastName("Boss");
        testManager.setDepartment("Engineering");
        testManager.setPosition("Manager");
        List<Employee> reports = new ArrayList<Employee>();
        reports.add(testEmployee);
        testManager.setDirectReports(reports);

        //Test that employee creation works
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testManager, Employee.class).getBody();
        assertNotNull(createdEmployee.getEmployeeId());
        assertEquals(1, createdEmployee.getDirectReports().size());

        //assert created structure exists and has no reports
        ReportingStructure testStructure = new ReportingStructure();
        testStructure.updateEmployee(testManager);
        ReportingStructure createdStructure = restTemplate.postForEntity(reportUrl, createdEmployee, ReportingStructure.class).getBody();
        assertNotNull(createdStructure);
        assertEquals(1, createdStructure.getNumberOfReports());
    }

    @Test
    public void testReportStructureWithNestedDependants() {
        Employee testEmployee1 = new Employee();
        testEmployee1.setFirstName("John");
        testEmployee1.setLastName("Doe");
        testEmployee1.setDepartment("Engineering");
        testEmployee1.setPosition("Developer");

        Employee testEmployee2 = new Employee();
        testEmployee2.setFirstName("Jane");
        testEmployee2.setLastName("Doe");
        testEmployee2.setDepartment("Engineering");
        testEmployee2.setPosition("Developer");

        Employee testManager = new Employee();
        testManager.setFirstName("Bill");
        testManager.setLastName("Manager");
        testManager.setDepartment("Engineering");
        testManager.setPosition("Manager");
        List<Employee> reports = new ArrayList<Employee>();
        reports.add(testEmployee1);
        reports.add(testEmployee2);
        testManager.setDirectReports(reports);

        

        Employee testBoss = new Employee();
        testBoss.setFirstName("James");
        testBoss.setLastName("DaBoss");
        testBoss.setDepartment("Engineering");
        testBoss.setPosition("Boss");
        List<Employee> bossreports = new ArrayList<Employee>();
        bossreports.add(testManager);
        testBoss.setDirectReports(bossreports);

        //Test that employee creation works
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testManager, Employee.class).getBody();
        assertNotNull(createdEmployee.getEmployeeId());
        assertEquals(2, createdEmployee.getDirectReports().size());
        Employee createdBoss = restTemplate.postForEntity(employeeUrl, testBoss, Employee.class).getBody();
        assertNotNull(createdBoss.getEmployeeId());
        assertEquals(1, createdBoss.getDirectReports().size());


        //assert created structure exists and has no reports
        ReportingStructure testStructure = new ReportingStructure();
        testStructure.updateEmployee(testBoss);
        ReportingStructure createdStructure = restTemplate.postForEntity(reportUrl, createdBoss, ReportingStructure.class).getBody();
        assertNotNull(createdStructure);
        assertEquals(3, createdStructure.getNumberOfReports());
    }
}