package pe.jsaire.tiendaapp.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.jsaire.tiendaapp.infraestructures.abstract_services.UsuarioService;
import pe.jsaire.tiendaapp.models.dto.request.UsuarioRequest;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody UsuarioRequest usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@Valid @RequestBody UsuarioRequest usuario) {
        usuario.setAdmin(false);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }
}
