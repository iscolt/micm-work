package icu.miners.micm.work.repository;

import icu.miners.micm.work.model.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/isColt
 * @date: 2020/11/10
 * @see: icu.miners.micm.work.repository
 * @version: v1.0.0
 */
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    Organization findByName(String name);
}
