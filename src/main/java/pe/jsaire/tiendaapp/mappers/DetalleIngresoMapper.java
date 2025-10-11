package pe.jsaire.tiendaapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pe.jsaire.tiendaapp.models.dto.request.DetalleIngresoRequest;
import pe.jsaire.tiendaapp.models.dto.response.DetalleIngresoResponse;
import pe.jsaire.tiendaapp.models.entities.DetalleIngreso;

@Mapper(componentModel = "spring")
public interface DetalleIngresoMapper {

    DetalleIngresoMapper INSTANCE = Mappers.getMapper(DetalleIngresoMapper.class);

    DetalleIngresoResponse toDto(DetalleIngreso detalleIngreso);

    DetalleIngreso toEntity(DetalleIngresoRequest detalleIngresoRequest);
}
