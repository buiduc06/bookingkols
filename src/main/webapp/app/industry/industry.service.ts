import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { IndustryDTO } from 'app/industry/industry.model';


@Injectable({
  providedIn: 'root',
})
export class IndustryService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/industries';

  getAllIndustries() {
    return this.http.get<IndustryDTO[]>(this.resourcePath);
  }

  getIndustry(id: number) {
    return this.http.get<IndustryDTO>(this.resourcePath + '/' + id);
  }

  createIndustry(industryDTO: IndustryDTO) {
    return this.http.post<number>(this.resourcePath, industryDTO);
  }

  updateIndustry(id: number, industryDTO: IndustryDTO) {
    return this.http.put<number>(this.resourcePath + '/' + id, industryDTO);
  }

  deleteIndustry(id: number) {
    return this.http.delete(this.resourcePath + '/' + id);
  }

}
