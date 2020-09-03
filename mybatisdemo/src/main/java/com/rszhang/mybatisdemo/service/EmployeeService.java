package com.rszhang.mybatisdemo.service;

import com.rszhang.mybatisdemo.bean.Employee;
import com.rszhang.mybatisdemo.bean.EmployeeExample;
import com.rszhang.mybatisdemo.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Flylater
 * @version : 1.0
 * @date : 2020/8/3
 */
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 查询员工
     * @return
     */
    public List<Employee> getAll() {
        return employeeMapper.selectByExampleWithDept(null);
    }

    /**
     * 根据条件查询员工
     * @return
     */
    public List<Employee> getAll(Employee employee) {
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        if (employee.getEmpName() != null && !employee.getEmpName().equals("")) {
            criteria.andEmpNameEqualTo(employee.getEmpName());
        }
        if (employee.getGender().equals("男")) {
            employee.setGender("M");
            criteria.andGenderEqualTo(employee.getGender());
        } else if (employee.getGender().equals("女")) {
            employee.setGender("F");
            criteria.andGenderEqualTo(employee.getGender());
        }
        if (employee.getEmail() != null && !employee.getEmail().equals("")) {
            criteria.andEmailEqualTo(employee.getEmail());
        }
        if (employee.getdId() != null && employee.getdId() != 0) {
            criteria.andDIdEqualTo(employee.getdId());
        }
        return employeeMapper.selectByExampleWithDept(example);
    }

    /**
     * 保存新增员工
     * @param employee
     * @return
     */
    public int saveEmp(Employee employee) {
        return employeeMapper.insertSelective(employee);
    }

    /**
     * 校验用户名是否可用
     * @param empName
     * @return true: 代表当前姓名可用 false: 姓名不可用
     */
    public Boolean checkUser(String empName) {
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        long count = employeeMapper.countByExample(example);

        return count == 0;
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    public Employee getEmp(Integer id) {
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        return employee;
    }

    /**
     * 员工更新
     * @param employee
     */
    public Integer updateEmp(Employee employee) {
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }

    /**
     * 员工删除
     * @param id
     */
    public Integer deleteEmp(Integer id) {
       return employeeMapper.deleteByPrimaryKey(id);
    }
}
