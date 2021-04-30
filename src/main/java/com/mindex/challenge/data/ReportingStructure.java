package com.mindex.challenge.data;

import java.util.List;


public class ReportingStructure {
    private Employee employee;
    private int numberOfReports;

    public ReportingStructure() {
    }

    public void updateEmployee(Employee _employee) {
        employee = _employee;
        numberOfReports = getReportsUnderEmployee(employee);
    }

    private int getReportsUnderEmployee(Employee emp) {
        List<Employee> reports = emp.getDirectReports();
        int count = 0;
        if (reports != null) {
            count += reports.size();//add all of the current employee's dependants to the count
            for (Employee e : reports) {
                count += getReportsUnderEmployee(e);
            }
        }
        return count;
    }

    public Employee getEmployee() {
        return employee;
    }

    public int getNumberOfReports(){
        return numberOfReports;
    }
}
