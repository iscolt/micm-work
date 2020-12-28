package icu.miners.micm.work.service.impl;

import icu.miners.micm.work.model.entity.Organization;
import icu.miners.micm.work.repository.OrganizationRepository;
import icu.miners.micm.work.service.OrganizationService;
import icu.miners.micm.work.service.StudentService;
import icu.miners.micm.work.service.base.AbstractCrudService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/isColt
 * @date: 2020/11/10
 * @see: icu.miners.micm.work.service.impl
 * @version: v1.0.0
 */
@Service
public class OrganizationServiceImpl extends AbstractCrudService<Organization, Integer> implements OrganizationService  {

    @Resource
    private StudentService studentService;

    @Resource
    private OrganizationRepository organizationRepository;

    protected OrganizationServiceImpl(JpaRepository<Organization, Integer> repository) {
        super(repository);
    }

    @Override
    public Organization getCurrentOrganization() {
        return studentService.getCurrentUser().getOrganization();
    }

    @Override
    public Organization getByName(String name) {
        return organizationRepository.findByName(name);
    }
}
