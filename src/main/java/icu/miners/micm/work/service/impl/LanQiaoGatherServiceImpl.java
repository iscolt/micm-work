package icu.miners.micm.work.service.impl;

import icu.miners.micm.work.model.entity.HomeWork;
import icu.miners.micm.work.model.entity.LanQiaoGather;
import icu.miners.micm.work.service.HomeWorkService;
import icu.miners.micm.work.service.LanQiaoGatherService;
import icu.miners.micm.work.service.base.AbstractCrudService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/isColt
 * @date: 2020/10/28
 * @see: icu.miners.micm.work.service.impl
 * @version: v1.0.0
 */
@Service
public class LanQiaoGatherServiceImpl extends AbstractCrudService<LanQiaoGather, Integer> implements LanQiaoGatherService {
    protected LanQiaoGatherServiceImpl(JpaRepository<LanQiaoGather, Integer> repository) {
        super(repository);
    }
}
