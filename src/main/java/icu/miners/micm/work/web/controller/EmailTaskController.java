package icu.miners.micm.work.web.controller;

import icu.miners.micm.work.annotation.CheckRole;
import icu.miners.micm.work.annotation.UserLoginToken;
import icu.miners.micm.work.model.base.ResponseResult;
import icu.miners.micm.work.model.entity.EmailTask;
import icu.miners.micm.work.model.entity.HomeWork;
import icu.miners.micm.work.model.entity.Student;
import icu.miners.micm.work.service.EmailTaskService;
import icu.miners.micm.work.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/isColt
 * @date: 2020/9/29
 * @see: icu.miners.micm.work.web.controller
 * @version: v1.0.0
 */
@Api
@RestController
@RequestMapping(value = "api/email/task")
@Transactional
public class EmailTaskController {

    @Resource
    private StudentService studentService;

    @Resource
    private EmailTaskService emailTaskService;

    @ApiOperation(value = "查看邮件任务列表")
    @UserLoginToken
    @CheckRole
    @GetMapping("{status}")
    public ResponseResult<List<EmailTask>> list(@PathVariable(value = "status") short status) {
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功", emailTaskService.listByStatus(status));
    }

    @ApiOperation(value = "订阅作业提醒(默认提前6小时提醒)")
    @UserLoginToken
    @PostMapping("rss")
    public ResponseResult<Void> list(HomeWork homeWork, Integer hour) {
        if (hour == null) hour = 6;
        if (homeWork.getEnd() == null || homeWork.getEnd().getTime() < new Date().getTime()) {
            return new ResponseResult<>(HttpStatus.EXPECTATION_FAILED.value(), "订阅失败，无需订阅");
        }
        Student student = studentService.getCurrentUser();
        if (student == null || student.getEmail() == null) {
            return new ResponseResult<>(HttpStatus.EXPECTATION_FAILED.value(), "未绑定邮箱");
        }
        emailTaskService.warnRss(homeWork, student, hour);
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功");
    }

    @ApiOperation(value = "删除邮箱任务")
    @UserLoginToken
    @CheckRole
    @DeleteMapping("{taskId}")
    public ResponseResult<Void> delete(@PathVariable(value = "taskId") Integer taskId) {
        emailTaskService.removeById(taskId);
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功");
    }

    @ApiOperation(value = "冻结/解冻")
    @UserLoginToken
    @CheckRole
    @GetMapping("/freeze/{taskId}")
    public ResponseResult<Void> freeze(@PathVariable(value = "taskId") Integer taskId) {
        EmailTask emailTask = emailTaskService.fetchById(taskId).orElse(null);
        if (emailTask == null) {
            return new ResponseResult<>(HttpStatus.EXPECTATION_FAILED.value(), "无此数据");
        }
        emailTask.setDeleted(!emailTask.isDeleted());
        emailTaskService.update(emailTask);
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功");
    }
}
