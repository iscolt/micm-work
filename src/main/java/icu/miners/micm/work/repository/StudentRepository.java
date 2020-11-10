package icu.miners.micm.work.repository;

import icu.miners.micm.work.model.entity.Organization;
import icu.miners.micm.work.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findByNumber(String number);

    List<Student> findByDeletedIs(boolean deleted);

    List<Student> findAllByOrganization(Organization organization);

    List<Student> findByOrganizationAndDeletedIs(Organization organization, boolean deleted);

    Student findFirstByEmail(String email);
}
