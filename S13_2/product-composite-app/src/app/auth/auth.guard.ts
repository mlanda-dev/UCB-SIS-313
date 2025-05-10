import { Injectable } from "@angular/core";
import { Router, UrlTree } from "@angular/router";
import { KeycloakAuthGuard } from "keycloak-angular";

import { KeycloakService } from "keycloak-angular";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard extends KeycloakAuthGuard {

  constructor(
    protected override readonly router: Router,
    protected readonly keycloak: KeycloakService
  ) {
    super(router, keycloak);
  }

  async isAccessAllowed(): Promise<boolean | UrlTree> {
    if (!this.authenticated) {
      await this.router.navigate(['/home']);
    }
    return this.authenticated;
  }
}