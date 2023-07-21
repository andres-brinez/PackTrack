package packTrack.Proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import packTrack.Proyecto.modelos.Usuario;
import packTrack.Proyecto.services.UsuarioService;

import java.util.List;

@Controller // marca la clase como un controlador  para manejar las solicitudes HTTP y enviar respuestas tipo html
public class controllerUsuario {
    @Autowired // Conecta esta clase  con el servicio
    UsuarioService usuarioService; // Se crea una instancia del servicio para poder usar los metodos de jpa

    //? SERVICIOS REST
    @GetMapping("/inicio") // indica que el metodo maneja las solicitudes HTTP GET
    public String hello() {
        return "Hola a todAs ";
    }

    @GetMapping({"/","/usuarios"}) // {ruta1, ruta2,..} maneja varias rutas para el mismo metodo (servicio)
    public String viewUsuarios(Model model) {
        System.out.println("Se accedió a la vista de usuarios");
        List<Usuario> listaUsuarios = usuarioService.getAllUsuarios(); // Se obtiene la lista de usuarios usando el metodo del servicio que deuelve la lista de usuarios
        model.addAttribute("listaUsuarios", listaUsuarios); // Se agrega la lista de usuarios al modelo para poder usarla en la vista
        return "historialUsuarios"; // Se retorna el nombre de la vista
    }



}
