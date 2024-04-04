import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { InfluenceService } from 'app/influence/influence.service';
import { InfluenceDTO } from 'app/influence/influence.model';


@Component({
  selector: 'app-influence-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './influence-list.component.html'})
export class InfluenceListComponent implements OnInit, OnDestroy {

  influenceService = inject(InfluenceService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  influences?: InfluenceDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@influence.delete.success:Influence was removed successfully.`,
      'influence.video.influence.referenced': $localize`:@@influence.video.influence.referenced:This entity is still referenced by Video ${details?.id} via field Influence.`
    };
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
    this.influenceService.getAllInfluences()
        .subscribe({
          next: (data) => this.influences = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(id: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.influenceService.deleteInfluence(id)
          .subscribe({
            next: () => this.router.navigate(['/influences'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => {
              if (error.error?.code === 'REFERENCED') {
                const messageParts = error.error.message.split(',');
                this.router.navigate(['/influences'], {
                  state: {
                    msgError: this.getMessage(messageParts[0], { id: messageParts[1] })
                  }
                });
                return;
              }
              this.errorHandler.handleServerError(error.error)
            }
          });
    }
  }

}
