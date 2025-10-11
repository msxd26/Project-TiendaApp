package pe.jsaire.tiendaapp.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngresoResponse {
    private Long idingreso;
    private Long idproveedor;
    private Long idusuario;
    private String tipoComprobante;
    private String serieComprobante;
    private String numComprobante;
    private LocalDateTime fecha;
    private BigDecimal impuesto;
    private BigDecimal total;
    private String estado;
    private List<DetalleIngresoResponse> detalles;
}
