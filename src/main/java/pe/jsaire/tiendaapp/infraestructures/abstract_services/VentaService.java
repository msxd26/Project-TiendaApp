package pe.jsaire.tiendaapp.infraestructures.abstract_services;

import org.springframework.data.domain.Page;
import pe.jsaire.tiendaapp.models.dto.request.DetalleVentaRequest;
import pe.jsaire.tiendaapp.models.dto.request.VentaRequest;
import pe.jsaire.tiendaapp.models.dto.response.VentaResponse;
import pe.jsaire.tiendaapp.utils.enums.SortType;

public interface VentaService extends GenericService<VentaRequest, VentaResponse, Long> {

    String FIELD_BY_SORT = "fechaHora";

    Page<VentaResponse> getAll(Integer page, Integer size, SortType sortType);

    VentaResponse addDetalle(Long id, DetalleVentaRequest detalleVentaRequest);

    VentaResponse removeDetalle(Long id, Long idDetalle);
}
