package org.jeecg.modules.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.test.client.UserServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: TODO
 * @author: scott
 * @date: 2020年05月18日 16:37
 */
@Slf4j
@RestController
@RequestMapping("/demo")
public class NacosDemoController {
    @Autowired
    UserServiceClient userServiceClient;
//    @Autowired
//    DemoUserMapper demoUserMapper;

    @GetMapping("/hello")
    public String hello(){
        return "hello world , my name is Nacos Demo";
    }

    //@RequiresPermissions("user:edit")
    @RequiresRoles({"admin"})
    @GetMapping("/hello/feign")
    public String helloFeign(HttpServletRequest request, @RequestParam(name = "token", required = false) String token){
        log.info("remoteUserService.info() {}",userServiceClient.info("jeecg"));
        //log.info("remoteUserService.listDemo() {}",userServiceClient.listDemo());
        //JSONObject js = userServiceClient.getUserInfo(request,token);
        //log.info("feign result : " + js.toJSONString());
        return "hello world , my name is Nacos Demo";
    }

    @GetMapping("/randomImage/{key}")
    public String randomImage(HttpServletRequest request,@PathVariable String key){
        Result<String> res = userServiceClient.randomImage(key);
        String msg = "feign result : " + res.toString();
        log.info(msg);
        return msg;
    }

    @GetMapping("/timeout")
    public String timeout() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("休眠了3秒");
        return "timeout test";
    }
}
