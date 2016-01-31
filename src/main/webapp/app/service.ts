import {Floor, Elevator} from './data';
import {Injectable} from 'angular2/core';
import {Http, RequestOptions, Headers} from 'angular2/http';
import {Observable} from 'rxjs/Rx';


@Injectable()
export class Service {
    private floorUrl = "api/floors";
    private elevatorUrl = "api/elevators";
    constructor (private http: Http) {}

    getFloors() {
        return this.http.get(this.floorUrl)
            .map(res => <Floor[]> res.json())
            .catch(this.logAndPassOn);
    }

    updateFloor(floor: Floor){
        var headers = new Headers();
        headers.append('Content-Type', 'application/json');
        this.http.put(this.floorUrl, JSON.stringify(floor), {headers : headers})
            .catch(this.logAndPassOn)
            .subscribe(()=>console.log("Level "+floor.level+" updated"));
    }

    getElevators() {
        return this.http.get(this.elevatorUrl)
            .map(res => <Elevator[]> res.json())
            .catch(this.logAndPassOn);
    }

    private logAndPassOn (error: Error) {
        console.error(error);
        return Observable.throw(error);
    }
}