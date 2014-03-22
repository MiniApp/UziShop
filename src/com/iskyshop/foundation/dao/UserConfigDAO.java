package com.iskyshop.foundation.dao;

import com.iskyshop.core.base.GenericDAO;
import com.iskyshop.foundation.domain.UserConfig;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("userConfigDAO")
public class UserConfigDAO extends GenericDAO<UserConfig> {
}