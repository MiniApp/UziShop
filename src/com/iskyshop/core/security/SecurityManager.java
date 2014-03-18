/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.core.security;

import java.util.Map;

public abstract interface SecurityManager {
    public abstract Map<String, String> loadUrlAuthorities();
}