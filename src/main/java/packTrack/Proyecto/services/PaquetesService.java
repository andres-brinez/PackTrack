package packTrack.Proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import packTrack.Proyecto.modelos.Paquete;
import packTrack.Proyecto.repository.PaquetesRepository;

import java.util.List;

@Service
public class PaquetesService {

    @Autowired // Inyectar dependencia
    private PaquetesRepository paquetesRepository;

    public List<Paquete> getAllPaquetes(){
        return paquetesRepository.findAll();
    }

    public Paquete getPaqueteById(Long id){
        return paquetesRepository.findById(id).get();
    }

    public boolean saveOrUpdatePaquete(Paquete paquete){
        Paquete paquete1 = paquetesRepository.save(paquete);

        if(paquetesRepository.findById(paquete1.getId()) != null){
            return true;
        }
        return false;
    }

    public boolean deletePaquete(Long id){

        paquetesRepository.deleteById(id);

        if(paquetesRepository.findById(id).isEmpty()){
            return true;
        }
        return false;
    }






}
