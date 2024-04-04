import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { VideoService } from 'app/video/video.service';
import { VideoDTO } from 'app/video/video.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-video-edit',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './video-edit.component.html'
})
export class VideoEditComponent implements OnInit {

  videoService = inject(VideoService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  influenceValues?: Map<number,string>;
  currentId?: number;

  editForm = new FormGroup({
    id: new FormControl({ value: null, disabled: true }),
    fileName: new FormControl(null, [Validators.required, Validators.maxLength(255)]),
    fileType: new FormControl(null, [Validators.maxLength(255)]),
    fileSize: new FormControl(null),
    filePath: new FormControl(null, [Validators.maxLength(255)]),
    influence: new FormControl(null)
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@video.update.success:Video was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentId = +this.route.snapshot.params['id'];
    this.videoService.getInfluenceValues()
        .subscribe({
          next: (data) => this.influenceValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.videoService.getVideo(this.currentId!)
        .subscribe({
          next: (data) => updateForm(this.editForm, data),
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.editForm.markAllAsTouched();
    if (!this.editForm.valid) {
      return;
    }
    const data = new VideoDTO(this.editForm.value);
    this.videoService.updateVideo(this.currentId!, data)
        .subscribe({
          next: () => this.router.navigate(['/videos'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
