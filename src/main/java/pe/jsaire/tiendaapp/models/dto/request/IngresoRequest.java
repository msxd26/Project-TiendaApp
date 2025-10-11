package pe.jsaire.tiendaapp.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngresoRequest implements Serializable {
    private Long idproveedor;
    private Long idusuario;
    private String tipoComprobante;
    private String serieComprobante;
    private String numComprobante;
    private String estado;
    private List<DetalleIngresoRequest> detalles;
}
