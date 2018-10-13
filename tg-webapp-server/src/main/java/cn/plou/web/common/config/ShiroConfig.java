package cn.plou.web.common.config;

import java.util.LinkedHashMap;
import java.util.Map;

import cn.plou.web.common.config.common.Constant;
import cn.plou.web.common.utils.RedisUtil;
import cn.plou.web.common.utils.UserUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.plou.web.common.shiro.StatelessAccessControlFilter;
import cn.plou.web.common.shiro.StatelessDefaultSubjectFactory;
import cn.plou.web.common.shiro.UserRealm;
import cn.plou.web.common.shiro.filter.CorsFilter;
import redis.clients.jedis.Jedis;


/**
 * @author bootdo 1992lcg@163.com
 */
@Configuration
public class ShiroConfig extends WebMvcConfigurerAdapter {

	@Bean
	public CorsFilter corsFilterFilter() {
		return new CorsFilter();
	}

	@Value("${systemPath}")
	private  String  systemPath;
	@Value("${heatManagePath}")
	private  String  heatManagePath;
	@Value("${chargePath}")
	private  String  chargePath;
	/*@Value("${balancePath}")
	private  String  balancePath;
*/
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
		factoryBean.setSecurityManager(securityManager);
		factoryBean.getFilters().put("statelessAuthc", statelessAuthcFilter());
		//拦截器.
		Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
		filterChainDefinitionMap.put("/swagger-ui.html#/**", "statelessAuthc");
		filterChainDefinitionMap.put(systemPath+"/**", "statelessAuthc");
		filterChainDefinitionMap.put(heatManagePath+"/**", "statelessAuthc");
		filterChainDefinitionMap.put(chargePath+"/**", "statelessAuthc");
		//filterChainDefinitionMap.put(balancePath+"/**", "statelessAuthc");
		//filterChainDefinitionMap.put("/log/logout", "statelessAuthc");
		factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return factoryBean;
	}

	/**
	 * shiro安全管理器:
	 * 主要是身份认证的管理，缓存管理，cookie管理，所以在实际开发中我们主要是和SecurityManager进行打交道的
	 */
	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setSubjectFactory(subjectFactory());
		securityManager.setSessionManager(sessionManager());
		securityManager.setRealm(userRealm());

//		 * 禁用使用Sessions 作为存储策略的实现，但它没有完全地禁用Sessions
//		 * 所以需要配合context.setSessionCreationEnabled(false);

		((DefaultSessionStorageEvaluator)((DefaultSubjectDAO)securityManager.getSubjectDAO()).getSessionStorageEvaluator()).setSessionStorageEnabled(false);
		return securityManager;
	}
	/**
	 * subject工厂管理器.
	 */
	@Bean
	public DefaultWebSubjectFactory subjectFactory(){
		StatelessDefaultSubjectFactory subjectFactory = new StatelessDefaultSubjectFactory();
		return subjectFactory;
	}
	/**
	 * session管理器：
	 * sessionManager通过sessionValidationSchedulerEnabled禁用掉会话调度器，因为禁用掉了会话，所以没必要再定期过期会话了。
	 */
	@Bean
	public DefaultSessionManager sessionManager(){
		DefaultSessionManager sessionManager = new DefaultSessionManager();
		sessionManager.setSessionValidationSchedulerEnabled(false);
		return sessionManager;
	}

	/**
	 * 自己定义的realm.
	 */
	@Bean
	public UserRealm userRealm(){
		UserRealm realm = new UserRealm();
		return realm;
	}

	/**
	 * 访问控制器.
	 */
	@Bean
	public StatelessAccessControlFilter statelessAuthcFilter(){
		StatelessAccessControlFilter statelessAuthcFilter = new StatelessAccessControlFilter();
		return statelessAuthcFilter;
	}

	/**
	 *  开启shiro aop注解支持.使用代理方式;所以需要开启代码支持;
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 *   自动代理所有的advisor:由Advisor决定对哪些类的方法进行AOP代理。
	 */
	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
		daap.setProxyTargetClass(true);
		return daap;
	}

