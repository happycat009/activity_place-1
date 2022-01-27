package com.huangjiabin.springboot.service.impl;

import com.huangjiabin.springboot.mapper.StudentMapper;
import com.huangjiabin.springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Override
    public int getStudentCount() {
        int num=studentMapper.selectStudentCount();
        return num;
    }
}
