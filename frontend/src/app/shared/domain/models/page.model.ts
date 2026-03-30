export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
  empty: boolean;
}

export interface ErrorResponse {
  status: number;
  message: string;
  timestamp: string;
  details?: Record<string, string>;
}
