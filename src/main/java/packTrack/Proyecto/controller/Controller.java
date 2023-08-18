package packTrack.Proyecto.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import packTrack.Proyecto.modelos.Usuario;
import packTrack.Proyecto.services.UsuariosService;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    UsuariosService usuariosService;

    @GetMapping({"/","/home"})// {ruta1, ruta2,..} maneja varias rutas para el mismo metodo (servicio)
    public String index(Model model, HttpServletRequest request) {

        // request es un objeto que contiene la informacion de la peticion que se hace al servidor
        //System.out.println("Usuario: " + request.getUserPrincipal().getName()); // obtener el nombre del usuario que inicio sesion
        Long idUsuario =Long.parseLong(request.getUserPrincipal().getName()); // obtener el id del usuario que inició sesion en este el lo toma como el nombre

        Usuario usuario = usuariosService.getUsuarioById((idUsuario)); // obtener el usuario que inicio sesion

        model.addAttribute("nombreUsuario", usuario.getNombre()); // se agrega un atributo al modelo para poder usarlo en la vista

        return "index";
    }

    @GetMapping("/registro")
    public String registro(Model model, @ModelAttribute("mensaje") String mensajeRecibido){

        Usuario nuevoUsuario = new Usuario();
        model.addAttribute("usuario", nuevoUsuario); //se guarda un objeto en el  modelo para poder usarlo en la vista y guardar valores

        return "/usuarios/registroUsuario"; // Se retorna el nombre de la vista
    }

    @PostMapping("registro/guardarUsuario")
    public String guardarUsuario(Usuario usuario, RedirectAttributes redirectAttributes){

        usuario.setRol("Usuario"); // cuando se crea  un usuario se le asigna el rol de usuario

        // Encriptar contraseña
        String passwordEncriptado = passwordEncoder().encode(usuario.getPassword());
        usuario.setPassword(passwordEncriptado);

        if (usuariosService.saveOrUpdateUsuario(usuario)) { // Si el usuario se guarda correctamente
            redirectAttributes.addFlashAttribute("mensaje", "saveOk"); // Se agrega un mensaje al modelo para poder usarlo en la vista
            return "redirect:/login"; // Se redirecciona al servicio, no al template
        }
        redirectAttributes.addFlashAttribute("mensaje", "saveError"); // Se agrega un mensaje al modelo para poder usarlo en la vista
        return "redirect:/registro";   // Se redirecciona al servicio, no al template
    }

    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false) String error, Model model, @ModelAttribute("mensaje") String mensajeRecibido) // @RequestParam(name = "error", required = false) String error -> permite recibir un parametro por la url y guardarlo en la variable error
    {
        model.addAttribute("mensaje", mensajeRecibido); // Se agrega el mensaje que se recibe al modelo para poder usarlo en la vista
        return "/usuarios/loginUsuario";
    }

    // Controlador que lleva  al template de no autorizado
    @RequestMapping("/accesoDenegado")
    public String denegado(){
        return "accessDenied";
    }


    // Encriptar contraseña
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
