package packTrack.Proyecto.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import packTrack.Proyecto.handler.CustomSuccessHandler;

import javax.sql.DataSource;

@Configuration //Indica que es una clase de configuracion y tiene metodos anotados con @Bean que retornan objetos que seran administrados por el contenedor de Spring
@EnableWebSecurity //Habilita la seguridad web en una aplicacion Spring MVC
public class SecurityConfig {

    @Autowired
    private DataSource dataSource; // copia virtual de la base de datos para poder hacer consultas y no ir directamente a la base de datos, esto es más eficiente

    @Autowired
    CustomSuccessHandler customSuccessHandler; // Se crea una instancia del manejador de exito para poder usarlo en la configuracion de seguridad

    // Cuando el usuario intente iniciar sesión se debe hacer una consulta a la base de datos para verificar que el usuario exista
    //? Validar la existencia del usuario, codificar la contraseña y verificar que coincida con la contraseña encriptada de la base de datos ( hace la autenticacion de los usuarios)
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception
    {
        try {
            auth.jdbcAuthentication()
                    .dataSource(dataSource) // ! cuando se hace el casteo  CAST(? AS BIGINT) se quita el error de que el tipo de dato no coincide -  ERROR: operator does not exist: bigint = character varying, org.springframework.security.authentication.InternalAuthenticationServiceException: PreparedStatementCallback; bad SQL grammar [select numero_identificacion,password,estado from usuarios where numero_identificacion = ?]
                    // ! Cuando se hace el casteo aparece el siguiente error: ERROR: invalid input syntax for type bigint: ""

                    .usersByUsernameQuery("select numero_identificacion,password,estado from usuarios where numero_identificacion = CAST(? AS BIGINT)") // se hace la consulta a la base de datos para verificar que el usuario exista
                    // ! Tambien aparece el error del casteo en esta consulta - ERROR: operator does not exist: bigint = character varying, org.springframework.security.authentication.InternalAuthenticationServiceException: PreparedStatementCallback; bad SQL grammar [select numero_identificacion, rol from usuarios where numero_identificacion=?]
                    .authoritiesByUsernameQuery("select numero_identificacion, rol from usuarios where numero_identificacion=CAST(? AS BIGINT)") // obtiene el rol
                    .passwordEncoder(new BCryptPasswordEncoder());
        }

        catch (Exception e) {

            System.out.println("Error en la consulta de usuarios");
        }
    }

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception // Cadena de filtros de seguridad que se van ejecutando
    {

        // Configuracion en los endpoints publicos y privados para restringir el acceso a las rutas
        return http
                .csrf(csrf ->  csrf.disable()) // desactiva la proteccion csrf

                // Autorizacion de los endpoints
                .authorizeHttpRequests(authRaquests ->
                        authRaquests
                                .requestMatchers("/login","registro").permitAll() // los endpoints que empiecen con  /registro y login son publicos y no requieren autenticacion
                                //.requestMatchers("/historialUsuarios/**").hasRole("Administrador") // los endpoints que empiecen con /usuarios requieren autenticacion y el rol de administrador
                                // Parece que los roles se deben Guardar con "ROLE_" + rol
                                .requestMatchers("/historialUsuarios").hasRole("ADMIN") // el enpoint /historialUsuarios requiere autenticacion y el rol de administrador
                                .requestMatchers("/Home").hasAnyRole("Administrador","Usuario","Empleado") // el enpoint /Home requiere autenticacion y el rol de administrador, usuario y empleado
                                .anyRequest().authenticated() // cualquier otro endpoint requiere autenticacion
                                // en el caso del que el usuario no tenga el rol se redirecciona a la pagina de acceso denegado

                )
                // en el caso de que haya un error con el rol
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedHandler((request, response, accessDeniedException) ->
                                response.sendRedirect("/accesoDenegado")
                        )
                )
                //.formLogin(Customizer.withDefaults()) // el login por defecto es el que viene por defecto de spring
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login") // donde será la pagina de login, es decir la ruta de la pagina de login
                                .permitAll() // permite el acceso a todos los usuarios
                                .failureHandler((request, response, exception) -> {
                                    // en el caso de que el usuario este deshabilitado
                                    if (exception instanceof DisabledException) { // si la excepcion es de tipo DisabledException es porque el usuario esta deshabilitado
                                        request.getSession().setAttribute("mensaje", "Usuario deshabilitado"); //  establece un atributo llamado "mensaje" en la sesión de la solicitud actual.
                                        response.sendRedirect("/login?deshabilitado=true");
                                    }
                                    // si hay algun error aparece esta url, y desde la plantilla se genera un error
                                    else {
                                        //request.getSession().setAttribute("mensaje", "Credenciales de inicio de sesión incorrectas"); // se comentó para no enviarla a la plantilla y ver otra forma de generar mensajes
                                        response.sendRedirect("/login?error=true");
                                    }
                                })

                                .defaultSuccessUrl("/Home", true) // si se inicia sesion correctamente se redirecciona a esta ruta
                                // estos dos son los campos que se deben enviar en el formulario de login, por eso debe tener el nombre del input de cada uno
                                .usernameParameter("numeroIdentificacion") // nombre del input del numero de identificacion
                                .passwordParameter("password")

                                .successHandler((AuthenticationSuccessHandler) customSuccessHandler) // se envia el manejador de exito para que se ejecute cuando se inicie sesion correctamente para que se haga la redireccion dependiendo del rol



                )



              .logout(logout ->
                  logout
                          .permitAll() // permite el acceso a todos los usuarios
                          .logoutUrl("/logout") // ruta para cerrar sesion
                          .logoutSuccessUrl("/login?logout=true") // si se cierra sesion correctamente se redirecciona a esta ruta
                          .invalidateHttpSession(true) // invalida la sesion
                          .deleteCookies("JSESSIONID") // elimina la cookie de la sesion
              )

                .build();
    }


}
