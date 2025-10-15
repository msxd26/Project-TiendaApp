package pe.jsaire.tiendaapp.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaResponse implements Serializable {
    private Long idcategoria;
    private String nombre;
    private String descripcion;
    private Boolean estado;
}
