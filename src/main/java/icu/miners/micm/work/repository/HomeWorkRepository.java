package icu.miners.micm.work.repository;

import icu.miners.micm.work.model.entity.HomeWork;
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
public interface HomeWorkRepository extends JpaRepository<HomeWork, Integer> {

    List<HomeWork> findAllByStatus(short status);
}
