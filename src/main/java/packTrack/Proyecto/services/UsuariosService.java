package packTrack.Proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import packTrack.Proyecto.modelos.Usuario;
import packTrack.Proyecto.repository.UsuariosRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuariosService {

    // Se crean los metodos para el CRUD de la tabla Usuario usando la instancia del repositorio que hereda de jpa
    // Se  pueden crear las acciones que se quieren realizar en la tabla Usuario
    @Autowired // Conecta esta clase  con el repositorio, se hace la inyeccion de dependencias
    UsuariosRepository usuariosRepository; // Se crea una instancia del repositorio para poder usar los metodos de jpa


    // Metodo que retornará la lista de Usuarios usuando metodo el metodo findAll() heredado de jpa con usuarioRepository
    public List<Usuario> getAllUsuarios(){

        List<Usuario> usuarioList = new ArrayList<>(); // Se crea una lista de usuarios

        // Regresa un objeto iterable, se puede acceder a cada uno de los usuarios y agregarlos a la lista
        usuariosRepository.findAll().forEach(Usuario ->usuarioList.add(Usuario));

        return usuarioList;
    }

    public Usuario getUsuarioById(Long id){
        return usuariosRepository.findById(id).get();
    }

    // Metodo para guardar o actualizar un usuario
    // JPA sabe si se debe guardar o actualizar dependiendo si existe o no la llave primaria
    public boolean saveOrUpdateUsuario(Usuario usuario) {

        Usuario user = usuariosRepository.save(usuario); // regresa el usuario (Una entidad )

        if (usuariosRepository.findById(user.getNumeroIdentificacion()) != null) { // Si se retorna un  usuario significa que se actualizó o se guardó
            return true;
        }
        return false;
    }

    public boolean deleteUsuario(Long id) {

        usuariosRepository.deleteById(id);

        // verifica si se eliminó el usuario
        if (usuariosRepository.findById(id).isEmpty()) { // Si se busca el usuario y no se encuentra y no devuelve nada  significa que se eliminó
            return true;
        }
        return false;
    }

    // obtener empleados
    public List<Usuario> getEmpleados(){

        List<Usuario> empleados = usuariosRepository.findByRol("Empleado");
        return empleados;
    }
}
