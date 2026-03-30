package pe.jsaire.tiendaapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.jsaire.tiendaapp.models.dto.request.RolRequest;
import pe.jsaire.tiendaapp.models.dto.response.RolResponse;
import pe.jsaire.tiendaapp.models.entities.Rol;

@Mapper(componentModel = "spring")
public interface RolMapper {

    @Mapping(target = "idrol", source = "idRol")
    RolResponse toResponse(Rol rol);

    Rol toEntity(RolRequest rolRequest);
}
