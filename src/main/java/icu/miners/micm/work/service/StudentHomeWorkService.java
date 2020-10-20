package icu.miners.micm.work.service;

import icu.miners.micm.work.model.entity.HomeWork;
import icu.miners.micm.work.model.entity.Student;
import icu.miners.micm.work.model.entity.StudentHomeWork;
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
public interface StudentHomeWorkService extends CrudService<StudentHomeWork, Integer> {

    List<StudentHomeWork> listByHomeWork(HomeWork homeWork);

    List<StudentHomeWork> listByHomeWork(HomeWork homeWork, short status);

    StudentHomeWork getByHomeWorkAndStudent(HomeWork homeWork, Student student);

    List<StudentHomeWork> findByHomework(HomeWork homeWork);

    List<StudentHomeWork> findByStudent(Student student);
}
