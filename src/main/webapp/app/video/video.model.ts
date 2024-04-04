export class VideoDTO {

  constructor(data:Partial<VideoDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  fileName?: string|null;
  fileType?: string|null;
  fileSize?: number|null;
  filePath?: string|null;
  influence?: number|null;

}
