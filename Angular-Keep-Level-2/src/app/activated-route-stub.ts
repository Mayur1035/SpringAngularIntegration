import { ReplaySubject } from 'rxjs/ReplaySubject';
import { convertToParamMap, ParamMap, Params } from '@angular/router';

export class ActivatedRouteStub {
    
    private subject = new ReplaySubject<ParamMap>();

    constructor(initialParams: Params) {
        this.setParamMap(initialParams);
    }

    readonly paramMap = this.subject.asObservable();

    setParamMap(params: Params) {
        this.subject.next(convertToParamMap(params));
    }

    getsnapshot() {
        return {params: this.paramMap};
    }
}