import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CatergoryEditorComponent } from './catergory-editor.component';

describe('CatergoryEditorComponent', () => {
  let component: CatergoryEditorComponent;
  let fixture: ComponentFixture<CatergoryEditorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CatergoryEditorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CatergoryEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
