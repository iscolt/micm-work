package icu.miners.micm.work.service;

import icu.miners.micm.work.model.entity.HomeWork;
import icu.miners.micm.work.model.entity.StudentHomeWork;
import icu.miners.micm.work.service.base.CrudService;

import java.util.List;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/isColt
 * @date: 2020/9/15
 * @see: icu.miners.carte_alimentaire.service
 * @version: v1.0.0
 */
public interface HomeWorkService extends CrudService<HomeWork, Integer> {

    List<HomeWork> listByStatus(short status);

    /**
     * 发布作业
     * @param homeWork
     * @return
     */
    HomeWork releaseHomework(HomeWork homeWork);

    /**
     * 检查作业是否过期
     * @return
     */
    Boolean checkIsNotExpired(HomeWork homeWork);

    /**
     * 获取作业文件夹
     * @param homeWork
     * @return
     */
    String getHomeWorkFolderPath(HomeWork homeWork);
}
