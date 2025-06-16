package br.studio.pilates.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import br.studio.pilates.service.AlunoService;
import br.studio.pilates.service.UsuarioService;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider alunoAuthenticationProvider(AlunoService alunoService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(alunoService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public DaoAuthenticationProvider usuarioAuthenticationProvider(UsuarioService usuarioService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(usuarioService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            DaoAuthenticationProvider alunoAuthenticationProvider,
            DaoAuthenticationProvider usuarioAuthenticationProvider
    ) {
        return new ProviderManager(List.of(alunoAuthenticationProvider, usuarioAuthenticationProvider));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider alunoAuthenticationProvider,
            DaoAuthenticationProvider usuarioAuthenticationProvider
    ) throws Exception {

        http
            .authenticationProvider(alunoAuthenticationProvider)
            .authenticationProvider(usuarioAuthenticationProvider)

            .csrf(csrf -> csrf.disable())

            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    if (request.getRequestURI().startsWith("/api/")) {
                        response.sendError(401, "Unauthorized");
                    } else {
                        response.sendRedirect("/web/login");
                    }
                })
            )

            .authorizeHttpRequests(auth -> auth
                // 🔓 Libera acesso total às rotas da API
                .requestMatchers(
                    "/api/**").permitAll()


                // 🔓 Libera acesso a login, home pública e assets (css, js, imagens)
                .requestMatchers("/web/login", "/web/home", "/css/**", "/js/**", "/img/**", "/api/**").permitAll()

                // 🔐 Restringe acesso conforme roles para telas web
                .requestMatchers("/web/aluno/**").hasRole("ALUNO")
                .requestMatchers("/web/recepcionista/**").hasRole("RECEPCAO")
                .requestMatchers("/agendaInstrutor/**").hasRole("INSTRUTOR")

                // 🔒 Qualquer outra rota precisa estar autenticado
                .anyRequest().authenticated()
            )

            // 🔑 Configuração do login via formulário web
            .formLogin(form -> form
                .loginPage("/web/login")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/web/aluno/home", true)
                .successHandler(successHandler)
                .failureUrl("/web/login?error=true")
                .permitAll()
            )

            // 🔓 Configuração do logout
            .logout(logout -> logout
                .logoutUrl("/perform_logout")
                .logoutSuccessUrl("/web/login?logout=true")
                .permitAll()
            );

        return http.build();
    }
}
