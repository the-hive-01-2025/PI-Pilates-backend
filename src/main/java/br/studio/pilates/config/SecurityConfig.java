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

/**
 * Configuração central de segurança da aplicação usando Spring Security.
 * Define autenticação, autorização, regras de acesso e configuração de login/logout.
 */
@Configuration
public class SecurityConfig {

    // Manipulador customizado para redirecionamento após login bem-sucedido
    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    /**
     * Bean para criptografia de senhas usando BCrypt.
     * Usado para comparar senha armazenada com a senha digitada no login.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provedor de autenticação para usuários do tipo Aluno.
     * Configura o serviço que carrega detalhes do usuário e o encoder de senha.
     */
    @Bean
    public DaoAuthenticationProvider alunoAuthenticationProvider(AlunoService alunoService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(alunoService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Provedor de autenticação para usuários do tipo Usuario (geral).
     * Configura o serviço de detalhes e o encoder.
     */
    @Bean
    public DaoAuthenticationProvider usuarioAuthenticationProvider(UsuarioService usuarioService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(usuarioService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Gerenciador de autenticação que reúne os dois provedores criados acima.
     * Permite autenticar tanto Aluno quanto Usuário geral, delegando ao provider correto.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            DaoAuthenticationProvider alunoAuthenticationProvider,
            DaoAuthenticationProvider usuarioAuthenticationProvider
    ) {
        return new ProviderManager(List.of(alunoAuthenticationProvider, usuarioAuthenticationProvider));
    }

    /**
     * Configuração da cadeia de filtros de segurança HTTP.
     * Define regras de autorização, login, logout, tratamento de exceções e CSRF.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider alunoAuthenticationProvider,
            DaoAuthenticationProvider usuarioAuthenticationProvider
    ) throws Exception {

        http
            // Adiciona os provedores de autenticação definidos
            .authenticationProvider(alunoAuthenticationProvider)
            .authenticationProvider(usuarioAuthenticationProvider)

            // Desabilita proteção CSRF para facilitar uso da API (ajustar conforme necessidade)
            .csrf(csrf -> csrf.disable())

            // Configura tratamento customizado para acessos não autorizados
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    // Se a requisição for para API, retorna erro 401 Unauthorized
                    if (request.getRequestURI().startsWith("/api/")) {
                        response.sendError(401, "Unauthorized");
                    } else {
                        // Para rotas web, redireciona para a página de login
                        response.sendRedirect("/web/login");
                    }
                })
            )

            // Define regras de autorização para as URLs
            .authorizeHttpRequests(auth -> auth
                // Permite acesso livre a todas as rotas da API
                .requestMatchers("/api/**").permitAll()

                // Permite acesso livre para páginas públicas e arquivos estáticos
                .requestMatchers("/web/login", "/web/home", "/css/**", "/js/**", "/img/**", "/api/**").permitAll()

                // Requer role ALUNO para acessar páginas e recursos de aluno
                .requestMatchers("/web/aluno/**").hasRole("ALUNO")

                // Requer role RECEPCAO para acessar telas da recepção
                .requestMatchers("/web/recepcionista/**").hasRole("RECEPCAO")
                .requestMatchers("/web/recepcionista/aluno/**").hasRole("RECEPCAO")

                // Requer role INSTRUTOR para acessar agenda do instrutor
                .requestMatchers("/agendaInstrutor/**").hasRole("INSTRUTOR")

                // Para qualquer outra rota, exige autenticação
                .anyRequest().authenticated()
            )

            // Configuração do formulário de login padrão web
            .formLogin(form -> form
                .loginPage("/web/login")                    // Página de login customizada
                .loginProcessingUrl("/perform_login")       // URL que processa o login
                .defaultSuccessUrl("/web/aluno/home", true) // URL default após login (usada se successHandler não redirecionar)
                .successHandler(successHandler)              // Handler customizado para login bem-sucedido
                .failureUrl("/web/login?error=true")         // URL em caso de falha no login
                .permitAll()
            )

            // Configuração do logout
            .logout(logout -> logout
                .logoutUrl("/perform_logout")                // URL para disparar logout
                .logoutSuccessUrl("/web/login?logout=true") // Redireciona para login após logout
                .permitAll()
            );

        // Constroi e retorna o filtro de segurança configurado
        return http.build();
    }
}
