export class AdminDTO {

  constructor(data:Partial<AdminDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  name?: string|null;
  email?: string|null;
  password?: string|null;
  status?: string|null;

}
