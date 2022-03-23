package com.huangjiabin.site.sys.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huangjiabin.site.sys.model.*;
import com.huangjiabin.site.sys.service.StudentService;
import com.huangjiabin.site.sys.service.UserService;
import com.huangjiabin.site.sys.service.UserStudentService;
import com.huangjiabin.site.sys.util.EntityUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangjiabin
 * @since 2022-02-12
 */
@RestController
@RequestMapping("/sys/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserStudentService userStudentService;

    @ApiOperation(value = "分页查询用户基本信息")
    @GetMapping("/getUserPage/{current}/{size}")
    public RespBean getUserList(@PathVariable("size") Long size,@PathVariable("current") Long current) throws NullPointerException {
        IPage page  = new Page();
        //设置分页的数据
        page.setCurrent(current);//页码
        page.setSize(size);// 每页的记录数
        IPage<Map<String,Object>> result = userService.pageMaps(page);
        return RespBean.success("true",result);
    }

    @ApiOperation(value = "创建用户账号，并返回用户信息")
    @PostMapping("/createUser")
    public RespBean createUser(@RequestBody(required = false) User user, HttpServletRequest request){
        if(EntityUtil.checkEntityAllFieldsIsNull(user).getCode()==500){
            return EntityUtil.checkEntityAllFieldsIsNull(user);
        }
        boolean result = userService.save(user);
        RespBean success = RespBean.success("账号创建成功", user);
        return success;
    }
    @ApiOperation(value = "创学生账号，成功返回学生信息")
    @Transactional
    @PostMapping("/createUserStudent")
    public RespBean createUserStudent(@RequestBody(required = false) Map<String,Object> map, HttpServletRequest request){
        System.out.println(map);
        boolean result1;
        boolean result2 = false;
        User user;
        Student student = new Student();
        try {
            System.out.println(EntityUtil.mapToBean(map, User.class));
            user = EntityUtil.mapToBean(map, User.class);
            result1 = userService.save(user);
            if(result1){
                student = EntityUtil.mapToBean(map, Student.class);
                student.setStudentId(user.getId());
                result2 = studentService.save(student);
            }
            if(result2){
                Map<String,Object> map1 = new HashMap();
                map1.put("user",user);
                map1.put("student",student);
                RespBean success = RespBean.success(result1+"", map1);
                return success;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        RespBean error = RespBean.error("创建失败");
        return error;
    }

    @ApiOperation(value = "更改用户信息，传所有参数")
    @PutMapping("/updateUser")
    public RespBean updateUser(@RequestBody User user){
        boolean result = userService.updateById(user);
        if(result){

            return RespBean.success("更改成功",user);
        }
        return RespBean.error("更改失败");
    }
    @ApiOperation(value = "根据id删除用户信息")
    @DeleteMapping("/deleteUserById/{id}")
    public RespBean deleteUserById(@PathVariable("id") Long id){
        boolean result = userService.removeById(id);
        if(result){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}

