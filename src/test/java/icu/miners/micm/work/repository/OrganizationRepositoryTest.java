package icu.miners.micm.work.repository;

import icu.miners.micm.work.model.entity.Organization;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/isColt
 * @date: 2020/11/10
 * @see: icu.miners.micm.work.repository
 * @version: v1.0.0
 */
@SpringBootTest
@Transactional
class OrganizationRepositoryTest {

    @Resource
    private OrganizationRepository organizationRepository;

    @Test
    void findAll() {
        System.out.println(organizationRepository.findAll());
    }

    @Test
    void init() {
//        {name: "计算机与通信工程学院"},
//        {name: "土木与建筑工程学院"},
//        {name: "机电学院"},
//        {name: "汽车工程学院"},
//        {name: "商学院"},
//        {name: "工商税务管理学院"},
//        {name: "国际酒店与饮食文化学院"},
//        {name: "公益慈善管理学院"},
//        {name: "外国语学院"},
//        {name: "艺术学院"},
//        Organization parent = new Organization();
//        parent.setName("南京工业大学浦江学院");
//        parent.setStatus((short)1);
//        organizationRepository.saveAndFlush(parent);
//
//        Organization org1 = new Organization();
//        org1.setName("计算机与通信工程学院");
//        org1.setStatus((short)1);
//        org1.setParent(parent);
//        organizationRepository.save(org1);
    }
}
