package icu.miners.micm.work.service;

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
public interface StudentService extends CrudService<Student, Integer> {

    Student getByNumber(String number);

    Boolean checkRole(String number);

    String getToken(Student student);

    Student getCurrentUser();

    List<Student> listValid();

    Student getByEmail(String email);
}
