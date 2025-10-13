package pe.jsaire.tiendaapp.infraestructures.abstract_services;

import pe.jsaire.tiendaapp.models.dto.request.DetalleIngresoRequest;
import pe.jsaire.tiendaapp.models.dto.request.IngresoRequest;
import pe.jsaire.tiendaapp.models.dto.response.IngresoResponse;

public interface IngresoService extends GenericService<IngresoRequest, IngresoResponse, Long> {


    IngresoResponse addDetalle(Long id, DetalleIngresoRequest detalleIngreso);

    IngresoResponse removeDetalle(Long id, Long idDetalle);
}
