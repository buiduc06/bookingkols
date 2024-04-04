export class IndustryDTO {

  constructor(data:Partial<IndustryDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  name?: string|null;
  icon?: string|null;

}
