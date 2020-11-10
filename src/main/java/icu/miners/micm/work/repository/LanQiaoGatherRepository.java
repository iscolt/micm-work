package icu.miners.micm.work.repository;

import icu.miners.micm.work.model.entity.HomeWork;
import icu.miners.micm.work.model.entity.LanQiaoGather;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/isColt
 * @date: 2020/10/28
 * @see: icu.miners.micm.work.repository
 * @version: v1.0.0
 */
@Repository
public interface LanQiaoGatherRepository extends JpaRepository<LanQiaoGather, Integer> {

    Page<LanQiaoGather> findAllByCollege(String college, Pageable pageable);

    LanQiaoGather findByNumber(String number);
}
