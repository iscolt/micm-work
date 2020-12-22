package icu.miners.micm.work.web.controller;

import icu.miners.micm.work.annotation.CheckRole;
import icu.miners.micm.work.annotation.UserLoginToken;
import icu.miners.micm.work.model.base.ResponseResult;
import icu.miners.micm.work.model.entity.Organization;
import icu.miners.micm.work.model.entity.Question;
import icu.miners.micm.work.model.entity.Student;
import icu.miners.micm.work.service.HomeWorkService;
import icu.miners.micm.work.service.OrganizationService;
import icu.miners.micm.work.service.QuestionService;
import icu.miners.micm.work.service.StudentService;
import icu.miners.micm.work.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/IvenCc
 * @date: 2020/12/22
 * @see: icu.miners.micm.work.web.controller
 * @version: v1.0.0
 */
@Api
@RestController
@RequestMapping(value = "api/question")
@Transactional
@CrossOrigin
public class QuestionController {

    @Resource
    private OrganizationService organizationService;

    @Resource
    private QuestionService questionService;

    @ApiOperation(value = "导入题库")
    @UserLoginToken
    @CheckRole
    @PostMapping("import/excel")
    @ResponseBody
    public ResponseResult<Void> importByExcel(HttpServletRequest request, String subject) throws Exception {
        Organization organization = organizationService.getCurrentOrganization();
        if (organization == null) {
            return new ResponseResult<>(HttpStatus.FAILED_DEPENDENCY.value(), "未绑定组织");
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        MultipartFile file = multipartRequest.getFile("file");
        if (file.isEmpty()) {
            return new ResponseResult<>(500,"文件不能为空");
        }
        InputStream inputStream = file.getInputStream();
        List<List<Object>> list = ExcelUtil.getBankListByExcel(inputStream, file.getOriginalFilename());
        inputStream.close();

        List<Question> questions = new ArrayList<>();
        Question question = null;
        for (int i = 1; i < list.size(); i++) {
            List<Object> objects = list.get(i);
            question = new Question();
            question.setType(objects.get(0).toString());
            question.setStem(objects.get(1).toString());
            question.setAnswer(objects.get(2).toString());
            question.setResolve(objects.get(3).toString());
            question.setScore(Double.parseDouble(objects.get(4).toString()));
            // 选项为最后几项
            question.setOptions("[");
            for (int j = 5; j < objects.size(); j++) {
                if (j == objects.size()-1) {
                    question.setOptions(question.getOptions() + "\"" + objects.get(j).toString() + "\"]");
                } else {
                    question.setOptions(question.getOptions() + "\"" + objects.get(j).toString() + "\", ");
                }
            }
            question.setOrganization(organization);
            question.setSubject(subject);
            questions.add(question);
        }
        questionService.createInBatch(questions);
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功");
    }

    @ApiOperation(value = "题库")
    @UserLoginToken
    @GetMapping("")
    public ResponseResult<List<Question>> listBySubject(String subject) {
        if (subject == null ||subject.equals("")) {
            return new ResponseResult<>(HttpStatus.OK.value(), "操作成功", questionService.listAll());
        }
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功", questionService.findBySubject(subject));
    }

    @ApiOperation(value = "科目")
    @UserLoginToken
    @GetMapping("subject")
    public ResponseResult<List<String>> listSubject() {
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功", questionService.findSubject());
    }
}
