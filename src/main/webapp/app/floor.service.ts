import {Floor} from './floor';
import {FLOORS} from './mock-data';
import {Injectable} from 'angular2/core';
import {Http, RequestOptions, Headers}       from 'angular2/http';
import {Observable} from 'rxjs/Rx';

@Injectable()
export class FloorService {
    constructor (private http: Http) {}

    getFloors() {
        return this.http.get("api/floors")
            .map(res => <Floor[]> res.json())
            .catch(this.logAndPassOn);
    }

    update(floor: Floor){
        var headers = new Headers();
        headers.append('Content-Type', 'application/json');
        this.http.put("api/floors", JSON.stringify(floor), {headers : headers});
    }

    private logAndPassOn (error: Error) {
        // in a real world app, we may send the server to some remote logging infrastructure
        // instead of just logging it to the console
        console.error(error);
        return Observable.throw(error);
    }
}
