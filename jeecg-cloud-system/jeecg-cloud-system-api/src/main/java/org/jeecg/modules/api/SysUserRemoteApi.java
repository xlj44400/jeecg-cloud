package org.jeecg.modules.api;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.ServiceNameConstants;
import org.jeecg.common.system.vo.SysUserCacheInfo;
import org.jeecg.modules.api.factory.SysUserRemoteApiFallbackFactory;
import org.jeecg.modules.api.fallback.SysUserRemoteApiFallbackImpl;
import org.jeecg.modules.system.entity.SysPermission;
import org.jeecg.modules.system.entity.SysPermissionDataRule;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Component
@FeignClient(contextId = "sysUserRemoteApi", value = ServiceNameConstants.SYSTEM_SERVICE, fallback = SysUserRemoteApiFallbackImpl.class,fallbackFactory = SysUserRemoteApiFallbackFactory.class)
public interface SysUserRemoteApi {
    /**
     * 通过用户名获取用户角色集合
     *
     * @param username 用户名
     * @return 角色集合
     */
    @GetMapping("/sys/user/rolesSet/{username}")
    Result<Set<String>> getUserRolesSet(@PathVariable("username") String username);

    /**
     * 通过用户名获取用户权限集合
     *
     * @param username 用户名
     * @return 权限集合
     */
    @GetMapping("/sys/user/permissionsSet/{username}")
    Result<Set<String>> getUserPermissionsSet(@PathVariable("username") String username);

    /**
     * 通过component查询菜单配置信息
     * @param component
     * @return
     */
    @GetMapping("/sys/user/queryComponentPermission")
    List<SysPermission> queryComponentPermission(@RequestParam("component") String component);

    /**
     * 通过请求地址查找菜单信息
     * @param method
     * @param path
     * @return
     */
    @GetMapping("/sys/user/queryRequestPermission")
    List<SysPermission> queryRequestPermission(@RequestParam("method") String method, @RequestParam("path") String path);

    /**
     * 获取用户的数据权限
     * @param username
     * @param permissionId
     * @return
     */
    @GetMapping("/sys/user/queryUserDataRule")
    List<SysPermissionDataRule> queryUserDataRule(@RequestParam("username") String username, @RequestParam("permissionId") String permissionId);

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    @GetMapping("/sys/user/getCacheUser")
    SysUserCacheInfo getCacheUser(@RequestParam("username") String username);

}
