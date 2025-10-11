package pe.jsaire.tiendaapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pe.jsaire.tiendaapp.models.dto.request.RolRequest;
import pe.jsaire.tiendaapp.models.dto.response.RolResponse;
import pe.jsaire.tiendaapp.models.entities.Rol;

@Mapper(componentModel = "spring")
public interface RolMapper {

    RolMapper INSTANCE = Mappers.getMapper(RolMapper.class);

    RolResponse toResponse(Rol rol);

    Rol toEntity(RolRequest rolRequest);
}
