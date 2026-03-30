package pe.jsaire.tiendaapp.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.jsaire.tiendaapp.infraestructures.abstract_services.IngresoService;
import pe.jsaire.tiendaapp.models.dto.request.DetalleIngresoRequest;
import pe.jsaire.tiendaapp.models.dto.request.IngresoRequest;
import pe.jsaire.tiendaapp.models.dto.response.IngresoResponse;
import pe.jsaire.tiendaapp.security.AuthorityConstants;
import pe.jsaire.tiendaapp.utils.enums.SortType;

@RestController
@RequestMapping("/ingreso")
@RequiredArgsConstructor
public class IngresoController {

    private final IngresoService ingresoService;

    @GetMapping
    @PreAuthorize(AuthorityConstants.READ_WAREHOUSE)
    public ResponseEntity<Page<IngresoResponse>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "NONE") SortType sortType) {
        return ResponseEntity.ok(ingresoService.getAll(page, size, sortType));
    }

    @GetMapping("/{id}")
    @PreAuthorize(AuthorityConstants.READ_WAREHOUSE)
    public ResponseEntity<IngresoResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ingresoService.findById(id));
    }

    @PostMapping
    @PreAuthorize(AuthorityConstants.WRITE_INGRESO)
    public ResponseEntity<IngresoResponse> create(@Valid @RequestBody IngresoRequest ingresoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ingresoService.save(ingresoRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(AuthorityConstants.ADMIN_ONLY)
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        ingresoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{idIngreso}/detalles")
    @PreAuthorize(AuthorityConstants.WRITE_INGRESO)
    public ResponseEntity<IngresoResponse> addDetalleVenta(@Valid @RequestBody DetalleIngresoRequest ingresoRequest
            , @PathVariable Long idIngreso) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ingresoService.addDetalle(idIngreso, ingresoRequest));
    }

    @PatchMapping("/{idIngreso}/detalles/{idDetalle}")
    @PreAuthorize(AuthorityConstants.ADMIN_ONLY)
    public ResponseEntity<IngresoResponse> removeDetalleVenta(
            @PathVariable Long idIngreso,
            @PathVariable Long idDetalle) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ingresoService.removeDetalle(idIngreso, idDetalle));
    }
}
