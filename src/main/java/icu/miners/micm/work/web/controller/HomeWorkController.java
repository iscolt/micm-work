package icu.miners.micm.work.web.controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;

import icu.miners.micm.work.annotation.CheckRole;
import icu.miners.micm.work.annotation.UserLoginToken;
import icu.miners.micm.work.model.base.ResponseResult;
import icu.miners.micm.work.model.entity.HomeWork;
import icu.miners.micm.work.model.entity.Student;
import icu.miners.micm.work.model.entity.StudentHomeWork;
import icu.miners.micm.work.model.param.LoginParam;
import icu.miners.micm.work.service.HomeWorkService;
import icu.miners.micm.work.service.StudentHomeWorkService;
import icu.miners.micm.work.service.StudentService;
import icu.miners.micm.work.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

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
@RequestMapping(value = "api/homework")
@Transactional
@CrossOrigin
public class HomeWorkController {

    @Resource
    HttpServletRequest req;

    @Resource
    private StudentService studentService;

    @Resource
    private HomeWorkService homeWorkService;

    @Resource
    private StudentHomeWorkService studentHomeWorkService;

    @ApiOperation(value = "查看作业列表 0 未开始 1 进行中 2 已结束")
    @UserLoginToken
    @GetMapping(value = "{status}")
    public ResponseResult<List<HomeWork>> listByStatus(@PathVariable(value = "status") short status) {
        if (status == -1) {
            return new ResponseResult<>(HttpStatus.OK.value(), "操作成功", homeWorkService.listAll());
        }
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功", homeWorkService.listByStatus(status));
    }

    @ApiOperation(value = "发布作业")
    @UserLoginToken
    @CheckRole
    @PostMapping("")
    public ResponseResult<HomeWork> add(@RequestBody HomeWork homeWork) {
        HomeWork update = homeWorkService.releaseHomework(homeWork);
        // 发布作业，同时分配给每位学生
        List<Student> students = studentService.listValid();
        List<StudentHomeWork> studentHomeWorks = new ArrayList<>();
        students.forEach(student -> {
            if (student.getId() == 1) return; // TODO 初始账号不分配作业
            StudentHomeWork studentHomeWork = studentHomeWorkService.getByHomeWorkAndStudent(homeWork, student);
            if (studentHomeWork == null) {
                studentHomeWork = new StudentHomeWork();
                studentHomeWork.setStudent(student);
                studentHomeWork.setHomeWork(update);
                studentHomeWork.setStatus((short)0);
                studentHomeWork.setSubTimes(0);
                studentHomeWorks.add(studentHomeWork);
            }
        });
        studentHomeWorkService.updateInBatch(studentHomeWorks);
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功", update);
    }

    @ApiOperation(value = "查看提交情况")
    @UserLoginToken
    @GetMapping("student/{homeworkId}")
    public ResponseResult<List<StudentHomeWork>> students(@PathVariable(value = "homeworkId") Integer homeworkId) {
        HomeWork homeWork = homeWorkService.fetchById(homeworkId).orElse(null);
        if (homeWork == null) {
            return new ResponseResult<>(HttpStatus.EXPECTATION_FAILED.value(), "无此数据");
        }
        List<StudentHomeWork> studentHomeWorks = studentHomeWorkService.listByHomeWork(homeWork);
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功", studentHomeWorks);
    }

    @ApiOperation(value = "提交作业")
    @UserLoginToken
    @CheckRole
    @PostMapping("student/sub/{homeworkId}")
    public ResponseResult<List<StudentHomeWork>> subHomeWork(@PathVariable(value = "homeworkId") Integer homeworkId
            , @RequestParam("file") MultipartFile file) {
        Student student = studentService.getCurrentUser();
        if (student == null) {
            return new ResponseResult<>(HttpStatus.EXPECTATION_FAILED.value(), "未登陆");
        }
//        HomeWork homeWork = new HomeWork();
//        homeWork.setId(homeworkId);
        HomeWork homeWork = homeWorkService.fetchById(homeworkId).orElse(null);
        if (homeWork == null) {
            return new ResponseResult<>(HttpStatus.EXPECTATION_FAILED.value(), "无此作业");
        }
        Boolean aBoolean = homeWorkService.checkIsNotExpired(homeWork);
        if(!aBoolean) { // 作业已过期/未开始
            return new ResponseResult<>(HttpStatus.EXPECTATION_FAILED.value(), "作业已过期/未开始");
        }
        // 更新对应的StudentHomeWork
        StudentHomeWork studentHomeWork = studentHomeWorkService.getByHomeWorkAndStudent(homeWork, student);
        if (studentHomeWork == null) {
            studentHomeWork = new StudentHomeWork();
            studentHomeWork.setStudent(student);
            studentHomeWork.setHomeWork(homeWork);
            studentHomeWork.setStatus((short)1);
            studentHomeWork.setSubTimes(1);
            studentHomeWorkService.update(studentHomeWork);
        }
        // 将附件存到文件夹中 homework + homework.getId()
        try {
            String fileName = file.getOriginalFilename();  // 获取原始文件名
            // TODO 检查文件名是否规范 25马文艺，只检测前两位
            if (fileName == null || !student.getNumber().equals(fileName.substring(0, 2))) {
                return new ResponseResult<>(HttpStatus.EXPECTATION_FAILED.value(), "文件名不规范。参照：25马文艺");
            }
            if (studentHomeWork.getResource() != null && !studentHomeWork.getResource().equals(fileName)) {
                return new ResponseResult<>(HttpStatus.EXPECTATION_FAILED.value(), "两次文件名不同，请重新上传");
            }
            String destFileName = FileUtil.getUserHomeFolderPath() + homeWorkService.getHomeWorkFolderPath(homeWork) + File.separator + fileName; // 获取用户目录，拼接到作业目录
            //4.第一次运行的时候，这个文件所在的目录往往是不存在的，这里需要创建一下目录（创建到了webapp下uploaded文件夹下）
            File destFile = new File(destFileName);
            destFile.getParentFile().mkdirs(); // 创建一下目录防止不存在
            file.transferTo(destFile); // 把浏览器上传的文件复制到希望的位置

            studentHomeWork.setStatus((short)1);
            studentHomeWork.setSubTimes(studentHomeWork.getSubTimes() + 1);
            studentHomeWork.setResource(fileName);
            return new ResponseResult<>(HttpStatus.OK.value(), "操作成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new ResponseResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "上传失败, " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "上传失败, " + e.getMessage());
        }
    }

    @ApiOperation(value = "删除作业")
    @UserLoginToken
    @CheckRole
    @DeleteMapping("{homeworkId}")
    public ResponseResult<Void> delete(@PathVariable(value = "homeworkId") Integer homeworkId) {
        homeWorkService.removeById(homeworkId);
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功");
    }

    @ApiOperation(value = "冻结/解冻")
    @UserLoginToken
    @CheckRole
    @GetMapping("/freeze/{homeworkId}")
    public ResponseResult<List<Student>> freeze(@PathVariable(value = "homeworkId") Integer homeworkId) {
        HomeWork homeWork = homeWorkService.fetchById(homeworkId).orElse(null);
        if (homeWork == null) {
            return new ResponseResult<>(HttpStatus.EXPECTATION_FAILED.value(), "无此数据");
        }
        homeWork.setDeleted(!homeWork.isDeleted());
        homeWorkService.update(homeWork);
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功");
    }

}
