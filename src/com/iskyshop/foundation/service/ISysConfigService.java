/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.service;

import com.iskyshop.foundation.domain.SysConfig;

public abstract interface ISysConfigService {
    public abstract boolean save(SysConfig paramSysConfig);

    public abstract boolean delete(SysConfig paramSysConfig);

    public abstract boolean update(SysConfig paramSysConfig);

    public abstract SysConfig getSysConfig();
}