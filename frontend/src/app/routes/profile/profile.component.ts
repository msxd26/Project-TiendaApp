import { Component, OnInit, signal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { AuthService } from '../../core/services/auth.service';
import { UsuarioResponse } from '../../shared/domain/models/usuario.model';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [MatCardModule, MatListModule, MatIconModule, MatChipsModule, MatProgressSpinnerModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit {
  user = signal<UsuarioResponse | null>(null);
  loading = signal(true);

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.authService.getMyProfile().subscribe({
      next: (data) => {
        this.user.set(data);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }
}
