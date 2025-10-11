package pe.jsaire.tiendaapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pe.jsaire.tiendaapp.models.dto.request.DetalleVentaRequest;
import pe.jsaire.tiendaapp.models.dto.response.DetalleVentaResponse;
import pe.jsaire.tiendaapp.models.entities.DetalleVenta;

@Mapper(componentModel = "spring")
public interface DetalleVentaMapper {

    DetalleVentaMapper INSTANCE = Mappers.getMapper(DetalleVentaMapper.class);

    DetalleVentaResponse toResponse(DetalleVenta detalleVenta);
    
    DetalleVenta toEntity(DetalleVentaRequest detalleVentaRequest);
}
