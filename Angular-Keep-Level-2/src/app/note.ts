import { Category } from "./category";
import { Reminder } from "./reminder";

export class Note {
    title:string ;
    text:string ;
    id : string;
    state: string;
    updatedOn:string;
    category : Category;
    reminders : Reminder;
    constructor(){
      this.title = '' ;
      this.text = '' ;
      this.state = '' ;
      this.updatedOn='';
      this.category = new Category();
      this.reminders= new Reminder();
    }
}