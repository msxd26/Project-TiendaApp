export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  email: string;
  message: string;
}

export interface JwtPayload {
  sub: string;
  authorities: string;
  exp: number;
  iat: number;
}
