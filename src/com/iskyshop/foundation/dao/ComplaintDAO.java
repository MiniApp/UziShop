package com.iskyshop.foundation.dao;

import com.iskyshop.core.base.GenericDAO;
import com.iskyshop.foundation.domain.Complaint;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("complaintDAO")
public class ComplaintDAO extends GenericDAO<Complaint> {
}