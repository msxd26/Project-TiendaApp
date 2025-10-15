package pe.jsaire.tiendaapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pe.jsaire.tiendaapp.models.dto.request.UsuarioRequest;
import pe.jsaire.tiendaapp.models.dto.response.UsuarioResponse;
import pe.jsaire.tiendaapp.models.entities.Usuario;

@Mapper(componentModel = "spring", uses = {RolMapper.class})
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    @Mapping(target = "idusuario", source = "idUsuario")
    @Mapping(target = "role", source = "rols")
    @Mapping(target = "numDocumento", source = "numeroDocumento")
    UsuarioResponse toResponse(Usuario usuario);

    Usuario toEntity(UsuarioRequest usuarioRequest);
}
