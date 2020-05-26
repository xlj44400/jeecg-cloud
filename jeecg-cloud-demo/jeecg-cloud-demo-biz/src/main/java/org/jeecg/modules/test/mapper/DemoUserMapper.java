package org.jeecg.modules.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.dto.UserDto;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @Author scott
 * @since 2018-12-20
 */
public interface DemoUserMapper extends BaseMapper<UserDto> {
	/**
	 * 通过用户账号查询用户信息
	 * @param username
	 * @return
	 */
	public UserDto getUserByName(@Param("username") String username);

}
