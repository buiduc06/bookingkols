import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { InfluenceListComponent } from './influence/influence-list.component';
import { InfluenceAddComponent } from './influence/influence-add.component';
import { InfluenceEditComponent } from './influence/influence-edit.component';
import { PlatformListComponent } from './platform/platform-list.component';
import { PlatformAddComponent } from './platform/platform-add.component';
import { PlatformEditComponent } from './platform/platform-edit.component';
import { CategoryListComponent } from './category/category-list.component';
import { CategoryAddComponent } from './category/category-add.component';
import { CategoryEditComponent } from './category/category-edit.component';
import { IndustryListComponent } from './industry/industry-list.component';
import { IndustryAddComponent } from './industry/industry-add.component';
import { IndustryEditComponent } from './industry/industry-edit.component';
import { VideoListComponent } from './video/video-list.component';
import { VideoAddComponent } from './video/video-add.component';
import { VideoEditComponent } from './video/video-edit.component';
import { AdminListComponent } from './admin/admin-list.component';
import { AdminAddComponent } from './admin/admin-add.component';
import { AdminEditComponent } from './admin/admin-edit.component';
import { ErrorComponent } from './error/error.component';


export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    title: $localize`:@@home.index.headline:Welcome to your new app!`
  },
  {
    path: 'influences',
    component: InfluenceListComponent,
    title: $localize`:@@influence.list.headline:Influences`
  },
  {
    path: 'influences/add',
    component: InfluenceAddComponent,
    title: $localize`:@@influence.add.headline:Add Influence`
  },
  {
    path: 'influences/edit/:id',
    component: InfluenceEditComponent,
    title: $localize`:@@influence.edit.headline:Edit Influence`
  },
  {
    path: 'platforms',
    component: PlatformListComponent,
    title: $localize`:@@platform.list.headline:Platforms`
  },
  {
    path: 'platforms/add',
    component: PlatformAddComponent,
    title: $localize`:@@platform.add.headline:Add Platform`
  },
  {
    path: 'platforms/edit/:id',
    component: PlatformEditComponent,
    title: $localize`:@@platform.edit.headline:Edit Platform`
  },
  {
    path: 'categories',
    component: CategoryListComponent,
    title: $localize`:@@category.list.headline:Categories`
  },
  {
    path: 'categories/add',
    component: CategoryAddComponent,
    title: $localize`:@@category.add.headline:Add Category`
  },
  {
    path: 'categories/edit/:id',
    component: CategoryEditComponent,
    title: $localize`:@@category.edit.headline:Edit Category`
  },
  {
    path: 'industries',
    component: IndustryListComponent,
    title: $localize`:@@industry.list.headline:Industries`
  },
  {
    path: 'industries/add',
    component: IndustryAddComponent,
    title: $localize`:@@industry.add.headline:Add Industry`
  },
  {
    path: 'industries/edit/:id',
    component: IndustryEditComponent,
    title: $localize`:@@industry.edit.headline:Edit Industry`
  },
  {
    path: 'videos',
    component: VideoListComponent,
    title: $localize`:@@video.list.headline:Videos`
  },
  {
    path: 'videos/add',
    component: VideoAddComponent,
    title: $localize`:@@video.add.headline:Add Video`
  },
  {
    path: 'videos/edit/:id',
    component: VideoEditComponent,
    title: $localize`:@@video.edit.headline:Edit Video`
  },
  {
    path: 'admins',
    component: AdminListComponent,
    title: $localize`:@@admin.list.headline:Admins`
  },
  {
    path: 'admins/add',
    component: AdminAddComponent,
    title: $localize`:@@admin.add.headline:Add Admin`
  },
  {
    path: 'admins/edit/:id',
    component: AdminEditComponent,
    title: $localize`:@@admin.edit.headline:Edit Admin`
  },
  {
    path: 'error',
    component: ErrorComponent,
    title: $localize`:@@error.headline:Error`
  },
  {
    path: '**',
    component: ErrorComponent,
    title: $localize`:@@notFound.headline:Page not found`
  }
];
