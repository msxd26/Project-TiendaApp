export interface UsuarioRequest {
  nombre: string;
  tipoDocumento: string;
  numDocumento: string;
  direccion: string;
  telefono: string;
  email: string;
  password: string;
  admin: boolean;
  roles?: RolRequest[];
}

export interface UsuarioResponse {
  idusuario: number;
  nombre: string;
  tipoDocumento: string;
  numDocumento: string;
  direccion: string;
  telefono: string;
  email: string;
  estado: boolean;
  role: RolResponse[];
}

export interface RolRequest {
  nombre: string;
}

export interface RolResponse {
  idrol: number;
  nombre: string;
  descripcion: string;
  estado: boolean;
}
