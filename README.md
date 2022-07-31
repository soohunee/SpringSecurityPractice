# SpringSecurityPractice

**Security with JSON Web Token**
1. Client --> POST /login, username/password --> Application
2. Authentication process (verifies credentials are correct)
3. Client <-- Json Web Token(JWT) <-- Application
4. Client(JWT) --> GET /users, JWT --> Application
5. Application checks JWT if it is valid or it has permission to access

**Samples**
<img width="937" alt="Screen Shot 2022-07-31 at 8 02 38 PM" src="https://user-images.githubusercontent.com/76514241/182023079-533f8842-179c-41a8-9471-bbd0a193c627.png">

accessToken : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTExIiwicm9sZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvbG9naW4iLCJleHAiOjE2NTkyNjAxNDN9.sQx7Oiu8tjfobvwBTsjkGA-2MVD2mn-M8MKf4qI8Y4I

<img width="711" alt="Screen Shot 2022-07-31 at 8 02 46 PM" src="https://user-images.githubusercontent.com/76514241/182023090-79fabd9a-e860-408f-9557-559a3c155c2a.png">

refreshToken : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTExIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2xvZ2luIiwiZXhwIjoxNjU5MjYxMzQzfQ.iuYj8NwGWzBRgmizD8eJG8StAjPi-_wd3d1AmP4A0Ds

<img width="716" alt="Screen Shot 2022-07-31 at 8 02 50 PM" src="https://user-images.githubusercontent.com/76514241/182023093-904f828a-0657-46ed-9690-1d7c96ebca2a.png">



**Scenario**
1. Login
2. Get accessToken, refreshToken
3. Front-end developer will save tokens somewhere on the client
4. When I need to access any resource, i will send accessToken
5. When accessToken expires, front-end will look for the refreshToken
6. Send another request immediately with refreshToken (same authorization header starting with "Bearer ")
