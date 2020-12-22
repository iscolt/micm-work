package icu.miners.micm.work.repository;

import icu.miners.micm.work.model.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/IvenCc
 * @date: 2020/12/22
 * @see: icu.miners.micm.work.repository
 * @version: v1.0.0
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Query(nativeQuery = true, value = "select distinct subject from question")
    List<String> findSubject();

    List<Question> findBySubject(String subject);
}
