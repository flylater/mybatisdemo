package com.rszhang.mybatisdemo.controller;

import com.rszhang.mybatisdemo.bean.Department;
import com.rszhang.mybatisdemo.bean.Msg;
import com.rszhang.mybatisdemo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DeptController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 返回所有的部门信息
     */
    @ResponseBody
    @RequestMapping("/depts")
    public Msg getDepts() {
        List<Department> list = departmentService.getDepts();
        return Msg.success().add("depts", list);
    }
}
