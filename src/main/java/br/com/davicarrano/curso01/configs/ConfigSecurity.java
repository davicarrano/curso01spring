package br.com.davicarrano.curso01.configs;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.davicarrano.curso01.filters.ExposeHeaderFilter;
import br.com.davicarrano.curso01.security.JWTAuthenticationFilter;
import br.com.davicarrano.curso01.security.JWTAuthorizationFilter;
import br.com.davicarrano.curso01.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfigSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired 
	private UserDetailsService userDetailsService;

	private static final String[] PUBLIC_MATCHERS_SOMENTE_GET = { "/produtos/**", "/clientes/**" };

	private static final String[] PUBLIC_MATCHERS_SOMENTE_POST = { "/categorias/**","/clientes","/clientes/foto" };

	private static final String[] PUBLIC_MATCHERS_SOMENTE_POST_PICTURE = { "/categorias/**" };
	
	private static final String[] PUBLIC_MATCHERS = { "/h2-console/**", };

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println(env.getActiveProfiles());

		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}

		http.cors();
		http.csrf().disable();
		http.authorizeRequests()
				/* h2 */
				.antMatchers(PUBLIC_MATCHERS).permitAll()

				/* produtos e categorias e pedidos e clientes */
				.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_SOMENTE_GET).permitAll()
				.antMatchers(HttpMethod.POST,PUBLIC_MATCHERS_SOMENTE_POST).permitAll()
				.anyRequest().authenticated();
		
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil,userDetailsService));
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
		
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
