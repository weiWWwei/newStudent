package com.ww.backend.sys.service.impl;

import com.ww.backend.sys.entity.Student;
import com.ww.backend.sys.mapper.StudentMapper;
import com.ww.backend.sys.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ww
 * @since 2023-05-03
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}
