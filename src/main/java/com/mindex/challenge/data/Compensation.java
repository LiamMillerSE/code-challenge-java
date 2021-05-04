package com.mindex.challenge.data;

import java.util.Date;

public class Compensation {
    private Employee employee;
    private int salary;
    private Date effectiveDate;

    public Compensation() {
    }

    public void setEmployee(Employee _employee) {
        employee = _employee;
    }

    public void setSalary(int _salary) {
        salary = _salary;
    }

    public void setEffectiveDate(Date _effectiveDate) {
        effectiveDate = _effectiveDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public int getSalary(){
        return salary;
    }

    public Date getEffectiveDate(){
        return effectiveDate;
    }
}
