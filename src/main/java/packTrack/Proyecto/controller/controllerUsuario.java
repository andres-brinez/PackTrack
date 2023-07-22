package packTrack.Proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
        List<Usuario> listaUsuarios = usuarioService.getAllUsuarios(); // Se obtiene la lista de usuarios usando el metodo del servicio que deuelve la lista de usuarios
        model.addAttribute("listaUsuarios", listaUsuarios); // Se agrega la lista de usuarios al modelo para poder usarla en la vista
        return "historialUsuarios"; // Se retorna el nombre de la vista
    }

    @GetMapping("/usuarios/crearUsuario")
    public String crearUsuario(Model model) {
        Usuario nuevoUsuario = new Usuario();
        model.addAttribute("usuario", nuevoUsuario); //se guarda un objeto en el  modelo para poder usarlo en la vista y guardar valores
        return "crearUsuario"; // Se retorna el nombre de la vista
    }

    // Para guardar el usuario se usa el metodo del servicio que guarda el usuario en la base de datos
    @PostMapping("/usuarios/guardarUsuario")
    public String guardarUsuario(Usuario usuario, RedirectAttributes redirectAttributes) {

        if (usuarioService.saveOrUpdateUsuario(usuario)==true) { // Si el usuario se guarda correctamente
            //redirectAttributes.addFlashAttribute("success", "Usuario guardado correctamente"); // Se agrega un atributo al modelo para poder usarlo en la vista
            return "redirect:/usuarios"; // Se redirecciona al servicio, no al template

        }
            //redirectAttributes.addFlashAttribute("error", "No se pudo guardar el usuario"); // Se agrega un atributo al modelo para poder usarlo en la vista
        return "redirect:/usuarios/crearUsuario";   // Se redirecciona al servicio, no al template
    }




}
