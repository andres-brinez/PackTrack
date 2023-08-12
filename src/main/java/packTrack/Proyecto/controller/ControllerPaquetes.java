package packTrack.Proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import packTrack.Proyecto.modelos.Paquete;
import packTrack.Proyecto.modelos.Usuario;
import packTrack.Proyecto.services.PaquetesService;
import packTrack.Proyecto.services.UsuariosService;

import java.util.List;

@Controller
public class ControllerPaquetes {

    @Autowired // Inyectar dependencia
    private PaquetesService paquetesService;
    @Autowired
    private UsuariosService usuariosService;

    @GetMapping("/paquetes")
       public String viewPaquetes(Model model, @ModelAttribute("mensaje") String mensajeRecibido){
        List<Paquete> listaPaquetes = paquetesService.getAllPaquetes();
        model.addAttribute("listaPaquetes", listaPaquetes);
        model.addAttribute("mensaje", mensajeRecibido);
        return "/paquetes/historialPaquetes";
    }

    @GetMapping("/crearPaquete")
    public String crearPaquete(Model model, @ModelAttribute("mensaje") String mensajeRecibido){

        Paquete nuevoPaquete = new Paquete();
        List< Usuario> listaEmpleados = usuariosService.getEmpleados();

        model.addAttribute("listaEmpleados", listaEmpleados);
        model.addAttribute("paquete", nuevoPaquete);
        model.addAttribute("mensaje", mensajeRecibido);
        return "/paquetes/crearPaquete";
    }

    @PostMapping("/guardarPaquete")
    public String guardarPaquete(Paquete paquete, RedirectAttributes redirectAttributes){

        String clasificacion = paquete.generarClasificacion(); // Generar la clasificación del paquete antes de guardarlo
        paquete.setClasificacion(clasificacion);

        if(paquetesService.saveOrUpdatePaquete(paquete)== true) {
            redirectAttributes.addFlashAttribute("mensaje", "saveOk");
            return "redirect:/paquetes";
        }
        redirectAttributes.addFlashAttribute("mensaje", "saveError");
        return "redirect:/crearPaquete";

    }

    @GetMapping("verPaquete/{id}")
    public  String verPaquete(@PathVariable long id, Model model){
        Paquete paquete = paquetesService.getPaqueteById(id);
        model.addAttribute("paquete", paquete);
        return "/paquetes/verPaquete";
    }


    @GetMapping("editarPaquete/{id}")
    public  String editarPaquete(@PathVariable long id, Model model){
        Paquete paquete = paquetesService.getPaqueteById(id);
        List< Usuario> listaEmpleados = usuariosService.getEmpleados();
        model.addAttribute("listaEmpleados", listaEmpleados);
        model.addAttribute("paquete", paquete);
        return "/paquetes/editarPaquete";
    }

    @PostMapping("/actualizarPaquete")
    public String actualizarPaquete(Paquete paquete,RedirectAttributes redirectAttributes) {

        paquete.actualizarClasificacion(paquete.getPeso(),paquete.getAltura(), paquete.getAncho(), paquete.getLargo()); // Actualizar la clasificación del paquete antes de guardarlo

        if (paquetesService.saveOrUpdatePaquete(paquete)) {
            redirectAttributes.addFlashAttribute("mensaje", "updateOk");
            return "redirect:/paquetes";
        }

        redirectAttributes.addFlashAttribute("mensaje", "updateError");
        return "redirect:/editarPaquete/" + paquete.getId();
    }

    @GetMapping("eliminarPaquete/{id}")
    public String eliminarPaquete(@PathVariable long id, RedirectAttributes redirectAttributes){
        if(paquetesService.deletePaquete(id)){
            redirectAttributes.addFlashAttribute("mensaje", "deleteOk");
            return "redirect:/paquetes";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/paquetes";
    }






}
