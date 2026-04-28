import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ApiService } from '../services/api.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.scss'],
})
export class DialogComponent implements OnInit {
  actionBtn = 'Save';
  statusList = ['on reading', 'finished reading', 'not yet read'];
  bookForm!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private api: ApiService,
    private dialogRef: MatDialogRef<DialogComponent>,
    @Inject(MAT_DIALOG_DATA) public editData: any
  ) {}

  ngOnInit(): void {
    this.bookForm = this.formBuilder.group({
      bookName: ['', Validators.required],
      isbn: ['', Validators.required],
      topic: ['', Validators.required],
      publicationDate: ['', Validators.required],
      status: ['', Validators.required],
      price: ['', Validators.required],
      comment: ['', Validators.required],
    });

    if (this.editData) {
      this.actionBtn = 'Update';
      this.bookForm.controls['bookName'].setValue(this.editData.bookName);
      this.bookForm.controls['isbn'].setValue(this.editData.isbn);
      this.bookForm.controls['topic'].setValue(this.editData.topic);
      this.bookForm.controls['publicationDate'].setValue(
        this.editData.publicationDate
      );
      this.bookForm.controls['status'].setValue(this.editData.status);
      this.bookForm.controls['price'].setValue(this.editData.price);
      this.bookForm.controls['comment'].setValue(this.editData.comment);
    }
  }

  addBook() {
    if (!this.editData) {
      if (this.bookForm.valid) {
        this.api.postBook(this.bookForm.value).subscribe({
          next: (response) => {
            alert('Book added successfully');
            this.bookForm.reset();
            this.dialogRef.close('save');
          },
          error: () => {
            alert('Error while adding the book');
          },
        });
      }
    } else {
      this.updateBook();
    }
  }

  updateBook() {
    this.api.putBook(this.bookForm.value, this.editData.bookId).subscribe({
      next: (response) => {
        alert('Book updated successfully');
        this.bookForm.reset();
        this.dialogRef.close('update');
      },
      error: (error) => {
        alert('Error while updating the book');
      },
    });
  }
}
