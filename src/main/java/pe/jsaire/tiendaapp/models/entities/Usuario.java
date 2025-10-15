package pe.jsaire.tiendaapp.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity(name = "usuario")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "idusuario")
    private Long idUsuario;
    private String nombre;
    @Column(name = "tipo_documento")
    private String tipoDocumento;
    @Column(name = "num_documento")
    private String numeroDocumento;
    private String direccion;
    private String telefono;
    private String email;
    private String password;
    private boolean estado;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "idusuario"),
            inverseJoinColumns = @JoinColumn(name = "idrol"), uniqueConstraints = {
            @UniqueConstraint(columnNames = {"idusuario", "idrol"})
    })
    private Set<Rol> rols;

    @Transient
    private boolean isAdmin;

    @PrePersist
    public void prePersist() {
        this.estado = true;
    }
}
