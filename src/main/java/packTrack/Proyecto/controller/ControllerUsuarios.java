package packTrack.Proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import packTrack.Proyecto.modelos.Usuario;
import packTrack.Proyecto.services.UsuariosService;


import java.util.List;

@Controller // marca la clase como un controlador  para manejar las solicitudes HTTP y enviar respuestas tipo html
public class ControllerUsuarios {
    @Autowired // Conecta esta clase  con el servicio
    UsuariosService usuariosService; // Se crea una instancia del servicio para poder usar los metodos de jpa

    //? SERVICIOS REST

    @GetMapping({"/Usuarios", "/historialUsuarios"}) // {ruta1, ruta2,..} maneja varias rutas para el mismo metodo (servicio)
    public String viewUsuarios(@ModelAttribute("mensaje") String mensajeRecibido, Model model) {
        // @ModelAttribute("mensaje") String mensaje: Se usa para obtener el mensaje enviado por el metodo que redirecciona
        List<Usuario> listaUsuarios = usuariosService.getAllUsuarios(); // Se obtiene la lista de usuarios usando el metodo del servicio que deuelve la lista de usuarios
        model.addAttribute("listaUsuarios", listaUsuarios); // Se agrega la lista de usuarios al modelo para poder usarla en la vista
        model.addAttribute("mensaje", mensajeRecibido); // Se agrega el mensaje al modelo para poder usarlo en la vista
        return "/usuarios/historialUsuarios"; // Se retorna el nombre de la vista
    }

    @GetMapping("/crearUsuario")
    public String crearUsuario(Model model,@ModelAttribute("mensaje") String mensajeRecibido) {
        Usuario nuevoUsuario = new Usuario();
        model.addAttribute("usuario", nuevoUsuario); //se guarda un objeto en el  modelo para poder usarlo en la vista y guardar valores
        model.addAttribute("mensaje", mensajeRecibido); // Se agrega el mensaje al modelo para poder usarlo en la vista
        return "/usuarios/crearUsuario"; // Se retorna el nombre de la vista
    }

    // Para guardar el usuario se usa el metodo del servicio que guarda el usuario en la base de datos
    @PostMapping("/guardarUsuario")
    public String guardarUsuario(Usuario usuario, RedirectAttributes redirectAttributes) {

        // Encriptar contraseña
        String passwordEncriptado = passwordEncoder().encode(usuario.getPassword());
        usuario.setPassword(passwordEncriptado);

        if (usuariosService.saveOrUpdateUsuario(usuario)) { // Si el usuario se guarda correctamente
            redirectAttributes.addFlashAttribute("mensaje", "saveOk"); // Se agrega un mensaje al modelo para poder usarlo en la vista
            return "redirect:/historialUsuarios"; // Se redirecciona al servicio, no al template
        }
        redirectAttributes.addFlashAttribute("mensaje", "saveError"); // Se agrega un mensaje al modelo para poder usarlo en la vista
        return "redirect:/crearUsuario";   // Se redirecciona al servicio, no al template
    }

    @GetMapping("/editarUsuario/{numeroIdentificacion}")
    //@PathVariable se usa para obtener el valor de la variable en la ruta
    public String editarUsuario(Model model, @PathVariable long numeroIdentificacion,@ModelAttribute("mensaje") String mensajeRecibido) {
        Usuario usuario = usuariosService.getUsuarioById(numeroIdentificacion);

        model.addAttribute("usuario", usuario);
        model.addAttribute("mensaje", mensajeRecibido); // Se agrega el mensaje al modelo para poder usarlo en la vista
        return "usuarios/editarUsuario";
    }

    @PostMapping("/actualizarUsuario")
    public String actualizarUsuario(Usuario usuario,RedirectAttributes redirectAttributes) {
        // encryptar contraseña
        String passwordEncriptado = passwordEncoder().encode(usuario.getPassword());
        usuario.setPassword(passwordEncriptado);

        if (usuariosService.saveOrUpdateUsuario(usuario)) {
            redirectAttributes.addFlashAttribute("mensaje", "updateOk");
            return "redirect:/historialUsuarios";
        }
        redirectAttributes.addFlashAttribute("mensaje", "updateError");
        return "redirect:/editarUsuario/" + usuario.getNumeroIdentificacion();
    }

    @GetMapping("/eliminarUsuario/{numeroIdentificacion}")
    public String eliminarUsuario(@PathVariable long numeroIdentificacion,RedirectAttributes redirectAttributes) {

        if (usuariosService.deleteUsuario(numeroIdentificacion)) {
            redirectAttributes.addFlashAttribute("mensaje", "deleteOk");
            return "redirect:/historialUsuarios";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/historialUsuarios";
    }

    @GetMapping("/verUsuario/{id}")
    public String verUsuaruio(@PathVariable long id,Model model) {
        Usuario usuario=usuariosService.getUsuarioById(id);
        model.addAttribute("usuario",usuario);

        return  "usuarios/verUsuario";

    }

    // Metodo Encriptar contraseña

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }





}