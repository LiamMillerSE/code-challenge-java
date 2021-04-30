package com.mindex.challenge.service;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.data.Employee;

public interface ReportingStructureService {
    ReportingStructure create(ReportingStructure structure);
    ReportingStructure read(Employee employee);
    ReportingStructure update(ReportingStructure structure);
}
