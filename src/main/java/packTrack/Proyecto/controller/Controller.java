package packTrack.Proyecto.controller;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping({"/","/Home"})// {ruta1, ruta2,..} maneja varias rutas para el mismo metodo (servicio)
    public String index(){
        return "index";
    }
}
