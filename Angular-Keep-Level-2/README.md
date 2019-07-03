# Keep Assignment - Level 2

**Aim**: The objective of this level in the assignment is to cover the following areas.

- Component Design
- Component Interaction
- Authentication & Security
- Routing & Guards
- Testing

**Description:**

**The Keep application demands following things to be covered:**
- Authentication: Authentication using Login View as discussed during the live sessions. The authentication api is already been created and can be cloned from [Authentication Server](https://gitlab-cts.stackroute.in/Ravi.Chandran/Authentication-Server). (Authentication, Routing & Guards)
- Notes View: A collection of notes.
- List View: A collection of notes but classified into three lists
	- Not Started
	- Started
	- Completed
	***Hint***: The notes may need an additional property to classify them into lists. 
- Edit Note View: Both the NotesView and the ListView should be able to edit notes and should be able to update the list and notes view. (Component Design & Component Interactions)
- Note Taker Component: Both the NotesView and the ListView should have a common NoteTakerComponent present in the DashboardComponent. (Named Router Outlets, Sharing Data Using Services)
- Unit Test Cases for all the Components and Services.
- E2E Test Cases for the frontend application.

**Problem Instructions:**

You need to use certain specifications while developing this application as shared below (please make sure to use exactly the same names as highlighted) and your structure shall be like shown in the image [structure.png ](/structure.png) in this repository and server folder shall be as shown here [structure_server.png ](/structure_server.png). (You need to merge the package.json of the Authentication Server with the root directory's package.json)

1. `Note` class to be found at `/src/app/note.ts` and created for Note model with properties `id` (Number), `title` (string), `text` (string) and `state` (string), exported and used in the other components/services wherever required.
2. `AppModule` as the root module which includes components - `AppComponent, HeaderComponent, NoteComponent, LoginComponent, DashboardComponent, NoteViewComponent, ListViewComponent, EditNoteViewComponent, EditNoteOpenerComponent, NoteTakerComponent`, services - `NotesService, AuthenticationService, RouterService` and a route guard - `CanActivateRouteGuard`
3. `AuthenticationService` shall be at `src/app/services/authentication.service` with methods -  
   a. `authenticateUser()` to authorize a user with Authentication Server
   INPUT - JSON, eg `{
    					userName: 'admin',
    					password: 'password'
  					  }`  
   URL - `http://localhost:3000/auth/v1`  
   METHOD - `POST`  
   RETURN TYPE - `Observable<HttpResponse>` (returns the token)  
   b. `setBearerToken()` to save user token in local storage with key `bearerToken`,  
   INPUT - string (token)  
   RETURN TYPE - void  
   c. `getBearerToken()` to fetch the user token from local storage, returns the token itself  
   INPUT - no params  
   RETURN TYPE - string (token)  
   d. `isUserAuthenticated()` to validate user authentication from backend based on a token  
   INPUT - string (token)  
   URL - `http://localhost:3000/au.th/v1/isAuthenticated`  
   METHOD - `POST`  
   RETURN TYPE - `Promise<boolean>`  
4. `NotesService` shall be at `src/app/services/notes.service` with two properties -> `notes` which is array of all the updated notes and `notesSubject` which is a BehaviourSubject of array of all updated notes and helps to emit changes to this array across subscribers, the service has methods as below -  
	a. `fetchNotesFromServer()` to fetch notes from backend and update the `notes` and `notesSubject` likewise in the service  
		INPUT - no params  
		URL - `http://localhost:3000/api/v1/notes`  
		METHOD - `GET`  
		RETURN TYPE - void  
	b. `getNotes()` to return the `notesSubject` of the service  
		INPUT - no params  
		RETURN TYPE - `BehaviorSubject<Array<Note>>`  
	c. `addNote()` to persist the new note created with server and update the `notes` and `notesSubject` of the service  
		INPUT - Note  
		URL - `http://localhost:3000/api/v1/notes`  
		METHOD - `POST`  
		RETURN TYPE - `Observable<Note>`  
	d. `editNote()` to persist the updated note with server and update the `notes` and `notesSubject` of the service  
		INPUT - Note  
		URL - `http://localhost:3000/api/v1/notes/${note.id}`  
		METHOD - `PUT`  
		RETURN TYPE - `Observable<Note>`  
	e. `getNoteById()` to fetch the note matching provided Id from `notes` collection of the service  
		INPUT - Number (noteId)  
		RETURN TYPE - Note  
5. `RouterService` to navigate to various routes of the application as given below and shall be at `src/app/services/router.service` with methods -  
	a. `routeToDashboard()` to navigate to dashboard with the note view (`/dashboard/view/noteview`) or list view (`/dashboard/view/listview`) whichever is default (for our implementation, keep the note view as default)  
		INPUT - no params  
	b. `routeToLogin()` to navigate to login view (`/login`)  
		INPUT - no params  
	c. `routeToEditNoteView()` to navigate to edit view of the note of given noteId (`/dashboard/(view/noteview//noteEditOutlet:note/${noteId}/edit)`)  
		INPUT - Number (noteId)  
	d. `routeBack()` to navigate back to previous page  
		INPUT - no params  
6. `AppComponent` at `src/app/app.component` selector `app-root` which renders the HeaderComponent and has a RouterOutlet to render LoginComponent or DashboardComponent based on route.
7. `HeaderComponent` at `src/app/header/header.component` selector `app-header` with a Material toolbar and two icons with class names `switchToListView` and `switchToNoteView` on click of which the views are switched accordingly, the component class to have a property `isNoteView` which is set based on the route rendered and likewise hides one of the icons from the template.
8. `LoginComponent` at `src/app/login/login.component` selector `app-login` has a form in its template with two input fields with names `username` and `password` and a `Submit` button on click of which component method `loginSubmit()` is called which gets the form values, validates user authenticity via AuthenticationService, on success saves the token in local storage via AuthenticationService and navigates the user to Dashboard view, else on error sets the error message in a property `submitMessage` on the component class and displays same as text of a paragraph/div/label element with class `error-message` in the template.
9. `DashboardComponent` at `src/app/dashboard/dashboard.component` selector `app-dashboard` upon initialization after successful authentication sets the notes collection of NotesService with data from backend by calling notesService.fetchNotesFromServer(), it renders NoteTakerComponent and has two RouterOutlets, the first unnamed RouterOutlet displays the NoteViewComponent or ListViewComponent based on primary route and the second named RouterOutlet with name `noteEditOutlet` renders the EditViewComponent based on the secondary route.
10. `NoteTakerComponent` at `src/app/note-taker/note-taker.component` selector `app-note-taker` has Material Expansion Panel in the template with two form fields, input with name `title` for note title and textarea with name `text` for note text and a `Done` button. On clicking the button notesService.addNote() is called. In case of any server error, error message is set as text content of a label marked with class `error-message` and in case value is not provided for title/text, the label says `Title and text both are required fields`.
11. `NoteViewComponent` at `src/app/note-view/note-view.component` selector `app-note-view` which displays the collection of all the notes. The component class is expected to have a `notes` property which gets populated with the updated list of notes by subscribing to the notesSubject of notesService via notesService.getNotes(). Each note is displayed as a separate NoteComponent and the list is wrapped inside a parent div with class `keep-c-note-container`.
12. `NoteComponent` at `src/app/note/note.component` selector `app-note` to display a single note as a Material Card, with card title set to note title and card content set to note text. On click of this card, user is navigated to Edit View of the same note.
13. `ListViewComponent` at `src/app/list-view/list-view.component` selector `app-list-view` which displays all the notes but classified into three lists represented by three properties in the component class as - `notStartedNotes`, `startedNotes`, `completedNotes` based on the `state` property of Note objects `not-started`, `started`, `completed` respectively. The three lists are filtered based on the complete set of updated notes received by subscribing to the notesSubject of notesService via notesService.getNotes(). Each list to contain Material cards representing every note and the list itself to be wrapped inside a div with class `keep-c-list-container`
14. `EditNoteOpenerComponent` at `src/app/edit-note-opener/edit-note-opener.component` selector `app-edit-note-opener` which gets created when application is routed to Edit View. This component extracts noteId from query parameter and passed same as data while opening EditNoteViewComponent in a Material Dialog.
15. `EditNoteViewComponent` at `src/app/edit-note-view/edit-note-view.component` selector `app-edit-note-view` which enables editing of a note by fetching the entire note using notesService.getNoteById() based on noteId provided in the data while opening this component in a Material Dialog and displaying the note properties in various form fields which can be edited. Use input form field with name `editTitle` for note's title, textarea form field with name `editText` for note's text, select form field with name `editStatus` and three options as not-started, started, completed of which the state of current note is selected. It has a `Save` button at the bottom on click of which updated values shall be persisted via notesService.editNote() and the dialog is closed. Whenever the dialog is closed, previous route is rendered by calling routerService.routeBack(). In case of any server error, the template has a label with class `error-message`, the text of which is set with the error object's message property.
16. `CanActivateRouteGuard` at `src/app/can-activate-route.guard` to protect the Dashboard route against unauthentic access. It shall implement CanActivate and works based on the result of authenticationService.isUserAuthenticated(), i.e. in case user is authenticated it returns true, else it navigates the user to Login view.
17. Please make sure the commands `npm install`, `ng build`, `ng serve` (to start the frontend), `npm run serve` (to start the backend) runs directly from the root directory `Angular-Keep-Level-2` 

**Submitting your solution for preliminary automated review**  
1. Open `https://hobbes-cts.stackroute.in/#/` and login into the platform  
2. Under `Assignment repository` select `Angular-Keep-Level-2`, and branch `master`
3. Under `Your solution repository` select your own repository and branch
4. Press `Submit`
5. Press `click here` for the feedback
6. Evaluation will take couple of minutes to complete after which you need to refresh your browser and get the updated status
7. Watch out for your total score and detailed status on each test in the coloured blocks on the screen
8. Fix failing test cases and re-submit your solution