package pe.jsaire.tiendaapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pe.jsaire.tiendaapp.models.dto.request.VentaRequest;
import pe.jsaire.tiendaapp.models.dto.response.VentaResponse;
import pe.jsaire.tiendaapp.models.entities.Venta;

@Mapper(componentModel = "spring", uses = {DetalleVentaMapper.class})
public interface VentaMapper {

    VentaMapper INSTANCE = Mappers.getMapper(VentaMapper.class);

    @Mapping(target = "idcliente", source = "venta.persona.idPersona")
    @Mapping(target = "idusuario", source = "venta.usuario.idUsuario")
    @Mapping(target = "idventa", source = "venta.idVenta")
    @Mapping(target = "numComprobante", source = "venta.numeroComprobante")
    @Mapping(target = "detalles", source = "venta.detalleVentas")
    @Mapping(target = "estado", source = "venta.estado")
    VentaResponse toResponse(Venta venta);

    Venta toEntity(VentaRequest ventaRequest);
}
