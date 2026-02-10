package school_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Public access to login page and static resources
                .requestMatchers("/login.html", "/style.css").permitAll()
                
                // Teacher can access everything
                .requestMatchers("/api/teachers/**", "/teachers.html").hasRole("TEACHER")
                .requestMatchers("/api/departments/**", "/departments.html").hasRole("TEACHER")
                
                // Both roles can access students, courses, and enrollments
                .requestMatchers("/api/students/**", "/students.html").hasAnyRole("TEACHER", "STUDENT")
                .requestMatchers("/api/courses/**", "/courses.html").hasAnyRole("TEACHER", "STUDENT")
                .requestMatchers("/api/enrollments/**", "/enrollments.html").hasAnyRole("TEACHER", "STUDENT")
                
                // Home page accessible to authenticated users
                .requestMatchers("/", "/index.html", "/hello", "/status").authenticated()
                
                // All other requests need authentication
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login.html?logout")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable()); // Disable CSRF for simplicity in this learning project

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails teacher = User.builder()
            .username("teacher")
            .password(passwordEncoder().encode("teacher123"))
            .roles("TEACHER")
            .build();

        UserDetails student = User.builder()
            .username("student")
            .password(passwordEncoder().encode("student123"))
            .roles("STUDENT")
            .build();

        return new InMemoryUserDetailsManager(teacher, student);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
