package pe.jsaire.tiendaapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pe.jsaire.tiendaapp.models.dto.request.PersonaRequest;
import pe.jsaire.tiendaapp.models.dto.response.PersonaResponse;
import pe.jsaire.tiendaapp.models.entities.Persona;

@Mapper(componentModel = "spring")
public interface PersonaMapper {

    PersonaMapper INSTANCE = Mappers.getMapper(PersonaMapper.class);

    @Mapping(target = "idpersona", source = "idPersona")
    @Mapping(target = "numDocumento", source = "numeroDocumento")
    PersonaResponse toResponse(Persona persona);

    @Mapping(target = "numeroDocumento", source = "numDocumento")
    Persona toEntity(PersonaRequest personaRequest);
}
