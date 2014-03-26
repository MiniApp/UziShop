package com.iskyshop.foundation.dao;

import com.iskyshop.core.base.GenericDAO;
import com.iskyshop.foundation.domain.User;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("userDAO")
public class UserDAO extends GenericDAO<User> {
}