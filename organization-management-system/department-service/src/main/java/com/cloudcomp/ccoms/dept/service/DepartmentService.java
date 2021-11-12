package com.cloudcomp.ccoms.dept.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudcomp.ccoms.dept.advice.ResourceNotFoundException;
import com.cloudcomp.ccoms.dept.client.EmployeeClient;
import com.cloudcomp.ccoms.dept.dao.DepartmentRepository;
import com.cloudcomp.ccoms.dept.model.Department;

@Service
public class DepartmentService {

    @Autowired
    EmployeeClient empClient;

    @Autowired
    DepartmentRepository deptrepo;

    public List<Department> getAllDepts() {
        return (List<Department>) deptrepo.findAll();
    }

    public Department addDept(Department dept) {
        deptrepo.save(dept);
        return dept;
    }

    public List<Department> addDepts(List<Department> depts) {
        deptrepo.saveAll(depts);
        return depts;

    }

    public void deleteAllDepts() {
        deptrepo.deleteAll();
    }

    public Department getDeptById(Long deptId)  throws ResourceNotFoundException {

        Optional<Department> depts = deptrepo.findById(deptId);
        if (depts.isPresent()) {
            return depts.get();
        } else {
           // return null;
        	throw new ResourceNotFoundException("Id not found");
        }
    }

    public List<Department> getDeptsByOrgId(Long orgId)  {
       
    	return deptrepo.findDeptByOrgId(orgId);
    }

    public List<Department> getDeptswithEmpsUsingOrgid(Long orgId) {
        List<Department> finaldepts = new ArrayList<>();
        List<Department> depts = deptrepo.findDeptByOrgId(orgId);
        for (Department dept : depts) {
            Department tmp = new Department(dept.getId(), dept.getOrgId(), dept.getName());
            tmp.setEmps(empClient.findEmpsByDeptId(dept.getId()));
            finaldepts.add(tmp);
        }
        return finaldepts;
    }

    public List<Department> getDeptswithEmps() {
        List<Department> finaldepts = new ArrayList<>();
        for (Department dept : deptrepo.findAll()) {
            dept.setEmps(empClient.findEmpsByDeptId(dept.getId()));
            finaldepts.add(dept);
        }
        return finaldepts;
    }
}
