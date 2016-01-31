import {Component, OnInit} from 'angular2/core';
import {HTTP_PROVIDERS}    from 'angular2/http';
import {Floor, Elevator} from './data';
import {Service} from './service';
@Component({
    selector: 'my-app',
    templateUrl: "template/app.html",
    styleUrls: ["css/app.css"],
    providers: [HTTP_PROVIDERS, Service]
})
export class AppComponent implements OnInit {
    public title = 'Elevator System';
    public floors: Floor[];
    public elevators: Elevator[];
    private timerId: number;

    constructor(private _service: Service) { }

    getFloors() {
        this._service.getFloors().subscribe(
            floors => this.floors = floors,
            error => alert(`Server error. Try again later`));
    }

    getElevators() {
        this._service.getElevators().subscribe(
            elevators => this.elevators = elevators,
            error => window.clearInterval(this.timerId));
    }

    ngOnInit() {
        var self = this;
        this.getFloors();
        this.timerId = window.setInterval(()=>self.getElevators(), 1000);
    }

    onSelect(floor: Floor) {
        this._service.updateFloor(floor)
    }
}
