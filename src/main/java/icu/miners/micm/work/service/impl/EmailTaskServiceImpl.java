package icu.miners.micm.work.service.impl;

import icu.miners.micm.work.model.entity.EmailTask;
import icu.miners.micm.work.model.entity.HomeWork;
import icu.miners.micm.work.model.entity.Organization;
import icu.miners.micm.work.model.entity.Student;
import icu.miners.micm.work.repository.EmailTaskRepository;
import icu.miners.micm.work.service.EmailTaskService;
import icu.miners.micm.work.service.base.AbstractCrudService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

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
 * @date: 2020/9/15
 * @see: icu.miners.carte_alimentaire.service.impl
 * @version: v1.0.0
 */
@Service
public class EmailTaskServiceImpl extends AbstractCrudService<EmailTask, Integer> implements EmailTaskService {

    @Resource
    private EmailTaskRepository emailTaskRepository;

    protected EmailTaskServiceImpl(JpaRepository<EmailTask, Integer> repository) {
        super(repository);
    }

    @Override
    public List<EmailTask> listByStatus(short status) {
        return emailTaskRepository.findAllByStatus(status);
    }

    @Override
    public List<EmailTask> listByStatusAndOrganization(short status, Organization organization) {
        return emailTaskRepository.findAllByStatusAndOrganization(status, organization);
    }

    @Override
    public List<EmailTask> listAllByOrganization(Organization organization) {
        return emailTaskRepository.findAllByOrganization(organization);
    }

    @Override
    public void warnRss(HomeWork homeWork, Student student, Integer hour) {
        EmailTask emailTask = new EmailTask();
        emailTask.setFromAddr("1329208516@qq.com");
        emailTask.setToAddr(student.getEmail());
        emailTask.setTitle("作业即将截至");
        emailTask.setContent("距离作业【"+homeWork.getName()+"】截至还有" + hour + "小时");
        emailTask.setCategory((short)1);
        emailTask.setSendDate(new Date(homeWork.getEnd().getTime() - hour * 60 * 60 * 1000));
        emailTask.setStatus((short)0);
        emailTask.setHomeWork(homeWork);
        update(emailTask);
    }

    @Override
    public List<EmailTask> findByHomework(HomeWork homeWork) {
        return emailTaskRepository.findAllByHomeWork(homeWork);
    }

}
