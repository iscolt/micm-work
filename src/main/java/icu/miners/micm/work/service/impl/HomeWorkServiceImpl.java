package icu.miners.micm.work.service.impl;
import java.io.File;
import java.util.Date;
import java.sql.Timestamp;

import icu.miners.micm.work.model.entity.EmailTask;
import icu.miners.micm.work.model.entity.HomeWork;
import icu.miners.micm.work.repository.HomeWorkRepository;
import icu.miners.micm.work.service.EmailTaskService;
import icu.miners.micm.work.service.HomeWorkService;
import icu.miners.micm.work.service.base.AbstractCrudService;
import icu.miners.micm.work.utils.FileUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/isColt
 * @date: 2020/9/15
 * @see: icu.miners.carte_alimentaire.service.impl
 * @version: v1.0.0
 */
@Service
public class HomeWorkServiceImpl extends AbstractCrudService<HomeWork, Integer> implements HomeWorkService {

    @Resource
    private HomeWorkRepository homeWorkRepository;

    @Resource
    private EmailTaskService emailTaskService;

    protected HomeWorkServiceImpl(JpaRepository<HomeWork, Integer> repository) {
        super(repository);
    }

    @Override
    public List<HomeWork> listByStatus(short status) {
        return homeWorkRepository.findAllByStatus(status);
    }

    @Override
    public HomeWork releaseHomework(HomeWork homeWork) {
        HomeWork update = update(homeWork);
        // 提交方式 0 打包邮箱发送 1 其他
        if (update.getSubMethod() == 0) {
            // 新建一个邮箱任务
            EmailTask emailTask = new EmailTask();
            emailTask.setFromAddr("1329208516@qq.com");
            emailTask.setToAddr(homeWork.getSubEmail());
            emailTask.setTitle("20转本计科01班-" + homeWork.getName());
            emailTask.setCategory((short)0);
            // TODO 作业结束5分钟 发送
            Date sendDate = new Date(homeWork.getEnd().getTime() + 300000);
            emailTask.setSendDate(sendDate);
            emailTask.setStatus((short)0);
            emailTask.setHomeWork(homeWork);
            emailTask.setResource(FileUtil.getUserHomeFolderPath() + getHomeWorkFolderPath(homeWork));

            // 新建一个目录，存放作业文件 homework1
            FileUtil.createFolderOnUserHome(getHomeWorkFolderPath(update));
        }
        return update;
    }

    @Override
    public Boolean checkIsNotExpired(HomeWork homeWork) {
        long now = new Date().getTime();

        if (homeWork.getStatus() == 0 || homeWork.getStatus() == 2) { // 0 未开始 1 进行中 2 已结束
            return false;
        }
        if (homeWork.getEnd().getTime() < now) { // 已结束
            return false;
        }
        if (homeWork.getBegin().getTime() > now) { // 未开始
            return false;
        }
        return true;
    }

    @Override
    public String getHomeWorkFolderPath(HomeWork homeWork) {
        return "micm-work" + File.separator + "homework" + homeWork.getId();
    }
}
