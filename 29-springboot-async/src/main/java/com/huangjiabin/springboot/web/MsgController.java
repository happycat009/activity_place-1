package com.huangjiabin.springboot.web;

import com.huangjiabin.springboot.model.Msg;
import com.huangjiabin.springboot.service.AsyncMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@Controller
public class MsgController {
    @Autowired
    private AsyncMsgService asyncMsgService;
    private Logger logger= LoggerFactory.getLogger(MsgController.class);

    @RequestMapping("/msg/sendMsg")
    public @ResponseBody Msg sendMsg() throws InterruptedException, ExecutionException {
        /*
            关于线程：springboot会默认给我们一个线程池，供我们调用
            context-> beanFactory-> singletonObjects-> applicationTaskExecutor(线程池)

            */
        logger.info("Async start...");
        Thread.sleep(3000);
        Future<Msg> msg=asyncMsgService.asyncSendMsg();
        logger.info("Async end!!!");
        try {
            return msg.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
    @RequestMapping("/msg/sendString")
    public @ResponseBody void sendString() throws InterruptedException, ExecutionException {
        logger.info("Async start2...");
        asyncMsgService.asyncSendString();
        logger.info("Async end2!!!");
    }
    @RequestMapping("/msg/sendMine")
    public @ResponseBody void sendMine() throws InterruptedException, ExecutionException {
        logger.info("Async start3...");
        asyncMsgService.sendMineAsyncMsg();
        logger.info("Async end3!!!");
    }
    @RequestMapping("/msg/sendMine2")
    public @ResponseBody Object sendMine2() throws InterruptedException, ExecutionException {
        logger.info("Async start4...");
        //调用service层的异步任务
        //(发短信，要发10几万短信，为了提高发短信的效率，所以可以把发短信用线程池执行，同时可以有多个线程在执行发短信)
        Object result =  asyncMsgService.sendMineAsyncMsg2();
        logger.info("Async end4!!!");
        return result;

    }
}
