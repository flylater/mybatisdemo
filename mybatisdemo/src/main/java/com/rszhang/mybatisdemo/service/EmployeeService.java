package com.rszhang.mybatisdemo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rszhang.mybatisdemo.bean.Employee;
import com.rszhang.mybatisdemo.bean.EmployeeExample;
import com.rszhang.mybatisdemo.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 查询员工
     * @return
     */
    @Cacheable
    public List<Employee> getAll() {
        return employeeMapper.selectByExampleWithDept(null);
    }

//    /**
//     * 根据条件查询员工
//     * @return
//     */
////    @Cacheable(value = "query", key = "args[0].hashCode()")
////    @Cacheable(value = "query", key = "#employee.empName+#employee.gender+#employee.email+#employee.dId+")
//    public List<Employee> getAll(Employee employee) {
//        EmployeeExample example = new EmployeeExample();
//        EmployeeExample.Criteria criteria = example.createCriteria();
//        if (employee.getEmpName() != null && !employee.getEmpName().equals("")) {
//            criteria.andEmpNameEqualTo(employee.getEmpName());
//        }
//        if (employee.getGender().equals("男")) {
//            employee.setGender("M");
//            criteria.andGenderEqualTo(employee.getGender());
//        } else if (employee.getGender().equals("女")) {
//            employee.setGender("F");
//            criteria.andGenderEqualTo(employee.getGender());
//        }
//        if (employee.getEmail() != null && !employee.getEmail().equals("")) {
//            criteria.andEmailEqualTo(employee.getEmail());
//        }
//        if (employee.getdId() != null && employee.getdId() != 0) {
//            criteria.andDIdEqualTo(employee.getdId());
//        }
//        return employeeMapper.selectByExampleWithDept(example);
//    }

    /**
     * 根据条件查询员工
     * @return
     */
    @Cacheable(value = "query", key = "args[0] + '&' + args[1]")
    public PageInfo getAll(Integer pn, Employee employee) {
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

        PageHelper.startPage(pn, 5);
        List<Employee> employees = employeeMapper.selectByExampleWithDept(example);

        PageInfo page = new PageInfo(employees, 5);

        return page;
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
