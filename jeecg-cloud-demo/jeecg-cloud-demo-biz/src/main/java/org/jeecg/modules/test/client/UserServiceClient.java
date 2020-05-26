package org.jeecg.modules.test.client;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.ServiceNameConstants;
import org.jeecg.common.system.vo.LoginUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Component
@FeignClient(contextId = "userServiceClient", value =ServiceNameConstants.SYSTEM_SERVICE)
public interface UserServiceClient {

    @GetMapping("/sys/user/getUserSectionInfoByToken")
    public JSONObject getUserInfo(HttpServletRequest request, @RequestParam(name = "token", required = false) String token);

    @GetMapping(value = "/sys/randomImage/{key}")
    public Result<String> randomImage(@PathVariable("key") String key);

    /**
     * 通过用户名查询用户、角色信息
     *
     * @param username 用户名
     * @return R
     */
    @GetMapping(value = "/sys/user/info/{username}")
    Result<LoginUser> info(@PathVariable("username") String username);

    @GetMapping(value = "/test/jeecgDemo/list")
    Result<?> listDemo();
}
