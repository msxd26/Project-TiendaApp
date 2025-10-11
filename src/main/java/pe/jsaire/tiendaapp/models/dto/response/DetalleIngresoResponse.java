package pe.jsaire.tiendaapp.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleIngresoResponse implements Serializable {
    private Long iddetalleIngreso;
    private Long idarticulo;
    private Integer cantidad;
    private BigDecimal precio;
}
