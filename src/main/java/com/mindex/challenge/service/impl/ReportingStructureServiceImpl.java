package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.ReportingStructureRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
    
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private ReportingStructureRepository reportRepository;

    @Override
    public ReportingStructure create(ReportingStructure structure) {
        LOG.debug("Creating ReportingStructure [{}]", structure);
        
        reportRepository.insert(structure);
        return structure;
    }

    @Override
    public ReportingStructure read(Employee employee) {
        LOG.debug("Creating report structure from employee with ID [{}]", employee.getEmployeeId());

        ReportingStructure structure = reportRepository.findByEmployee(employee);

        if (structure == null) {
            throw new RuntimeException("Invalid employee ID: " + employee.getEmployeeId());
        }

        return structure;
    }

    @Override
    public ReportingStructure update(ReportingStructure structure) {
        LOG.debug("Updating ReportingStructure [{}]", structure);

        return reportRepository.save(structure);
    }

}