package com.handemdowns.config;

import com.handemdowns.security.CustomAccessDeniedHandler;
import com.handemdowns.security.CustomAuthenticationFailureHandler;
import com.handemdowns.security.CustomAuthenticationSuccessHandler;
import com.handemdowns.security.CustomUserDetailsService;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@ComponentScan(basePackages = {"com.handemdowns.security"})
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private CustomUserDetailsService userDetailsService;
	private CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    private CustomAuthenticationFailureHandler authenticationFailureHandler;
    private CustomAccessDeniedHandler accessDeniedHandler;
    private DataSource dataSource;

    @Autowired
	public WebSecurityConfig(CustomUserDetailsService userDetailsService, CustomAuthenticationSuccessHandler authenticationSuccessHandler,
							 CustomAuthenticationFailureHandler authenticationFailureHandler, CustomAccessDeniedHandler accessDeniedHandler,
							 DataSource dataSource) {
        super();
		this.userDetailsService = userDetailsService;
		this.authenticationSuccessHandler = authenticationSuccessHandler;
		this.authenticationFailureHandler = authenticationFailureHandler;
		this.accessDeniedHandler = accessDeniedHandler;
		this.dataSource = dataSource;
	}
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/**", "/logfile").hasAuthority("PERMISSION_ADMIN")
                .antMatchers("/account-settings", "/my-ads", "/get-my-ads", "/hold-ad", "/activate-ad",
                        "/watchlist", "/watch-ad", "/unwatch-ad").hasAuthority("PERMISSION_USER")
                .antMatchers("/**","/resources/**").permitAll()
                .antMatchers("/invalidSession*").anonymous()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
				.loginProcessingUrl("/login/submit")
                .defaultSuccessUrl("/")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .permitAll()
                .and()
                .sessionManagement()
                .sessionFixation().none()
                .and().rememberMe()
                .rememberMeParameter("remember-me").tokenRepository(persistentTokenRepository()).tokenValiditySeconds(86400)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .csrf()
                .ignoringAntMatchers("/search")
                .and()
                .logout()
                .invalidateHttpSession(false)
                .deleteCookies("JSESSIONID", "remember-me")
                .permitAll();
    }
	
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        tokenRepositoryImpl.setDataSource(dataSource);
        return tokenRepositoryImpl;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }
}