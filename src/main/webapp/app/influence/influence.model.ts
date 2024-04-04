export class InfluenceDTO {

  constructor(data:Partial<InfluenceDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  name?: string|null;
  gender?: string|null;
  height?: number|null;
  weight?: number|null;
  costMin?: number|null;
  costMax?: number|null;
  profilePicture?: string|null;
  introduce?: string|null;
  status?: string|null;
  platforms?: number[]|null;
  categories?: number[]|null;
  industries?: number[]|null;

}
