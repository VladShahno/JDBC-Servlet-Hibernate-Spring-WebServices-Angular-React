import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http'
import {Observable} from 'rxjs';
import {User} from '../user-models/user';
import {UserForCreate} from "../user-models/user-for-create";
import {Router} from "@angular/router";

export interface TokenResponse {
  user: string;
  token: string;
  role: string;
}

export interface LoginRequest {
  login: string,
  password: string
}

export function getAuthorizationHeaders() {
  return {'Authorization': `Bearer ${getToken()}`};
}

export function getToken() {
  return window.localStorage.getItem('jwtToken');
}

@Injectable({
  providedIn: 'root'
})
export class UserService {

  problem!: Map<string, string>

  private baseURL = "http://localhost:8080/api";
  jwtToken = window.localStorage.getItem('jwtToken');

  constructor(private httpClient: HttpClient, private router: Router) {
  }

  getUsersList(): Observable<User[]> {
    return this.httpClient.get<User[]>(`${this.baseURL}/users/all`, {
      headers: getAuthorizationHeaders()
    });
  }

  createUser(user: UserForCreate): Observable<Object> {
    return this.httpClient.post(`${this.baseURL}/users`, user, {
      headers: getAuthorizationHeaders()
    });
  }

  getUserByLogin(login: string): Observable<UserForCreate> {
    return this.httpClient.get<UserForCreate>(`${this.baseURL}/users/${login}`, {
      headers: getAuthorizationHeaders()
    });
  }

  updateUser(login: string, user: UserForCreate): Observable<Object> {
    return this.httpClient.put(`${this.baseURL}/users/update${login}`, user, {
      headers: getAuthorizationHeaders()
    });
  }

  deleteUser(login: string): Observable<Object> {
    return this.httpClient.delete(`${this.baseURL}/users/${login}`, {
      headers: getAuthorizationHeaders()
    });
  }

  loginUser(loginRequest: LoginRequest): Observable<TokenResponse> {
    return this.httpClient.post<TokenResponse>(`${this.baseURL}/login`, loginRequest);
  }

  logoutUser() {
    localStorage.clear();
  }

  loggedIn() {
    return !!localStorage.getItem('jwtToken')
  }
}
