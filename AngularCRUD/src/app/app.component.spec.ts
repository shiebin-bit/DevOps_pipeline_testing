import { TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { of } from 'rxjs';
import { AppComponent } from './app.component';
import { MatDialog } from '@angular/material/dialog';
import { ApiService } from './services/api.service';

describe('AppComponent', () => {
  const apiServiceMock = {
    getBooks: () => of([]),
    deleteBook: () => of({}),
  };

  const matDialogMock = {
    open: () => ({
      afterClosed: () => of(null),
    }),
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AppComponent],
      providers: [
        { provide: ApiService, useValue: apiServiceMock },
        { provide: MatDialog, useValue: matDialogMock },
      ],
      schemas: [NO_ERRORS_SCHEMA],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should define the book table columns', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.displayedColumns).toContain('bookName');
    expect(app.displayedColumns).toContain('action');
  });
});
