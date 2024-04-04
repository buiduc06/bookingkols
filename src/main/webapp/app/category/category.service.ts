import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { CategoryDTO } from 'app/category/category.model';


@Injectable({
  providedIn: 'root',
})
export class CategoryService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/categories';

  getAllCategories() {
    return this.http.get<CategoryDTO[]>(this.resourcePath);
  }

  getCategory(id: number) {
    return this.http.get<CategoryDTO>(this.resourcePath + '/' + id);
  }

  createCategory(categoryDTO: CategoryDTO) {
    return this.http.post<number>(this.resourcePath, categoryDTO);
  }

  updateCategory(id: number, categoryDTO: CategoryDTO) {
    return this.http.put<number>(this.resourcePath + '/' + id, categoryDTO);
  }

  deleteCategory(id: number) {
    return this.http.delete(this.resourcePath + '/' + id);
  }

}
