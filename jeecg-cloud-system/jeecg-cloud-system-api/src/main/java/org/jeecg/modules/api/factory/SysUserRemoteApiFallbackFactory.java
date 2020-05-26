package org.jeecg.modules.api.factory;

import feign.hystrix.FallbackFactory;
import org.jeecg.modules.api.SysUserRemoteApi;
import org.jeecg.modules.api.fallback.SysUserRemoteApiFallbackImpl;
import org.springframework.stereotype.Component;

/**
 * @author scott
 * @date 2020/05/22
 */
@Component
public class SysUserRemoteApiFallbackFactory implements FallbackFactory<SysUserRemoteApi> {

	@Override
	public SysUserRemoteApiFallbackImpl create(Throwable throwable) {
		SysUserRemoteApiFallbackImpl remoteUserServiceFallback = new SysUserRemoteApiFallbackImpl();
		remoteUserServiceFallback.setCause(throwable);
		return remoteUserServiceFallback;
	}
}
