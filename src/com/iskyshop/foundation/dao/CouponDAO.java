package com.iskyshop.foundation.dao;

import com.iskyshop.core.base.GenericDAO;
import com.iskyshop.foundation.domain.Coupon;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("couponDAO")
public class CouponDAO extends GenericDAO<Coupon> {
}