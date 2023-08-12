package packTrack.Proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import packTrack.Proyecto.modelos.Paquete;

@Repository
public interface PaquetesRepository extends JpaRepository<Paquete, Long> {


}
