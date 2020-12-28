package icu.miners.micm.work.service;

import icu.miners.micm.work.model.entity.Organization;
import icu.miners.micm.work.service.base.CrudService;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/isColt
 * @date: 2020/11/10
 * @see: icu.miners.micm.work.service
 * @version: v1.0.0
 */
public interface OrganizationService extends CrudService<Organization, Integer> {
    Organization getCurrentOrganization();

    Organization getByName(String name);
}
