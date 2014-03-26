package com.iskyshop.foundation.dao;

import com.iskyshop.core.base.GenericDAO;
import com.iskyshop.foundation.domain.Favorite;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("favoriteDAO")
public class FavoriteDAO extends GenericDAO<Favorite> {
}