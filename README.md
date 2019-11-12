# Web service et authentification

## Situation

On imagine une situation avec un web service gérant les fêtes et les maîtres de ces fêtes.

En situation normale, seule la connexion à travers une appli intermédiaire (frontend) est possible, mais on ne peut pas l'assurer niveau réseau. C'est une situation courante où le frontend possède une connexion avec des hauts privilèges sur le web service et doit lui même filtrer les requêtes des utilisateurs.

En situation nominae le web service est non authentifié et il donc en pratique accessible quand même par un utilisateur, ce qui  bypass complètement les sécurités potentiellement mise en place par le frontend.

![archi](https://raw.githubusercontent.com/coding-dojo-sndip/pumpkin-authentication/master/archi.PNG)

Au cours de ce conding dojo nous nous focalisons sur le web service.

## Première authentification basique

Objectif : rajouter l'authentification basique sur le service à travers un filtre. A titre d'exercice, nous choississons de n'utiliser aucune librairie spécifique pour gérer le basic mais d'utiliser uniquement java core. pour limiter les dépendances à un environnement extérieur l'identifiant/mdp basic à vérifier est stocké en dur sur le serveur.

Résultat : les opérations de PATCH sont maintenant sécurisé avec Basic. Dans la pratique cela signifie que ces identifiants sont renseigné dans le fontend, ainsi seule le frontend peut effectuer ces opérations. c'est alors à la charge du frontend d'accepter ou non les demande de patch selon ce qu'il considère être légitime. Il doit donc gérer lui même son authentification, le web service n'a aucune idée et ne pas contrôler l'authentification utilisée par le frontend pour accepter les requêtes.

## Authentification avancée avec Keycloak

Objectif : écrire un nouveau filtre qui effectuera l'authentification avec Keycloak. On suppose donc que l'application frontend authentifie l'utilisateur avec Keycloak, donc récupère un jeton et le transfère au web service.

Résultat : ici aussi le web service sécurise les opérations PATCH mais en plus cette fois ci le web service a la maîtrise complète de l'authentification. Si le frontend est "percé", ça ne remet pas en cause la sécurité du backend.

### Eléments de contexte

- Keycloak installé en local sur 8181  : `standalone.bat -Djboss.socket.binding.port-offset=101`
- Realm configuré avec client dont la redirect uri autorisée est `urn:ietf:wg:oauth:2.0:oob`

Pour obtenir un jeton :
- Simulation de la redirection du frontend vers Keycloak :
  - http://localhost:8181/auth/realms/pumpkin/protocol/openid-connect/auth?client_id=client-test-web&redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=code&scope=openid
  - Le code affiché correspond au code à usage unique retransféré par Keycloak au frontend
- Simulation de la requête du frontend à keyckloak pour récupérer le jeton :
  ```
  POST http://localhost:8181/auth/realms/pumpkin/protocol/openid-connect/token
  grant_type:authorization_code
  code:<code fourni par keycloak>
  client_id:client-test-web
  redirect_uri:urn:ietf:wg:oauth:2.0:oob
  ```
  - Dans le json en retour choisir le champ `access_token`
- Envoi de la requête avec authentification :
  - Ajouter l'entête `Authorization: Bearer ` + le contenu de champ `access_token` précedemment récupéré
