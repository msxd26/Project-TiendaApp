import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { AuthService } from '../../../core/services/auth.service';
import { ArticuloResponse } from '../../../shared/domain/models/articulo.model';
import { PersonaResponse } from '../../../shared/domain/models/persona.model';
import { NotificationService } from '../../../shared/services/notification.service';
import { ArticleService } from '../../articles/article.service';
import { PersonService } from '../../people/person.service';
import { SaleService } from '../sale.service';

@Component({
  selector: 'app-sale-form',
  standalone: true,
  imports: [ReactiveFormsModule, MatDialogModule, MatFormFieldModule, MatInputModule, MatSelectModule, MatButtonModule, MatIconModule],
  templateUrl: './sale-form.component.html',
  styleUrl: './sale-form.component.scss'
})
export class SaleFormComponent implements OnInit {
  form: FormGroup;
  clients: PersonaResponse[] = [];
  articles: ArticuloResponse[] = [];

  get detalles(): FormArray {
    return this.form.get('detalles') as FormArray;
  }

  constructor(
    private fb: FormBuilder,
    private svc: SaleService,
    private personSvc: PersonService,
    private articleSvc: ArticleService,
    private authService: AuthService,
    private notif: NotificationService,
    private ref: MatDialogRef<SaleFormComponent>
  ) {
    this.form = this.fb.group({
      idcliente: [null, Validators.required],
      idusuario: [null],
      tipoComprobante: ['Boleta', Validators.required],
      serieComprobante: ['', Validators.required],
      numComprobante: ['', Validators.required],
      estado: ['Aceptado'],
      detalles: this.fb.array([])
    });
  }

  ngOnInit(): void {
    // Fetch and set current user dynamically
    this.authService.getMyProfile().subscribe({
      next: (user) => {
        if (user && user.idusuario) {
          this.form.patchValue({ idusuario: user.idusuario });
        }
      },
      error: () => this.notif.error('No se pudo cargar tu id de usuario')
    });

    // Load clients (tipoPersona = 'Cliente')
    this.personSvc.getAll(0, 100).subscribe(page => {
      this.clients = page.content.filter(p => p.tipoPersona === 'Cliente');
    });

    // Load articles with stock
    this.articleSvc.getAll(0, 100).subscribe(page => {
      this.articles = page.content.filter(a => a.estado && a.stock > 0);
    });

    // Add first detail row
    this.addDetalle();
  }

  addDetalle(): void {
    this.detalles.push(this.fb.group({
      idarticulo: [null, Validators.required],
      cantidad: [1, [Validators.required, Validators.min(1)]]
    }));
  }

  removeDetalle(index: number): void {
    this.detalles.removeAt(index);
  }

  onSave(): void {
    if (this.form.invalid || this.detalles.length === 0) {
      this.form.markAllAsTouched();
      // Debug loop to find invalid controls
      const invalidControls = [];
      const controls = this.form.controls;
      for (const name in controls) {
        if (controls[name].invalid) {
          invalidControls.push(name);
          // print child errors if it's the FormArray
          if (name === 'detalles') {
             const detArr = this.form.get('detalles') as FormArray;
             detArr.controls.forEach((detGroup: any, index) => {
                for (const dname in detGroup.controls) {
                   if (detGroup.controls[dname].invalid) {
                       invalidControls.push(`Fila ${index} [${dname}] = vacio o error`);
                   }
                }
             });
          }
        }
      }
      if (this.detalles.length === 0) invalidControls.push('detalles vacios');
      
      console.warn('El formulario es inválido. Campos pendientes:', invalidControls);
      this.notif.error('Por favor, complete todos los campos obligatorios correctamente.');
      return;
    }

    console.log('Enviando datos al backend:', this.form.value);
    this.svc.create(this.form.value).subscribe({
      next: () => {
        this.notif.success('Venta registrada exitosamente');
        this.ref.close(true);
      },
      error: (e) => {
        console.error('Error desde el backend:', e);
        this.notif.handleHttpError(e, 'Error al registrar venta');
      }
    });
  }
}
