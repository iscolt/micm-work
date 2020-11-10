package icu.miners.micm.work.repository;

import icu.miners.micm.work.model.entity.EmailTask;
import icu.miners.micm.work.model.entity.HomeWork;
import icu.miners.micm.work.model.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
 * @see: icu.miners.carte_alimentaire.repository
 * @version: v1.0.0
 */
@Repository
public interface EmailTaskRepository extends JpaRepository<EmailTask, Integer> {

    List<EmailTask> findAllByStatus(Short status);

    List<EmailTask> findAllByStatusAndOrganization(Short status, Organization organization);

    List<EmailTask> findAllByOrganization(Organization organization);

    EmailTask findByHomeWorkAndToAddr(HomeWork homeWork, String toAddr);

    List<EmailTask> findAllByStatusAndDeletedAndSendDateLessThan(Short status, boolean deleted, Date sendDate);

    EmailTask findByHomeWorkAndCategory(HomeWork homeWork, short category);

    List<EmailTask> findAllByHomeWork(HomeWork homeWork);
}
