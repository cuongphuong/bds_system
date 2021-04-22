package com.sys.pp.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/", "/login", "/logout").permitAll();

		 //page requires login as ROLE_USER or ROLE_ADMIN.
		 http.authorizeRequests().antMatchers("/post").access("hasAnyRole('ROLE_USER')");

		// admin page requires login as ROLE_ADMIN.
		http.authorizeRequests().antMatchers("/admin/*").access("hasAnyRole('ROLE_ADMIN')");

		// Config for Login Form
		http.authorizeRequests().and().formLogin()//
			.loginProcessingUrl("/j_spring_security_check")
			.loginPage("/login")
			.defaultSuccessUrl("/userAccountInfo")
			.failureUrl("/login?error=true")
			.usernameParameter("email")
			.passwordParameter("pass").and()
			.logout().logoutUrl("/logout")
			.logoutSuccessUrl("/logoutSuccessful");

		// Config Remember Me.
		http.authorizeRequests().and().rememberMe()
			.tokenRepository(this.persistentTokenRepository())
			.tokenValiditySeconds(1 * 24 * 60 * 60); // 24h
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery(this.getUserQuery())
			.authoritiesByUsernameQuery(this.getAuthoritiesQuery())
			.passwordEncoder(new BCryptPasswordEncoder());
	}

	private String getUserQuery() {
		StringBuilder sql = new StringBuilder();
		sql.append("select email as username, pass as password, true as enabled ");
		sql.append("from Users ");
		sql.append("where email=?");
		return sql.toString();
	}

	private String getAuthoritiesQuery() {
		StringBuilder sql = new StringBuilder();
		sql.append("select u.email as username, r.role as role ");
		sql.append("from Roles r ");
		sql.append("inner join Users u ");
		sql.append("on (r.user_id=u.user_id) ");
		sql.append("where u.email=?");
		return sql.toString();
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}
}