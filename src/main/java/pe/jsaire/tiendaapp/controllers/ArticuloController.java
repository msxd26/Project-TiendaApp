package pe.jsaire.tiendaapp.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.jsaire.tiendaapp.infraestructures.abstract_services.ArticuloService;
import pe.jsaire.tiendaapp.models.dto.request.ArticuloRequest;
import pe.jsaire.tiendaapp.models.dto.response.ArticuloResponse;
import pe.jsaire.tiendaapp.security.AuthorityConstants;
import pe.jsaire.tiendaapp.utils.enums.SortType;

import java.util.List;

@RestController
@RequestMapping("/articulo")
@RequiredArgsConstructor
public class ArticuloController {

    private final ArticuloService articuloService;

    @GetMapping
    @PreAuthorize(AuthorityConstants.READ_CATALOG)
    public ResponseEntity<Page<ArticuloResponse>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "NONE") SortType sortType) {
        return ResponseEntity.ok(articuloService.getAll(page, size, sortType));
    }

    @GetMapping("/buscar")
    @PreAuthorize(AuthorityConstants.READ_CATALOG)
    public ResponseEntity<List<ArticuloResponse>> buscar(@RequestParam String nombre) {
        return ResponseEntity.ok(articuloService.buscarPorNombre(nombre));
    }

    @GetMapping("/{id}")
    @PreAuthorize(AuthorityConstants.READ_CATALOG)
    public ResponseEntity<?> read(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(articuloService.findById(id));
    }

    @PostMapping
    @PreAuthorize(AuthorityConstants.WRITE_ARTICLE)
    public ResponseEntity<?> create(@Valid @RequestBody ArticuloRequest articuloRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(articuloService.save(articuloRequest));
    }

    @PutMapping("/{id}")
    @PreAuthorize(AuthorityConstants.WRITE_ARTICLE)
    public ResponseEntity<?> update(@Valid @RequestBody ArticuloRequest articuloRequest, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(articuloService.update(articuloRequest, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(AuthorityConstants.ADMIN_ONLY)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        articuloService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
