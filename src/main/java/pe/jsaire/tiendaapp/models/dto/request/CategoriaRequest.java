package pe.jsaire.tiendaapp.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaRequest {
    private String nombre;
    private String descripcion;
    private Boolean estado;
}
