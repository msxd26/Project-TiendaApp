package pe.jsaire.tiendaapp.infraestructures.abstract_services;

import pe.jsaire.tiendaapp.models.dto.request.UsuarioRequest;
import pe.jsaire.tiendaapp.models.dto.response.UsuarioResponse;

import java.util.Set;

public interface UsuarioService extends GenericService<UsuarioRequest, UsuarioResponse, Long> {


    Set<UsuarioResponse> findAll();
    
}
