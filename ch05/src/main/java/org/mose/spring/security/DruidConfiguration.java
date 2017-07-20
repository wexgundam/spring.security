package org.mose.spring.security;

import com.alibaba.druid.filter.logging.LogFilter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 配置Druid
 * <p>
 * 提供两种配置方式
 * <p>
 * 1. 基于servlet3的配置方式，已注释
 * <p>
 * 2. 基于spirng提供的注册器的配置方式
 */
@Configuration
public class DruidConfiguration {
    /**
     * 声明name为“druidDataSource”的bean，对应druid sql监控才能显示
     *
     * @return
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource(LogFilter logFilter) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.getProxyFilters().add(logFilter);
        return druidDataSource;
    }

    /**
     * 参数说明
     * <p>
     * dataSourceLogEnabled     * 所有DataSource相关的日志
     * connectionLogEnabled     * 所有连接相关的日志
     * connectionLogErrorEnabled     * 所有连接上发生异常的日志
     * statementLogEnabled     * 所有Statement相关的日志
     * statementLogErrorEnabled     * 所有Statement发生异常的日志
     * resultSetLogEnabled
     * resultSetLogErrorEnabled
     * connectionConnectBeforeLogEnabled
     * connectionConnectAfterLogEnabled
     * connectionCommitAfterLogEnabled
     * connectionRollbackAfterLogEnabled
     * connectionCloseAfterLogEnabled
     * statementCreateAfterLogEnabled
     * statementPrepareAfterLogEnabled
     * statementPrepareCallAfterLogEnabled
     * statementExecuteAfterLogEnabled
     * statementExecuteQueryAfterLogEnabled
     * statementExecuteUpdateAfterLogEnabled
     * statementExecuteBatchAfterLogEnabled
     * statementCloseAfterLogEnabled
     * statementParameterSetLogEnabled
     * resultSetNextAfterLogEnabled
     * resultSetOpenAfterLogEnabled
     * resultSetCloseAfterLogEnabled
     *
     * @return
     */
    @Bean
    public LogFilter logFilter() {
        LogFilter logFilter = new Slf4jLogFilter();
        logFilter.setStatementLogEnabled(true);
        logFilter.setResultSetLogEnabled(true);
        return logFilter;
    }

    /**
     * 注册一个Druid内置的StatViewServlet，用于展示Druid的统计信息。
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

        //添加初始化参数：initParams

//        //白名单 (没有配置或者为空，则允许所有访问)
//        servletRegistrationBean.addInitParameter("allow", "192.168.1.88,127.0.0.1");
//        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
//        servletRegistrationBean.addInitParameter("deny", "192.168.1.80");
//        //登录查看信息的账号密码.
//        servletRegistrationBean.addInitParameter("loginUsername", "root");
//        servletRegistrationBean.addInitParameter("loginPassword", "123456");
        //是否能够重置数据(禁用HTML页面上的“Reset All”功能)
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    /**
     * 注册一个：filterRegistrationBean,添加请求过滤规则
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());

        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");

        //添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter(
                "exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    /**
     * 监听Spring
     * 1.定义拦截器
     * 2.定义切入点
     * 3.定义通知类
     *
     * @return
     */
    @Bean
    public DruidStatInterceptor druidStatInterceptor() {
        return new DruidStatInterceptor();
    }

    @Bean
    public JdkRegexpMethodPointcut druidStatPointcut() {
        JdkRegexpMethodPointcut druidStatPointcut = new JdkRegexpMethodPointcut();
        String patterns = "com.critc.plat.*.service.*";
        druidStatPointcut.setPatterns(patterns);
        return druidStatPointcut;
    }

    @Bean
    public Advisor druidStatAdvisor(JdkRegexpMethodPointcut druidStatPointcut, DruidStatInterceptor druidStatInterceptor) {
        return new DefaultPointcutAdvisor(druidStatPointcut, druidStatInterceptor);
    }
}


