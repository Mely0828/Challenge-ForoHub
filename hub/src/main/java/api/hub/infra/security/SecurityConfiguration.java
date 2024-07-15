package api.hub.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private SecurityFilter securityFilter;

    // Configuración del filtro de seguridad y políticas de autorización
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable());  // Deshabilita CSRF

        httpSecurity.sessionManagement(sess -> sess
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));  // Configura la política de creación de sesiones como sin estado

        httpSecurity.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()  // Permite acceso sin autenticación a /login
                        .requestMatchers(HttpMethod.POST, "/registro").permitAll()  // Permite acceso sin autenticación a /registro
                        .requestMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()  // Permite acceso sin autenticación a Swagger UI y documentación de API
                        .anyRequest().authenticated()  // Requiere autenticación para cualquier otra solicitud
        );

        httpSecurity.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);  // Añade el filtro de seguridad antes del filtro de autenticación básica de usuario/contraseña

        return httpSecurity.build();
    }

    // Configuración del administrador de autenticación
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Configuración del codificador de contraseñas BCrypt
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
