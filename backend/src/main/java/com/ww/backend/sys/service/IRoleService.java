package com.ww.backend.sys.service;

import com.ww.backend.sys.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ww
 * @since 2023-05-03
 */
public interface IRoleService extends IService<Role> {
    void addRole(Role role);

    Role getRoleById(Integer id);

    void updateRole(Role role);

    void deleteRoleById(Integer id);
}
