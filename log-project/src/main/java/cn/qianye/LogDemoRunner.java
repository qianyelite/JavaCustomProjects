package cn.qianye;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LogDemoRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(LogDemoRunner.class);
    //Spring Boot 会调用已注册 Runner 的 run()
    @Override
    public void run(String... args) throws Exception {
        String keyword = "周杰伦";
        System.out.println("println：开始搜索，关键词：" + keyword);

        logger.debug("调试日志：查询参数 keyword={}", keyword);
        logger.info("普通日志：开始搜索，关键词：{}", keyword);
        logger.warn("警告日志：这是一次模拟警告");

        try {
            throw new IllegalStateException("模拟失败");
        } catch (IllegalStateException e) {
            //最后的 e 是异常对象，不需要占位符。 SLF4J 会将它识别为异常，额外打印异常堆栈
            logger.error("错误日志：搜索失败，关键词：{}", keyword, e);
        }

        logger.info("日志练习结束");
    }
}
