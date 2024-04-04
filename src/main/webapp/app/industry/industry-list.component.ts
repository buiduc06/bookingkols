import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { IndustryService } from 'app/industry/industry.service';
import { IndustryDTO } from 'app/industry/industry.model';


@Component({
  selector: 'app-industry-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './industry-list.component.html'})
export class IndustryListComponent implements OnInit, OnDestroy {

  industryService = inject(IndustryService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  industries?: IndustryDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@industry.delete.success:Industry was removed successfully.`    };
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
    this.industryService.getAllIndustries()
        .subscribe({
          next: (data) => this.industries = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(id: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.industryService.deleteIndustry(id)
          .subscribe({
            next: () => this.router.navigate(['/industries'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => this.errorHandler.handleServerError(error.error)
          });
    }
  }

}
