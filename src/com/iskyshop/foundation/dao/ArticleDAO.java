package com.iskyshop.foundation.dao;

import com.iskyshop.core.base.GenericDAO;
import com.iskyshop.foundation.domain.Article;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("articleDAO")
public class ArticleDAO extends GenericDAO<Article> {
}