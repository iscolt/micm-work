package icu.miners.micm.work.web.controller;

import icu.miners.micm.work.annotation.UserLoginToken;
import icu.miners.micm.work.model.base.ResponseResult;
import icu.miners.micm.work.model.entity.LanQiaoGather;
import icu.miners.micm.work.model.entity.Student;
import icu.miners.micm.work.repository.LanQiaoGatherRepository;
import icu.miners.micm.work.service.LanQiaoGatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.TypeCache;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/isColt
 * @date: 2020/10/28
 * @see: icu.miners.micm.work.web.controller
 * @version: v1.0.0
 */
@Api
@RestController
@RequestMapping(value = "api/lanqiao")
@Transactional
@CrossOrigin
public class LanQiaoGatherController {

    @Resource
    private LanQiaoGatherService lanQiaoGatherService;

    @Resource
    private LanQiaoGatherRepository lanQiaoGatherRepository;

    @ApiOperation(value = "蓝桥杯校赛报名表")
    @GetMapping("")
    public ResponseResult<List<LanQiaoGather>> list(String college, @PageableDefault(sort = {"created"}, direction = DESC) Pageable pageable) {
        if (college == null || college.equals("") || college.equals("-1")) {
            return new ResponseResult<>(HttpStatus.OK.value(), "操作成功", lanQiaoGatherRepository.findAll(pageable).getContent());
        }
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功", lanQiaoGatherRepository.findAllByCollege(college, pageable).getContent());
    }

    @ApiOperation(value = "蓝桥杯校赛报名")
    @PostMapping("apply")
    public ResponseResult<LanQiaoGather> apply(@RequestBody LanQiaoGather lanQiaoGather) {
        LanQiaoGather result = null;
        try {
//            String dateString = "2020-11-05 23:59:00";
//            Date endDate= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
//            if (new Date().getTime() > endDate.getTime()) {
//                return new ResponseResult<>(HttpStatus.FAILED_DEPENDENCY.value(), "报名截止！");
//            }
            LanQiaoGather byNumber = lanQiaoGatherRepository.findByNumber(lanQiaoGather.getNumber());
            if(byNumber != null) {
                lanQiaoGather.setId(byNumber.getId());
                return new ResponseResult<>(HttpStatus.OK.value(), "报名信息已更新！");
            }
            result = lanQiaoGatherService.update(lanQiaoGather);
        } catch (Exception e) {
            return new ResponseResult<>(HttpStatus.FAILED_DEPENDENCY.value(), "操作异常，请联系管理员");
        }
        return new ResponseResult<>(HttpStatus.OK.value(), "报名成功", result);
    }

    @ApiOperation(value = "蓝桥杯校赛报名删除")
    @DeleteMapping("{id}")
    public ResponseResult<Void> delete(@PathVariable(value = "id") Integer id) {
        LanQiaoGather lanQiaoGather = lanQiaoGatherService.fetchById(id).orElse(null);
        if (lanQiaoGather != null) {
            lanQiaoGatherService.remove(lanQiaoGather);
        }
        return new ResponseResult<>(HttpStatus.OK.value(), "操作成功");
    }

}
