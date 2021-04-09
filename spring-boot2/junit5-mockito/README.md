
Spring Security
- build `mvn clean install -DskipTests=true`
- Just adding security dependency gives a login page. Username is `user` and password is random available from startup console.
- go to http://localhost:8080

Basic Authentication
- username and password are sent as base64 encoded.
- works with spring version 2.4.4 (did not work with 2.1.0)
- see `Config` class

Allowing resources
- see `Config` class

In-memory users
- see `userDetailsService` in `Config` class which created user `riaz/uddin`
- users with roles and permissions

Restrict apis with role-based authentication
- See `antMatchers("/api/**)` in `Config` class

Permission based actions
- can be done in Config class. done here using annotations on controllers.
- see commented `antMatchers` in `Config` class
- To enable via annotations, Add `@EnableGlobalMethodSecurity` in `Config` class, and see `@PreAuthorize` in `StudentManagementController`


Reference
- https://www.youtube.com/watch?v=her_7pa0vrg
