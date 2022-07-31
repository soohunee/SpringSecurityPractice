# SpringSecurityPractice

**Security with JSON Web Token**
1. Client --> POST /login, username/password --> Application
2. Authentication process (verifies credentials are correct)
3. Client <-- Json Web Token(JWT) <-- Application
4. Client(JWT) --> GET /users, JWT --> Application
5. Application checks JWT if it is valid or it has permission to access

**Samples**
![](../../../../var/folders/t3/cysgcp6x3wd6rjr0b136hd5c0000gn/T/TemporaryItems/NSIRD_screencaptureui_zfyWmY/Screen Shot 2022-07-31 at 6.28.23 PM.png)

accessToken : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTExIiwicm9sZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvbG9naW4iLCJleHAiOjE2NTkyNjAxNDN9.sQx7Oiu8tjfobvwBTsjkGA-2MVD2mn-M8MKf4qI8Y4I
![](../../../../var/folders/t3/cysgcp6x3wd6rjr0b136hd5c0000gn/T/TemporaryItems/NSIRD_screencaptureui_cT5abx/Screen Shot 2022-07-31 at 6.29.18 PM.png)

refreshToken : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTExIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2xvZ2luIiwiZXhwIjoxNjU5MjYxMzQzfQ.iuYj8NwGWzBRgmizD8eJG8StAjPi-_wd3d1AmP4A0Ds
![](../../../../var/folders/t3/cysgcp6x3wd6rjr0b136hd5c0000gn/T/TemporaryItems/NSIRD_screencaptureui_Z2kFek/Screen Shot 2022-07-31 at 6.29.30 PM.png)


**Scenario**
1. Login
2. Get accessToken, refreshToken
3. Front-end developer will save tokens somewhere on the client
4. When I need to access any resource, i will send accessToken
5. When accessToken expires, front-end will look for the refreshToken
6. Send another request immediately with refreshToken (same authorization header starting with "Bearer ")