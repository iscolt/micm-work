package icu.miners.micm.work.web.controller;

import icu.miners.micm.work.annotation.CheckRole;
import icu.miners.micm.work.annotation.UserLoginToken;
import icu.miners.micm.work.model.base.ResponseResult;
import icu.miners.micm.work.model.entity.Student;
import icu.miners.micm.work.model.param.LoginParam;
import icu.miners.micm.work.service.HomeWorkService;
import icu.miners.micm.work.service.StudentService;
import icu.miners.micm.work.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/isColt
 * @date: 2020/9/15
 * @see: icu.miners.carte_alimentaire.controller
 * @version: v1.0.0
 */
@Api
@RestController
@RequestMapping(value = "api/student")
@Transactional
@CrossOrigin
public class StudentController {

    @Resource
    private StudentService studentService;

    @Resource
    private HomeWorkService homeWorkService;

    /**
     * 用户登陆接口
     * @param loginParam
     * @return
     */
    @PostMapping("login")
    @ApiOperation(value = "用户登陆")
    public ResponseResult<String> login(@RequestBody LoginParam loginParam) {
        Student student = studentService.getByNumber(loginParam.getNumber());
        if (student == null) {
            return new ResponseResult<>(HttpStatus.EXPECTATION_FAILED.value(), "用户不存在");
        }
        if (student.isDeleted()) { // false 未冻结
            return new ResponseResult<>(HttpStatus.EXPECTATION_FAILED.value(), "账号被冻结");
        }
        String token = null;
        if (!DigestUtils.md5DigestAsHex(loginParam.getPassword().getBytes()).equals(student.getPassword())) {
            if (student.getPassword() == null && student.getInit() == 0) { // 如果密码为空，并且还未初始化
                student.setPassword(DigestUtils.md5DigestAsHex(loginParam.getPassword().getBytes()));
                student.setInit((short)1);
                studentService.update(student);
                token = studentService.getToken(student);
                return new ResponseResult<>(HttpStatus.OK.value(), "操作成功, 密码已经初始化!", token);
            }
            return new ResponseResult<>(HttpStatus.EXPECTATION_FAILED.value(), "用户名/密码错误");
        }
        token = studentService.getToken(student);
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功", token);
    }

    @ApiOperation(value = "导入学生")
    @UserLoginToken
    @CheckRole
    @PostMapping("import/excel")
    @ResponseBody
    public ResponseResult<Void> importByExcel(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        MultipartFile file = multipartRequest.getFile("file");
        if (file.isEmpty()) {
            return new ResponseResult<>(500,"文件不能为空");
        }
        InputStream inputStream = file.getInputStream();
        List<List<Object>> list = ExcelUtil.getBankListByExcel(inputStream, file.getOriginalFilename());
        inputStream.close();

        List<Student> students = new ArrayList<>();
        Student student = null;
        for (int i = 0; i < list.size(); i++) {
            List<Object> lo = list.get(i);
            if (studentService.getByNumber(String.valueOf(lo.get(0))) != null) continue;
            student = new Student();
            student.setNumber(String.valueOf(lo.get(0)));
            student.setInit((short)0);
            student.setRole((short)0);
            students.add(student);
        }
        studentService.createInBatch(students);
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功");
    }

    @ApiOperation(value = "学生列表")
    @UserLoginToken
    @GetMapping("")
    public ResponseResult<List<Student>> list() {
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功", studentService.listAll());
    }

    @ApiOperation(value = "学生绑定邮箱")
    @UserLoginToken
    @GetMapping("bind/email")
    public ResponseResult<List<Student>> bindEmail(String email) {
        // TODO 验证邮箱的真实性
        Student student = studentService.getCurrentUser();
        if (student == null) {
            return new ResponseResult<>(HttpStatus.EXPECTATION_FAILED.value(), "无此用户");
        }
        Student byEmail = studentService.getByEmail(email);
        if (byEmail != null) {
            return new ResponseResult<>(HttpStatus.EXPECTATION_FAILED.value(), "邮箱被占用");
        }
        student.setEmail(email);
        studentService.update(student);
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功", studentService.listAll());
    }

    @ApiOperation(value = "冻结/解冻")
    @UserLoginToken
    @CheckRole
    @GetMapping("/freeze/{stuId}")
    public ResponseResult<List<Student>> freeze(@PathVariable(value = "stuId") Integer stuId) {
        Student student = studentService.fetchById(stuId).orElse(null);
        if (student == null) {
            return new ResponseResult<>(HttpStatus.EXPECTATION_FAILED.value(), "无此用户");
        }
        // 解冻后，分配还未分配的作业
        if (student.isDeleted()) { // 冻结中
            homeWorkService.assignHomeWork(student);
        }
        student.setDeleted(!student.isDeleted());
        studentService.update(student);
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功");
    }
}
