package pe.jsaire.tiendaapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.jsaire.tiendaapp.models.dto.request.DetalleVentaRequest;
import pe.jsaire.tiendaapp.models.dto.response.DetalleVentaResponse;
import pe.jsaire.tiendaapp.models.entities.DetalleVenta;

@Mapper(componentModel = "spring")
public interface DetalleVentaMapper {


    @Mapping(target = "iddetalleVenta", source = "detalleVenta.idDetalleVenta")
    @Mapping(target = "idarticulo", source = "detalleVenta.articulo.idArticulo")
    DetalleVentaResponse toResponse(DetalleVenta detalleVenta);

    DetalleVenta toEntity(DetalleVentaRequest detalleVentaRequest);
}
