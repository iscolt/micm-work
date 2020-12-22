package icu.miners.micm.work.service;

import icu.miners.micm.work.model.entity.Question;
import icu.miners.micm.work.service.base.CrudService;
import java.util.List;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/IvenCc
 * @date: 2020/12/22
 * @see: icu.miners.micm.work.service
 * @version: v1.0.0
 */
public interface QuestionService extends CrudService<Question, Integer> {

    /**
     * 查询所有科目
     * @return
     */
    List<String> findSubject();

    /**
     * 根据科目查题库
     * @param subject
     * @return
     */
    List<Question> findBySubject(String subject);
}
