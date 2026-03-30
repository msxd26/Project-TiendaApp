import { TipoPersona } from '../enums/tipo-persona.enum';

export interface PersonaRequest {
  tipoPersona: TipoPersona;
  nombre: string;
  tipoDocumento: string;
  numDocumento: string;
  direccion: string;
  telefono: string;
  email: string;
}

export interface PersonaResponse {
  idpersona: number;
  tipoPersona: string;
  nombre: string;
  tipoDocumento: string;
  numDocumento: string;
  direccion: string;
  telefono: string;
  email: string;
}
