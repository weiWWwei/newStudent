package com.ww.backend.sys.mapper;

import com.ww.backend.sys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ww
 * @since 2023-05-03
 */
public interface UserMapper extends BaseMapper<User> {

     List<String> getRoleNameByUserId(Integer userId);
}
