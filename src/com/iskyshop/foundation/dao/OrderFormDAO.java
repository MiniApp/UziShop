package com.iskyshop.foundation.dao;

import com.iskyshop.core.base.GenericDAO;
import com.iskyshop.foundation.domain.OrderForm;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("orderFormDAO")
public class OrderFormDAO extends GenericDAO<OrderForm> {
}