import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { VideoDTO } from 'app/video/video.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class VideoService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/videos';

  getAllVideos() {
    return this.http.get<VideoDTO[]>(this.resourcePath);
  }

  getVideo(id: number) {
    return this.http.get<VideoDTO>(this.resourcePath + '/' + id);
  }

  createVideo(videoDTO: VideoDTO) {
    return this.http.post<number>(this.resourcePath, videoDTO);
  }

  updateVideo(id: number, videoDTO: VideoDTO) {
    return this.http.put<number>(this.resourcePath + '/' + id, videoDTO);
  }

  deleteVideo(id: number) {
    return this.http.delete(this.resourcePath + '/' + id);
  }

  getInfluenceValues() {
    return this.http.get<Record<string,string>>(this.resourcePath + '/influenceValues')
        .pipe(map(transformRecordToMap));
  }

}
