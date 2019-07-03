import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryTakerComponent } from './category-taker.component';

describe('CategoryTakerComponent', () => {
  let component: CategoryTakerComponent;
  let fixture: ComponentFixture<CategoryTakerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CategoryTakerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CategoryTakerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
