package icu.miners.micm.work.repository;

import icu.miners.micm.work.model.entity.HomeWork;
import icu.miners.micm.work.model.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
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
public interface HomeWorkRepository extends JpaRepository<HomeWork, Integer> {

    List<HomeWork> findAllByStatusOrderByEnd(short status);

    List<HomeWork> findAllByOrganizationOrderByEnd(Organization organizatio);

    List<HomeWork> findAllByStatusAndOrganizationOrderByEnd(short status, Organization organization);

    List<HomeWork> findByIdNotIn(List<Integer> ids);
}
