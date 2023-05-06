package com.ww.backend.sys.mapper;

import com.ww.backend.sys.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ww
 * @since 2023-05-03
 */
public interface MenuMapper extends BaseMapper<Menu> {
    public List<Menu> getMunuListByUserId(@Param("userId") Integer userId, @Param("pid") Integer pid);
}
