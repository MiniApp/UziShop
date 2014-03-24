package com.iskyshop.view.web.action;

import com.iskyshop.core.mv.JModelAndView;
import com.iskyshop.core.security.support.SecurityUserHolder;
import com.iskyshop.core.tools.CommUtil;
import com.iskyshop.core.tools.Md5Encrypt;
import com.iskyshop.foundation.domain.Goods;
import com.iskyshop.foundation.domain.GoodsCart;
import com.iskyshop.foundation.domain.Group;
import com.iskyshop.foundation.domain.Store;
import com.iskyshop.foundation.domain.StoreCart;
import com.iskyshop.foundation.domain.SysConfig;
import com.iskyshop.foundation.domain.User;
import com.iskyshop.foundation.service.IAccessoryService;
import com.iskyshop.foundation.service.IArticleClassService;
import com.iskyshop.foundation.service.IArticleService;
import com.iskyshop.foundation.service.IBargainGoodsService;
import com.iskyshop.foundation.service.IDeliveryGoodsService;
import com.iskyshop.foundation.service.IGoodsBrandService;
import com.iskyshop.foundation.service.IGoodsCartService;
import com.iskyshop.foundation.service.IGoodsClassService;
import com.iskyshop.foundation.service.IGoodsFloorService;
import com.iskyshop.foundation.service.IGoodsService;
import com.iskyshop.foundation.service.IGroupGoodsService;
import com.iskyshop.foundation.service.IGroupService;
import com.iskyshop.foundation.service.IMessageService;
import com.iskyshop.foundation.service.INavigationService;
import com.iskyshop.foundation.service.IPartnerService;
import com.iskyshop.foundation.service.IRoleService;
import com.iskyshop.foundation.service.IStoreCartService;
import com.iskyshop.foundation.service.IStoreService;
import com.iskyshop.foundation.service.ISysConfigService;
import com.iskyshop.foundation.service.IUserConfigService;
import com.iskyshop.foundation.service.IUserService;
import com.iskyshop.manage.admin.tools.MsgTools;
import com.iskyshop.view.web.tools.GoodsFloorViewTools;
import com.iskyshop.view.web.tools.GoodsViewTools;
import com.iskyshop.view.web.tools.NavViewTools;
import com.iskyshop.view.web.tools.StoreViewTools;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexViewAction {

	@Autowired
	private ISysConfigService configService;

	@Autowired
	private IUserConfigService userConfigService;

	@Autowired
	private IGoodsClassService goodsClassService;

	@Autowired
	private IGoodsBrandService goodsBrandService;

	@Autowired
	private IPartnerService partnerService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IArticleClassService articleClassService;

	@Autowired
	private IArticleService articleService;

	@Autowired
	private IAccessoryService accessoryService;

	@Autowired
	private IMessageService messageService;

	@Autowired
	private IStoreService storeService;

	@Autowired
	private IGoodsService goodsService;

	@Autowired
	private INavigationService navigationService;

	@Autowired
	private IGroupGoodsService groupGoodsService;

	@Autowired
	private IGroupService groupService;

	@Autowired
	private IGoodsFloorService goodsFloorService;

	@Autowired
	private IBargainGoodsService bargainGoodsService;

	@Autowired
	private IDeliveryGoodsService deliveryGoodsService;

	@Autowired
	private IStoreCartService storeCartService;

	@Autowired
	private IGoodsCartService goodsCartService;

	@Autowired
	private NavViewTools navTools;

	@Autowired
	private GoodsViewTools goodsViewTools;

	@Autowired
	private StoreViewTools storeViewTools;

	@Autowired
	private MsgTools msgTools;

	@Autowired
	private GoodsFloorViewTools gf_tools;

	@RequestMapping({ "/top.htm" })
	public ModelAndView top(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("top.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		List msgs = new ArrayList();
		if (SecurityUserHolder.getCurrentUser() != null) {
			Map params = new HashMap();
			params.put("status", Integer.valueOf(0));
			params.put("reply_status", Integer.valueOf(1));
			params.put("from_user_id", SecurityUserHolder.getCurrentUser()
					.getId());
			params.put("to_user_id", SecurityUserHolder.getCurrentUser()
					.getId());
			msgs = this.messageService
					.query("select obj from Message obj where obj.parent.id is null and (obj.status=:status and obj.toUser.id=:to_user_id) or (obj.reply_status=:reply_status and obj.fromUser.id=:from_user_id) ",
							params, -1, -1);
		}
		Store store = null;
		if (SecurityUserHolder.getCurrentUser() != null)
			store = this.storeService.getObjByProperty("user.id",
					SecurityUserHolder.getCurrentUser().getId());
		mv.addObject("store", store);
		mv.addObject("navTools", this.navTools);
		mv.addObject("msgs", msgs);
		List<GoodsCart> list = new ArrayList();
		List<StoreCart> cart = new ArrayList();
		List<StoreCart> user_cart = new ArrayList();
		List<StoreCart> cookie_cart = new ArrayList();
		User user = null;
		if (SecurityUserHolder.getCurrentUser() != null) {
			user = this.userService.getObjById(SecurityUserHolder
					.getCurrentUser().getId());
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
			if (!(cart_session_id.equals(""))) {
				if (user.getStore() != null) {
					params.clear();
					params.put("cart_session_id", cart_session_id);
					params.put("user_id", user.getId());
					params.put("sc_status", Integer.valueOf(0));
					params.put("store_id", user.getStore().getId());
					List store_cookie_cart = this.storeCartService
							.query("select obj from StoreCart obj where (obj.cart_session_id=:cart_session_id or obj.user.id=:user_id) and obj.sc_status=:sc_status and obj.store.id=:store_id",
									params, -1, -1);
					for (Iterator localIterator1 = store_cookie_cart.iterator(); localIterator1
							.hasNext();) {
						StoreCart sc = (StoreCart) localIterator1.next();
						for (GoodsCart gc : ((StoreCart) sc).getGcs()) {
							gc.getGsps().clear();
							this.goodsCartService.delete(gc.getId());
						}
						this.storeCartService.delete(((StoreCart) sc).getId());
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
			} else {
				params.clear();
				params.put("user_id", user.getId());
				params.put("sc_status", Integer.valueOf(0));
				user_cart = this.storeCartService
						.query("select obj from StoreCart obj where obj.user.id=:user_id and obj.sc_status=:sc_status",
								params, -1, -1);
			}

		} else if (!(cart_session_id.equals(""))) {
			params.clear();
			params.put("cart_session_id", cart_session_id);
			params.put("sc_status", Integer.valueOf(0));
			cookie_cart = this.storeCartService
					.query("select obj from StoreCart obj where obj.cart_session_id=:cart_session_id and obj.sc_status=:sc_status",
							params, -1, -1);
		}

		// suhao for (StoreCart sc = user_cart.iterator(); ((Iterator)
		// sc).hasNext();) {
		for (Iterator<StoreCart> it = user_cart.iterator(); it.hasNext();) {
			StoreCart sc = it.next();
			int k = 1;
			for (StoreCart sc1 : cart) {
				if (sc1.getStore() != null && sc1.getStore().getId().equals(sc.getStore().getId())) {
					k = 0;
				}
			}
			if (k != 0)
				cart.add(sc);
		}
		for (Iterator<StoreCart> it = cookie_cart.iterator(); it.hasNext();) {
			StoreCart sc = it.next();
			int l = 1;
			for (StoreCart sc1 : cart) {
				if (sc1.getStore() != null
						&& sc.getStore() != null
						&& sc1.getStore().getId().equals(sc.getStore().getId())) {
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
		if (cart != null) {
			for (Iterator<StoreCart> it = cart.iterator(); it.hasNext();) {
				StoreCart sc = it.next();
				if (sc != null) {
					// list.addAll(sc.getGcs());
					for (Iterator<GoodsCart> its = sc.getGcs().iterator(); its
							.hasNext();) {
						GoodsCart gc = its.next();
						list.add(gc);
					}
				}
			}
		}
		float total_price = 0.0F;
		for (GoodsCart gc : list) {
			Goods goods = this.goodsService.getObjById(gc.getGoods().getId());
			if (CommUtil.null2String(gc.getCart_type()).equals("combin"))
				total_price = CommUtil.null2Float(goods.getCombin_price());
			else {
				total_price = CommUtil.null2Float(Double.valueOf(CommUtil.mul(
						Integer.valueOf(gc.getCount()),
						goods.getGoods_current_price())))
						+ total_price;
			}
		}
		mv.addObject("total_price", Float.valueOf(total_price));
		mv.addObject("cart", list);
		return ((ModelAndView) mv);
	}

	@RequestMapping({ "/nav.htm" })
	public ModelAndView nav(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("nav.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		mv.addObject("navTools", this.navTools);
		return mv;
	}

	@RequestMapping({ "/nav1.htm" })
	public ModelAndView nav1(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("nav1.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		Map params = new HashMap();
		params.put("display", Boolean.valueOf(true));
		List gcs = this.goodsClassService
				.query("select obj from GoodsClass obj where obj.parent.id is null and obj.display=:display order by obj.sequence asc",
						params, 0, 15);
		mv.addObject("gcs", gcs);
		mv.addObject("navTools", this.navTools);
		return mv;
	}

	@RequestMapping({ "/head.htm" })
	public ModelAndView head(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("head.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		String type = CommUtil.null2String(request.getAttribute("type"));
		mv.addObject("type", (type.equals("")) ? "goods" : type);
		return mv;
	}

	@RequestMapping({ "/login_head.htm" })
	public ModelAndView login_head(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("login_head.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		return mv;
	}

	@RequestMapping({ "/floor.htm" })
	public ModelAndView floor(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("floor.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		Map params = new HashMap();
		params.put("gf_display", Boolean.valueOf(true));
		List floors = this.goodsFloorService
				.query("select obj from GoodsFloor obj where obj.gf_display=:gf_display and obj.parent.id is null order by obj.gf_sequence asc",
						params, -1, -1);
		mv.addObject("floors", floors);
		mv.addObject("gf_tools", this.gf_tools);
		mv.addObject("url", CommUtil.getURL(request));
		return mv;
	}

	@RequestMapping({ "/footer.htm" })
	public ModelAndView footer(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("footer.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		mv.addObject("navTools", this.navTools);
		return mv;
	}

	@RequestMapping({ "/index.htm" })
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("index.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		Map params = new HashMap();
		params.put("display", Boolean.valueOf(true));
		List gcs = this.goodsClassService
				.query("select obj from GoodsClass obj where obj.parent.id is null and obj.display=:display order by obj.sequence asc",
						params, 0, 15);
		mv.addObject("gcs", gcs);
		params.clear();
		params.put("audit", Integer.valueOf(1));
		params.put("recommend", Boolean.valueOf(true));
		List gbs = this.goodsBrandService
				.query("select obj from GoodsBrand obj where obj.audit=:audit and obj.recommend=:recommend order by obj.sequence",
						params, -1, -1);
		mv.addObject("gbs", gbs);
		params.clear();
		List img_partners = this.partnerService
				.query("select obj from Partner obj where obj.image.id is not null order by obj.sequence asc",
						params, -1, -1);
		mv.addObject("img_partners", img_partners);
		List text_partners = this.partnerService
				.query("select obj from Partner obj where obj.image.id is null order by obj.sequence asc",
						params, -1, -1);
		mv.addObject("text_partners", text_partners);
		params.clear();
		params.put("mark", "news");
		List acs = this.articleClassService
				.query("select obj from ArticleClass obj where obj.parent.id is null and obj.mark!=:mark order by obj.sequence asc",
						params, 0, 9);
		mv.addObject("acs", acs);
		params.clear();
		params.put("class_mark", "news");
		params.put("display", Boolean.valueOf(true));
		List articles = this.articleService
				.query("select obj from Article obj where obj.articleClass.mark=:class_mark and obj.display=:display order by obj.addTime desc",
						params, 0, 5);
		mv.addObject("articles", articles);
		params.clear();
		params.put("store_recommend", Boolean.valueOf(true));
		params.put("goods_status", Integer.valueOf(0));
		List store_reommend_goods_list = this.goodsService
				.query("select obj from Goods obj where obj.store_recommend=:store_recommend and obj.goods_status=:goods_status order by obj.store_recommend_time desc",
						params, -1, -1);
		List store_reommend_goods = new ArrayList();
		int max = (store_reommend_goods_list.size() >= 5) ? 4
				: store_reommend_goods_list.size() - 1;
		for (int i = 0; i <= max; ++i) {
			store_reommend_goods.add((Goods) store_reommend_goods_list.get(i));
		}
		mv.addObject("store_reommend_goods", store_reommend_goods);
		mv.addObject("store_reommend_goods_count", Double.valueOf(Math
				.ceil(CommUtil.div(
						Integer.valueOf(store_reommend_goods_list.size()),
						Integer.valueOf(5)))));
		mv.addObject("goodsViewTools", this.goodsViewTools);
		mv.addObject("storeViewTools", this.storeViewTools);
		if (SecurityUserHolder.getCurrentUser() != null) {
			mv.addObject("user", this.userService.getObjById(SecurityUserHolder
					.getCurrentUser().getId()));
		}
		params.clear();
		params.put("beginTime", new Date());
		params.put("endTime", new Date());
		List groups = this.groupService
				.query("select obj from Group obj where obj.beginTime<=:beginTime and obj.endTime>=:endTime",
						params, -1, -1);
		if (groups.size() > 0) {
			params.clear();
			params.put("gg_status", Integer.valueOf(1));
			params.put("gg_recommend", Integer.valueOf(1));
			params.put("group_id", ((Group) groups.get(0)).getId());
			List ggs = this.groupGoodsService
					.query("select obj from GroupGoods obj where obj.gg_status=:gg_status and obj.gg_recommend=:gg_recommend and obj.group.id=:group_id order by obj.gg_recommend_time desc",
							params, 0, 1);
			if (ggs.size() > 0)
				mv.addObject("group", ggs.get(0));
		}
		params.clear();
		params.put("bg_time",
				CommUtil.formatDate(CommUtil.formatShortDate(new Date())));
		params.put("bg_status", Integer.valueOf(1));
		List bgs = this.bargainGoodsService
				.query("select obj from BargainGoods obj where obj.bg_time=:bg_time and obj.bg_status=:bg_status",
						params, 0, 5);
		mv.addObject("bgs", bgs);
		params.clear();
		params.put("d_status", Integer.valueOf(1));
		params.put("d_begin_time", new Date());
		params.put("d_end_time", new Date());
		List dgs = this.deliveryGoodsService
				.query("select obj from DeliveryGoods obj where obj.d_status=:d_status and obj.d_begin_time<=:d_begin_time and obj.d_end_time>=:d_end_time order by obj.d_audit_time desc",
						params, 0, 5);
		mv.addObject("dgs", dgs);
		return mv;
	}

	@RequestMapping({ "/close.htm" })
	public ModelAndView close(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("close.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		return mv;
	}

	@RequestMapping({ "/404.htm" })
	public ModelAndView error404(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("404.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		String iskyshop_view_type = CommUtil.null2String(request.getSession(
				false).getAttribute("iskyshop_view_type"));
		if ((iskyshop_view_type != null) && (!(iskyshop_view_type.equals("")))
				&& (iskyshop_view_type.equals("weixin"))) {
			String store_id = CommUtil.null2String(request.getSession(false)
					.getAttribute("store_id"));
			mv = new JModelAndView("weixin/404.html",
					this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request,
					response);
			mv.addObject("url", CommUtil.getURL(request)
					+ "/weixin/index.htm?store_id=" + store_id);
		}

		return mv;
	}

	@RequestMapping({ "/500.htm" })
	public ModelAndView error500(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("500.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		String iskyshop_view_type = CommUtil.null2String(request.getSession(
				false).getAttribute("iskyshop_view_type"));
		if ((iskyshop_view_type != null) && (!(iskyshop_view_type.equals("")))
				&& (iskyshop_view_type.equals("weixin"))) {
			String store_id = CommUtil.null2String(request.getSession(false)
					.getAttribute("store_id"));
			mv = new JModelAndView("weixin/500.html",
					this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request,
					response);
			mv.addObject("url", CommUtil.getURL(request)
					+ "/weixin/index.htm?store_id=" + store_id);
		}

		return mv;
	}

	@RequestMapping({ "/goods_class.htm" })
	public ModelAndView goods_class(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("goods_class.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		Map params = new HashMap();
		params.put("display", Boolean.valueOf(true));
		List gcs = this.goodsClassService
				.query("select obj from GoodsClass obj where obj.parent.id is null and obj.display=:display order by obj.sequence asc",
						params, -1, -1);
		mv.addObject("gcs", gcs);
		return mv;
	}

	@RequestMapping({ "/forget.htm" })
	public ModelAndView forget(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("forget.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		SysConfig config = this.configService.getSysConfig();
		if (!(config.isEmailEnable())) {
			mv = new JModelAndView("error.html",
					this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request,
					response);
			mv.addObject("op_title", "系统关闭邮件功能，不能找回密码");
			mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
		}
		return mv;
	}

	@RequestMapping({ "/find_pws.htm" })
	public ModelAndView find_pws(HttpServletRequest request,
			HttpServletResponse response, String userName, String email,
			String code) {
		ModelAndView mv = new JModelAndView("success.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		HttpSession session = request.getSession(false);
		String verify_code = (String) session.getAttribute("verify_code");
		if (code.toUpperCase().equals(verify_code)) {
			User user = this.userService.getObjByProperty("userName", userName);
			if (user.getEmail().equals(email.trim())) {
				String pws = CommUtil.randomString(6).toLowerCase();
				String subject = this.configService.getSysConfig().getTitle()
						+ "密码找回邮件";
				String content = user.getUsername() + ",您好！您通过密码找回功能重置密码，新密码为："
						+ pws;
				boolean ret = this.msgTools.sendEmail(email, subject, content);
				if (ret) {
					user.setPassword(Md5Encrypt.md5(pws));
					this.userService.update(user);
					mv.addObject("op_title", "新密码已经发送到邮箱:<font color=red>"
							+ email + "</font>，请查收后重新登录");
					mv.addObject("url", CommUtil.getURL(request)
							+ "/user/login.htm");
				} else {
					mv = new JModelAndView("error.html",
							this.configService.getSysConfig(),
							this.userConfigService.getUserConfig(), 1, request,
							response);
					mv.addObject("op_title", "邮件发送失败，密码暂未执行重置");
					mv.addObject("url", CommUtil.getURL(request)
							+ "/forget.htm");
				}
			} else {
				mv = new JModelAndView("error.html",
						this.configService.getSysConfig(),
						this.userConfigService.getUserConfig(), 1, request,
						response);
				mv.addObject("op_title", "用户名、邮箱不匹配");
				mv.addObject("url", CommUtil.getURL(request) + "/forget.htm");
			}
		} else {
			mv = new JModelAndView("error.html",
					this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request,
					response);
			mv.addObject("op_title", "验证码不正确");
			mv.addObject("url", CommUtil.getURL(request) + "/forget.htm");
		}
		return mv;
	}

	@RequestMapping({ "/switch_recommend_goods.htm" })
	public ModelAndView switch_recommend_goods(HttpServletRequest request,
			HttpServletResponse response, int recommend_goods_random) {
		ModelAndView mv = new JModelAndView("switch_recommend_goods.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		Map params = new HashMap();
		params.put("store_recommend", Boolean.valueOf(true));
		params.put("goods_status", Integer.valueOf(0));
		List store_reommend_goods_list = this.goodsService
				.query("select obj from Goods obj where obj.store_recommend=:store_recommend and obj.goods_status=:goods_status order by obj.store_recommend_time desc",
						params, -1, -1);
		List store_reommend_goods = new ArrayList();
		int begin = recommend_goods_random * 5;
		if (begin > store_reommend_goods_list.size() - 1) {
			begin = 0;
		}
		int max = begin + 5;
		if (max > store_reommend_goods_list.size()) {
			begin -= max - store_reommend_goods_list.size();
			--max;
		}
		for (int i = 0; i < store_reommend_goods_list.size(); ++i) {
			if ((i >= begin) && (i < max)) {
				store_reommend_goods.add((Goods) store_reommend_goods_list
						.get(i));
			}
		}
		mv.addObject("store_reommend_goods", store_reommend_goods);
		return mv;
	}

	@RequestMapping({ "/outline.htm" })
	public ModelAndView outline(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("error.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		mv.addObject("op_title", "该用户在其他地点登录，您被迫下线！");
		mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
		return mv;
	}
}