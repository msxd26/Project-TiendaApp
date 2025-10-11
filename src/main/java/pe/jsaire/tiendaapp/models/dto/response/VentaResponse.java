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
public class VentaResponse {
    private Long idventa;
    private Long idcliente;
    private Long idusuario;
    private String tipoComprobante;
    private String serieComprobante;
    private String numComprobante;
    private LocalDateTime fechaHora;
    private BigDecimal impuesto;
    private BigDecimal total;
    private String estado;
    private List<DetalleVentaResponse> detalles;
}
