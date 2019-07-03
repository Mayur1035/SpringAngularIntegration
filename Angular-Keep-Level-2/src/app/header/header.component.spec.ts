import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MatToolbarModule } from '@angular/material/toolbar';
import { HeaderComponent } from './header.component';
import { By } from '@angular/platform-browser';

describe('Testing HeaderComponent', () => {
  let component: HeaderComponent;
  let fixture: ComponentFixture<HeaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [HeaderComponent],
      imports: [MatToolbarModule]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderComponent);
    component = fixture.componentInstance;
    // fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have keep as text content of Mat toolbar', () => {
    let de = fixture.debugElement.query(By.css('mat-toolbar'));
    expect(de).toBeTruthy();
    const dec = de.query(By.css('keep-span'));
    expect(dec.nativeElement.textContent).toBe('Keep Application');
  });
});
