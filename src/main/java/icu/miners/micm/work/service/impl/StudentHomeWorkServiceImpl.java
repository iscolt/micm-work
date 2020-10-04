package icu.miners.micm.work.service.impl;

import icu.miners.micm.work.model.entity.HomeWork;
import icu.miners.micm.work.model.entity.Student;
import icu.miners.micm.work.model.entity.StudentHomeWork;
import icu.miners.micm.work.repository.StudentHomeWorkRepository;
import icu.miners.micm.work.service.StudentHomeWorkService;
import icu.miners.micm.work.service.base.AbstractCrudService;
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
public class StudentHomeWorkServiceImpl extends AbstractCrudService<StudentHomeWork, Integer> implements StudentHomeWorkService {

    @Resource
    private StudentHomeWorkRepository studentHomeWorkRepository;

    protected StudentHomeWorkServiceImpl(JpaRepository<StudentHomeWork, Integer> repository) {
        super(repository);
    }

    @Override
    public List<StudentHomeWork> listByHomeWork(HomeWork homeWork) {
        return studentHomeWorkRepository.findAllByHomeWork(homeWork);
    }

    @Override
    public StudentHomeWork getByHomeWorkAndStudent(HomeWork homeWork, Student student) {
        return studentHomeWorkRepository.findByHomeWorkAndStudent(homeWork, student);
    }

    @Override
    public List<StudentHomeWork> findByHomework(HomeWork homeWork) {
        return studentHomeWorkRepository.findAllByHomeWork(homeWork);
    }
}
