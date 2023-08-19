package packTrack.Proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class ControllerEnvios {

    @GetMapping("/envios")
    public String envios(){
        return "/envios/historialEnvios";
    }
}
