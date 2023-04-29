# Basic auth demo

## Introduction

The Basic Authentication relies on username and password send in the headers of each and every HTTP request. The header looks like this:

```
Authorization: Basic base64(<username>:<password>)
```

In this schema:

* `base64` is a basic 64 encoding function
* `username` is the name of the user
* `password` is the password of the user

For example if the username is `Aladdin` and the password is `open sesame` the header will be:

```
Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
```

_NOTE_: The username and password are separated with a colon.

Full description is available in [RFC 7617](https://datatracker.ietf.org/doc/html/rfc7617).  

## About the example

* Spring Boot 3.x
* Spring Data 
* Spring Security

The users are stored in a MySQL DB. By default it tries to connect to local host on the standard port 3306.
This can be changed in `application.yaml`. For convenience a docker compose is provided for apple M1 chips in folder `local`.

Since `ddl-auto` is set to `create-drop` few exceptions may be thrown on startup.

Important classes:

* `SecurityConfig` - the spring security configuration. Allows single anonymous endpoint and protects everything else.
* `ApiController` - all endpoints anonymous and protected
* `SecurityApplication` - the application entry point. Annotated with `@EnableMethodSecurity` so that the `@PreAtuhorized` annotation can be used.
* `UserInit` - initializes couple of users - manager and an employee.

Endpoints:

* `/pages/anonymous` - available for anonymous users
* `/pages/all` - available for all logged-in users
* `/pages/managers` - available for managers only
* `/pages/employees` - available for employees and managers

Users:

`manager` - a manager user
`employee` - an employee

Password: topsecret

Examples:

1. Try to access `/pages/managers` without credentials. Not authenticated. 
```
curl -i  http://localhost:8080/pages/managers
...
HTTP/1.1 401 
...
```

2. Try to access `/pages/managers` with correct credentials. Allowed.
```
curl -i --user manager:topsecret http://localhost:8080/pages/managers
HTTP/1.1 200

{"msg":"All Managers"}
```

3. Try to access `/pages/managers` with employee credentials. Forbidden.
```
curl -i --user employee:topsecret http://localhost:8080/pages/managers
...
HTTP/1.1 403

{"timestamp":"2023-04-29T13:40:28.908+00:00","status":403,"error":"Forbidden","path":"/pages/managers"}
...
``` 

4. Try to access `/pages/anonymous ` without  credentials. Allowed.
```
...
curl -i  http://localhost:8080/pages/anonymous  
...
HTTP/1.1 200

{"msg":"All Anonymous"}
...
```

3. Try to access protected endpoint with wrong  credentials. Not authenticated.
```
curl -i --user senko:senko http://localhost:8080/pages/managers
...
HTTP/1.1 401 
...
```
