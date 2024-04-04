import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { InfluenceDTO } from 'app/influence/influence.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class InfluenceService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/influences';

  getAllInfluences() {
    return this.http.get<InfluenceDTO[]>(this.resourcePath);
  }

  getInfluence(id: number) {
    return this.http.get<InfluenceDTO>(this.resourcePath + '/' + id);
  }

  createInfluence(influenceDTO: InfluenceDTO) {
    return this.http.post<number>(this.resourcePath, influenceDTO);
  }

  updateInfluence(id: number, influenceDTO: InfluenceDTO) {
    return this.http.put<number>(this.resourcePath + '/' + id, influenceDTO);
  }

  deleteInfluence(id: number) {
    return this.http.delete(this.resourcePath + '/' + id);
  }

  getPlatformsValues() {
    return this.http.get<Record<string,string>>(this.resourcePath + '/platformsValues')
        .pipe(map(transformRecordToMap));
  }

  getCategoriesValues() {
    return this.http.get<Record<string,string>>(this.resourcePath + '/categoriesValues')
        .pipe(map(transformRecordToMap));
  }

  getIndustriesValues() {
    return this.http.get<Record<string,string>>(this.resourcePath + '/industriesValues')
        .pipe(map(transformRecordToMap));
  }

}
