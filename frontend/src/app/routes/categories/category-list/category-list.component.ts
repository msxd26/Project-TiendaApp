import { Component, OnInit, signal } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { FormsModule } from '@angular/forms';
import { CategoryService } from '../category.service';
import { CategoriaResponse } from '../../../shared/domain/models/categoria.model';
import { AuthService } from '../../../core/services/auth.service';
import { NotificationService } from '../../../shared/services/notification.service';
import { ConfirmDialogComponent } from '../../../shared/ui/confirm-dialog/confirm-dialog.component';
import { CategoryFormComponent } from '../category-form/category-form.component';

@Component({
  selector: 'app-category-list',
  standalone: true,
  imports: [MatTableModule, MatPaginatorModule, MatButtonModule, MatIconModule, MatFormFieldModule, MatInputModule, MatDialogModule, MatChipsModule, MatProgressSpinnerModule, FormsModule],
  templateUrl: './category-list.component.html',
  styleUrl: './category-list.component.scss'
})
export class CategoryListComponent implements OnInit {
  columns = ['nombre', 'descripcion', 'estado', 'acciones'];
  data = signal<CategoriaResponse[]>([]); totalElements = signal(0); loading = signal(true);
  searchTerm = ''; currentPage = 0; canCreate: boolean;

  constructor(private svc: CategoryService, private auth: AuthService, private dialog: MatDialog, private notif: NotificationService) {
    this.canCreate = auth.hasRole('ROLE_ADMIN');
  }
  ngOnInit() { this.loadData(); }
  loadData() { this.loading.set(true); this.svc.getAll(this.currentPage, 10).subscribe({ next: p => { this.data.set(p.content); this.totalElements.set(p.totalElements); this.loading.set(false); }, error: () => this.loading.set(false) }); }
  onSearch() { if (!this.searchTerm.trim()) { this.loadData(); return; } this.loading.set(true); this.svc.search(this.searchTerm).subscribe({ next: d => { this.data.set(d); this.totalElements.set(d.length); this.loading.set(false); }, error: () => this.loading.set(false) }); }
  onPage(e: PageEvent) { this.currentPage = e.pageIndex; this.loadData(); }
  openForm(cat?: CategoriaResponse) { this.dialog.open(CategoryFormComponent, { width: '450px', data: cat || null }).afterClosed().subscribe(r => { if (r) this.loadData(); }); }
  onDelete(cat: CategoriaResponse) { this.dialog.open(ConfirmDialogComponent, { data: { title: 'Eliminar Categoría', message: `¿Eliminar "${cat.nombre}"?` } }).afterClosed().subscribe(ok => { if (ok) this.svc.delete(cat.idcategoria).subscribe({ next: () => { this.notif.success('Eliminada'); this.loadData(); }, error: e => this.notif.handleHttpError(e, 'Error') }); }); }
}
