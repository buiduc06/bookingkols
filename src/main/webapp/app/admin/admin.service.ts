import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { AdminDTO } from 'app/admin/admin.model';


@Injectable({
  providedIn: 'root',
})
export class AdminService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/admins';

  getAllAdmins() {
    return this.http.get<AdminDTO[]>(this.resourcePath);
  }

  getAdmin(id: number) {
    return this.http.get<AdminDTO>(this.resourcePath + '/' + id);
  }

  createAdmin(adminDTO: AdminDTO) {
    return this.http.post<number>(this.resourcePath, adminDTO);
  }

  updateAdmin(id: number, adminDTO: AdminDTO) {
    return this.http.put<number>(this.resourcePath + '/' + id, adminDTO);
  }

  deleteAdmin(id: number) {
    return this.http.delete(this.resourcePath + '/' + id);
  }

}
