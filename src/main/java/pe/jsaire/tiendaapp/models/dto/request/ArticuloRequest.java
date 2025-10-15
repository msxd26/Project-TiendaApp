package pe.jsaire.tiendaapp.models.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.jsaire.tiendaapp.utils.validations.isExistByNombre;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticuloRequest implements Serializable {

    @Size(min = 1)
    private Long idcategoria;

    @NotBlank(message = "El campo  no puede estar en blanco")
    private String codigo;

    @NotBlank(message = "El campo  no puede estar en blanco")
    @isExistByNombre
    private String nombre;

    @DecimalMin(value = "0.1", message = "El campo no puede estar en blanco")
    @Positive
    private BigDecimal precioVenta;

    @PositiveOrZero(message = "El campo no puede ser negativo")
    private Integer stock;

    @NotBlank(message = "El campo  no puede estar en blanco")
    private String descripcion;
}
