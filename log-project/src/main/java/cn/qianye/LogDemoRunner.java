package cn.qianye;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LogDemoRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(LogDemoRunner.class);

    private static final Logger accessLogger = LoggerFactory.getLogger("SEARCH_ACCESS");//和日志打印文件中logger name对应
    //Spring Boot 会调用已注册 Runner 的 run()
    @Override
    public void run(String... args) throws Exception {
        simulateSearch("demo-001", "周杰伦", false);
        simulateSearch("demo-002", "周杰伦", true);
        logger.info("日志练习结束，当前 requestId={}", MDC.get("requestId"));
    }

    /** 执行一次独立模拟请求，并在结束时清理本次设置的上下文。 */
    private void simulateSearch(String requestId, String keyword, boolean fail) {
        MDC.put("requestId", requestId);
        try {
            logger.info("开始搜索，keyword={}", keyword);
            logger.debug("查询参数 fail={}", fail);

            if (fail) {
                throw new IllegalStateException("模拟搜索失败");
            }

            int resultCount = 10;
            logger.info("搜索完成，resultCount={}", resultCount);
            accessLogger.info("keyword={} resultCount={} status=SUCCESS", keyword, resultCount);
        } catch (IllegalStateException e) {
            logger.error("搜索失败，keyword={}", keyword, e);
            accessLogger.info("keyword={} resultCount=0 status=FAIL", keyword);
        } finally {
            MDC.remove("requestId");
        }
    }
}
