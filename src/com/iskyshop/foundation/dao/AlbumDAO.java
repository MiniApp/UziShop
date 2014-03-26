package com.iskyshop.foundation.dao;

import com.iskyshop.core.base.GenericDAO;
import com.iskyshop.foundation.domain.Album;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("albumDAO")
public class AlbumDAO extends GenericDAO<Album> {
}