package br.edu.toledoprudente.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Autowired 
    DataSource dataSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
          .csrf().disable()
            .authorizeHttpRequests((requests) -> requests
            	
                .requestMatchers("/login").permitAll()
               // .requestMatchers("/usuario/novo/**").hasRole("ADM")
                .anyRequest().authenticated()
                
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .defaultSuccessUrl("/produtos/listar", true)
                .permitAll()
                .failureUrl("/login-error")
            )
            .logout((logout) -> logout .invalidateHttpSession(true)
    		        .clearAuthentication(true)
    		        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
    		        .logoutSuccessUrl("/login").permitAll());

        return http.build();
    }

}
      /* Cria usuário no banco de dados*/
  /*  @Bean UserDetailsManager users(DataSource dataSource) {

      UserDetails user = User.builder() .username("anapaula@ana.com")
      .password(passwordEncoder().encode("password")) .roles("ADM") .build();
      
      JdbcUserDetailsManager users = new JdbcUserDetailsManager(this.dataSource);
      users.createUser(user);
      
      return users; }
     

    //Carrega os usuários do banco
    @Bean
    public UserDetailsManager users2(DataSource dataSource) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        return users;
    }

    //Define o Encriptador padrão
    @Bean
    public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder(16);   
    }


}
*/
/*

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.edu.toledoprudente.dao.ClientesDAO;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
   // métodos de configuração

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
	    http.authorizeHttpRequests( (authorize) -> authorize
	           .requestMatchers("/").permitAll()
	           .requestMatchers("/user/cadastro").hasAuthority("ADMIN")
	           .anyRequest().authenticated()
	     )
	    .formLogin()
	       .loginPage("/login")
	       .defaultSuccessUrl("/", true)
	       .failureUrl("/login-error")
	       .permitAll()
	    .and()
	       .logout()
	       .logoutSuccessUrl("/")
	       .deleteCookies("JSESSIONID")
	    .and()
	       .exceptionHandling()
	       .accessDeniedPage("/negado");

	    return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Autowired
	@Bean
	public UserDetailsService userDetailsService(AuthenticationManagerBuilder auth) throws Exception {
	    BCryptPasswordEncoder enconder = new BCryptPasswordEncoder();

	    UserDetails user =
	         User.builder()
	            .username("joao")
	            .password(enconder.encode("joao"))
	            .roles("ADM")
	            .build();

	    auth.jdbcAuthentication()
	    .passwordEncoder(enconder).withUser(user);

	    return new InMemoryUserDetailsManager(user);
	}
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http,
	                    PasswordEncoder passwordEncoder,
	                    UserDetailsService userDetailsService) throws Exception {
	    return http.getSharedObject(AuthenticationManagerBuilder.class)
	               .userDetailsService(userDetailsService)
	               .passwordEncoder(passwordEncoder)
	               .and()
	               .build();
	}
	
}






*/