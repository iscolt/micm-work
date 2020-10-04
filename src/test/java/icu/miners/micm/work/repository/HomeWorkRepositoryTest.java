package icu.miners.micm.work.repository;

import icu.miners.micm.work.model.entity.HomeWork;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/isColt
 * @date: 2020/10/4
 * @see: icu.miners.micm.work.repository
 * @version: v1.0.0
 */
@Transactional
@SpringBootTest
class HomeWorkRepositoryTest {

    @Resource
    private HomeWorkRepository homeWorkRepository;

    @Test
    void findByIdNotIn() {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        List<HomeWork> homeWork = homeWorkRepository.findByIdNotIn(ids);
        System.out.println(homeWork);
    }
}
