import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {User} from "../model/user.model";

@Injectable({ providedIn: 'root' })
export class UserService {
    constructor(private http: HttpClient) { }
    baseUrl: string = 'http://localhost:8080/users/';

    getAll() {
        return this.http.get<any[]>(this.baseUrl);
    }

    register(user: User){
        return this.http.post<User>(this.baseUrl + 'register/', user);
    }

    delete(id) {
        return this.http.delete(`${config.apiUrl}/users/${id}`);
    }

    findByUsername(username : string) {
        return this.http.get(`${config.apiUrl}/users/${username}`);
    }

    findById(id : number) {
        return this.http.get(`${config.apiUrl}/users/${id}`);
    }
}
