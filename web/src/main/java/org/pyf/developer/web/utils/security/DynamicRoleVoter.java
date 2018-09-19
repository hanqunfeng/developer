package org.pyf.developer.web.utils.security;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;

import java.util.Collection;
import java.util.Map;
import java.util.Set;


public class DynamicRoleVoter extends SimpleBaseUserInfoService implements
		AccessDecisionVoter<Object> {

	public DynamicRoleVoter() {
	}



	private PathMatcher pathMatcher = new AntPathMatcher();
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.vote.AccessDecisionVoter#supports(java.lang
	 * .Class)
	 */
	public boolean supports(Class<?> clazz) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.springframework.security.vote.AccessDecisionVoter#supports(org.
	 * springframework.security.ConfigAttribute)
	 */
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.springframework.security.vote.AccessDecisionVoter#vote(org.
	 * springframework.security.Authentication, java.lang.Object,
	 * org.springframework.security.ConfigAttributeDefinition)
	 */
	@SuppressWarnings("rawtypes")
	public int vote(Authentication authentication, Object object,
			Collection arg2) {
		int result = ACCESS_ABSTAIN;
		if (!(object instanceof FilterInvocation))
			return result;
		FilterInvocation invo = (FilterInvocation) object;
		String url = invo.getRequestUrl();//当前请求的URL
		Set<SimpleGrantedAuthority> authorities = null;
		String userId = authentication.getName();
		//获得当前用户的可访问资源，自定义的查询方法，之后和当前请求资源进行匹配，成功则放行，否则拦截	
		authorities = loadUserAuthorities(userId);
		Map<String, Set<String>> urlAuths = authService.getUrlAuthorities();
		Set<String> keySet = urlAuths.keySet();
		for (String key : keySet) {
			boolean matched = pathMatcher.match(key, url);
			if (!matched)
				continue;
			Set<String> mappedAuths = urlAuths.get(key);
			if (contain(authorities, mappedAuths)) {
				result = ACCESS_GRANTED;
				break;
			}
		}
		return result;
	}
	
	
	protected boolean contain(Set<SimpleGrantedAuthority> authorities,
			Set<String> mappedAuths) {
		if (CollectionUtils.isEmpty(mappedAuths)
				|| CollectionUtils.isEmpty(authorities))
			return false;
		for (SimpleGrantedAuthority item : authorities) {
			if (mappedAuths.contains(item.getAuthority()))
				return true;
		}
		return false;
	}
}
