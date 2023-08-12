package packTrack.Proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import packTrack.Proyecto.modelos.Usuario;

import java.util.List;

@Repository                                // <Clase relacionada, Tipo de dato de la llave primaria>
public interface UsuariosRepository extends JpaRepository <Usuario, Long> {

    // obtener los usuarios que son Empleados
    List<Usuario> findByRol(String rol);
}
