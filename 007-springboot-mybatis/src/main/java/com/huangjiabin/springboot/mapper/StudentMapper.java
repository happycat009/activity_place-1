package com.huangjiabin.springboot.mapper;

import com.huangjiabin.springboot.model.Student;
import org.apache.ibatis.annotations.Mapper;

//@Mapper//扫描dao接口到spring容器,可以不在这里加，在Application类上加
public interface StudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
}