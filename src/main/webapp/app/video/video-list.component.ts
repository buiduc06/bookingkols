import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { VideoService } from 'app/video/video.service';
import { VideoDTO } from 'app/video/video.model';


@Component({
  selector: 'app-video-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './video-list.component.html'})
export class VideoListComponent implements OnInit, OnDestroy {

  videoService = inject(VideoService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  videos?: VideoDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@video.delete.success:Video was removed successfully.`    };
    return messages[key];
  }

  ngOnInit() {
    this.loadData();
    this.navigationSubscription = this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.loadData();
      }
    });
  }

  ngOnDestroy() {
    this.navigationSubscription!.unsubscribe();
  }
  
  loadData() {
    this.videoService.getAllVideos()
        .subscribe({
          next: (data) => this.videos = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(id: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.videoService.deleteVideo(id)
          .subscribe({
            next: () => this.router.navigate(['/videos'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => this.errorHandler.handleServerError(error.error)
          });
    }
  }

}
