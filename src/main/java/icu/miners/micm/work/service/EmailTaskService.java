package icu.miners.micm.work.service;

import icu.miners.micm.work.model.entity.EmailTask;
import icu.miners.micm.work.model.entity.HomeWork;
import icu.miners.micm.work.model.entity.Organization;
import icu.miners.micm.work.model.entity.Student;
import icu.miners.micm.work.service.base.CrudService;

import java.util.List;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/isColt
 * @date: 2020/9/15
 * @see: icu.miners.carte_alimentaire.service
 * @version: v1.0.0
 */
public interface EmailTaskService extends CrudService<EmailTask, Integer> {

    List<EmailTask> listByStatus(short status);

    List<EmailTask> listByStatusAndOrganization(short status, Organization organization);

    List<EmailTask> listAllByOrganization(Organization organization);

    /**
     * 作业提醒订阅
     * @param homeWork
     * @param student
     */
    void warnRss(HomeWork homeWork, Student student, Integer hour);

    List<EmailTask> findByHomework(HomeWork homeWork);
}
