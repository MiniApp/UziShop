package com.iskyshop.foundation.dao;

import com.iskyshop.core.base.GenericDAO;
import com.iskyshop.foundation.domain.Activity;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("activityDAO")
public class ActivityDAO extends GenericDAO<Activity> {
}