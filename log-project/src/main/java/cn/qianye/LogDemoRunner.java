package cn.qianye;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LogDemoRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(LogDemoRunner.class);

    private static final Logger accessLogger = LoggerFactory.getLogger("SEARCH_ACCESS");//和日志打印文件中logger name对应
    //Spring Boot 会调用已注册 Runner 的 run()
    @Override
    public void run(String... args) throws Exception {
        String keyword = "周杰伦";
        String requestId = "demo-001";

        System.out.println("println：开始搜索，关键词：" + keyword);

        logger.debug("调试日志：查询参数 keyword={}", keyword);
        logger.info("开始搜索，requestId={}，keyword={}", requestId, keyword);

        // 本阶段使用固定结果模拟搜索，不访问外部服务。
        int resultCount = 10;
        accessLogger.debug("requestId={} keyword={} resultCount={} status=SUCCESS",
                requestId, keyword, resultCount);

        logger.warn("警告日志：这是一次模拟警告");

        try {
            throw new IllegalStateException("模拟失败");
        } catch (IllegalStateException e) {
            //最后的 e 是异常对象，不需要占位符。 SLF4J 会将它识别为异常，额外打印异常堆栈
            logger.error("搜索失败，requestId={}，keyword={}", requestId, keyword, e);
            accessLogger.debug("requestId={} keyword={} resultCount={} status=FAIL",
                    "demo-002", keyword, 0);
        }

        // 仅用于观察大小滚动，验证完成后删除这段循环。
        String padding = "x".repeat(200);
        for (int i = 0; i < 3000; i++) {
            logger.info("滚动练习 index={} padding={}", i, padding);
            accessLogger.info("requestId=roll-{} keyword={} status=SUCCESS padding={}",
                    i, keyword, padding);
        }

        logger.info("日志练习结束");
    }
}
