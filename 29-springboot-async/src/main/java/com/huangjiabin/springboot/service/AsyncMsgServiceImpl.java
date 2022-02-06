package com.huangjiabin.springboot.service;

import com.huangjiabin.springboot.model.Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class AsyncMsgServiceImpl implements AsyncMsgService {
    @Autowired
    private Msg msg;
    private Logger logger = LoggerFactory.getLogger(AsyncMsgServiceImpl.class);
    @Autowired
    private ThreadPoolTaskExecutor applicationTaskExecutor;

    @Async
    @Override
    public Future<Msg> asyncSendMsg() {
        logger.info("asyncSendMsg start...");
        System.out.println("是的消息已经发送");
        msg.setTitle("111");
        msg.setContent("222");
        logger.info("asyncSendMsg end!!!");
        return new AsyncResult<Msg>(msg);
    }

    @Async
    @Override
    public void asyncSendString() {
        logger.info("asyncSendMsg start...");
        System.out.println("你好");
        logger.info("asyncSendMsg end!!!");
    }

    @Async("asyncExecutor")
    @Override
    public void sendMineAsyncMsg() {
        logger.info("start executeAsync......");
        System.out.println("sendMineAsyncMsg...");
        logger.info("end executeAsync......");
    }

    @Override
    public Object sendMineAsyncMsg2() {
        logger.info("start executeAsync......");

        List<Object> resultList = new ArrayList<>();

        //使用Future方式执行多任务，生成一个结果集合
        List<Future> futures = new ArrayList<>();
        //查询出需要发送短信的用户手机号
        for (int i = 0; i < 1000; i++) {
            //并发处理
            Future<String> future = applicationTaskExecutor.submit(() -> {
                //给用户发送营销短信
                System.out.println("恭喜您获得15天VIP体验资格.");
                String result = "恭喜您获得15天VIP体验资格.";
                return result;
            });
            futures.add(future);
        }

        try {
            //查询任务执行的结果
            for (Future<?> future : futures) {
                while (true) {//CPU轮询：每个future都并发轮循，判断完成状态然后获取结果
                    if (future.isDone() && !future.isCancelled()) {//获取future成功完成状态，或者使用future.get(1000*1, TimeUnit.MILLISECONDS)
                        Object i = future.get();//获取结果
                        System.out.println("任务 i=" + i + " 获取完成!" + new Date());
                        resultList.add(i);
                        break;//当前future获取结果完毕，退出while循环
                    } else {
                        Thread.sleep(5);//每次轮询休息5毫秒（CPU纳秒级），避免CPU高速轮循耗空CPU
                    }
                }
                //Object i = future.get();
                //Object i2 = future.get(1, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("end executeAsync......");

        return resultList;
    }
}
