import { Component, OnInit, signal } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDialog } from '@angular/material/dialog';
import { DecimalPipe, DatePipe } from '@angular/common';
import { SaleService } from '../sale.service';
import { VentaResponse } from '../../../shared/domain/models/venta.model';
import { AuthService } from '../../../core/services/auth.service';
import { NotificationService } from '../../../shared/services/notification.service';
import { ConfirmDialogComponent } from '../../../shared/ui/confirm-dialog/confirm-dialog.component';
import { SaleFormComponent } from '../sale-form/sale-form.component';

@Component({
  selector: 'app-sale-list',
  standalone: true,
  imports: [MatTableModule, MatPaginatorModule, MatButtonModule, MatIconModule, MatChipsModule, MatProgressSpinnerModule, DecimalPipe, DatePipe],
  templateUrl: './sale-list.component.html',
  styleUrl: './sale-list.component.scss'
})
export class SaleListComponent implements OnInit {
  columns = ['idventa','tipoComprobante','serieComprobante','fechaHora','total','estado','acciones'];
  data = signal<VentaResponse[]>([]); total = signal(0); loading = signal(true);
  page = 0; canDelete: boolean; canCreate: boolean;
  constructor(private svc: SaleService, private auth: AuthService, private dialog: MatDialog, private notif: NotificationService) {
    this.canDelete = auth.hasRole('ROLE_ADMIN');
    this.canCreate = auth.hasRole('ROLE_ADMIN') || auth.hasRole('ROLE_VENDEDOR');
  }
  ngOnInit() { this.load(); }
  load() { this.loading.set(true); this.svc.getAll(this.page, 10).subscribe({ next: p => { this.data.set(p.content); this.total.set(p.totalElements); this.loading.set(false); }, error: () => this.loading.set(false) }); }
  onPage(e: PageEvent) { this.page = e.pageIndex; this.load(); }
  onDelete(r: VentaResponse) { this.dialog.open(ConfirmDialogComponent, { data: { title: 'Eliminar', message: `¿Eliminar venta #${r.idventa}?` } }).afterClosed().subscribe(ok => { if (ok) this.svc.delete(r.idventa).subscribe({ next: () => { this.notif.success('Eliminada'); this.load(); }, error: e => this.notif.handleHttpError(e, 'Error') }); }); }

  onAdd() {
    this.dialog.open(SaleFormComponent, { width: '800px', disableClose: true }).afterClosed().subscribe(res => {
      if (res) this.load();
    });
  }
}
