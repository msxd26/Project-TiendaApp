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
public class ArticuloResponse implements Serializable {

    private Long idarticulo;
    private Long idcategoria;
    private String codigo;
    private String nombre;
    private BigDecimal precioVenta;
    private Integer stock;
    private String descripcion;
    private Boolean estado;
}
