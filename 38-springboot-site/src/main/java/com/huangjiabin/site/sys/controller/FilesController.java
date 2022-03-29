package com.huangjiabin.site.sys.controller;


import com.huangjiabin.site.sys.util.ExcelUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文件表 前端控制器
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-12
 */
@RestController
@RequestMapping("/sys/files")
public class FilesController {

    @RequestMapping("/upload")
    public Map pubggupload(@RequestParam("file") MultipartFile file) throws Exception {
        Map<String,Object> map = new HashMap<>(16);
        //调用工具类解析excel文件
        List<ArrayList<String>> row = ExcelUtils.analysis(file);
        //打印信息
        for (int i = 0;i<row.size();i++){
            List<String> cell = row.get(i);
            for (int j = 0;j<cell.size();j++){
                System.out.print(cell.get(j)+" ");
            }
            System.out.println();
        }

        map.put("status","success");

        return map;

    }
}

