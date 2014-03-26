package com.iskyshop.foundation.dao;

import com.iskyshop.core.base.GenericDAO;
import com.iskyshop.foundation.domain.Document;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("documentDAO")
public class DocumentDAO extends GenericDAO<Document> {
}