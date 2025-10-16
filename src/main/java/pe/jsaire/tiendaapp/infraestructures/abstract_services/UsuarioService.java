package pe.jsaire.tiendaapp.infraestructures.abstract_services;

import pe.jsaire.tiendaapp.models.dto.request.RolRequest;
import pe.jsaire.tiendaapp.models.dto.request.UsuarioRequest;
import pe.jsaire.tiendaapp.models.dto.response.UsuarioResponse;

import java.util.Set;

public interface UsuarioService {

    UsuarioResponse findById(Long id);

    UsuarioResponse save(UsuarioRequest usuarioRequest);

    void delete(Long id);

    Set<UsuarioResponse> findAll();

    boolean existsByEmail(String email);

    void addRol(Long id, RolRequest rol);

    void removeRol(Long id, String rolNombre);


}
