package com.huangjiabin.site.sys.controller;

import com.huangjiabin.site.sys.model.AjaxResult;
import com.huangjiabin.site.sys.util.MediaVideoTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * * @projectName videoservice
 * * @title IndexController
 * * @package com.de.controller
 * * @description  首页
 * * @author IT_CREAT     
 * * @date  2020 2020/5/17/017 5:15  
 * * @version c1.0.0
 */
@Slf4j
@Controller
public class IndexController {
    AtomicInteger sign = new AtomicInteger();
    ConcurrentHashMap<Integer, String> pathMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<Integer, PipedOutputStream> outputStreamMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<Integer, PipedInputStream> inputStreamMap = new ConcurrentHashMap<>();

    @GetMapping("/")
    public String indexView() {
        return "index";
    }

    @GetMapping(  "/test")
    public String testView() {
        return "test";
    }

    @PostMapping("/putVideo")
    @ResponseBody
    public AjaxResult putVideoPath(String path) {
        try {
            int id = sign.getAndIncrement();
            pathMap.put(id, path);
            PipedOutputStream pipedOutputStream = new PipedOutputStream();
            PipedInputStream pipedInputStream = new PipedInputStream();
            pipedOutputStream.connect(pipedInputStream);
            outputStreamMap.put(id, pipedOutputStream);
            inputStreamMap.put(id, pipedInputStream);
            return AjaxResult.success(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AjaxResult.error();
        }
    }

    @GetMapping("/getVideo")
    public void getVideo(HttpServletRequest request, HttpServletResponse response, int id) {
        log.info("进来了" + id);
        String path = pathMap.get(id);
        String fileName = UUID.randomUUID().toString();
        // 用于测试的时候，本地文件读取走这里
        if (path.endsWith(".mp4")) {
            String[] split = new File(path).getName().split("\\.");
            fileName = split[0];
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".flv");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            write(id, outputStream);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void write(int id, OutputStream outputStream) {
        try {
            String path = pathMap.get(id);
            PipedOutputStream pipedOutputStream = outputStreamMap.get(id);
            new Thread(() -> {
                MediaVideoTransfer mediaVideoTransfer = new MediaVideoTransfer();
                mediaVideoTransfer.setOutputStream(pipedOutputStream);
                mediaVideoTransfer.setRtspTransportType("udp");
                mediaVideoTransfer.setRtspUrl(path);
                mediaVideoTransfer.live();
            }).start();

            print(inputStreamMap.get(id), outputStream);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void print(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        IndexController indexController = new IndexController();
        AjaxResult ajaxResult = indexController.putVideoPath("D:/杂七杂八/好好说再见.mp4");
        indexController.write((int) ajaxResult.get("data"), new FileOutputStream("D:/杂七杂八/好好说再见.mp4"));
    }
}
