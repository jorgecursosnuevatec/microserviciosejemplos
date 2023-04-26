
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	protected InMemoryUserDetailsManager usersDetailsMemory() throws Exception {
		List<UserDetails> users = List.of(User.withUsername("user1").password("{noop}user1") // lo de {noop} se pone
				// para no obligar a
				// usar mecanismo de
				// encriptación
				.roles("USER").build(),
				User.withUsername("admin").password("{noop}admin").roles("USER", "ADMIN").build(),
				User.withUsername("user2").password("{noop}user2").roles("OPERATOR").build());
		return new InMemoryUserDetailsManager(users);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		// solo los miembros del rol admin podrán realizar altas
		// y para acceder la lista de cursos, tendrán que estar autenticados
		.antMatchers(HttpMethod.POST, "/curso").hasRole("ADMIN").antMatchers("/curso/*")
		.hasAnyRole("ADMIN", "OPERATOR").antMatchers(HttpMethod.PUT, "/curso").authenticated()
		.antMatchers("/cursos/**").authenticated().and().httpBasic(); // forma de solicitar los credenciales
		return http.build();
	}

}
