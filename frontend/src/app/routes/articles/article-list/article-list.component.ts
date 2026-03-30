import { Component, OnInit, signal } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { FormsModule } from '@angular/forms';
import { DecimalPipe } from '@angular/common';
import { ArticleService } from '../article.service';
import { ArticuloResponse } from '../../../shared/domain/models/articulo.model';
import { SortType } from '../../../shared/domain/enums/sort-type.enum';
import { AuthService } from '../../../core/services/auth.service';
import { NotificationService } from '../../../shared/services/notification.service';
import { ConfirmDialogComponent } from '../../../shared/ui/confirm-dialog/confirm-dialog.component';
import { ArticleFormComponent } from '../article-form/article-form.component';

@Component({
  selector: 'app-article-list',
  standalone: true,
  imports: [
    MatTableModule, MatPaginatorModule, MatButtonModule, MatIconModule,
    MatFormFieldModule, MatInputModule, MatSelectModule, MatDialogModule,
    MatChipsModule, MatProgressSpinnerModule, FormsModule, DecimalPipe
  ],
  templateUrl: './article-list.component.html',
  styleUrl: './article-list.component.scss'
})
export class ArticleListComponent implements OnInit {
  displayedColumns = ['codigo', 'nombre', 'precioVenta', 'stock', 'estado', 'acciones'];
  data = signal<ArticuloResponse[]>([]);
  totalElements = signal(0);
  loading = signal(true);
  pageSize = 10;
  currentPage = 0;
  sortType: SortType = 'NONE';
  searchTerm = '';
  canCreate: boolean;
  canEdit: boolean;
  canDelete: boolean;

  constructor(
    private articleService: ArticleService,
    private authService: AuthService,
    private dialog: MatDialog,
    private notification: NotificationService
  ) {
    this.canCreate = this.authService.hasAnyRole(['ROLE_ADMIN', 'ROLE_ALMACENERO']);
    this.canEdit = this.authService.hasAnyRole(['ROLE_ADMIN', 'ROLE_ALMACENERO']);
    this.canDelete = this.authService.hasRole('ROLE_ADMIN');
  }

  ngOnInit(): void { this.loadData(); }

  loadData(): void {
    this.loading.set(true);
    this.articleService.getAll(this.currentPage, this.pageSize, this.sortType).subscribe({
      next: (page) => { this.data.set(page.content); this.totalElements.set(page.totalElements); this.loading.set(false); },
      error: () => this.loading.set(false)
    });
  }

  onSearch(): void {
    if (!this.searchTerm.trim()) { this.loadData(); return; }
    this.loading.set(true);
    this.articleService.search(this.searchTerm).subscribe({
      next: (items) => { this.data.set(items); this.totalElements.set(items.length); this.loading.set(false); },
      error: () => this.loading.set(false)
    });
  }

  clearSearch(): void { this.searchTerm = ''; this.loadData(); }

  onPage(event: PageEvent): void { this.currentPage = event.pageIndex; this.pageSize = event.pageSize; this.loadData(); }

  openForm(article?: ArticuloResponse): void {
    this.dialog.open(ArticleFormComponent, { width: '500px', data: article || null })
      .afterClosed().subscribe(result => { if (result) this.loadData(); });
  }

  onDelete(article: ArticuloResponse): void {
    this.dialog.open(ConfirmDialogComponent, { data: { title: 'Eliminar Artículo', message: `¿Estás seguro de eliminar "${article.nombre}"?` } })
      .afterClosed().subscribe(confirmed => {
        if (confirmed) {
          this.articleService.delete(article.idarticulo).subscribe({
            next: () => { this.notification.success('Artículo eliminado'); this.loadData(); },
            error: (err) => this.notification.handleHttpError(err, 'Error al eliminar')
          });
        }
      });
  }
}
