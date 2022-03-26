package com.huangjiabin.site.sys.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huangjiabin.site.sys.model.*;
import com.huangjiabin.site.sys.service.StudentService;
import com.huangjiabin.site.sys.service.UserRoleService;
import com.huangjiabin.site.sys.service.UserService;
import com.huangjiabin.site.sys.service.UserStudentService;
import com.huangjiabin.site.sys.util.EntityUtil;
import com.huangjiabin.site.sys.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserRoleService userRoleService;
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
    public RespBean createUser(@RequestBody Map map, HttpServletRequest request){
        //请求头中获取Authorization值
        String autHeader=request.getHeader("Authorization");
        Claims claims = null;
        try {
            //存在token，先判断是否存在Authorization值，值是否以Bearer开头
            if(null!=autHeader && autHeader.startsWith("Bearer")) {
                //从Authorization值中获取token
                String emailToken = autHeader.substring("Bearer".length());
                if(jwtTokenUtil.isTokenExpiration(emailToken)){
                    return RespBean.error("验证码失效请重新获取！！！");
                }else {
                    if(!jwtTokenUtil.getCodeByToken(emailToken).equals(map.get("code"))){
                        return RespBean.error("验证码错误");
                    }
                }
            }else {
                RespBean.error("无效token");
            }
                User user = EntityUtil.mapToBean(map, User.class);
                user.setCredit(6);
                user.setDelFlag(0);
                user.setDisabled(0);
                userService.save(user);
                UserRole userRole = new UserRole(user.getId(),new Long(user.getTitle()));
                userRoleService.save(userRole);
                RespBean success = RespBean.success("账号创建成功", user);
                return success;
        } catch (ExpiredJwtException e) {
                claims = e.getClaims();
            return RespBean.error("验证码过期",claims);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("用户创建失败");
    }
    @ApiOperation(value = "创学生账号，成功返回学生信息")
    @Transactional
    @PostMapping("/createUserStudent")
    public RespBean createUserStudent(@RequestBody(required = false) Map<String,Object> map, HttpServletRequest request){
        try {
        //请求头中获取Authorization值
        String autHeader=request.getHeader("Authorization");
        Claims claims = null;
        //存在token，先判断是否存在Authorization值，值是否以Bearer开头
        if(null!=autHeader && autHeader.startsWith("Bearer")) {
            //从Authorization值中获取token
            String emailToken = autHeader.substring("Bearer".length());
            if(jwtTokenUtil.isTokenExpiration(emailToken)){
                return RespBean.error("验证码失效请重新获取！！！");
            }else {
                if(!jwtTokenUtil.getCodeByToken(emailToken).equals(map.get("code"))){
                    return RespBean.error("验证码错误");
                }
            }
        }else {
            RespBean.error("无效token");
        }
        boolean result1;
        boolean result2 = false;
        User user;
        Student student = new Student();
            user = EntityUtil.mapToBean(map, User.class);
            user.setCredit(6);
            user.setDelFlag(0);
            user.setDisabled(0);
            result1 = userService.save(user);
            UserRole userRole = new UserRole(user.getId(),new Long(28));
            userRoleService.save(userRole);
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

