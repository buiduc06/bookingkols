import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { VideoService } from 'app/video/video.service';
import { VideoDTO } from 'app/video/video.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-video-add',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './video-add.component.html'
})
export class VideoAddComponent implements OnInit {

  videoService = inject(VideoService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  influenceValues?: Map<number,string>;

  addForm = new FormGroup({
    fileName: new FormControl(null, [Validators.required, Validators.maxLength(255)]),
    fileType: new FormControl(null, [Validators.maxLength(255)]),
    fileSize: new FormControl(null),
    filePath: new FormControl(null, [Validators.maxLength(255)]),
    influence: new FormControl(null)
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@video.create.success:Video was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.videoService.getInfluenceValues()
        .subscribe({
          next: (data) => this.influenceValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new VideoDTO(this.addForm.value);
    this.videoService.createVideo(data)
        .subscribe({
          next: () => this.router.navigate(['/videos'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
