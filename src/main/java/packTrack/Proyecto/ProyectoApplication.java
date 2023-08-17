package packTrack.Proyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication() // indica que es una aplicacion de spring boot
// exclude={SecurityAutoConfiguration.class}  deshabilita la configuracion de seguridad automatica de spring boot
// para poder usar la configuracion de seguridad personalizada

public class ProyectoApplication {

    public static void main(String[] args) {SpringApplication.run(ProyectoApplication.class, args);}// Inicia la aplicacion como punto de partida

	// * Informacion
	// ? exito
	// ! error
	// TODO: pendiente








}
