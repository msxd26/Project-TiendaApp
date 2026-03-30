import { Component, OnInit, signal } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { PersonService } from '../person.service';
import { PersonaResponse } from '../../../shared/domain/models/persona.model';
import { AuthService } from '../../../core/services/auth.service';
import { NotificationService } from '../../../shared/services/notification.service';
import { ConfirmDialogComponent } from '../../../shared/ui/confirm-dialog/confirm-dialog.component';
import { PersonFormComponent } from '../person-form/person-form.component';

@Component({
  selector: 'app-person-list',
  standalone: true,
  imports: [MatTableModule, MatPaginatorModule, MatButtonModule, MatIconModule, MatDialogModule, MatProgressSpinnerModule],
  templateUrl: './person-list.component.html',
  styleUrl: './person-list.component.scss'
})
export class PersonListComponent implements OnInit {
  columns = ['nombre','tipoPersona','numDocumento','email','telefono','acciones'];
  data = signal<PersonaResponse[]>([]); total = signal(0); loading = signal(true);
  page = 0; canCreate: boolean; canEdit: boolean; canDelete: boolean;
  constructor(private svc: PersonService, private auth: AuthService, private dialog: MatDialog, private notif: NotificationService) {
    this.canCreate = auth.hasAnyRole(['ROLE_ADMIN','ROLE_VENDEDOR']);
    this.canEdit = auth.hasRole('ROLE_ADMIN'); this.canDelete = auth.hasRole('ROLE_ADMIN');
  }
  ngOnInit() { this.load(); }
  load() { this.loading.set(true); this.svc.getAll(this.page, 10).subscribe({ next: p => { this.data.set(p.content); this.total.set(p.totalElements); this.loading.set(false); }, error: () => this.loading.set(false) }); }
  onPage(e: PageEvent) { this.page = e.pageIndex; this.load(); }
  openForm(p?: PersonaResponse) { this.dialog.open(PersonFormComponent, { width: '550px', data: p || null }).afterClosed().subscribe(r => { if (r) this.load(); }); }
  onDelete(p: PersonaResponse) { this.dialog.open(ConfirmDialogComponent, { data: { title: 'Eliminar', message: `¿Eliminar "${p.nombre}"?` } }).afterClosed().subscribe(ok => { if (ok) this.svc.delete(p.idpersona).subscribe({ next: () => { this.notif.success('Eliminada'); this.load(); }, error: e => this.notif.handleHttpError(e, 'Error') }); }); }
}
