import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { AdminService } from 'app/admin/admin.service';
import { AdminDTO } from 'app/admin/admin.model';


@Component({
  selector: 'app-admin-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './admin-list.component.html'})
export class AdminListComponent implements OnInit, OnDestroy {

  adminService = inject(AdminService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  admins?: AdminDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@admin.delete.success:Admin was removed successfully.`    };
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
    this.adminService.getAllAdmins()
        .subscribe({
          next: (data) => this.admins = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(id: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.adminService.deleteAdmin(id)
          .subscribe({
            next: () => this.router.navigate(['/admins'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => this.errorHandler.handleServerError(error.error)
          });
    }
  }

}