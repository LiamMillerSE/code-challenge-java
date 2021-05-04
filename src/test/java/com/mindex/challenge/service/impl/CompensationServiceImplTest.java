package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
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

import java.util.Calendar;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {
    private String compensationUrl;
    private String employeeUrl;
    private String compensationIdUrl;

    @Autowired
    private CompensationService compensationService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation";
        employeeUrl = "http://localhost:" + port + "/employee";
        compensationIdUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    @Test
    public void testCreateRead() {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        // Employee Create checks
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();
        assertNotNull(createdEmployee.getEmployeeId());

        Compensation comp = new Compensation();
        comp.setEmployee(createdEmployee);
        comp.setSalary(1000000);
        Calendar cal = Calendar.getInstance();
        cal.set(2021,1,1);
        comp.setEffectiveDate(cal.getTime());

        // Compensation Create Checks
        Compensation createdComp = restTemplate.postForEntity(compensationUrl, comp, Compensation.class).getBody();
        assertNotNull(createdComp);
        assertEquals(1000000, createdComp.getSalary());
        assertEquals(cal.getTime(), createdComp.getEffectiveDate());
        assertEquals(createdEmployee.getEmployeeId(), createdComp.getEmployee().getEmployeeId());
        
        // Compensation read checks
        Compensation readComp = restTemplate.getForEntity(compensationIdUrl, Compensation.class, createdEmployee.getEmployeeId()).getBody();
        assertNotNull(readComp);
        assertEquals(1000000, readComp.getSalary());
        assertEquals(cal.getTime(), readComp.getEffectiveDate());
        assertEquals(createdEmployee.getEmployeeId(), readComp.getEmployee().getEmployeeId());
    }
}
