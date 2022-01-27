package com.huangjiabin.springboot.service.impl;

import com.huangjiabin.springboot.mapper.StudentMapper;
import com.huangjiabin.springboot.model.Student;
import com.huangjiabin.springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student queryStudentById(Integer id) {
        Student student = studentMapper.selectByPrimaryKey(id);
        return student;
    }

    @Override
    @Transactional //添加事务
    public int updateStudentById(Student student) {
        int num = studentMapper.updateByPrimaryKeySelective(student);  //执行成功
        int a=10/0;     //报错，回滚
        return num;
    }
}
