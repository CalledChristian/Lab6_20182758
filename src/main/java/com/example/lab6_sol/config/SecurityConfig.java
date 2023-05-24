package com.example.lab6_sol.config;


import com.example.lab6_sol.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    final DataSource dataSource;
    final UsuarioRepository usuarioRepository;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    public SecurityConfig(DataSource dataSource, UsuarioRepository usuarioRepository) {
        this.dataSource = dataSource;
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //Usar el formulario por defecto de spring security
        http.formLogin()
                .loginPage("/")
                .loginProcessingUrl("/validacionusuario")
                .usernameParameter("correo")
                .passwordParameter("contrasena")
                .successHandler((request, response, authentication) -> {


                    DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");

                    //HttpSession session = request.getSession();
                    //session.setAttribute("usuario",usuarioRepository.findByCorreo(authentication.getName()));
                    if (defaultSavedRequest != null) {
                        String targetURL = defaultSavedRequest.getRedirectUrl();
                        redirectStrategy.sendRedirect(request, response, targetURL);
                    } else {
                        String rol = "";
                        for (GrantedAuthority role : authentication.getAuthorities()) {
                            rol = role.getAuthority();
                            break;
                        }

                        if (rol.equals("Estudiante")) {
                            response.sendRedirect("/estudiante/lista");
                        }else {
                            response.sendRedirect("/");
                        }
                    }

                });

        //para cerrar sesion
        http.logout().logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);


        http.authorizeHttpRequests()
                .requestMatchers("/estudiante", "/estudiante/*").hasAnyAuthority("Estudiante")
                //.requestMatchers("/shipper", "/shipper/**").hasAuthority("admin")
                //Dejar accesible a todos los usuarios cualquier otra ruta con anyRequest()
                .anyRequest().permitAll();


        /*http.logout().logoutSuccessUrl("/").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);*/

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        JdbcUserDetailsManager jdbc = new JdbcUserDetailsManager(dataSource);

        String sql1 = "SELECT correo,password,activo FROM usuario where correo = ? ";
        String sql2 = "SELECT u.correo, r.nombre FROM usuario u INNER JOIN rol r ON (u.rolid = r.id) " +
                "WHERE u.correo = ? ";

        jdbc.setUsersByUsernameQuery(sql1);
        jdbc.setAuthoritiesByUsernameQuery(sql2);
        return jdbc;
    }

}
