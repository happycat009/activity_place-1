package com.huangjiabin.springboot.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.huangjiabin.springboot.service.StudentService;
import org.springframework.stereotype.Component;

/*因该把接口实现类加载到spring容器里*/
@Component
@Service(interfaceClass = StudentService.class,version = "1.0.0",timeout = 15000)
//上面一句话就把接口暴露出去了
public class StudentServiceImpl implements StudentService {
    @Override
    public Integer queryAllStudnetCount() {
        //调用数据持久层
        return 10000;
    }
}
