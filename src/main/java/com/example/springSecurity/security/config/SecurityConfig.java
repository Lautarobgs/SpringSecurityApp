
package com.example.springSecurity.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests()
                    .requestMatchers("/holanoseg").permitAll() ///Indica que el endpoint holanoseg puede acceder sin seguridad
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll() ///Para cada endpoint con seguridad redirecciona al login
                .and()
                .httpBasic() ///Hace que el cliente pueda mandar el usuario y pass en cada solicitud http NO ES LA FORMA MAS SEGURA
                .and()
                .build();
    }
}