/*

    @Autowired
	private AuthorizationInterceptor authorizationInterceptor;

	@Autowired
	private CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authorizationInterceptor);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(currentUserMethodArgumentResolver);
	}
*/
	@Bean
	public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
//*/
//	@Bean
//	ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
//		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//		shiroFilterFactoryBean.setSecurityManager(securityManager);
//		shiroFilterFactoryBean.setLoginUrl("/login");
//		shiroFilterFactoryBean.setSuccessUrl("/index");
//		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
//		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//		filterChainDefinitionMap.put("/css/**", "anon");
//		filterChainDefinitionMap.put("/js/**", "anon");
//		filterChainDefinitionMap.put("/fonts/**", "anon");
//		filterChainDefinitionMap.put("/img/**", "anon");
//		filterChainDefinitionMap.put("/docs/**", "anon");
//		filterChainDefinitionMap.put("/druid/**", "anon");
//		filterChainDefinitionMap.put("/upload/**", "anon");
//		filterChainDefinitionMap.put("/files/**", "anon");
//		filterChainDefinitionMap.put("/logout", "logout");
//		filterChainDefinitionMap.put("/", "anon");
//		filterChainDefinitionMap.put("/webapp/**", "anon");
//		filterChainDefinitionMap.put("/mobile/**", "anon");
//		filterChainDefinitionMap.put("/test/**", "anon");
//		filterChainDefinitionMap.put("/company", "anon");
//		filterChainDefinitionMap.put("/company/**", "anon");
//		filterChainDefinitionMap.put("/swagger-ui.html", "anon");
//		filterChainDefinitionMap.put("/swagger-ui.html/**", "anon");
//		filterChainDefinitionMap.put("**/swagger-ui.html", "anon");
//		filterChainDefinitionMap.put("**/swagger-ui.html/**", "anon");
//		filterChainDefinitionMap.put("/webjars/**", "anon");
//		//filterChainDefinitionMap.put("/**", "authc");
//		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//		return shiroFilterFactoryBean;
//	}
//
//	@Bean
//	public SecurityManager securityManager1() {
//		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//		// 设置realm.
//		securityManager.setRealm(userRealm());
//		securityManager.setCacheManager(ehCacheManager());
//		securityManager.setSessionManager(sessionManager());
//		return securityManager;
//	}
//
//	@Bean
//	UserRealm userRealm() {
//		UserRealm userRealm = new UserRealm();
//		return userRealm;
//	}
//
//	/**
//	 * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持;
//	 *
//	 * @param securityManager
//	 * @return
//	 */
//	@Bean
//	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
//		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//		return authorizationAttributeSourceAdvisor;
//	}
//
//	@Bean
//	public SessionDAO sessionDAO() {
//		return new MemorySessionDAO();
//	}
//
//	/**
//	 * shiro session的管理
//	 */
//	@Bean
//	public DefaultWebSessionManager sessionManager() {
//		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//		sessionManager.setSessionDAO(sessionDAO());
//		Collection<SessionListener> listeners = new ArrayList<SessionListener>();
//		listeners.add(new BDSessionListener());
//		sessionManager.setSessionListeners(listeners);
//		return sessionManager;
//	}
//
//	@Bean
//	public EhCacheManager ehCacheManager() {
//		EhCacheManager em = new EhCacheManager();
//		em.setCacheManagerConfigFile("classpath:config/ehcache.xml");
//		return em;
//	}
//
//	/**
//	 * 缓存管理器
//	 * 可能已经存在CacheManager，在ehcache-core 2.5版本之后再次创建CacheManager会失败
//	 * @return
//	 */
//	@Bean
//	public EhCacheManager ehCacheManager() {
//		CacheManager cacheManager = CacheManager.getCacheManager("es");
//		if(cacheManager == null){
//			try {
//				cacheManager = CacheManager.create(ResourceUtils.getInputStreamForPath("classpath:config/ehcache.xml"));
//			} catch (IOException e) {
//				throw new RuntimeException("initialize cacheManager failed");
//			}
//		}
//		EhCacheManager ehCacheManager = new EhCacheManager();
//		ehCacheManager.setCacheManager(cacheManager);
//		return ehCacheManager;
//	}
}
