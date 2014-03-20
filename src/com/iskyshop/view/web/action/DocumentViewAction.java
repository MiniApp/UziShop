/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.view.web.action;

import com.iskyshop.core.mv.JModelAndView;
import com.iskyshop.foundation.domain.Document;
import com.iskyshop.foundation.service.IDocumentService;
import com.iskyshop.foundation.service.ISysConfigService;
import com.iskyshop.foundation.service.IUserConfigService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DocumentViewAction {

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private IUserConfigService userConfigService;

    @Autowired
    private IDocumentService documentService;

    @RequestMapping({ "/doc.htm" })
    public ModelAndView doc(HttpServletRequest request, HttpServletResponse response, String mark) {
        ModelAndView mv = new JModelAndView("doc.html", this.configService.getSysConfig(),
                this.userConfigService.getUserConfig(), 1, request, response);
        Document obj = this.documentService.getObjByProperty("mark", mark);
        mv.addObject("obj", obj);
        return mv;
    }
}