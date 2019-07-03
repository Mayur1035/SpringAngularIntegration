import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CatergoryViewComponent } from './catergory-view.component';

describe('CatergoryViewComponent', () => {
  let component: CatergoryViewComponent;
  let fixture: ComponentFixture<CatergoryViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CatergoryViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CatergoryViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
