import { browser, by, element } from 'protractor';
describe ('e2e test suite',() => {
  it ('when page is loaded user is redirected to login ', () => {
		 browser.get('/');
		 expect (browser.getCurrentUrl()).toContain('login');
		 browser.sleep(3000);
	});
	// browser.sleep(1000);
  it ('when user logs in,notes view is shown ', () => {
     const inputElements = element.all(by.css('input'));
     inputElements.get(0).sendKeys('admin');
     inputElements.get(1).sendKeys('password');
     element(by.css('button')).click();
     browser.sleep(3000);
     expect (browser.getCurrentUrl()).toContain('/dashboard/view/noteview');
	});
	// browser.sleep(1000);
  it ('user is able to get the notes at the page from server ', () => {
     browser.sleep(3000);
     const notes = element.all(by.css('mat-card'));
     console.log(notes);
		 expect (notes.count()).toBe(notes.count());
		 browser.sleep(3000);
	});
	// browser.sleep(1000);
  it ('Should be able to take new note',() => {
     const panel = element(by.css('mat-expansion-panel-header'));
		 panel.click();
		 let inputElements = element.all(by.css('input'));
		 inputElements.get(0).sendKeys('test note 1');
		 inputElements = element.all(by.css('textarea'));
		 inputElements.get(0).sendKeys('test text');
		 inputElements =  element.all(by.css('mat-select'));
		 inputElements.get(0).sendKeys('Started');
		 browser.sleep(3000);
		 element(by.css('button')).click();
		 const notes = element.all(by.css('mat-card'));
		 expect (notes.count()).toBe(notes.count());
		 browser.sleep(3000);
	});
	// browser.sleep(1000);
  it ( 'added notes should remain on the page when browser is refreshed',() => {
		 const notes = element.all(by.css('mat-card'));
		 expect ( notes.count()).toBe( notes.count());
		 browser.sleep(3000);
  });
});