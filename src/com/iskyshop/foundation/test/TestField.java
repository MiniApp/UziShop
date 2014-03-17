/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.test;

import com.easyjf.beans.BeanWrapper;
import com.iskyshop.core.tools.CommUtil;
import java.beans.PropertyDescriptor;
import java.io.PrintStream;

public class TestField {
    public static void main(String[] args) throws ClassNotFoundException {
        String field = "store.grade";
        if (field.indexOf(".") > 0) {
            Class entity = Class.forName("com.iskyshop.domain."
                    + CommUtil.first2upper(field.substring(field.indexOf("_") + 1, field.indexOf("."))));

            String propertyName = field.substring(field.indexOf(".") + 1);
            System.out.println(" Ù–‘÷µ:" + propertyName);
            BeanWrapper entity_wrapper = new BeanWrapper(entity);
            PropertyDescriptor[] entity_propertys = entity_wrapper.getPropertyDescriptors();
            for (PropertyDescriptor pd : entity_propertys)
                if (pd.getName().equals(propertyName))
                    System.out.println(pd.getName());
        }
    }
}