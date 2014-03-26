package com.iskyshop.foundation.dao;

import com.iskyshop.core.base.GenericDAO;
import com.iskyshop.foundation.domain.Message;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("messageDAO")
public class MessageDAO extends GenericDAO<Message> {
}