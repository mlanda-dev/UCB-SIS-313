import { KeycloakService } from "keycloak-angular";
import { environment } from '@env/environment';

export function initializeKeycloak(keycloak: KeycloakService) {

    return () =>
        keycloak.init({
            config: {
                url: environment.keycloak.url,
                realm: environment.keycloak.realm,
                clientId: environment.keycloak.clientId,
            },
            initOptions: {                
                onLoad: 'login-required',
            }
        });
}