import { Injectable } from '@angular/core';
import { Reminder } from '../reminder';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { AuthenticationService } from './authentication.service';
import { Observable } from 'rxjs/Observable';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable()
export class ReminderServiceService {

  token: any;
  reminders: Array<Reminder>;
  reminderSubject: BehaviorSubject<Array<Reminder>>;
  private reminderSvcUrl: string;

  constructor(private http: HttpClient,
    private authSvc: AuthenticationService) {
    this.reminderSvcUrl = 'http://localhost:8084/api/v1/reminder/';
    this.reminders = [];
    this.reminderSubject = new BehaviorSubject(this.reminders);
    this.fetchRemindersFromServer();
  }

  fetchRemindersFromServer() {
    this.token = this.authSvc.getBearerToken();
    return this.http.get<Array<Reminder>>(this.reminderSvcUrl, {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${this.token}`)
    }).subscribe(
      remindersArray => {
        this.reminders = remindersArray;
        this.reminderSubject.next(this.reminders);
      },
      err => {
        console.log(err);
      }
      );
  }

  getReminders(): Observable<Array<Reminder>> {
    return this.reminderSubject;
  }

  addReminders(reminder: Reminder): Observable<Reminder> {
    return this.http.post<Reminder>(this.reminderSvcUrl, reminder,
      {
        headers: new HttpHeaders()
          .set('Authorization', `Bearer ${this.token}`)
      }).do(
      addedReminder => {
        this.reminders.push(addedReminder);
        this.reminderSubject.next(this.reminders);
      },
      err => {
        console.log(err);
      }
      );
  }

  getReminderById(reminderId): Reminder {
    const reminder = this.reminders.find(reminder => reminder.reminderId === reminderId);
    return Object.assign({}, reminder);
  }

  editReminder(reminder): Observable<Reminder> {
    return this.http.put<Reminder>(`${this.reminderSvcUrl}${reminder.reminderId}`, reminder
      , {
        headers: new HttpHeaders()
          .set('Authorization', `Bearer ${this.token}`)
      }).do(
      editedReminder => {
        const reminder = this.reminders.find(reminder => reminder.reminderId === editedReminder.reminderId);
        Object.assign(reminder, editedReminder);
        this.reminderSubject.next(this.reminders);
      },
      err => {
        console.log(err);
      }
      );
  }

  deleteReminder(reminder: Reminder): Observable<Reminder> {
    return this.http.delete<Reminder>(`${this.reminderSvcUrl}${reminder.reminderId}`,
      {
        headers: new HttpHeaders()
          .set('Authorization', `Bearer ${this.token}`)
      }).do(
      deletedNote => {
        this.reminders.pop();
        this.reminderSubject.next(this.reminders);
      },
      err => {
        console.log(err);
      }
      );
  }

}
