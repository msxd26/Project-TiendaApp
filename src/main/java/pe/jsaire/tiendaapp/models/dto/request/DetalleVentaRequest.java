package pe.jsaire.tiendaapp.models.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVentaRequest implements Serializable {

    @NotNull(message = "El artículo es requerido")
    @Positive(message = "El id del artículo debe ser positivo")
    private Long idarticulo;

    @NotNull(message = "La cantidad es requerida")
    @Positive(message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;
}
