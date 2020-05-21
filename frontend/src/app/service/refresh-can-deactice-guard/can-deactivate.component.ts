import { HostListener } from '@angular/core';
import { FormGroup } from '@angular/forms';

export abstract class ComponentCanDeactivate {

    abstract canDeactivate(): boolean;

    @HostListener('window:beforeunload', ['$event'])
    unloadNotification($event: any) {

        const bool = this.canDeactivate();
        if (!bool) {

            $event.returnValue = true;
        }
    }

}


export abstract class FormCanDeactivate extends ComponentCanDeactivate {

    abstract get form(): FormGroup;
    abstract get formIsSubmitted(): boolean;

    public canDeactivate(): boolean {

        const user = localStorage.getItem('userData');
        // when the refresh token expires userData is cleared from localstorge
        // the user will be redirected to login page so we if the form is dirty
        // or not redirect to login

        if (!user) {
            // TODO save data??!!
            return true;
        }
        return this.formIsSubmitted || !this.form.dirty;

    }
}