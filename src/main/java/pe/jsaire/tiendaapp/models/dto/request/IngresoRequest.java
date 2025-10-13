package pe.jsaire.tiendaapp.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.jsaire.tiendaapp.utils.validations.isExistsBySerieComprobanteIngreso;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngresoRequest implements Serializable {
    private Long idproveedor;
    private Long idusuario;

    @NotBlank(message = "El campo  no puede estar en blanco")
    private String tipoComprobante;
    @NotBlank(message = "El campo  no puede estar en blanco")
    @isExistsBySerieComprobanteIngreso
    private String serieComprobante;
    @NotBlank(message = "El campo  no puede estar en blanco")
    private String numComprobante;
    @NotBlank(message = "El campo  no puede estar en blanco")
    private String estado;
    private List<DetalleIngresoRequest> detalles;
}
