package packTrack.Proyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import packTrack.Proyecto.modelos.Usuario;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class}) // indica que es una aplicacion de spring boot
@RestController //  marca la clase como un controlador de tipo REST para manejar las solicitudes HTTP y enviar respuestas
public class ProyectoApplication {

    public static void main(String[] args) {SpringApplication.run(ProyectoApplication.class, args);}// Inicia la aplicacion como punto de partida

	// * Informacion
	// ? exito
	// ! error
	// TODO: pendiente


    //? SERVICIOS REST
	@GetMapping("/inicio") // indica que el metodo maneja las solicitudes HTTP GET
	public String hello() {
		return "Hola a todAs ";
	}

	@GetMapping("/pruebaModelo")
	public String prueba() {

		Usuario usuario = new Usuario(1105610650,"Andres Felipe Briñez","comedor20","Administrador");
		usuario.setNombre("Maria Lopez");
		System.out.println(usuario.getNombre());
		return usuario.getNombre();

	}


}
