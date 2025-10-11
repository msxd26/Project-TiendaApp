package pe.jsaire.tiendaapp.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVentaResponse {
    private Long iddetalleVenta;
    private Long idarticulo;
    private Integer cantidad;
    private BigDecimal precio;
    private BigDecimal descuento;
}
