package icu.miners.micm.work.web.controller;

import icu.miners.micm.work.annotation.CheckRole;
import icu.miners.micm.work.annotation.UserLoginToken;
import icu.miners.micm.work.model.base.ResponseResult;
import icu.miners.micm.work.model.entity.Article;
import icu.miners.micm.work.model.entity.EmailTask;
import icu.miners.micm.work.model.entity.HomeWork;
import icu.miners.micm.work.model.entity.Organization;
import icu.miners.micm.work.model.entity.Student;
import icu.miners.micm.work.service.ArticleService;
import icu.miners.micm.work.service.OrganizationService;
import icu.miners.micm.work.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
 * @date: 2020/12/8
 * @see: icu.miners.micm.work.web.controller
 * @version: v1.0.0
 */
@Api
@RestController
@RequestMapping(value = "api/article")
@Transactional
@CrossOrigin
@Slf4j
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @Resource
    private OrganizationService organizationService;

    @Resource
    private StudentService studentService;

    @ApiOperation(value = "获取所有文章")
    @UserLoginToken
    @GetMapping(value = "")
    public ResponseResult<List<Article>> list() {
        Organization organization = organizationService.getCurrentOrganization();
        if (organization != null) {
            return new ResponseResult<>(HttpStatus.OK.value(), "操作成功", articleService.listAll());
        }
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功", articleService.listAll());
    }

    @ApiOperation(value = "添加文章")
    @UserLoginToken
    @CheckRole
    @PostMapping("add")
    public ResponseResult<Void> add(@RequestBody Article article) {
        Organization organization = organizationService.getCurrentOrganization();
        Student currentUser = studentService.getCurrentUser();
        if (organization == null) {
            return new ResponseResult<>(HttpStatus.FAILED_DEPENDENCY.value(), "权限不足");
        }
        article.setStudent(currentUser);
        article.setOrganization(organization);
        articleService.update(article);
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功");
    }
}
