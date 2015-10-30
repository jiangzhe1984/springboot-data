package sanjiang; /**
 * Created by jiangzhe on 15-10-28.
 */


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration//配置控制
@ComponentScan//组件扫描
@EnableAutoConfiguration//启用自动配置
public class Application {
    //启动Spring Boot项目的唯一入口
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
