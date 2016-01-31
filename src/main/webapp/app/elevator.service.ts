import {Elevator} from './elevator';
import {ELEVATORS} from './mock-data';
import {Injectable} from 'angular2/core';
import {Http}       from 'angular2/http';
import {Observable} from 'rxjs/Rx';
import 'rxjs/Rx';
@Injectable()
export class ElevatorService {
    constructor (private http: Http) {}

    getElevators() {
        return this.http.get("api/elevators")
            .map(res => <Elevator[]> res.json())
            .catch(this.logAndPassOn);
    }

    private logAndPassOn (error: Error) {
        // in a real world app, we may send the server to some remote logging infrastructure
        // instead of just logging it to the console
        console.error(error);
        return Observable.throw(error);
    }
}