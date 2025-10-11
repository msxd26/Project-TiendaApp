package pe.jsaire.tiendaapp.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRolRequest {
    private Long idusuario;
    private Long idrol;
}
