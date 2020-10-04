package icu.miners.micm.work.repository;

import icu.miners.micm.work.model.entity.HomeWork;
import icu.miners.micm.work.model.entity.Student;
import icu.miners.micm.work.model.entity.StudentHomeWork;
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
public interface StudentHomeWorkRepository extends JpaRepository<StudentHomeWork, Integer> {

    List<StudentHomeWork> findAllByHomeWork(HomeWork homeWork);

    StudentHomeWork findByHomeWorkAndStudent(HomeWork homeWork, Student student);

    List<StudentHomeWork> findAllByStudent(Student student);
}
