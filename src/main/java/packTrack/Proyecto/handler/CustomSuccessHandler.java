package packTrack.Proyecto.handler;

// ? la clase CustomSuccessHandler se encarga de manejar el redireccionamiento de los usuarios dependiendo de su rol (Usuario, Empleado o Administrador) despues de que un usuario inicie sesión
// por defecto, cuando un usuario inicia sesión, se redirecciona a la página de inicio (/), pero con esta clase se puede redireccionar a una página diferente dependiendo del rol del usuario

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component // indica que esto debe verificarce al inicio del proceso (despues de la autenticacion) y no se puede pasar por alto
public class CustomSuccessHandler  extends SimpleUrlAuthenticationSuccessHandler {

    // en el caso de que el usuario se autentique correctamente
    // indicamos que se debe hacer despues del logi
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy(); // nueve elemento que ayuda a crear la nueva estrategia de  redireccionamiento cuando el usuario inicie sesion

    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        String targetUrl = determineTargetUrl(authentication); //metodo que regresa la url a donde debe ir dependiendo del rol del usuario
        if (response.isCommitted()) { // si la respuesta fue comprometida

            // si hay un error
            System.out.println("No se puede redireccionar");
            return;
        }
        // redireccionar a la url que se obtuvo en el metodo anterior
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    // metodo que regresa la url a donde debe ir dependiendo del rol del usuario
    // Argumentos: authentication: objeto que contiene la informacion del usuario que inicio sesion (autenticacion)
    protected String determineTargetUrl(Authentication authentication){

        String url = "";

        // se obtiene el rol del usuario que inicio sesion por medio de un recurso de spring security que son los authorities
        // authorities: son los roles que tiene el usuario que inicio sesion
        // (?) significa que puede ser cualquier tipo de dato
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities(); //Trae una coleccion de las personas que estan autenticadas y sus permisos, esto se trajo de la base de datos en setConfig

        List<String> roles = new ArrayList<String>();  // Lista para guardar  los roles

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority()); // guarda  los tipos de roles que tiene el usuario
        }

        // Dependiendo del rol del usuario, se tiene una url diferente
        if(esUsuario(roles)){
            url = "/home";
        }
        else if(esOperativo(roles)){
            url = "/paquetes";
        }
        else if(esAdministrativo(roles)){
            url = "/historialUsuarios";
        }
        // si no tiene permisos el usuario
        else{
            url = "/accesoDenegado";
        }
        return url;
    }

    // metodos para verificar que tipo de rol es el usuario
    // roles: Usuario, Empleado, Administrador
    private boolean esUsuario(List<String> roles) {
        if (roles.contains("Usuario")) {
            return true;
        }
        return false;
    }

    private boolean esOperativo(List<String> roles) {
        if (roles.contains("Empleado")) {
            return true;
        }
        return false;
    }

    private boolean esAdministrativo(List<String> roles) {
        if (roles.contains("ROLE_ADMIN")) {
            return true;
        }
        return false;
    }


}
