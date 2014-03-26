package com.iskyshop.foundation.dao;

import com.iskyshop.core.base.GenericDAO;
import com.iskyshop.foundation.domain.Payment;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("paymentDAO")
public class PaymentDAO extends GenericDAO<Payment> {
}