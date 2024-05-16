package com.ssafy.enjoytrip.config;

import java.sql.Driver;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource(value="classpath:/dbvalue.properties")
@EnableTransactionManagement
//@EnableAspectJAutoProxy
public class RootConfig {
	
	
	@Value("${jdbc.driver}")
	private String driver;
	@Value("${jdbc.url}")
	private String url;
	@Value("${jdbc.username}")
	private String userName;
	@Value("${jdbc.password}")
	private String password;
	
	@Bean
	public DataSource dataSource() throws ClassNotFoundException {
		SimpleDriverDataSource ds = new SimpleDriverDataSource();
		ds.setDriverClass((Class<Driver>) Class.forName(driver));
		ds.setUrl(url);
		ds.setUsername(userName);
		ds.setPassword(password);
		
		return ds;
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(@Autowired DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:/mybatis/mybatisConfig.xml"));
		return bean.getObject();
	}
	@Bean
	public SqlSessionTemplate sqlSession(@Autowired SqlSessionFactory fac) {
		SqlSessionTemplate tem = new SqlSessionTemplate(fac);
		return tem;
	}
}
