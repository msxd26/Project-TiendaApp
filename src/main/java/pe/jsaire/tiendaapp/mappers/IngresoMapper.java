package pe.jsaire.tiendaapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pe.jsaire.tiendaapp.models.dto.request.IngresoRequest;
import pe.jsaire.tiendaapp.models.dto.response.IngresoResponse;
import pe.jsaire.tiendaapp.models.entities.Ingreso;

@Mapper(componentModel = "spring")
public interface IngresoMapper {

    IngresoMapper INSTANCE = Mappers.getMapper(IngresoMapper.class);

    IngresoResponse toResponse(Ingreso ingreso);

    Ingreso toEntity(IngresoRequest ingresoRequest);
}
