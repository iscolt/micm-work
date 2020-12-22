package icu.miners.micm.work.service.impl;

import icu.miners.micm.work.model.entity.Question;
import icu.miners.micm.work.repository.QuestionRepository;
import icu.miners.micm.work.service.QuestionService;
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
 * @author: https://github.com/IvenCc
 * @date: 2020/12/22
 * @see: icu.miners.micm.work.service.impl
 * @version: v1.0.0
 */
@Service
public class QuestionServiceImpl extends AbstractCrudService<Question, Integer> implements QuestionService {

    @Resource
    private QuestionRepository questionRepository;

    protected QuestionServiceImpl(JpaRepository<Question, Integer> repository) {
        super(repository);
    }

    @Override
    public List<String> findSubject() {
        return questionRepository.findSubject();
    }

    @Override
    public List<Question> findBySubject(String subject) {
        return questionRepository.findBySubject(subject);
    }
}
