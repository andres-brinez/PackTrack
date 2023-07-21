package packTrack.Proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import packTrack.Proyecto.modelos.Envio;
import packTrack.Proyecto.modelos.Factura;
import packTrack.Proyecto.modelos.Paquete;
import packTrack.Proyecto.modelos.Usuario;

@RestController//  marca la clase como un controlador de tipo REST para manejar las solicitudes HTTP y enviar respuestas tipo JSON O XML
public class controllerPrueba {

    //? SERVICIOS REST paraa hacer pruebas

    @GetMapping("/pruebaModeloUsuario")
    public String prueba() {

        Usuario usuario = new Usuario(1105610650,"Andres Felipe Briñez","comedor20","Administrador");
        usuario.setNombre("Maria Lopez");
        System.out.println(usuario.getNombre());
        return usuario.getNombre();

    }

    @GetMapping("/pruebaModeloPaquete")
    public String pruebaPaquete() {
        Usuario empleado = new Usuario(1105610650,"Andres Felipe Briñez","comedor20","Empleado");
        Usuario usuario= new Usuario(1125646650,"Juan Felipe Lopez","comedor20","Usuario");

        Paquete paquete = new Paquete(usuario,empleado,"Paquete 1","Bogota","Paquete de prueba",1,200,100,200,300,100000);
        return paquete.toString();
    }

    @GetMapping("/pruebaModeloEnvio")
    public String pruebaEnvio() {
        Usuario empleado = new Usuario(1105610650,"Andres Felipe Briñez","comedor20","Empleado");
        Usuario usuario= new Usuario(1125646650,"Juan Felipe Lopez","comedor20","Usuario");
        Paquete paquete = new Paquete(usuario,empleado,"Paquete 1","Bogota","Paquete de prueba",1,200,100,200,300,100000);
        Envio envio = new Envio(paquete,"Bogota","Maria Lopez","Envio de un paquete","Economico");
        return envio.toString();
    }

    @GetMapping("/pruebaModeloFactura")
    public String pruebaFactura() {
        Usuario empleado = new Usuario(1105610650,"Andres Felipe Briñez","comedor20","Empleado");
        Usuario usuario= new Usuario(1125646650,"Juan Felipe Lopez","comedor20","Usuario");
        Paquete paquete = new Paquete(usuario,empleado,"Paquete 1","Bogota","Paquete de prueba",1,200,100,200,300,100000);
        Envio envio = new Envio(paquete,"Bogota","Maria Lopez","Envio de un paquete","Economico");
        Factura factura = new Factura(envio);

        return factura.generarFactura();
    }
}
