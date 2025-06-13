package br.studio.pilates.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import br.studio.pilates.service.AlunoService;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(AlunoService alunoService) {
        return alunoService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Garanta que as senhas estão encriptadas com BCrypt
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(AlunoService alunoService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(alunoService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/web/login", "/css/**", "/js/**").permitAll()
                                .requestMatchers("/api/**").permitAll()
                                .requestMatchers("/web/home").permitAll()
                                .requestMatchers("/web/aluno/**").authenticated()
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                                .loginPage("/web/login")
                                .loginProcessingUrl("/perform_login")
                                .defaultSuccessUrl("/web/aluno/home", true)
                                .failureUrl("/web/login?error=true")
                                .permitAll()
                )
                .logout(logout -> logout
                                .logoutUrl("/perform_logout")
                                .logoutSuccessUrl("/web/login?logout=true")
                                .permitAll()
                );

        return http.build();
    }
}
