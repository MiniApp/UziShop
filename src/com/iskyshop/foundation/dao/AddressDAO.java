package com.iskyshop.foundation.dao;

import com.iskyshop.core.base.GenericDAO;
import com.iskyshop.foundation.domain.Address;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("addressDAO")
public class AddressDAO extends GenericDAO<Address> {
}