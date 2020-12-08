package icu.miners.micm.work.service.impl;

import icu.miners.micm.work.model.entity.Article;
import icu.miners.micm.work.model.entity.EmailTask;
import icu.miners.micm.work.service.ArticleService;
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
 * @date: 2020/12/8
 * @see: icu.miners.micm.work.service.impl
 * @version: v1.0.0
 */
@Service
public class ArticleServiceImpl extends AbstractCrudService<Article, Integer> implements ArticleService {
    protected ArticleServiceImpl(JpaRepository<Article, Integer> repository) {
        super(repository);
    }
}
