export class CategoryDTO {

  constructor(data:Partial<CategoryDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  name?: string|null;
  icon?: string|null;

}
