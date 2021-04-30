package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReportingStructureController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    @Autowired
    private ReportingStructureService reportService;

    @PostMapping("/report")
    public ReportingStructure create(@RequestBody Employee employee) {
        LOG.debug("Received reporting structure create request for employee with id [{}]", employee.getEmployeeId());
        ReportingStructure structure = new ReportingStructure();
        structure.updateEmployee(employee);
        return reportService.create(structure);
    }

    @GetMapping("/report")
    public ReportingStructure read(@PathVariable Employee employee) {
        LOG.debug("Received reporting structure create request for employee with id [{}]", employee.getEmployeeId());
        
        return reportService.read(employee);
    }

    @PutMapping("/report")
    public ReportingStructure update(@RequestBody ReportingStructure structure) {
        LOG.debug("Received ReportingStructure create request for structure with employee id [{}]", structure.getEmployee().getEmployeeId());

        return reportService.update(structure);
    }
}
