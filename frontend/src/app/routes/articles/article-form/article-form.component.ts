import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { ArticleService } from '../article.service';
import { CategoryService } from '../../categories/category.service';
import { ArticuloResponse } from '../../../shared/domain/models/articulo.model';
import { CategoriaResponse } from '../../../shared/domain/models/categoria.model';
import { NotificationService } from '../../../shared/services/notification.service';

@Component({
  selector: 'app-article-form',
  standalone: true,
  imports: [ReactiveFormsModule, MatDialogModule, MatFormFieldModule, MatInputModule, MatSelectModule, MatButtonModule],
  templateUrl: './article-form.component.html',
  styleUrl: './article-form.component.scss'
})
export class ArticleFormComponent implements OnInit {
  form: FormGroup;
  isEdit: boolean;
  categories: CategoriaResponse[] = [];

  constructor(
    private fb: FormBuilder,
    private articleService: ArticleService,
    private categoryService: CategoryService,
    private notification: NotificationService,
    private dialogRef: MatDialogRef<ArticleFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ArticuloResponse | null
  ) {
    this.isEdit = !!data;
    this.form = this.fb.group({
      // Backend: @Size(min=1), required
      idcategoria: [data?.idcategoria || null, Validators.required],
      // Backend: @NotBlank
      codigo: [data?.codigo || '', [Validators.required, Validators.minLength(1)]],
      // Backend: @NotBlank + @isExistByNombreArticulo (server-side)
      nombre: [data?.nombre || '', [Validators.required, Validators.minLength(1)]],
      // Backend: @DecimalMin("0.1") + @Positive
      precioVenta: [data?.precioVenta || null, [Validators.required, Validators.min(0.1)]],
      // Backend: @PositiveOrZero
      stock: [data?.stock ?? 0, [Validators.required, Validators.min(0)]],
      // Backend: @NotBlank
      descripcion: [data?.descripcion || '', [Validators.required, Validators.minLength(1)]]
    });
  }

  ngOnInit(): void {
    this.categoryService.getAll(0, 100).subscribe(page => {
      this.categories = page.content;
    });
  }

  onSave(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    const obs = this.isEdit
      ? this.articleService.update(this.data!.idarticulo, this.form.value)
      : this.articleService.create(this.form.value);

    obs.subscribe({
      next: () => {
        this.notification.success(this.isEdit ? 'Artículo actualizado' : 'Artículo creado');
        this.dialogRef.close(true);
      },
      error: (err) => this.notification.handleHttpError(err, 'Error al guardar artículo')
    });
  }
}
