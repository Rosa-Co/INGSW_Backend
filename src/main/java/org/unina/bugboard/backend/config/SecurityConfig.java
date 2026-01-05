package org.unina.bugboard.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.unina.bugboard.backend.security.AuthEntryPointJwt;
import org.unina.bugboard.backend.security.JwtAuthenticationFilter;
import org.unina.bugboard.backend.service.impl.UserDetailsServiceImpl;

/**
 * Configurazione della sicurezza dell'applicazione.
 * Definisce i filtri, i provider di autenticazione e le regole di
 * autorizzazione.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    private final AuthEntryPointJwt unauthorizedHandler;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Costruttore per l'iniezione delle dipendenze.
     *
     * @param userDetailsService      servizio per il recupero dei dettagli utente
     * @param unauthorizedHandler     gestore delle eccezioni di autenticazione
     * @param jwtAuthenticationFilter filtro per l'autenticazione tramite JWT
     */
    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService, AuthEntryPointJwt unauthorizedHandler,
            JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configura il provider di autenticazione.
     * Imposta il servizio per i dettagli utente e l'encoder per le password.
     *
     * @return il DaoAuthenticationProvider configurato
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        // Imposto il password encoder
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    /**
     * Espone l'AuthenticationManager come bean.
     *
     * @param authConfig la configurazione dell'autenticazione
     * @return l'AuthenticationManager
     * @throws Exception se si verificano errori durante la creazione
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Definisce il bean per l'hashing delle password.
     * Utilizza BCrypt.
     *
     * @return il PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura la catena di filtri di sicurezza.
     * Disabilita CSRF, gestisce le eccezioni, imposta la sessione come stateless
     * e definisce le regole di accesso agli endpoint.
     *
     * @param http l'oggetto HttpSecurity da configurare
     * @return la SecurityFilterChain costruita
     * @throws Exception se si verificano errori durante la configurazione
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/api/users/**").authenticated()
                        .requestMatchers("/api/comments/**").authenticated()
                        .requestMatchers("/api/images/**").authenticated()
                        .requestMatchers("/api/issues/**").authenticated()
                        .requestMatchers("/api/test/**").permitAll()
                        .anyRequest().authenticated());
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
