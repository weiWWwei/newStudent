package com.ww.backend.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ww.backend.common.vo.Result;
import com.ww.backend.sys.entity.Student;
import com.ww.backend.sys.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ww
 * @since 2023-05-03
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private IStudentService studentService;

    @GetMapping("/all")
    public List<Student> getAllUser(){
        List<Student> students = studentService.list();
        return students;
    }
    @GetMapping("/get")
    public Result<?> getStudentPage(@RequestParam(value = "number", required = false) String number,
                                        @RequestParam(value = "uid", required = false) String uid,
                                        @RequestParam("pageNo") Long pageNo,
                                        @RequestParam("pageSize") Long pageSize){
        if(!StringUtils.hasLength(number)||!StringUtils.hasLength(uid)){
            return Result.fail("查询失败");
        }
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper();
        wrapper.eq(StringUtils.hasLength(number), Student::getNumber, number);
        wrapper.eq(StringUtils.hasLength(uid), Student::getUid, uid);
        wrapper.orderByDesc(Student::getScore);
        Page<Student> page = new Page<>(pageNo, pageSize);
        studentService.page(page, wrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());
        return Result.success(data);
    }
    @GetMapping("/list")
    public Result<?> getStudentListPage(@RequestParam(value = "number", required = false) String number,
                                     @RequestParam(value = "uid", required = false) String uid,
                                     @RequestParam("pageNo") Long pageNo,
                                     @RequestParam("pageSize") Long pageSize) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper();
        wrapper.eq(StringUtils.hasLength(number), Student::getNumber, number);
        wrapper.eq(StringUtils.hasLength(uid), Student::getUid, uid);
        wrapper.orderByDesc(Student::getScore);
        Page<Student> page = new Page<>(pageNo, pageSize);
        studentService.page(page, wrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());
        return Result.success(data);
    }
    @GetMapping("/filter")
    public Result<?> filterStudentList(@RequestParam(value = "number", required = false) String number,
                                        @RequestParam(value = "uid", required = false) String uid,
                                        @RequestParam("pageNo") Long pageNo,
                                        @RequestParam("pageSize") Long pageSize) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper();
        wrapper.eq(StringUtils.hasLength(number), Student::getNumber, number);
        wrapper.eq(StringUtils.hasLength(uid), Student::getUid, uid);
        wrapper.orderByDesc(Student::getScore);
        Page<Student> page = new Page<>(pageNo, pageSize);
        studentService.page(page, wrapper);
        List<Student> students = page.getRecords();
        List<Student> result  = new ArrayList<>();
        for(Student s: students)
        {
            int MaxSocre = 1000000;
            int cnt = 0;
            if(s.getChinese()<60){
                cnt++;
                MaxSocre=Math.min(MaxSocre,s.getChinese());
            }
            if(s.getMath()<60){
                cnt++;
                MaxSocre=Math.min(MaxSocre,s.getMath());
            }
            if(s.getEnglish()<60){
                cnt++;
                MaxSocre=Math.min(MaxSocre,s.getEnglish());
            }
            if(s.getPhysics()<60){
                cnt++;
                MaxSocre=Math.min(MaxSocre,s.getPhysics());
            }
            if(s.getBiology()<60){
                cnt++;
                MaxSocre=Math.min(MaxSocre,s.getBiology());
            }
            if(s.getChemistry()<60){
                cnt++;
                MaxSocre=Math.min(MaxSocre,s.getChemistry());
            }
            if(cnt==0){
                result.add(s);
            }else if(cnt==1&&MaxSocre<=50&&s.getScore()/6>60){
                result.add(s);
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", result);
        return Result.success(data);
    }

    @PostMapping("/add")
    public Result<?> addStudent(@RequestBody Student student){
        student.setScore(student.getChinese()+student.getMath()+student.getEnglish()+student.getPhysics()+student.getBiology()+student.getChemistry());
        studentService.save(student);
        return Result.success("新增用户成功");
    }
    @PutMapping
    public Result<?> updateStudent(@RequestBody Student student){
        student.setScore(student.getChinese()+student.getMath()+student.getEnglish()+student.getPhysics()+student.getBiology()+student.getChemistry());
        studentService.updateById(student);
        return Result.success("修改用户成功");
    }
    @GetMapping("/{id}")
    public Result<Student> getStudentById(@PathVariable("id") Integer id){
        Student student = studentService.getById(id);
        return Result.success(student);
    }
}
