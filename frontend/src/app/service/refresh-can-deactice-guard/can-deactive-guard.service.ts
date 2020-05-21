import { Injectable } from '@angular/core';
import { DeliveryInsertScreenComponent } from '../../pages/delivery-insert-screen/delivery-insert-screen.component';
import { CanDeactivate } from '@angular/router';
import { DialogService } from '../../components/dialog-alert/dialog.service';
import { ComponentCanDeactivate } from './can-deactivate.component';

@Injectable()
export class CanDeactivateGuard implements CanDeactivate<ComponentCanDeactivate> {

    constructor(private dialog: DialogService) { }
    canDeactivate = (component: ComponentCanDeactivate): boolean | Promise<boolean> => {
        if (!component.canDeactivate()) {

            return this.dialog.confirm('Attention!!', 'Unsaved Data will be lost! Do you really want to leave this page?')

                .then((confirmed) => confirmed)
                .catch(() => false);

        } else {
            return true;
        }


    }


}

