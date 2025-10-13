package pe.jsaire.tiendaapp.infraestructures.abstract_services;

import pe.jsaire.tiendaapp.models.dto.request.DetalleVentaRequest;
import pe.jsaire.tiendaapp.models.dto.request.VentaRequest;
import pe.jsaire.tiendaapp.models.dto.response.VentaResponse;

public interface VentaService extends GenericService<VentaRequest, VentaResponse, Long> {

    VentaResponse addDetalle(Long id, DetalleVentaRequest detalleVentaRequest);

    VentaResponse removeDetalle(Long id, Long idDetalle);

    
}
