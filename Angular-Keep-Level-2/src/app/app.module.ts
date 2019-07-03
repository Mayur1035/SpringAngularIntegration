import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule , ReactiveFormsModule } from '@angular/forms';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { MatListModule } from '@angular/material/list';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatTabsModule } from '@angular/material/tabs';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatMenuModule} from '@angular/material/menu';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { MatDialogRef } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes, ActivatedRoute } from '@angular/router';

import { NotesService } from './services/notes.service';
import { AuthenticationService } from './services/authentication.service';
import { RouterService } from './services/router.service';
import { UserAuthService } from './services/user-auth.service';
import { ReminderServiceService} from './services/reminder-service.service';
import { CategoriesService} from './services/categories.service';

import { CanActivateRouteGuard } from './can-activate-route.guard';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { EditNoteOpenerComponent } from './edit-note-opener/edit-note-opener.component';
import { EditNoteViewComponent } from './edit-note-view/edit-note-view.component';
import { NoteTakerComponent } from './note-taker/note-taker.component';
import { NoteViewComponent } from './note-view/note-view.component';
import { ListViewComponent } from './list-view/list-view.component';
import { NoteComponent } from './note/note.component';
import { NewUserComponent } from './new-user/new-user.component';
import { RegisterSuccessComponent } from './register-success/register-success.component';
import { NoteGridComponent } from './note-grid/note-grid.component';
import { ReminderSectionComponent } from './reminder-section/reminder-section.component';
import { CategorySectionComponent } from './category-section/category-section.component';
import { CategoryTakerComponent } from './category-section/category-taker/category-taker.component';
import { CatergoryViewComponent } from './category-section/catergory-view/catergory-view.component';
import { CatergoryEditorComponent } from './category-section/catergory-editor/catergory-editor.component';
import { CategoryEditOpenerComponent } from './category-section/category-edit-opener/category-edit-opener.component';
import { ReminderEditOpenerComponent } from './reminder-section/reminder-edit-opener/reminder-edit-opener.component';
import { ReminderTakerComponent } from './reminder-section/reminder-taker/reminder-taker.component';
import { ReminderEditorComponent } from './reminder-section/reminder-editor/reminder-editor.component';
import { ReminderViewComponent } from './reminder-section/reminder-view/reminder-view.component';
import { CategoryComponent } from './category-section/category/category.component';
import { ReminderComponent } from './reminder-section/reminder/reminder.component';

const appRoutes: Routes = [
  { path: 'login', component: LoginComponent, data: { page: 'login' } },
  { path: 'new-user', component: NewUserComponent, data: { page: 'register' } },
  {
    path: 'dashboard',
    component: DashboardComponent,
    data: { page: 'dashboard' },
    canActivate: [CanActivateRouteGuard],
    children: [
      {
        path: 'view/noteview', component: NoteViewComponent
      },
      {
        path: 'view/listview', component: ListViewComponent
      },
      {
        path: 'note/:noteid/edit', component: EditNoteOpenerComponent,
        outlet: 'noteEditOutlet'
      },
      {
        path: '', pathMatch: 'full', redirectTo: 'view/noteview'
      }
    ]
  },
  {
    path: 'category',
    component: CategorySectionComponent,
    data: { page: 'category' },
    canActivate: [CanActivateRouteGuard],
    children: [
      {
        path: 'view/categoryview', component: CatergoryViewComponent
      },
      {
        path: 'category/:categoryid/edit', component: CategoryEditOpenerComponent,
        outlet: 'categoryEditOutlet'
      },
      {
        path: '', pathMatch: 'full', redirectTo: 'view/categoryview'
      }
    ]
  },
  {
    path: 'reminder',
    component: ReminderSectionComponent,
    data: { page: 'reminder' },
    canActivate: [CanActivateRouteGuard],
    children: [
      {
        path: '', pathMatch: 'full', redirectTo: 'view/reminderview'
      },
      {
        path: 'view/reminderview', component: ReminderViewComponent
      },
      {
        path: 'reminder/:reminderid/edit', component: ReminderEditOpenerComponent,
        outlet: 'reminderEditOutlet'
      }
    ]
  },
  { path: '', pathMatch: 'full',  redirectTo: '/login' },
  { path: '**', pathMatch: 'full',  redirectTo: '/login' }
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    DashboardComponent,
    EditNoteOpenerComponent,
    EditNoteViewComponent,
    NoteTakerComponent,
    NoteViewComponent,
    ListViewComponent,
    NoteComponent,
    NewUserComponent,
    RegisterSuccessComponent,
    NoteGridComponent,
    ReminderSectionComponent,
    CategorySectionComponent,
    CategoryTakerComponent,
    CatergoryViewComponent,
    CatergoryEditorComponent,
    CategoryEditOpenerComponent,
    ReminderEditOpenerComponent,
    ReminderTakerComponent,
    ReminderEditorComponent,
    ReminderViewComponent,
    CategoryComponent,
    ReminderComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule ,
    MatToolbarModule,
    MatCardModule,
    MatMenuModule,
    MatExpansionModule,
    MatDatepickerModule,
    MatTooltipModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDialogModule,
    MatSnackBarModule,
    MatIconModule,
    MatSelectModule,
    MatListModule,
    MatGridListModule,
    MatTabsModule,
    BrowserAnimationsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [NotesService, AuthenticationService, RouterService, CanActivateRouteGuard, UserAuthService , ReminderServiceService, CategoriesService],
  bootstrap: [AppComponent],
  entryComponents: [EditNoteViewComponent,CatergoryEditorComponent, ReminderEditorComponent]
})
export class AppModule { }
