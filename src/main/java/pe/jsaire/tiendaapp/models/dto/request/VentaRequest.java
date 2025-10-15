package pe.jsaire.tiendaapp.models.dto.request;

import jakarta.validation.constraints.NotBlank;
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
public class VentaRequest implements Serializable {

    private Long idcliente;

    private Long idusuario;

    @NotBlank(message = "El campo  no puede estar en blanco")
    private String tipoComprobante;

    @NotBlank(message = "El campo  no puede estar en blanco")
    private String serieComprobante;

    @NotBlank(message = "El campo  no puede estar en blanco")
    private String numComprobante;

    @NotBlank(message = "El campo  no puede estar en blanco")
    private String estado;

    private List<DetalleVentaRequest> detalles;
}
