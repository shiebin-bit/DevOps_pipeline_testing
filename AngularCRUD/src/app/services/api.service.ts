import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  baseUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getBooks() {
    return this.http.get<any>(`${this.baseUrl}/allBooks`);
  }

  postBook(data: any) {
    return this.http.post<any>(`${this.baseUrl}/addBook`, data);
  }

  putBook(data: any, bookId: number) {
    return this.http.put<any>(`${this.baseUrl}/updateBook/${bookId}`, data);
  }

  deleteBook(bookId: number) {
    return this.http.delete<any>(`${this.baseUrl}/deleteBook/${bookId}`);
  }
}
