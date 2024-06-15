
package com.example.springSecurity.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean ///Componente que Spring Framework crea, configura y gestiona a lo largo del ciclo de vida de la app
    public SecurityFilterChain filterChain (HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf->csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http->{
                    http.requestMatchers(HttpMethod.GET,"/holanoseg").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/holaseg").hasAnyAuthority("READ");
                    http.anyRequest().denyAll();
                })
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider (){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder (){
        return NoOpPasswordEncoder.getInstance(); ///implementacion de password encoder, no encripta pass, NO RECOMENDADO, inyectado en authprov
    }

    @Bean
    public UserDetailsService userDetailsService (){///Inyectado en authprov
        List userDetailsList = new ArrayList<>();


        userDetailsList.add(User.withUsername("lautibgsadmin")
                .password("1234")
                .roles("ADMIN")
                .authorities("CREATE","READ","UPDATE","DELETE")
                .build());
        userDetailsList.add(User.withUsername("lautibgsuser")
                .password("1234")
                .roles("USER")
                .authorities("READ")
                .build());
        userDetailsList.add(User.withUsername("lautibgsuser2")
                .password("1234")
                .roles("USER")
                .authorities("UPDATE")
                .build());
        return new InMemoryUserDetailsManager(userDetailsList);
    }

}
