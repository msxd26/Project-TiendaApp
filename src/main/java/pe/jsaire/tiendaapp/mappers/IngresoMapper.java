package pe.jsaire.tiendaapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.jsaire.tiendaapp.models.dto.request.IngresoRequest;
import pe.jsaire.tiendaapp.models.dto.response.IngresoResponse;
import pe.jsaire.tiendaapp.models.entities.Ingreso;

@Mapper(componentModel = "spring", uses = {DetalleIngresoMapper.class})
public interface IngresoMapper {


    @Mapping(target = "idingreso", source = "idIngreso")
    @Mapping(target = "idproveedor", source = "persona.idPersona")
    @Mapping(target = "idusuario", source = "usuario.idUsuario")
    @Mapping(target = "detalles", source = "ingreso.detalleIngresos")
    @Mapping(target = "numComprobante", source = "numeroComprobante")
    IngresoResponse toResponse(Ingreso ingreso);

    Ingreso toEntity(IngresoRequest ingresoRequest);
}
