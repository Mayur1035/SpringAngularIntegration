import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryEditOpenerComponent } from './category-edit-opener.component';

describe('CategoryEditOpenerComponent', () => {
  let component: CategoryEditOpenerComponent;
  let fixture: ComponentFixture<CategoryEditOpenerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CategoryEditOpenerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CategoryEditOpenerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
