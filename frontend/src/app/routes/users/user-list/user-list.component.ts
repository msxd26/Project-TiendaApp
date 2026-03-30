import { Component, OnInit, signal } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDialog } from '@angular/material/dialog';
import { UserService } from '../user.service';
import { UsuarioResponse } from '../../../shared/domain/models/usuario.model';
import { NotificationService } from '../../../shared/services/notification.service';
import { ConfirmDialogComponent } from '../../../shared/ui/confirm-dialog/confirm-dialog.component';
import { UserFormComponent } from '../user-form/user-form.component';
import { MatDialogModule } from '@angular/material/dialog';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [MatTableModule, MatButtonModule, MatIconModule, MatChipsModule, MatProgressSpinnerModule, MatDialogModule],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.scss'
})
export class UserListComponent implements OnInit {
  columns = ['nombre','email','estado','roles','acciones'];
  data = signal<UsuarioResponse[]>([]); loading = signal(true);
  constructor(private svc: UserService, private dialog: MatDialog, private notif: NotificationService) {}
  ngOnInit() { this.load(); }
  load() { this.loading.set(true); this.svc.getAll().subscribe({ next: d => { this.data.set(d); this.loading.set(false); }, error: () => this.loading.set(false) }); }
  onCreate() {
    this.dialog.open(UserFormComponent, { width: '500px' }).afterClosed().subscribe(result => {
      if (result) this.load();
    });
  }
  onDelete(u: UsuarioResponse) { this.dialog.open(ConfirmDialogComponent, { data: { title: 'Eliminar Usuario', message: `¿Eliminar "${u.nombre}"?` } }).afterClosed().subscribe(ok => { if (ok) this.svc.delete(u.idusuario).subscribe({ next: () => { this.notif.success('Eliminado'); this.load(); }, error: e => this.notif.handleHttpError(e, 'Error') }); }); }
}
