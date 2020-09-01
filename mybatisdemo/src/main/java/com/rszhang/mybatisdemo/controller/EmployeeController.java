package com.rszhang.mybatisdemo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rszhang.mybatisdemo.bean.Employee;
import com.rszhang.mybatisdemo.bean.Msg;
import com.rszhang.mybatisdemo.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {
    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeService employeeService;

    @RequestMapping("/emps")
    public String getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
        // 引入 PageHelper 分页插件
        // 在查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn, 5);

        List<Employee> emps = employeeService.getAll();

        // 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了
        // 封装了详细的分页信息，包括查询出来的数据，传入连续显示的页数
        PageInfo page = new PageInfo(emps,5);
        model.addAttribute("pageInfo", page);
        return "emps.html";
    }

    @ResponseBody
    @RequestMapping("/empsjson")
    public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
        // 引入 PageHelper 分页插件
        // 在查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn, 5);

        List<Employee> emps = employeeService.getAll();

        // 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了
        // 封装了详细的分页信息，包括查询出来的数据，传入连续显示的页数
        PageInfo page = new PageInfo(emps,5);

        return Msg.success().add("pageInfo", page);
    }

    /**
     * 保存新增员工信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/empjson", method = RequestMethod.POST)
    public Msg saveEmp(@Valid Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            // 校验失败，返回错误信息
            Map<String, Object> map = new HashMap<>();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors) {
                map.put(error.getField(), error.getDefaultMessage());
            }
            return Msg.fail().add("errorFields", map);
        } else {
            employeeService.saveEmp(employee);
            return Msg.success();
        }

    }

    /**
     * 更新员工信息
     * @param employee
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/empjson/{empId}", method = RequestMethod.PUT)
    public Msg saveEmp(Employee employee) {
        logger.info("employee:" + employee);
        employeeService.updateEmp(employee);
        return Msg.success();
    }

    /**
     * 检查姓名是否可用
     * @param empName
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkuserjson")
    public Msg checkUser(@RequestParam("empName") String empName) {
        // 先判断用户名是否是合法的表达式
        String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
        if (!empName.matches(regx)) {
            return Msg.fail().add("va_msg", "用户名必须是6-16位英文和数字的组合或者2-5位中文");
        }

        // 判断用户名是否已存在
        Boolean b = employeeService.checkUser(empName);
        if (b) {
            return Msg.success();
        } else {
            return Msg.fail().add("va_msg", "用户名不可用");
        }
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/empjson/{id}", method = RequestMethod.GET)
    public Msg getEmp(@PathVariable("id") Integer id) {
        Employee employee = employeeService.getEmp(id);
        return Msg.success().add("emp", employee);
    }

    @ResponseBody
    @RequestMapping(value = "/empjson/{id}", method = RequestMethod.DELETE)
    public Msg deleteEmpById(@PathVariable("id") Integer id) {
        employeeService.deleteEmp(id);
        return Msg.success();
    }

    @RequestMapping("/list")
    public String getList(Model model) {
        List<Employee> employees = employeeService.getAll();
        model.addAttribute("employees", employees);

        return "list.html";
    }
}
