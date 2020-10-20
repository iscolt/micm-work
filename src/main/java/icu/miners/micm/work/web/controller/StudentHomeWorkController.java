package icu.miners.micm.work.web.controller;

import icu.miners.micm.work.annotation.CheckRole;
import icu.miners.micm.work.annotation.UserLoginToken;
import icu.miners.micm.work.model.base.ResponseResult;
import icu.miners.micm.work.model.entity.Student;
import icu.miners.micm.work.model.entity.StudentHomeWork;
import icu.miners.micm.work.service.StudentHomeWorkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/isColt
 * @date: 2020/10/20
 * @see: icu.miners.micm.work.web.controller
 * @version: v1.0.0
 */
@Api
@RestController
@RequestMapping(value = "api/student/homework")
@Transactional
@CrossOrigin
public class StudentHomeWorkController {

    @Resource
    private StudentHomeWorkService studentHomeWorkService;

    @ApiOperation(value = "改变作业提交状态")
    @UserLoginToken
    @CheckRole
    @GetMapping("/status/{sHId}")
    public ResponseResult<List<Student>> changeStatus(@PathVariable(value = "sHId") Integer sHId) {
        StudentHomeWork studentHomeWork = studentHomeWorkService.fetchById(sHId).orElse(null);
        if (studentHomeWork == null) {
            return new ResponseResult<>(HttpStatus.EXPECTATION_FAILED.value(), "无此用户");
        }
        studentHomeWork.setStatus(studentHomeWork.getStatus() == 0 ? (short)1 : (short)0);
        studentHomeWorkService.update(studentHomeWork);
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功");
    }
}
