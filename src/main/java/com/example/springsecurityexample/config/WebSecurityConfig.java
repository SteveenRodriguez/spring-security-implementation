package com.example.springsecurityexample.config;

import com.example.springsecurityexample.security.JWTAuthenticationFilter;
import com.example.springsecurityexample.security.JWTAuthorizationFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity, AuthenticationManager authManager) throws Exception {

        // jwtAuthorizationFilter como no es gestionado por el nucleo de spring, creamos una instancia de este método
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter();
        jwtAuthenticationFilter.setAuthenticationManager(authManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login"); //Ruta para autenticación http://localshos:8080/login

        return httpSecurity
                // desactivamos el csrf
                .csrf().disable()
                // ingresamos a las reglas de las solicitudes
                .authorizeHttpRequests()
                // cualquier solicitud que ingrese debe estar autenticada
                .requestMatchers("contactos")
                .permitAll()
                .anyRequest()
                // debe estar autenticada
                .authenticated()
                .and()
                // habilitamos la autenticación básica (se envía 1 usuario y 1 contraseña)
                .httpBasic()
                .and()
                // Gestión de las sesiones
                .sessionManagement()
                // establecemos la política de gestión de sesiones que sean sin estado
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //sin estado
                .and()
                // filtros para la autenticación y la autorización del usuario
                .addFilter(jwtAuthenticationFilter)
                // le pasamos el orden en como se va a ejecutar
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build(); // construimos el SecurityFilterChain
    }

    // metoco que carga en memoria un usuario con contraseña para probar la autenticación
   /* @Bean
    UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User
                .withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles()
                .build());
        return manager;
    }*/

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        System.out.println("pass: " + new BCryptPasswordEncoder().encode("steveen"));
    }
}
