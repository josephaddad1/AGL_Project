import { Component, OnInit, Input, Optional, Self, forwardRef, ViewChild } from '@angular/core';
import { Observable } from 'rxjs';
import { FormControl, FormGroup, NgControl, FormBuilder, NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';
import { startWith, debounceTime, map, tap } from 'rxjs/operators';
import { of } from 'rxjs';
import { CustomIdFullName } from 'src/app/interface/custom-id-full-name.interface';
import { MatAutocompleteTrigger } from '@angular/material';

@Component({
  selector: 'app-autocomplete',
  templateUrl: './autocomplete.component.html',
  styleUrls: ['./autocomplete.component.css'],
  // tslint:disable-next-line: whitespace
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => AutocompleteComponent),
      multi: true
    }]
})
export class AutocompleteComponent implements OnInit, ControlValueAccessor {

  onChange: (event) => void;


  @Input() array: CustomIdFullName[] = [];
  @Input() placeholder: string;
  @Input() isLoading: boolean;
  @Input() formControlName: string;
  @Input() form: FormGroup;
  @Input() component: string;
  @Input() clientSide: boolean;

  
  @ViewChild(MatAutocompleteTrigger, { static: true }) autocomplete: MatAutocompleteTrigger;

    @Input() set trigger(val: any) {
      this.autocomplete.closePanel();
    }

  value: any;
  filteredArray: CustomIdFullName[];

  constructor() { }


  writeValue(value: string): void {

  }
  registerOnChange(fn: any): void {
    this.onChange = fn;

  }
  registerOnTouched(fn: any): void {

  }
  setDisabledState?(isDisabled: boolean): void {
  }
  displayfn(object) {
    if (object) {
      return object.fullName;
    }

  }

  ngOnInit() {

  }

  // for client side
  change2(val: string) {
    if(this.clientSide)
    this.filteredArray = this._filter(val);
  }


  // for client side
  _filter(value): CustomIdFullName[] {
    const filterValue = (typeof value === 'string') ? value.toLowerCase().replace(/\s/g, '') : value?value.fullName:null;
  

    if (this.array) {
      return this.array.filter(option => {
        if(filterValue===option.fullName.toLowerCase().replace(/\s/g, '')){          
          this.form.get(this.formControlName).setValue(option);
        }else{
        return option.fullName.toLowerCase().replace(/\s/g, '').includes(filterValue);
        }
      });
    }
  }

  //// local auto complete
  _normalizeValue(value: string): string {
    return value.toLowerCase().replace(/\s/g, '');
  }
}

