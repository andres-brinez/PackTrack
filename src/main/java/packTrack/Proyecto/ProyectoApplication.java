package packTrack.Proyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class}) // indica que es una aplicacion de spring boot y que se excluya la configuracion de seguridad
public class ProyectoApplication {

    public static void main(String[] args) {SpringApplication.run(ProyectoApplication.class, args);}// Inicia la aplicacion como punto de partida

	// * Informacion
	// ? exito
	// ! error
	// TODO: pendiente








}
