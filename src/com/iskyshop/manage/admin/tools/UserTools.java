/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.manage.admin.tools;

import com.iskyshop.core.tools.CommUtil;
import com.iskyshop.foundation.domain.User;
import com.iskyshop.foundation.service.IUserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.concurrent.SessionRegistry;
import org.springframework.stereotype.Component;

@Component
public class UserTools {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private IUserService userSerivce;

    public List<User> query_user() {
        List users = new ArrayList();
        Object[] objs = this.sessionRegistry.getAllPrincipals();
        for (int i = 0; i < objs.length; ++i) {
            User user = this.userSerivce.getObjByProperty("userName", CommUtil.null2String(objs[i]));

            users.add(user);
        }

        return users;
    }

    public boolean userOnLine(String userName) {
        boolean ret = false;
        List<User> users = query_user();
        for (User user : users) {
            if ((user != null) && (user.getUsername().equals(userName.trim()))) {
                ret = true;
            }
        }
        return ret;
    }
}