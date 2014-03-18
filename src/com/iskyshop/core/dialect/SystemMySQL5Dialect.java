/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.core.dialect;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.type.NullableType;

public class SystemMySQL5Dialect extends MySQL5Dialect {
    public SystemMySQL5Dialect() {
        registerHibernateType(-1, Hibernate.TEXT.getName());
    }
}