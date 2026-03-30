package pe.jsaire.tiendaapp.infraestructures.abstract_services;

import org.springframework.data.domain.Page;
import pe.jsaire.tiendaapp.models.dto.request.DetalleIngresoRequest;
import pe.jsaire.tiendaapp.models.dto.request.IngresoRequest;
import pe.jsaire.tiendaapp.models.dto.response.IngresoResponse;
import pe.jsaire.tiendaapp.utils.enums.SortType;

public interface IngresoService extends GenericService<IngresoRequest, IngresoResponse, Long> {

    String FIELD_BY_SORT = "fecha";

    Page<IngresoResponse> getAll(Integer page, Integer size, SortType sortType);

    IngresoResponse addDetalle(Long id, DetalleIngresoRequest detalleIngreso);

    IngresoResponse removeDetalle(Long id, Long idDetalle);

    boolean existsIngresoBySerieComprobante(String serieComprobante);
}
