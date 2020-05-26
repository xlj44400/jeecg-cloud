package org.jeecg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class JeecgDemoApplication {

  public static void main(String[] args) throws UnknownHostException {

    ConfigurableApplicationContext application = SpringApplication.run(JeecgDemoApplication.class, args);
    Environment env = application.getEnvironment();
    String ip = InetAddress.getLocalHost().getHostAddress();
    String port = env.getProperty("server.port");
    log.info("\n----------------------------------------------------------\n\t" +
        "Application JeecgDemoApplication is running! Access URLs:\n\t" +
        "Local: \t\thttp://localhost:" + port  + "/\n\t" +
        "External: \thttp://" + ip + ":" + port  + "/\n\t" +
        "----------------------------------------------------------");

  }
}