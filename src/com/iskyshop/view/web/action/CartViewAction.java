package com.iskyshop.view.web.action;

import com.iskyshop.core.annotation.SecurityMapping;
import com.iskyshop.core.mv.JModelAndView;
import com.iskyshop.core.security.support.SecurityUserHolder;
import com.iskyshop.core.tools.CommUtil;
import com.iskyshop.core.tools.WebForm;
import com.iskyshop.foundation.domain.Address;
import com.iskyshop.foundation.domain.Area;
import com.iskyshop.foundation.domain.Coupon;
import com.iskyshop.foundation.domain.CouponInfo;
import com.iskyshop.foundation.domain.Goods;
import com.iskyshop.foundation.domain.GoodsCart;
import com.iskyshop.foundation.domain.GoodsSpecProperty;
import com.iskyshop.foundation.domain.GoodsSpecification;
import com.iskyshop.foundation.domain.Group;
import com.iskyshop.foundation.domain.GroupGoods;
import com.iskyshop.foundation.domain.OrderForm;
import com.iskyshop.foundation.domain.OrderFormLog;
import com.iskyshop.foundation.domain.Payment;
import com.iskyshop.foundation.domain.PredepositLog;
import com.iskyshop.foundation.domain.Store;
import com.iskyshop.foundation.domain.StoreCart;
import com.iskyshop.foundation.domain.SysConfig;
import com.iskyshop.foundation.domain.User;
import com.iskyshop.foundation.service.IAddressService;
import com.iskyshop.foundation.service.IAreaService;
import com.iskyshop.foundation.service.ICouponInfoService;
import com.iskyshop.foundation.service.IGoodsCartService;
import com.iskyshop.foundation.service.IGoodsService;
import com.iskyshop.foundation.service.IGoodsSpecPropertyService;
import com.iskyshop.foundation.service.IGroupGoodsService;
import com.iskyshop.foundation.service.IOrderFormLogService;
import com.iskyshop.foundation.service.IOrderFormService;
import com.iskyshop.foundation.service.IPaymentService;
import com.iskyshop.foundation.service.IPredepositLogService;
import com.iskyshop.foundation.service.IStoreCartService;
import com.iskyshop.foundation.service.IStoreService;
import com.iskyshop.foundation.service.ISysConfigService;
import com.iskyshop.foundation.service.ITemplateService;
import com.iskyshop.foundation.service.IUserConfigService;
import com.iskyshop.foundation.service.IUserService;
import com.iskyshop.manage.admin.tools.MsgTools;
import com.iskyshop.manage.admin.tools.PaymentTools;
import com.iskyshop.manage.seller.Tools.TransportTools;
import com.iskyshop.pay.tools.PayTools;
import com.iskyshop.view.web.tools.GoodsViewTools;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CartViewAction
{

  @Autowired
  private ISysConfigService configService;

  @Autowired
  private IUserConfigService userConfigService;

  @Autowired
  private IGoodsService goodsService;

  @Autowired
  private IGoodsSpecPropertyService goodsSpecPropertyService;

  @Autowired
  private IAddressService addressService;

  @Autowired
  private IAreaService areaService;

  @Autowired
  private IPaymentService paymentService;

  @Autowired
  private IOrderFormService orderFormService;

  @Autowired
  private IGoodsCartService goodsCartService;

  @Autowired
  private IStoreService storeService;

  @Autowired
  private IOrderFormLogService orderFormLogService;

  @Autowired
  private IUserService userService;

  @Autowired
  private ITemplateService templateService;

  @Autowired
  private IPredepositLogService predepositLogService;

  @Autowired
  private IGroupGoodsService groupGoodsService;

  @Autowired
  private ICouponInfoService couponInfoService;

  @Autowired
  private IStoreCartService storeCartService;

  @Autowired
  private MsgTools msgTools;

  @Autowired
  private PaymentTools paymentTools;

  @Autowired
  private PayTools payTools;

  @Autowired
  private TransportTools transportTools;

  @Autowired
  private GoodsViewTools goodsViewTools;

  private List<StoreCart> cart_calc(HttpServletRequest request)
  {
    List<StoreCart> cart = new ArrayList();
    List user_cart = new ArrayList();
    List cookie_cart = new ArrayList();
    User user = null;
    if (SecurityUserHolder.getCurrentUser() != null) {
      user = this.userService.getObjById(
        SecurityUserHolder.getCurrentUser().getId());
    }
    String cart_session_id = "";
    Map params = new HashMap();
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("cart_session_id")) {
          cart_session_id = CommUtil.null2String(cookie.getValue());
        }
      }
    }
    if (user != null) {
      if (!(cart_session_id.equals("")))
      {
        if (user.getStore() != null) {
          params.clear();
          params.put("cart_session_id", cart_session_id);
          params.put("user_id", user.getId());
          params.put("sc_status", Integer.valueOf(0));
          params.put("store_id", user.getStore().getId());
          List<StoreCart> store_cookie_cart = this.storeCartService
            .query("select obj from StoreCart obj where (obj.cart_session_id=:cart_session_id or obj.user.id=:user_id) and obj.sc_status=:sc_status and obj.store.id=:store_id", 
            params, -1, -1);
          for (Iterator<StoreCart> localIterator1 = store_cookie_cart.iterator(); localIterator1.hasNext(); ) { 
              StoreCart sc = localIterator1.next();
            for (GoodsCart gc : ((StoreCart)sc).getGcs()) {
              gc.getGsps().clear();
              this.goodsCartService.delete(gc.getId());
            }
            this.storeCartService.delete(((StoreCart)sc).getId());
          }
        }

        params.clear();
        params.put("cart_session_id", cart_session_id);
        params.put("sc_status", Integer.valueOf(0));
        cookie_cart = this.storeCartService
          .query("select obj from StoreCart obj where obj.cart_session_id=:cart_session_id and obj.sc_status=:sc_status", 
          params, -1, -1);

        params.clear();
        params.put("user_id", user.getId());
        params.put("sc_status", Integer.valueOf(0));
        user_cart = this.storeCartService
          .query("select obj from StoreCart obj where obj.user.id=:user_id and obj.sc_status=:sc_status", 
          params, -1, -1);
      }
      else {
        params.clear();
        params.put("user_id", user.getId());
        params.put("sc_status", Integer.valueOf(0));
        user_cart = this.storeCartService
          .query("select obj from StoreCart obj where obj.user.id=:user_id and obj.sc_status=:sc_status", 
          params, -1, -1);
      }

    }
    else if (!(cart_session_id.equals(""))) {
      params.clear();
      params.put("cart_session_id", cart_session_id);
      params.put("sc_status", Integer.valueOf(0));
      cookie_cart = this.storeCartService
        .query("select obj from StoreCart obj where obj.cart_session_id=:cart_session_id and obj.sc_status=:sc_status", 
        params, -1, -1);
    }

    //suhao for (Object sc = user_cart.iterator(); ((Iterator) sc).hasNext();) {
    for (Iterator<StoreCart> it = user_cart.iterator(); it.hasNext();) {
        
        StoreCart sc = it.next();
        int k = 1;
        for (StoreCart sc1 : cart) {
            if (sc1.getStore().getId().equals(sc.getStore().getId())) {
                k = 0;
            }
        }
        if (k != 0) {
            cart.add(sc);
        }
    }
    //suhao for (sc = cookie_cart.iterator(); ((Iterator)sc).hasNext(); ) { 
    for (Iterator<StoreCart> it = cookie_cart.iterator(); it.hasNext();) {
      StoreCart sc = it.next();
      int l = 1;
      for (StoreCart sc1 : cart) {
        if (sc1.getStore().getId().equals(sc.getStore().getId())) {
          l = 0;
          for (GoodsCart gc : sc.getGcs()) {
            gc.setSc(sc1);
            this.goodsCartService.update(gc);
          }
          this.storeCartService.delete(sc.getId());
        }
      }
      if (l != 0) {
        cart.add(sc);
      }
    }
    return ((List<StoreCart>)cart);
  }

  @RequestMapping({"/cart_menu_detail.htm"})
  public ModelAndView cart_menu_detail(HttpServletRequest request, HttpServletResponse response)
  {
    ModelAndView mv = new JModelAndView("cart_menu_detail.html", 
      this.configService.getSysConfig(), 
      this.userConfigService.getUserConfig(), 1, request, response);
    List<StoreCart> cart = cart_calc(request);
    List<GoodsCart> list = new ArrayList();
    if (cart != null) {
      for (StoreCart sc : cart) {
        if (sc != null)
          list.addAll(sc.getGcs());
      }
    }
    float total_price = 0.0F;
    for (GoodsCart gc : list) {
      Goods goods = this.goodsService.getObjById(gc.getGoods().getId());
      if (CommUtil.null2String(gc.getCart_type()).equals("combin"))
        total_price = CommUtil.null2Float(goods.getCombin_price());
      else {
        total_price = CommUtil.null2Float(Double.valueOf(CommUtil.mul(Integer.valueOf(gc.getCount()), 
          goods.getGoods_current_price()))) + 
          total_price;
      }
    }
    mv.addObject("total_price", Float.valueOf(total_price));
    mv.addObject("cart", list);
    return mv;
  }

  @RequestMapping({"/add_goods_cart.htm"})
  public void add_goods_cart(HttpServletRequest request, HttpServletResponse response, String id, String count, String price, String gsp, String buy_type)
  {
    String cart_session_id = "";
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("cart_session_id")) {
          cart_session_id = CommUtil.null2String(cookie.getValue());
        }
      }
    }

    if (cart_session_id.equals("")) {
      cart_session_id = UUID.randomUUID().toString();
      Cookie cookie = new Cookie("cart_session_id", cart_session_id);
      cookie.setDomain(CommUtil.generic_domain(request));
      response.addCookie(cookie);
    }
    List<StoreCart> cart = new ArrayList();
    List<StoreCart> user_cart = new ArrayList();
    List<StoreCart> cookie_cart = new ArrayList();
    User user = null;
    if (SecurityUserHolder.getCurrentUser() != null) {
      user = this.userService.getObjById(
        SecurityUserHolder.getCurrentUser().getId());
    }
    Map params = new HashMap();
    StoreCart sc = new StoreCart();
    if (user != null) {
      if (!(cart_session_id.equals("")))
      {
        if (user.getStore() != null) {
          params.clear();
          params.put("cart_session_id", cart_session_id);
          params.put("user_id", user.getId());
          params.put("sc_status", Integer.valueOf(0));
          params.put("store_id", user.getStore().getId());
          List store_cookie_cart = this.storeCartService
            .query("select obj from StoreCart obj where (obj.cart_session_id=:cart_session_id or obj.user.id=:user_id) and obj.sc_status=:sc_status and obj.store.id=:store_id", 
            params, -1, -1);
          for (Iterator localIterator1 = store_cookie_cart.iterator(); localIterator1.hasNext(); ) { 
              sc = (StoreCart)localIterator1.next();
            for (GoodsCart gc : sc.getGcs()) {
              gc.getGsps().clear();
              this.goodsCartService.delete(gc.getId());
            }
            this.storeCartService.delete(sc.getId());
          }
        }

        params.clear();
        params.put("cart_session_id", cart_session_id);
        params.put("sc_status", Integer.valueOf(0));
        cookie_cart = this.storeCartService
          .query("select obj from StoreCart obj where obj.cart_session_id=:cart_session_id and obj.sc_status=:sc_status", 
          params, -1, -1);

        params.clear();
        params.put("user_id", user.getId());
        params.put("sc_status", Integer.valueOf(0));
        user_cart = this.storeCartService
          .query("select obj from StoreCart obj where obj.user.id=:user_id and obj.sc_status=:sc_status", 
          params, -1, -1);
      }
      else {
        params.clear();
        params.put("user_id", user.getId());
        params.put("sc_status", Integer.valueOf(0));
        user_cart = this.storeCartService
          .query("select obj from StoreCart obj where obj.user.id=:user_id and obj.sc_status=:sc_status", 
          params, -1, -1);
      }

    }
    else if (!(cart_session_id.equals(""))) {
      params.clear();
      params.put("cart_session_id", cart_session_id);
      params.put("sc_status", Integer.valueOf(0));
      cookie_cart = this.storeCartService
        .query("select obj from StoreCart obj where obj.cart_session_id=:cart_session_id and obj.sc_status=:sc_status", 
        params, -1, -1);
    }

    for (StoreCart sc2 : user_cart)
    {
      int k = 1;
      for (StoreCart sc1 : cart) {
        if (sc1.getStore().getId().equals(sc2.getStore().getId())) {
          k = 0;
        }
      }
      if (k != 0) {
        cart.add(sc2);
      }
    }
    for (StoreCart sc3 : cookie_cart)
    {
      int l = 1;
      for (StoreCart sc1 : cart) {
        if (sc1.getStore().getId().equals(sc3.getStore().getId())) {
          l = 0;
          for (GoodsCart gc : sc1.getGcs()) {
            gc.setSc(sc1);
            this.goodsCartService.update(gc);
          }
          this.storeCartService.delete(sc1.getId());
        }
      }
      if (l != 0) {
        cart.add(sc3);
      }
    }

    String[] gsp_ids = gsp.split(",");
    Arrays.sort(gsp_ids);
    boolean add = true;
    double total_price = 0.0D;
    int i1 = 0;
    String[] gsp_ids1;
    for (StoreCart sc3 : cart)
      for (GoodsCart gc : sc3.getGcs())
        if ((gsp_ids != null) && (gsp_ids.length > 0) && 
          (gc.getGsps() != null) && (gc.getGsps().size() > 0)) {
          gsp_ids1 = new String[gc.getGsps().size()];
          for (int i = 0; i < gc.getGsps().size(); ++i) {
            gsp_ids1[i] = ((gc.getGsps().get(i) != null) ? 
              ((GoodsSpecProperty)gc
              .getGsps().get(i)).getId().toString() : "");
          }
          Arrays.sort(gsp_ids1);
          if ((!(gc.getGoods().getId().toString().equals(id))) || 
            (!(Arrays.equals(gsp_ids, gsp_ids1)))) continue;
          add = false;
        }
        else if (gc.getGoods().getId().toString().equals(id)) {
          add = false;
        }
    Object obj;
    if (add) {
      Goods goods = this.goodsService.getObjById(CommUtil.null2Long(id));
      String type = "save";
      StoreCart sc3 = new StoreCart();
      for (StoreCart sc1 : cart)
      {
        if (sc1.getStore().getId()
          .equals(goods.getGoods_store().getId())) {
          sc3 = sc1;
          type = "update";
          break;
        }
      }
      sc3.setStore(goods.getGoods_store());
      if (((String)type).equals("save")) {
        sc3.setAddTime(new Date());
        this.storeCartService.save(sc3);
      } else {
        this.storeCartService.update(sc3);
      }

      obj = new GoodsCart();
      ((GoodsCart)obj).setAddTime(new Date());
      if (CommUtil.null2String(buy_type).equals("")) {
        ((GoodsCart)obj).setCount(CommUtil.null2Int(count));
        ((GoodsCart)obj).setPrice(BigDecimal.valueOf(CommUtil.null2Double(price)));
      }
      if (CommUtil.null2String(buy_type).equals("combin")) {
        ((GoodsCart)obj).setCount(1);
        ((GoodsCart)obj).setCart_type("combin");
        ((GoodsCart)obj).setPrice(goods.getCombin_price());
      }
      ((GoodsCart)obj).setGoods(goods);
      String spec_info = "";
      GoodsSpecProperty spec_property;
      for (String gsp_id : gsp_ids) {
        spec_property = this.goodsSpecPropertyService
          .getObjById(CommUtil.null2Long(gsp_id));
        ((GoodsCart)obj).getGsps().add(spec_property);
        if (spec_property != null) {
          spec_info = spec_property.getSpec().getName() + ":" + 
            spec_property.getValue() + " " + spec_info;
        }
      }
      ((GoodsCart)obj).setSc(sc);
      ((GoodsCart)obj).setSpec_info(spec_info);
      this.goodsCartService.save((GoodsCart)obj);
      sc.getGcs().add((GoodsCart) obj);
      double cart_total_price = 0.0D;
      //suhao for (??? = sc.getGcs().iterator(); ((Iterator)???).hasNext(); ) { 
      for (Iterator<GoodsCart> it = sc.getGcs().iterator(); it.hasNext();) { 
        
        GoodsCart gc1 = it.next();
        if (CommUtil.null2String(gc1.getCart_type()).equals(""))
        {
          cart_total_price = cart_total_price + 
            CommUtil.null2Double(gc1.getGoods()
            .getGoods_current_price()) * 
            gc1.getCount();
        }
        if (!(CommUtil.null2String(gc1.getCart_type()).equals("combin")))
          continue;
        cart_total_price = cart_total_price + 
          CommUtil.null2Double(gc1.getGoods()
          .getCombin_price()) * 
          gc1.getCount();
      }

      sc.setTotal_price(BigDecimal.valueOf(
        CommUtil.formatMoney(Double.valueOf(cart_total_price))));
      if (user == null)
        sc.setCart_session_id(cart_session_id);
      else {
        sc.setUser(user);
      }
      if (((String)type).equals("save")) {
        sc.setAddTime(new Date());
        this.storeCartService.save(sc);
      } else {
        this.storeCartService.update(sc);
      }
      int i4 = 1;
      for (StoreCart sc1 : cart) {
        if (sc1.getStore().getId().equals(sc.getStore().getId())) {
          i4 = 0;
        }
      }
      if (i4 != 0) {
        cart.add(sc);
      }
    }
    for (Object type = cart.iterator(); ((Iterator)type).hasNext(); ) { StoreCart sc1 = (StoreCart)((Iterator)type).next();

      i1 += sc1.getGcs().size();
      for (obj = sc1.getGcs().iterator(); ((Iterator)obj).hasNext(); ) { GoodsCart gc1 = (GoodsCart)((Iterator)obj).next();

        total_price = total_price + 
          CommUtil.mul(gc1.getPrice(), Integer.valueOf(gc1.getCount()));
      }
    }
    Map map = new HashMap();
    map.put("count", Integer.valueOf(i1));
    map.put("total_price", Double.valueOf(total_price));
    String ret = Json.toJson(map, JsonFormat.compact());
    response.setContentType("text/plain");
    response.setHeader("Cache-Control", "no-cache");
    response.setCharacterEncoding("UTF-8");
    try
    {
      PrintWriter writer = response.getWriter();
      writer.print(ret);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  @RequestMapping({"/remove_goods_cart.htm"})
  public void remove_goods_cart(HttpServletRequest request, HttpServletResponse response, String id, String store_id)
  {
    GoodsCart gc = this.goodsCartService.getObjById(CommUtil.null2Long(id));
    StoreCart the_sc = gc.getSc();
    gc.getGsps().clear();

    this.goodsCartService.delete(CommUtil.null2Long(id));
    if (the_sc.getGcs().size() == 0) {
      this.storeCartService.delete(the_sc.getId());
    }
    List<StoreCart> cart = cart_calc(request);
    double total_price = 0.0D;
    double sc_total_price = 0.0D;
    double count = 0.0D;
    for (StoreCart sc2 : cart) {
      for (GoodsCart gc1 : sc2.getGcs()) {
        total_price = CommUtil.null2Double(gc1.getPrice()) * 
          gc1.getCount() + 
          total_price;
        count += 1.0D;
        if ((store_id == null) || (store_id.equals("")) || 
          (!(sc2.getStore().getId().toString().equals(store_id))))
          continue;
        sc_total_price = sc_total_price + 
          CommUtil.null2Double(gc1.getPrice()) * 
          gc1.getCount();
        sc2.setTotal_price(BigDecimal.valueOf(sc_total_price));
      }

      this.storeCartService.update(sc2);
    }
    request.getSession(false).setAttribute("cart", cart);
    Map map = new HashMap();
    map.put("count", Double.valueOf(count));
    map.put("total_price", Double.valueOf(total_price));
    map.put("sc_total_price", Double.valueOf(sc_total_price));
    response.setContentType("text/plain");
    response.setHeader("Cache-Control", "no-cache");
    response.setCharacterEncoding("UTF-8");
    try
    {
      PrintWriter writer = response.getWriter();
      writer.print(Json.toJson(map, JsonFormat.compact()));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  @RequestMapping({"/goods_count_adjust.htm"})
  public void goods_count_adjust(HttpServletRequest request, HttpServletResponse response, String cart_id, String store_id, String count)
  {
    List<StoreCart> cart = cart_calc(request);

    double goods_total_price = 0.0D;
    String error = "100";
    Goods goods = null;
    String cart_type = "";
    GoodsCart gc;
    for (StoreCart sc : cart)
      for (Iterator localIterator2 = sc.getGcs().iterator(); localIterator2.hasNext(); ) { gc = (GoodsCart)localIterator2.next();
        if (gc.getId().toString().equals(cart_id)) {
          goods = gc.getGoods();
          cart_type = CommUtil.null2String(gc.getCart_type());
        }
      }
    StoreCart sc;
    if (cart_type.equals("")) {
      if (goods.getGroup_buy() == 2) {
        GroupGoods gg = new GroupGoods();
        for (GroupGoods gg1 : goods.getGroup_goods_list()) {
          if (gg1.getGg_goods().equals(goods.getId())) {
            gg = gg1;
          }
        }
        if (gg.getGg_count() >= CommUtil.null2Int(count))
          //suhao for (gc = cart.iterator(); gc.hasNext(); ) { 
          for (Iterator<StoreCart> it = cart.iterator(); it.hasNext();) {
            sc = it.next();
            for (int i = 0; i < ((StoreCart)sc).getGcs().size(); ++i) {
              gc = (GoodsCart)((StoreCart)sc).getGcs().get(i);
              GoodsCart gc1 = gc;
              if (gc.getId().toString().equals(cart_id)) {
                ((StoreCart)sc).setTotal_price(BigDecimal.valueOf(CommUtil.add(
                  ((StoreCart)sc).getTotal_price(), 
                  Double.valueOf((CommUtil.null2Int(count) - gc
                  .getCount()) * 
                  CommUtil.null2Double(gc
                  .getPrice())))));
                gc.setCount(CommUtil.null2Int(count));
                gc1 = gc;
                ((StoreCart)sc).getGcs().remove(gc);
                ((StoreCart)sc).getGcs().add(gc1);
                goods_total_price = CommUtil.null2Double(gc1
                  .getPrice()) * 
                  gc1.getCount();
                this.storeCartService.update((StoreCart)sc);
              }
            }
          }
        else {
          error = "300";
        }
      }
      else if (goods.getGoods_inventory() >= CommUtil.null2Int(count)) {
        //suhao for (sc = cart.iterator(); ((Iterator)sc).hasNext(); ) { 
        for (Iterator<StoreCart> it = cart.iterator(); it.hasNext();) {
          sc = it.next();
          for (int i = 0; i < sc.getGcs().size(); ++i) {
            gc = (GoodsCart)sc.getGcs().get(i);
            GoodsCart gc1 = gc;
            if (gc.getId().toString().equals(cart_id)) {
              sc.setTotal_price(BigDecimal.valueOf(
                CommUtil.add(sc.getTotal_price(), 
                Double.valueOf((CommUtil.null2Int(count) - gc
                .getCount()) * 
                Double.parseDouble(gc
                .getPrice()
                .toString())))));
              gc.setCount(CommUtil.null2Int(count));
              gc1 = gc;
              sc.getGcs().remove(gc);
              sc.getGcs().add(gc1);
              goods_total_price = Double.parseDouble(gc1
                .getPrice().toString()) * 
                gc1.getCount();
              this.storeCartService.update(sc);
            }
          }
        }
      } else {
        error = "200";
      }
    }

    if (cart_type.equals("combin")) {
      if (goods.getGoods_inventory() >= CommUtil.null2Int(count))
        //suhao for (sc = cart.iterator(); ((Iterator)sc).hasNext(); ) { 
        for (Iterator<StoreCart> it = cart.iterator(); it.hasNext();) { 
          
          sc = it.next();
          for (int i = 0; i < sc.getGcs().size(); ++i) {
            gc = (GoodsCart)sc.getGcs().get(i);
            GoodsCart gc1 = (GoodsCart)gc;
            if (((GoodsCart)gc).getId().toString().equals(cart_id)) {
              sc.setTotal_price(BigDecimal.valueOf(CommUtil.add(
                sc.getTotal_price(), 
                Float.valueOf((CommUtil.null2Int(count) - ((GoodsCart)gc).getCount()) * 
                CommUtil.null2Float(((GoodsCart)gc).getGoods()
                .getCombin_price())))));
              ((GoodsCart)gc).setCount(CommUtil.null2Int(count));
              gc1 = (GoodsCart)gc;
              sc.getGcs().remove(gc);
              sc.getGcs().add(gc1);
              goods_total_price = Double.parseDouble(gc1
                .getPrice().toString()) * 
                gc1.getCount();
              this.storeCartService.update(sc);
            }
          }
        }
      else {
        error = "200";
      }
    }
    DecimalFormat df = new DecimalFormat("0.00");
    Object map = new HashMap();
    ((Map)map).put("count", count);
    //suhao for (Object gc = cart.iterator(); ((Iterator)gc).hasNext(); ) { 
    for (Iterator<StoreCart> it = cart.iterator(); it.hasNext();) {
      sc = it.next();

      if (sc.getStore().getId().equals(CommUtil.null2Long(store_id))) {
        ((Map)map).put("sc_total_price", 
          Float.valueOf(CommUtil.null2Float(sc.getTotal_price())));
      }
    }
    ((Map)map).put("goods_total_price", 
      Double.valueOf(df.format(goods_total_price)));
    ((Map)map).put("error", error);
    response.setContentType("text/plain");
    response.setHeader("Cache-Control", "no-cache");
    response.setCharacterEncoding("UTF-8");
    try
    {
      PrintWriter writer = response.getWriter();

      writer.print(Json.toJson(map, JsonFormat.compact()));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  @SecurityMapping(title="查看购物车", value="/goods_cart1.htm*", rtype="buyer", rname="购物流程1", rcode="goods_cart", rgroup="在线购物")
  @RequestMapping({"/goods_cart1.htm"})
  public ModelAndView goods_cart1(HttpServletRequest request, HttpServletResponse response)
  {
    ModelAndView mv = new JModelAndView("goods_cart1.html", 
      this.configService.getSysConfig(), 
      this.userConfigService.getUserConfig(), 1, request, response);
    List<StoreCart> cart = cart_calc(request);
    if (cart != null) {
      Store store = (SecurityUserHolder.getCurrentUser().getStore() != null) ? 
        SecurityUserHolder.getCurrentUser().getStore() : null;
      if (store != null) {
        for (StoreCart sc : cart) {
          if (sc.getStore().getId().equals(store.getId())) {
            for (GoodsCart gc : sc.getGcs()) {
              gc.getGsps().clear();
              this.goodsCartService.delete(gc.getId());
            }
            sc.getGcs().clear();
            this.storeCartService.delete(sc.getId());
          }
        }
      }
      request.getSession(false).setAttribute("cart", cart);
      mv.addObject("cart", cart);
      mv.addObject("goodsViewTools", this.goodsViewTools);
    } else {
      mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
        this.userConfigService.getUserConfig(), 1, request, 
        response);
      mv.addObject("op_title", "购物车信息为空");
      mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
    }

    if (this.configService.getSysConfig().isZtc_status()) {
      List ztc_goods = null;
      Map ztc_map = new HashMap();
      ztc_map.put("ztc_status", Integer.valueOf(3));
      ztc_map.put("now_date", new Date());
      ztc_map.put("ztc_gold", Integer.valueOf(0));
      List goods = this.goodsService
        .query("select obj from Goods obj where obj.ztc_status =:ztc_status and obj.ztc_begin_time <=:now_date and obj.ztc_gold>:ztc_gold order by obj.ztc_dredge_price desc", 
        ztc_map, -1, -1);

      ztc_goods = randomZtcGoods(goods);
      mv.addObject("ztc_goods", ztc_goods);
    }
    return mv;
  }

  private List<Goods> randomZtcGoods(List<Goods> goods) {
    Random random = new Random();
    int random_num = 0;
    int num = 0;
    if (goods.size() - 8 > 0) {
      num = goods.size() - 8;
      random_num = random.nextInt(num);
    }
    Map ztc_map = new HashMap();
    ztc_map.put("ztc_status", Integer.valueOf(3));
    ztc_map.put("now_date", new Date());
    ztc_map.put("ztc_gold", Integer.valueOf(0));
    List ztc_goods = this.goodsService
      .query("select obj from Goods obj where obj.ztc_status =:ztc_status and obj.ztc_begin_time <=:now_date and obj.ztc_gold>:ztc_gold order by obj.ztc_dredge_price desc", 
      ztc_map, random_num, 8);
    Collections.shuffle(ztc_goods);
    return ztc_goods;
  }

  @SecurityMapping(title="确认购物车填写地址", value="/goods_cart2.htm*", rtype="buyer", rname="购物流程2", rcode="goods_cart", rgroup="在线购物")
  @RequestMapping({"/goods_cart2.htm"})
  public ModelAndView goods_cart2(HttpServletRequest request, HttpServletResponse response, String store_id)
  {
    ModelAndView mv = new JModelAndView("goods_cart2.html", 
      this.configService.getSysConfig(), 
      this.userConfigService.getUserConfig(), 1, request, response);
    List<StoreCart> cart = cart_calc(request);
    StoreCart sc = null;
    if (cart != null) {
      for (StoreCart sc1 : cart) {
        if (sc1.getStore().getId().equals(CommUtil.null2Long(store_id))) {
          sc = sc1;
          break;
        }
      }
    }
    if (sc != null) {
      Map params = new HashMap();
      params.put("user_id", SecurityUserHolder.getCurrentUser().getId());
      List addrs = this.addressService
        .query("select obj from Address obj where obj.user.id=:user_id order by obj.addTime desc", 
        params, -1, -1);
      mv.addObject("addrs", addrs);
      if ((store_id == null) || (store_id.equals(""))) {
        store_id = sc.getStore().getId().toString();
      }
      String cart_session = CommUtil.randomString(32);
      request.getSession(false)
        .setAttribute("cart_session", cart_session);
      params.clear();
      params.put("coupon_order_amount", sc.getTotal_price());
      params.put("user_id", SecurityUserHolder.getCurrentUser().getId());
      params.put("coupon_begin_time", new Date());
      params.put("coupon_end_time", new Date());
      params.put("status", Integer.valueOf(0));
      List couponinfos = this.couponInfoService
        .query("select obj from CouponInfo obj where obj.coupon.coupon_order_amount<=:coupon_order_amount and obj.status=:status and obj.user.id=:user_id and obj.coupon.coupon_begin_time<=:coupon_begin_time and obj.coupon.coupon_end_time>=:coupon_end_time", 
        params, -1, -1);
      mv.addObject("couponinfos", couponinfos);
      mv.addObject("sc", sc);
      mv.addObject("cart_session", cart_session);
      mv.addObject("store_id", store_id);
      mv.addObject("transportTools", this.transportTools);
      mv.addObject("goodsViewTools", this.goodsViewTools);

      boolean goods_delivery = false;
      List<GoodsCart> goodCarts = sc.getGcs();
      for (GoodsCart gc : goodCarts) {
        if (gc.getGoods().getGoods_choice_type() == 0) {
          goods_delivery = true;
          break;
        }
      }
      mv.addObject("goods_delivery", Boolean.valueOf(goods_delivery));
    } else {
      mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
        this.userConfigService.getUserConfig(), 1, request, 
        response);
      mv.addObject("op_title", "购物车信息为空");
      mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
    }
    return mv;
  }

  @SecurityMapping(title="完成订单提交进入支付", value="/goods_cart3.htm*", rtype="buyer", rname="购物流程3", rcode="goods_cart", rgroup="在线购物")
  @RequestMapping({"/goods_cart3.htm"})
  public ModelAndView goods_cart3(HttpServletRequest request, HttpServletResponse response, String cart_session, String store_id, String addr_id, String coupon_id)
    throws Exception
  {
    ModelAndView mv = new JModelAndView("goods_cart3.html", 
      this.configService.getSysConfig(), 
      this.userConfigService.getUserConfig(), 1, request, response);
    String cart_session1 = (String)request.getSession(false).getAttribute(
      "cart_session");
    List<StoreCart> cart = cart_calc(request);
    if (cart != null) {
      if (CommUtil.null2String(cart_session1).equals(cart_session)) {
        request.getSession(false).removeAttribute("cart_session");
        WebForm wf = new WebForm();
        OrderForm of = (OrderForm)wf.toPo(request, OrderForm.class);
        of.setAddTime(new Date());
        of.setOrder_id(SecurityUserHolder.getCurrentUser().getId() + 
          CommUtil.formatTime("yyyyMMddHHmmss", new Date()));
        Address addr = this.addressService.getObjById(
          CommUtil.null2Long(addr_id));
        of.setAddr(addr);
        of.setOrder_status(10);
        of.setUser(SecurityUserHolder.getCurrentUser());
        of.setStore(this.storeService.getObjById(
          CommUtil.null2Long(store_id)));
        of.setTotalPrice(BigDecimal.valueOf(CommUtil.add(
          of.getGoods_amount(), of.getShip_price())));
        if (!(CommUtil.null2String(coupon_id).equals(""))) {
          CouponInfo ci = this.couponInfoService.getObjById(
            CommUtil.null2Long(coupon_id));
          ci.setStatus(1);
          this.couponInfoService.update(ci);
          of.setCi(ci);
          of.setTotalPrice(BigDecimal.valueOf(CommUtil.subtract(of
            .getTotalPrice(), ci.getCoupon().getCoupon_amount())));
        }
        of.setOrder_type("web");
        this.orderFormService.save(of);
        GoodsCart gc;
        for (StoreCart sc : cart) {
          if (sc.getStore().getId().toString().equals(store_id)) {
            for (Iterator localIterator2 = sc.getGcs().iterator(); localIterator2.hasNext(); ) { gc = (GoodsCart)localIterator2.next();
              gc.setOf(of);
              this.goodsCartService.update(gc);
            }
            sc.setCart_session_id(null);
            sc.setUser(SecurityUserHolder.getCurrentUser());
            sc.setSc_status(1);
            this.storeCartService.update(sc);
            break;
          }
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null)
        {
          Cookie[] arrayOfCookie1;
          int localGoodsCart1 = (arrayOfCookie1 = cookies).length; 
          for (int i = 0; i < localGoodsCart1; ++i) { 
            Cookie cookie = arrayOfCookie1[i];
            if (cookie.getName().equals("cart_session_id")) {
              cookie.setDomain(CommUtil.generic_domain(request));
              cookie.setValue("");
              cookie.setMaxAge(0);
              response.addCookie(cookie);
            }
          }
        }
        OrderFormLog ofl = new OrderFormLog();
        ofl.setAddTime(new Date());
        ofl.setOf(of);
        ofl.setLog_info("提交订单");
        ofl.setLog_user(SecurityUserHolder.getCurrentUser());
        this.orderFormLogService.save(ofl);
        mv.addObject("of", of);
        mv.addObject("paymentTools", this.paymentTools);
        if (this.configService.getSysConfig().isEmailEnable()) {
          send_email(request, of, of.getUser().getEmail(), 
            "email_tobuyer_order_submit_ok_notify");
        }
        if (this.configService.getSysConfig().isSmsEnbale())
          send_sms(request, of, of.getUser().getMobile(), 
            "sms_tobuyer_order_submit_ok_notify");
      }
      else {
        mv = new JModelAndView("error.html", 
          this.configService.getSysConfig(), 
          this.userConfigService.getUserConfig(), 1, request, 
          response);
        mv.addObject("op_title", "订单已经失效");
        mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
      }
    } else {
      mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
        this.userConfigService.getUserConfig(), 1, request, 
        response);
      mv.addObject("op_title", "订单信息错误");
      mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
    }
    return mv;
  }

  @SecurityMapping(title="订单支付详情", value="/order_pay_view.htm*", rtype="buyer", rname="购物流程3", rcode="goods_cart", rgroup="在线购物")
  @RequestMapping({"/order_pay_view.htm"})
  public ModelAndView order_pay_view(HttpServletRequest request, HttpServletResponse response, String id)
  {
    ModelAndView mv = new JModelAndView("order_pay.html", 
      this.configService.getSysConfig(), 
      this.userConfigService.getUserConfig(), 1, request, response);
    OrderForm of = this.orderFormService.getObjById(CommUtil.null2Long(id));
    if (of.getOrder_status() == 10) {
      mv.addObject("of", of);
      mv.addObject("paymentTools", this.paymentTools);
      mv.addObject("url", CommUtil.getURL(request));
    } else if (of.getOrder_status() < 10) {
      mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
        this.userConfigService.getUserConfig(), 1, request, 
        response);
      mv.addObject("op_title", "该订单已经取消！");
      mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
    } else {
      mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
        this.userConfigService.getUserConfig(), 1, request, 
        response);
      mv.addObject("op_title", "该订单已经付款！");
      mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
    }
    return mv;
  }

  @SecurityMapping(title="订单支付", value="/order_pay.htm*", rtype="buyer", rname="购物流程3", rcode="goods_cart", rgroup="在线购物")
  @RequestMapping({"/order_pay.htm"})
  public ModelAndView order_pay(HttpServletRequest request, HttpServletResponse response, String payType, String order_id)
  {
    ModelAndView mv = null;
    OrderForm of = this.orderFormService.getObjById(
      CommUtil.null2Long(order_id));
    if (of.getOrder_status() == 10) {
      if (CommUtil.null2String(payType).equals("")) {
        mv = new JModelAndView("error.html", 
          this.configService.getSysConfig(), 
          this.userConfigService.getUserConfig(), 1, request, 
          response);
        mv.addObject("op_title", "支付方式错误！");
        mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
      }
      else {
        List payments = new ArrayList();
        Map params = new HashMap();
        if (this.configService.getSysConfig().getConfig_payment_type() == 1) {
          params.put("mark", payType);
          params.put("type", "admin");
          payments = this.paymentService
            .query("select obj from Payment obj where obj.mark=:mark and obj.type=:type", 
            params, -1, -1);
        } else {
          params.put("store_id", of.getStore().getId());
          params.put("mark", payType);
          payments = this.paymentService
            .query("select obj from Payment obj where obj.mark=:mark and obj.store.id=:store_id", 
            params, -1, -1);
        }
        of.setPayment((Payment)payments.get(0));
        this.orderFormService.update(of);
        if (payType.equals("balance")) {
          mv = new JModelAndView("balance_pay.html", 
            this.configService.getSysConfig(), 
            this.userConfigService.getUserConfig(), 1, request, 
            response);
        } else if (payType.equals("outline")) {
          mv = new JModelAndView("outline_pay.html", 
            this.configService.getSysConfig(), 
            this.userConfigService.getUserConfig(), 1, request, 
            response);
          String pay_session = CommUtil.randomString(32);
          request.getSession(false).setAttribute("pay_session", 
            pay_session);
          mv.addObject("paymentTools", this.paymentTools);
          mv.addObject(
            "store_id", 
            this.orderFormService
            .getObjById(CommUtil.null2Long(order_id))
            .getStore().getId());
          mv.addObject("pay_session", pay_session);
        }
        else if (payType.equals("payafter")) {
          mv = new JModelAndView("payafter_pay.html", 
            this.configService.getSysConfig(), 
            this.userConfigService.getUserConfig(), 1, request, 
            response);
          String pay_session = CommUtil.randomString(32);
          request.getSession(false).setAttribute("pay_session", 
            pay_session);
          mv.addObject("paymentTools", this.paymentTools);
          mv.addObject(
            "store_id", 
            this.orderFormService
            .getObjById(CommUtil.null2Long(order_id))
            .getStore().getId());
          mv.addObject("pay_session", pay_session);
        } else {
          mv = new JModelAndView("line_pay.html", 
            this.configService.getSysConfig(), 
            this.userConfigService.getUserConfig(), 1, request, 
            response);
          mv.addObject("payType", payType);
          mv.addObject("url", CommUtil.getURL(request));
          mv.addObject("payTools", this.payTools);
          mv.addObject("type", "goods");
          mv.addObject("payment_id", of.getPayment().getId());
        }
        mv.addObject("order_id", order_id);
      }
    } else {
      mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
        this.userConfigService.getUserConfig(), 1, request, 
        response);
      mv.addObject("op_title", "该订单不能进行付款！");
      mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
    }
    return mv;
  }

  @SecurityMapping(title="订单线下支付", value="/order_pay_outline.htm*", rtype="buyer", rname="购物流程3", rcode="goods_cart", rgroup="在线购物")
  @RequestMapping({"/order_pay_outline.htm"})
  public ModelAndView order_pay_outline(HttpServletRequest request, HttpServletResponse response, String payType, String order_id, String pay_msg, String pay_session)
    throws Exception
  {
    ModelAndView mv = new JModelAndView("success.html", 
      this.configService.getSysConfig(), 
      this.userConfigService.getUserConfig(), 1, request, response);
    String pay_session1 = CommUtil.null2String(request.getSession(false)
      .getAttribute("pay_session"));
    if (pay_session1.equals(pay_session)) {
      OrderForm of = this.orderFormService.getObjById(
        CommUtil.null2Long(order_id));
      of.setPay_msg(pay_msg);
      Map params = new HashMap();
      params.put("mark", "outline");
      params.put("store_id", of.getStore().getId());
      List payments = this.paymentService
        .query("select obj from Payment obj where obj.mark=:mark and obj.store.id=:store_id", 
        params, -1, -1);
      if (payments.size() > 0) {
        of.setPayment((Payment)payments.get(0));
        of.setPayTime(new Date());
      }
      of.setOrder_status(15);
      this.orderFormService.update(of);
      if (this.configService.getSysConfig().isSmsEnbale()) {
        send_sms(request, of, of.getStore().getUser().getMobile(), 
          "sms_toseller_outline_pay_ok_notify");
      }
      if (this.configService.getSysConfig().isEmailEnable()) {
        send_email(request, of, 
          of.getStore().getUser().getEmail(), 
          "email_toseller_outline_pay_ok_notify");
      }

      OrderFormLog ofl = new OrderFormLog();
      ofl.setAddTime(new Date());
      ofl.setLog_info("提交线下支付申请");
      ofl.setLog_user(SecurityUserHolder.getCurrentUser());
      ofl.setOf(of);
      this.orderFormLogService.save(ofl);
      request.getSession(false).removeAttribute("pay_session");
      mv.addObject("op_title", "线下支付提交成功，等待卖家审核！");
      mv.addObject("url", CommUtil.getURL(request) + "/buyer/order.htm");
    } else {
      mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
        this.userConfigService.getUserConfig(), 1, request, 
        response);
      mv.addObject("op_title", "订单已经支付，禁止重复支付！");
      mv.addObject("url", CommUtil.getURL(request) + "/buyer/order.htm");
    }
    return mv;
  }

  @SecurityMapping(title="订单货到付款", value="/order_pay_payafter.htm*", rtype="buyer", rname="购物流程3", rcode="goods_cart", rgroup="在线购物")
  @RequestMapping({"/order_pay_payafter.htm"})
  public ModelAndView order_pay_payafter(HttpServletRequest request, HttpServletResponse response, String payType, String order_id, String pay_msg, String pay_session)
    throws Exception
  {
    ModelAndView mv = new JModelAndView("success.html", 
      this.configService.getSysConfig(), 
      this.userConfigService.getUserConfig(), 1, request, response);
    String pay_session1 = CommUtil.null2String(request.getSession(false)
      .getAttribute("pay_session"));
    if (pay_session1.equals(pay_session)) {
      OrderForm of = this.orderFormService.getObjById(
        CommUtil.null2Long(order_id));
      of.setPay_msg(pay_msg);
      Map params = new HashMap();
      params.put("mark", "payafter");
      params.put("store_id", of.getStore().getId());
      List payments = this.paymentService
        .query("select obj from Payment obj where obj.mark=:mark and obj.store.id=:store_id", 
        params, -1, -1);
      if (payments.size() > 0) {
        of.setPayment((Payment)payments.get(0));
        of.setPayTime(new Date());
      }
      of.setOrder_status(16);
      this.orderFormService.update(of);
      if (this.configService.getSysConfig().isSmsEnbale()) {
        send_sms(request, of, of.getStore().getUser().getMobile(), 
          "sms_toseller_payafter_pay_ok_notify");
      }
      if (this.configService.getSysConfig().isEmailEnable()) {
        send_email(request, of, 
          of.getStore().getUser().getEmail(), 
          "email_toseller_payafter_pay_ok_notify");
      }

      OrderFormLog ofl = new OrderFormLog();
      ofl.setAddTime(new Date());
      ofl.setLog_info("提交货到付款申请");
      ofl.setLog_user(SecurityUserHolder.getCurrentUser());
      ofl.setOf(of);
      this.orderFormLogService.save(ofl);
      request.getSession(false).removeAttribute("pay_session");
      mv.addObject("op_title", "货到付款提交成功，等待卖家发货！");
      mv.addObject("url", CommUtil.getURL(request) + "/buyer/order.htm");
    } else {
      mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
        this.userConfigService.getUserConfig(), 1, request, 
        response);
      mv.addObject("op_title", "订单已经支付，禁止重复支付！");
      mv.addObject("url", CommUtil.getURL(request) + "/buyer/order.htm");
    }
    return mv;
  }

  @SecurityMapping(title="订单预付款支付", value="/order_pay_balance.htm*", rtype="buyer", rname="购物流程3", rcode="goods_cart", rgroup="在线购物")
  @RequestMapping({"/order_pay_balance.htm"})
  public ModelAndView order_pay_balance(HttpServletRequest request, HttpServletResponse response, String payType, String order_id, String pay_msg)
    throws Exception
  {
    ModelAndView mv = new JModelAndView("success.html", 
      this.configService.getSysConfig(), 
      this.userConfigService.getUserConfig(), 1, request, response);
    OrderForm of = this.orderFormService.getObjById(
      CommUtil.null2Long(order_id));
    User user = this.userService.getObjById(
      SecurityUserHolder.getCurrentUser().getId());

    if (CommUtil.null2Double(user.getAvailableBalance()) > 
      CommUtil.null2Double(of.getTotalPrice())) {
      of.setPay_msg(pay_msg);
      of.setOrder_status(20);
      Map params = new HashMap();
      params.put("mark", "balance");
      params.put("store_id", of.getStore().getId());
      List payments = this.paymentService
        .query("select obj from Payment obj where obj.mark=:mark and obj.store.id=:store_id", 
        params, -1, -1);
      if (payments.size() > 0) {
        of.setPayment((Payment)payments.get(0));
        of.setPayTime(new Date());
      }
      boolean ret = this.orderFormService.update(of);
      if (this.configService.getSysConfig().isEmailEnable()) {
        send_email(request, of, 
          of.getStore().getUser().getEmail(), 
          "email_toseller_balance_pay_ok_notify");
        send_email(request, of, 
          of.getStore().getUser().getEmail(), 
          "email_tobuyer_balance_pay_ok_notify");
      }
      if (this.configService.getSysConfig().isSmsEnbale()) {
        send_sms(request, of, of.getStore().getUser().getMobile(), 
          "sms_toseller_balance_pay_ok_notify");
        send_sms(request, of, of.getUser().getMobile(), 
          "sms_tobuyer_balance_pay_ok_notify");
      }
      if (ret) {
        user.setAvailableBalance(BigDecimal.valueOf(CommUtil.subtract(
          user.getAvailableBalance(), of.getTotalPrice())));
        user.setFreezeBlance(BigDecimal.valueOf(CommUtil.add(
          user.getFreezeBlance(), of.getTotalPrice())));
        this.userService.update(user);
        PredepositLog log = new PredepositLog();
        log.setAddTime(new Date());
        log.setPd_log_user(user);
        log.setPd_op_type("消费");
        log.setPd_log_amount(BigDecimal.valueOf(-
          CommUtil.null2Double(of.getTotalPrice())));
        log.setPd_log_info("订单" + of.getOrder_id() + "购物减少可用预存款");
        log.setPd_type("可用预存款");
        this.predepositLogService.save(log);

        for (GoodsCart gc : of.getGcs()) {
          Goods goods = gc.getGoods();
          if ((goods.getGroup() != null) && (goods.getGroup_buy() == 2)) {
            for (GroupGoods gg : goods.getGroup_goods_list())
            {
              if (gg.getGroup().getId()
                .equals(goods.getGroup().getId())) {
                gg.setGg_count(gg.getGg_count() - gc.getCount());
                gg.setGg_def_count(gg.getGg_def_count() + 
                  gc.getCount());
                this.groupGoodsService.update(gg);
              }
            }
          }
          List gsps = new ArrayList();
          for (GoodsSpecProperty gsp : gc.getGsps()) {
            gsps.add(gsp.getId().toString());
          }
          String[] gsp_list = new String[gsps.size()];
          gsps.toArray(gsp_list);
          goods.setGoods_salenum(goods.getGoods_salenum() + 
            gc.getCount());
          Map temp;
          if (goods.getInventory_type().equals("all")) {
            goods.setGoods_inventory(goods.getGoods_inventory() - 
              gc.getCount());
          } else {
            List list = (List)Json.fromJson(ArrayList.class, 
              goods.getGoods_inventory_detail());
            for (Iterator localIterator4 = list.iterator(); localIterator4.hasNext(); ) { temp = (Map)localIterator4.next();
              String[] temp_ids = CommUtil.null2String(
                temp.get("id")).split("_");
              Arrays.sort(temp_ids);
              Arrays.sort(gsp_list);
              if (Arrays.equals(temp_ids, gsp_list)) {
                temp.put("count", 
                  Integer.valueOf(CommUtil.null2Int(temp.get("count")) - 
                  gc.getCount()));
              }
            }
            goods.setGoods_inventory_detail(Json.toJson(list, 
              JsonFormat.compact()));
          }
          for (GroupGoods gg : goods.getGroup_goods_list())
          {
            if ((!(gg.getGroup().getId()
              .equals(goods.getGroup().getId()))) || 
              (gg.getGg_count() != 0)) continue;
            goods.setGroup_buy(3);
          }

          this.goodsService.update(goods);
        }

      }

      OrderFormLog ofl = new OrderFormLog();
      ofl.setAddTime(new Date());
      ofl.setLog_info("预付款支付");
      ofl.setLog_user(SecurityUserHolder.getCurrentUser());
      ofl.setOf(of);
      this.orderFormLogService.save(ofl);
      mv.addObject("op_title", "预付款支付成功！");
      mv.addObject("url", CommUtil.getURL(request) + "/buyer/order.htm");
    } else {
      mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
        this.userConfigService.getUserConfig(), 1, request, 
        response);
      mv.addObject("op_title", "可用余额不足，支付失败！");
      mv.addObject("url", CommUtil.getURL(request) + "/buyer/order.htm");
    }
    return mv;
  }

  @SecurityMapping(title="订单支付结果", value="/order_finish.htm*", rtype="buyer", rname="购物流程3", rcode="goods_cart", rgroup="在线购物")
  @RequestMapping({"/order_finish.htm"})
  public ModelAndView order_finish(HttpServletRequest request, HttpServletResponse response, String order_id)
  {
    ModelAndView mv = new JModelAndView("order_finish.html", 
      this.configService.getSysConfig(), 
      this.userConfigService.getUserConfig(), 1, request, response);
    OrderForm obj = this.orderFormService.getObjById(
      CommUtil.null2Long(order_id));
    mv.addObject("obj", obj);
    return mv;
  }

  @SecurityMapping(title="地址新增", value="/cart_address.htm*", rtype="buyer", rname="购物流程3", rcode="goods_cart", rgroup="在线购物")
  @RequestMapping({"/cart_address.htm"})
  public ModelAndView cart_address(HttpServletRequest request, HttpServletResponse response, String id, String store_id)
  {
    ModelAndView mv = new JModelAndView("cart_address.html", 
      this.configService.getSysConfig(), 
      this.userConfigService.getUserConfig(), 1, request, response);
    List areas = this.areaService.query(
      "select obj from Area obj where obj.parent.id is null", null, 
      -1, -1);
    mv.addObject("areas", areas);
    mv.addObject("store_id", store_id);
    return mv;
  }

  @SecurityMapping(title="购物车中收货地址保存", value="/cart_address_save.htm*", rtype="buyer", rname="购物流程3", rcode="goods_cart", rgroup="在线购物")
  @RequestMapping({"/cart_address_save.htm"})
  public String cart_address_save(HttpServletRequest request, HttpServletResponse response, String id, String area_id, String store_id)
  {
    WebForm wf = new WebForm();
    Address address = null;
    if (id.equals("")) {
      address = (Address)wf.toPo(request, Address.class);
      address.setAddTime(new Date());
    } else {
      Address obj = this.addressService.getObjById(Long.valueOf(Long.parseLong(id)));
      address = (Address)wf.toPo(request, obj);
    }
    address.setUser(SecurityUserHolder.getCurrentUser());
    Area area = this.areaService.getObjById(CommUtil.null2Long(area_id));
    address.setArea(area);
    if (id.equals(""))
      this.addressService.save(address);
    else
      this.addressService.update(address);
    return "redirect:goods_cart2.htm?store_id=" + store_id;
  }

  @SecurityMapping(title="地址切换", value="/order_address.htm*", rtype="buyer", rname="购物流程3", rcode="goods_cart", rgroup="在线购物")
  @RequestMapping({"/order_address.htm"})
  public void order_address(HttpServletRequest request, HttpServletResponse response, String addr_id, String store_id)
  {
    List<StoreCart> cart = (List)request.getSession(false)
      .getAttribute("cart");
    StoreCart sc = null;
    if (cart != null) {
      for (StoreCart sc1 : cart) {
        if (sc1.getStore().getId().equals(CommUtil.null2Long(store_id))) {
          sc = sc1;
          break;
        }
      }
    }
    Address addr = this.addressService.getObjById(
      CommUtil.null2Long(addr_id));
    List sms = this.transportTools.query_cart_trans(sc, 
      CommUtil.null2String(addr.getArea().getId()));

    response.setContentType("text/plain");
    response.setHeader("Cache-Control", "no-cache");
    response.setCharacterEncoding("UTF-8");
    try
    {
      PrintWriter writer = response.getWriter();
      writer.print(Json.toJson(sms, JsonFormat.compact()));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void send_email(HttpServletRequest request, OrderForm order, String email, String mark) throws Exception
  {
    com.iskyshop.foundation.domain.Template template = this.templateService.getObjByProperty("mark", mark);
    if ((template != null) && (template.isOpen())) {
      String subject = template.getTitle();
      String path = request.getSession().getServletContext()
        .getRealPath("") + 
        File.separator + "vm" + File.separator;
      if (!(CommUtil.fileExist(path))) {
        CommUtil.createFolder(path);
      }
      PrintWriter pwrite = new PrintWriter(
        new OutputStreamWriter(new FileOutputStream(path + "msg.vm", false), "UTF-8"));
      pwrite.print(template.getContent());
      pwrite.flush();
      pwrite.close();

      Properties p = new Properties();
      p.setProperty("file.resource.loader.path", 
        request.getRealPath("/") + "vm" + File.separator);
      p.setProperty("input.encoding", "UTF-8");
      p.setProperty("output.encoding", "UTF-8");
      Velocity.init(p);
      org.apache.velocity.Template blank = Velocity.getTemplate("msg.vm", 
        "UTF-8");
      VelocityContext context = new VelocityContext();
      context.put("buyer", order.getUser());
      context.put("seller", order.getStore().getUser());
      context.put("config", this.configService.getSysConfig());
      context.put("send_time", CommUtil.formatLongDate(new Date()));
      context.put("webPath", CommUtil.getURL(request));
      context.put("order", order);
      StringWriter writer = new StringWriter();
      blank.merge(context, writer);

      String content = writer.toString();
      this.msgTools.sendEmail(email, subject, content);
    }
  }

  private void send_sms(HttpServletRequest request, OrderForm order, String mobile, String mark) throws Exception
  {
    com.iskyshop.foundation.domain.Template template = this.templateService.getObjByProperty("mark", mark);
    if ((template != null) && (template.isOpen())) {
      String path = request.getSession().getServletContext()
        .getRealPath("") + 
        File.separator + "vm" + File.separator;
      if (!(CommUtil.fileExist(path))) {
        CommUtil.createFolder(path);
      }
      PrintWriter pwrite = new PrintWriter(
        new OutputStreamWriter(new FileOutputStream(path + "msg.vm", false), "UTF-8"));
      pwrite.print(template.getContent());
      pwrite.flush();
      pwrite.close();

      Properties p = new Properties();
      p.setProperty("file.resource.loader.path", 
        request.getRealPath("/") + "vm" + File.separator);
      p.setProperty("input.encoding", "UTF-8");
      p.setProperty("output.encoding", "UTF-8");
      Velocity.init(p);
      org.apache.velocity.Template blank = Velocity.getTemplate("msg.vm", 
        "UTF-8");
      VelocityContext context = new VelocityContext();
      context.put("buyer", order.getUser());
      context.put("seller", order.getStore().getUser());
      context.put("config", this.configService.getSysConfig());
      context.put("send_time", CommUtil.formatLongDate(new Date()));
      context.put("webPath", CommUtil.getURL(request));
      context.put("order", order);
      StringWriter writer = new StringWriter();
      blank.merge(context, writer);

      String content = writer.toString();
      this.msgTools.sendSMS(mobile, content);
    }
  }
}