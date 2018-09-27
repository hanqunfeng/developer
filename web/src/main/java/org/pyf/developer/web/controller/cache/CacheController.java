/*
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                   *
 ****************************************************************************
 * 源文件名:  com.netqin.function.cache.controller.CacheController.java 													       
 * 功能: cpframework框架													   
 * 版本:	@version 1.0	                                                                   
 * 编制日期: 2014年3月26日 上午10:12:31 						    						                                        
 * 修改历史: (主要历史变动原因及说明)		
 * YYYY-MM-DD |    Author      |	 Change Description		      
 * 2014年3月26日    |    hanqunfeng     |     Created 
 */
package org.pyf.developer.web.controller.cache;


import org.pyf.developer.web.controller.base.CP_SimpleBaseController;
import org.pyf.developer.web.utils.log.CP_OperateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

import static org.pyf.developer.web.utils.log.CP_GlobalNamingConstant.OPERATE_CACHE;


/**
 * Description: <缓存管理>. <br>
 * <p>
 * <使用说明>
 * </p>
 * Makedate:2014年3月26日 上午10:12:31
 * 
 * @author hanqunfeng
 * @version V1.0
 */
@Controller
@SessionAttributes({ "message", "messageStatus", "arguments" })
public class CacheController extends CP_SimpleBaseController {
	@Resource(name = "cacheManager")
	SimpleCacheManager cacheManager;

	@Autowired
	SessionRegistry sessionRegistry;

	@RequestMapping("/cache/flush_all.do")
	@CP_OperateLog(value = "刷新全部缓存", type = OPERATE_CACHE)
	public ModelAndView handleFlushAll(Model model) {
		ModelAndView v = null;
		Iterator<String> iterator = getCacheNames();
		while (iterator.hasNext()) {
			String name = iterator.next();
			// System.out.println(name);
			Cache cache = cacheManager.getCache(name);
			cache.clear();
		}

		model.addAttribute("message", "message.cache.all.flushed");
		v = new ModelAndView(new RedirectView("list.do"));
		return v;
	}

	@RequestMapping("/cache/flush_byName.do")
	@CP_OperateLog(value = "刷新指定缓存", type = OPERATE_CACHE)
	public ModelAndView handleFlushByName(String cacheName, Model model) {
		ModelAndView v = null;
		Iterator<String> iterator = getCacheNames();
		while (iterator.hasNext()) {
			String name = iterator.next();
			if (name.equals(cacheName)) {
				Cache cache = cacheManager.getCache(name);
				cache.clear();

				break;
			}

		}

		model.addAttribute("message", "message.cache.group.flushed");
		model.addAttribute("arguments", cacheName);
		v = new ModelAndView(new RedirectView("list.do"));
		return v;
	}

	@RequestMapping("/cache/list.do")
	@CP_OperateLog(value = "刷新缓存列表", type = OPERATE_CACHE)
	public String handleFlushList(Model model) {
		Iterator<String> iterator = getCacheNames();
		model.addAttribute("results", iterator);
		
		this.listActiveUsers(model);
		
		//return "cacheView";
		return getView("cacheView");
	}

	private void listActiveUsers(Model model) {

		// 当前在线人数
		int currentActiveUserSize = sessionRegistry.getAllPrincipals().size();
		model.addAttribute("currentActiveUserSize", currentActiveUserSize);

		Map<Object, Date> lastActivityDates = new HashMap<Object, Date>();
		for (Object principal : sessionRegistry.getAllPrincipals()) {
			// a principal may have multiple active sessions
			for (SessionInformation session : sessionRegistry.getAllSessions(
					principal, false)) {
				// no last activity stored
				if (lastActivityDates.get(principal) == null) {
					lastActivityDates.put(principal, session.getLastRequest());
				} else {
					// check to see if this session is newer than the last
					// stored
					Date prevLastRequest = lastActivityDates.get(principal);
					if (session.getLastRequest().after(prevLastRequest)) {
						// update if so
						lastActivityDates.put(principal,
								session.getLastRequest());
					}
				}
			}
		}
		model.addAttribute("activeUsers", lastActivityDates);
	}

	/**
	 * 描述 : <获得缓存名称集合>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @return
	 */
	private Iterator<String> getCacheNames() {
		Collection<String> cacheNames = cacheManager.getCacheNames();
		Iterator<String> iterator = cacheNames.iterator();
		return iterator;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/cache/getCacheContent_byName.do")
	@CP_OperateLog(value = "查看指定缓存内容", type = OPERATE_CACHE)
	public String handleGetByName(String cacheName, Model model) {
		ConcurrentMap<Object, Object> map = null;
		Iterator<String> iterator = getCacheNames();
		while (iterator.hasNext()) {
			String name = iterator.next();
			if (name.equals(cacheName)) {
				ConcurrentMapCache cache = (ConcurrentMapCache) cacheManager
						.getCache(name);
				map = cache.getNativeCache();
				break;
			}

		}
		model.addAttribute("cacheName", cacheName);
		model.addAttribute("cacheMap", map);
		Iterator<String> iterator2 = getCacheNames();
		model.addAttribute("results", iterator2);
		//return "cacheView";
		return getView("cacheView");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/cache/getCacheContent_byListName.do")
	@CP_OperateLog(value = "刷新指定缓存内容", type = OPERATE_CACHE)
	public String handleFlushByListName(String cacheName, String cacheListName,
			Model model) {
		ConcurrentMap<Object, Object> map = null;
		Iterator<String> iterator = getCacheNames();
		while (iterator.hasNext()) {
			String name = iterator.next();
			if (name.equals(cacheName)) {
				ConcurrentMapCache cache = (ConcurrentMapCache) cacheManager
						.getCache(name);
				map = cache.getNativeCache();
				map.remove(cacheListName);
				break;
			}

		}
		model.addAttribute("cacheName", cacheName);
		model.addAttribute("cacheMap", map);
		Iterator<String> iterator2 = getCacheNames();
		model.addAttribute("results", iterator2);
		//return "cacheView";
		return getView("cacheView");
	}
}
