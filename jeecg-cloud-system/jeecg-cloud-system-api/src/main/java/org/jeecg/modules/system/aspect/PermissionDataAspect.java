package org.jeecg.modules.system.aspect;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.jeecg.common.aspect.annotation.PermissionData;
import org.jeecg.common.system.util.JeecgDataAutorUtils;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.SysPermissionDataRuleModel;
import org.jeecg.common.system.vo.SysUserCacheInfo;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.api.SysUserRemoteApi;
import org.jeecg.modules.system.entity.SysPermission;
import org.jeecg.modules.system.entity.SysPermissionDataRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
  * 数据权限切面处理类
  *  当被请求的方法有注解PermissionData时,会在往当前request中写入数据权限信息
 * @Date 2019年4月10日
 * @Version: 1.0
 */
@Aspect
@Component
@Slf4j
public class PermissionDataAspect {

	@Autowired
	private SysUserRemoteApi sysUserRemoteApi;

	@Pointcut("@annotation(org.jeecg.common.aspect.annotation.PermissionData)")
	public void pointCut() {

	}

	@Around("pointCut()")
	public Object arround(ProceedingJoinPoint point) throws  Throwable{
		HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		PermissionData pd = method.getAnnotation(PermissionData.class);
		String component = pd.pageComponent();
		authDataHandler(request, component);
		return  point.proceed();
	}

	private void authDataHandler(HttpServletRequest request, String component) {
		List<SysPermission> currentSyspermission = null;
		if(oConvertUtils.isNotEmpty(component)) {
			//1.通过注解属性pageComponent 获取菜单
			currentSyspermission = sysUserRemoteApi.queryComponentPermission(component);
		}else {
			String requestMethod = request.getMethod();
			String requestPath = request.getRequestURI().substring(request.getContextPath().length());
			requestPath = filterUrl(requestPath);
			log.info("拦截请求 >> "+requestPath+";请求类型 >> "+requestMethod);
			currentSyspermission = sysUserRemoteApi.queryRequestPermission(requestMethod, requestPath);
		}
		//3.通过用户名+菜单ID 找到权限配置信息 放到request中去
		if(currentSyspermission!=null && currentSyspermission.size()>0) {
			String username = JwtUtil.getUserNameByToken(request);
			List<SysPermissionDataRuleModel> dataRules = new ArrayList<SysPermissionDataRuleModel>();
			for (SysPermission sysPermission : currentSyspermission) {
				// update-begin--Author:scott Date:20191119 for：数据权限规则编码不规范，项目存在相同包名和类名 #722
				List<SysPermissionDataRule> temp = sysUserRemoteApi.queryUserDataRule(username, sysPermission.getId());
				if(temp!=null && temp.size()>0) {
					//dataRules.addAll(temp);
					dataRules = oConvertUtils.entityListToModelList(temp,SysPermissionDataRuleModel.class);
				}
				// update-end--Author:scott Date:20191119 for：数据权限规则编码不规范，项目存在相同包名和类名 #722
			}
			if(dataRules!=null && dataRules.size()>0) {
				JeecgDataAutorUtils.installDataSearchConditon(request, dataRules);
				SysUserCacheInfo userinfo = sysUserRemoteApi.getCacheUser(username);
				JeecgDataAutorUtils.installUserInfo(request, userinfo);
			}
		}
	}

	private String filterUrl(String requestPath){
		String url = "";
		if(oConvertUtils.isNotEmpty(requestPath)){
			url = requestPath.replace("\\", "/");
			url = requestPath.replace("//", "/");
			if(url.indexOf("//")>=0){
				url = filterUrl(url);
			}
			/*if(url.startsWith("/")){
				url=url.substring(1);
			}*/
		}
		return url;
	}

	/**
	 * 获取请求地址
	 * @param request
	 * @return
	 */
	private String getJgAuthRequsetPath(HttpServletRequest request) {
		String queryString = request.getQueryString();
		String requestPath = request.getRequestURI();
		if(oConvertUtils.isNotEmpty(queryString)){
			requestPath += "?" + queryString;
		}
		if (requestPath.indexOf("&") > -1) {// 去掉其他参数(保留一个参数) 例如：loginController.do?login
			requestPath = requestPath.substring(0, requestPath.indexOf("&"));
		}
		if(requestPath.indexOf("=")!=-1){
			if(requestPath.indexOf(".do")!=-1){
				requestPath = requestPath.substring(0,requestPath.indexOf(".do")+3);
			}else{
				requestPath = requestPath.substring(0,requestPath.indexOf("?"));
			}
		}
		requestPath = requestPath.substring(request.getContextPath().length() + 1);// 去掉项目路径
		return filterUrl(requestPath);
	}

	private boolean moHuContain(List<String> list,String key){
		for(String str : list){
			if(key.contains(str)){
				return true;
			}
		}
		return false;
	}




}
