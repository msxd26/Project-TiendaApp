package pe.jsaire.tiendaapp.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVentaRequest {
    private Long idarticulo;
    private Integer cantidad;
    private BigDecimal precio;
    private BigDecimal descuento;
}
