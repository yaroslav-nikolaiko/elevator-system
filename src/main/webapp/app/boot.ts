import {bootstrap}    from 'angular2/platform/browser'
import {AppComponent} from './app.component'
import {provide} from 'angular2/core';

bootstrap(AppComponent, [provide(Window, {useValue: window})]);

