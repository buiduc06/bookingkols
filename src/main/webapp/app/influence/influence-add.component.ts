import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { InfluenceService } from 'app/influence/influence.service';
import { InfluenceDTO } from 'app/influence/influence.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { validDouble } from 'app/common/utils';


@Component({
  selector: 'app-influence-add',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './influence-add.component.html'
})
export class InfluenceAddComponent implements OnInit {

  influenceService = inject(InfluenceService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  platformsValues?: Map<number,string>;
  categoriesValues?: Map<number,string>;
  industriesValues?: Map<number,string>;

  addForm = new FormGroup({
    name: new FormControl(null, [Validators.required, Validators.maxLength(255)]),
    gender: new FormControl(null),
    height: new FormControl(null, [validDouble]),
    weight: new FormControl(null, [validDouble]),
    costMin: new FormControl(null, [validDouble]),
    costMax: new FormControl(null, [validDouble]),
    profilePicture: new FormControl(null, [Validators.maxLength(255)]),
    introduce: new FormControl(null),
    status: new FormControl(null),
    platforms: new FormControl([]),
    categories: new FormControl([]),
    industries: new FormControl([])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@influence.create.success:Influence was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.influenceService.getPlatformsValues()
        .subscribe({
          next: (data) => this.platformsValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.influenceService.getCategoriesValues()
        .subscribe({
          next: (data) => this.categoriesValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.influenceService.getIndustriesValues()
        .subscribe({
          next: (data) => this.industriesValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new InfluenceDTO(this.addForm.value);
    this.influenceService.createInfluence(data)
        .subscribe({
          next: () => this.router.navigate(['/influences'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
