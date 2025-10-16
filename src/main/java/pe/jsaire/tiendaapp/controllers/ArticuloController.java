package pe.jsaire.tiendaapp.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.jsaire.tiendaapp.infraestructures.abstract_services.ArticuloService;
import pe.jsaire.tiendaapp.models.dto.request.ArticuloRequest;

@RestController
@RequestMapping("/articulo")
@RequiredArgsConstructor
public class ArticuloController {

    private final ArticuloService articuloService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ALMACENERO','VENDEDOR')")
    public ResponseEntity<?> read(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(articuloService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','ALMACENERO')")
    public ResponseEntity<?> create(@Valid @RequestBody ArticuloRequest articuloRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(articuloService.save(articuloRequest));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody ArticuloRequest articuloRequest, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(articuloService.update(articuloRequest, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        articuloService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
