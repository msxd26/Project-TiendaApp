package pe.jsaire.tiendaapp.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.jsaire.tiendaapp.infraestructures.abstract_services.VentaService;
import pe.jsaire.tiendaapp.models.dto.request.DetalleVentaRequest;
import pe.jsaire.tiendaapp.models.dto.request.VentaRequest;
import pe.jsaire.tiendaapp.models.dto.response.VentaResponse;
import pe.jsaire.tiendaapp.security.AuthorityConstants;
import pe.jsaire.tiendaapp.utils.enums.SortType;

@RestController
@RequestMapping("/venta")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    @PreAuthorize(AuthorityConstants.READ_SALES)
    public ResponseEntity<Page<VentaResponse>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "NONE") SortType sortType) {
        return ResponseEntity.ok(ventaService.getAll(page, size, sortType));
    }

    @GetMapping("/{id}")
    @PreAuthorize(AuthorityConstants.READ_SALES)
    public ResponseEntity<VentaResponse> readById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ventaService.findById(id));
    }

    @PostMapping
    @PreAuthorize(AuthorityConstants.WRITE_VENTA)
    public ResponseEntity<VentaResponse> create(@Valid @RequestBody VentaRequest ventaRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.save(ventaRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(AuthorityConstants.ADMIN_ONLY)
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        ventaService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{idVenta}/detalles")
    @PreAuthorize(AuthorityConstants.WRITE_VENTA)
    public ResponseEntity<VentaResponse> addDetalleVenta(
            @PathVariable Long idVenta,
            @RequestBody DetalleVentaRequest detalleRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(ventaService.addDetalle(idVenta, detalleRequest));
    }

    @PatchMapping("/{idVenta}/detalles/{idDetalle}")
    @PreAuthorize(AuthorityConstants.ADMIN_ONLY)
    public ResponseEntity<VentaResponse> removeDetalleVenta(
            @PathVariable Long idVenta,
            @PathVariable Long idDetalle) {
        return ResponseEntity.status(HttpStatus.OK).body(ventaService.removeDetalle(idVenta, idDetalle));
    }
}
