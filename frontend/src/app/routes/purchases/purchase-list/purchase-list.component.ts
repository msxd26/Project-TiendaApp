import { Component, OnInit, signal } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDialog } from '@angular/material/dialog';
import { DecimalPipe, DatePipe } from '@angular/common';
import { PurchaseService } from '../purchase.service';
import { IngresoResponse } from '../../../shared/domain/models/ingreso.model';
import { AuthService } from '../../../core/services/auth.service';
import { NotificationService } from '../../../shared/services/notification.service';
import { ConfirmDialogComponent } from '../../../shared/ui/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-purchase-list',
  standalone: true,
  imports: [MatTableModule, MatPaginatorModule, MatButtonModule, MatIconModule, MatChipsModule, MatProgressSpinnerModule, DecimalPipe, DatePipe],
  templateUrl: './purchase-list.component.html',
  styleUrl: './purchase-list.component.scss'
})
export class PurchaseListComponent implements OnInit {
  columns = ['idingreso','tipoComprobante','serieComprobante','fecha','total','estado','acciones'];
  data = signal<IngresoResponse[]>([]); total = signal(0); loading = signal(true);
  page = 0; canDelete: boolean;
  constructor(private svc: PurchaseService, private auth: AuthService, private dialog: MatDialog, private notif: NotificationService) {
    this.canDelete = auth.hasRole('ROLE_ADMIN');
  }
  ngOnInit() { this.load(); }
  load() { this.loading.set(true); this.svc.getAll(this.page, 10).subscribe({ next: p => { this.data.set(p.content); this.total.set(p.totalElements); this.loading.set(false); }, error: () => this.loading.set(false) }); }
  onPage(e: PageEvent) { this.page = e.pageIndex; this.load(); }
  onDelete(r: IngresoResponse) { this.dialog.open(ConfirmDialogComponent, { data: { title: 'Eliminar', message: `¿Eliminar ingreso #${r.idingreso}?` } }).afterClosed().subscribe(ok => { if (ok) this.svc.delete(r.idingreso).subscribe({ next: () => { this.notif.success('Eliminado'); this.load(); }, error: e => this.notif.handleHttpError(e, 'Error') }); }); }
}
