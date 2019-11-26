#请求获取token
http://127.0.0.1:8086/oauth/authorize?client_id=client1&response_type=code&redirect_uri=http://127.0.0.1:8085/redirect
#带上token访问受限的资源，不建议url带token方式，这里为了方便
#当访问oauth2-server的noauthmenu请求时及时带上token也会提示无法访问，这个和资源服务器的配置是有关系的，我们只配置了authmenu  
http://127.0.0.1:8085/getProtectedResoureFromOauth2Server?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ5eWwiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNTc0NzU1ODAxLCJhdXRob3JpdGllcyI6WyJ1c2VyOnRlc3QiLCJ1c2VyOmFkZCJdLCJqdGkiOiIxOTZhZDJmYy00OGYyLTRmNTgtODM4ZC1kYzg2MGVhYWE2OWUiLCJ1c2VyaW5mbyI6eyJwYXNzd29yZCI6bnVsbCwidXNlcm5hbWUiOiJ5eWwiLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoidXNlcjphZGQifSx7ImF1dGhvcml0eSI6InVzZXI6dGVzdCJ9XSwiYWNjb3VudE5vbkV4cGlyZWQiOnRydWUsImFjY291bnROb25Mb2NrZWQiOnRydWUsImNyZWRlbnRpYWxzTm9uRXhwaXJlZCI6dHJ1ZSwiZW5hYmxlZCI6dHJ1ZSwiaWQiOiIxIiwicGhvbmUiOiIxNTI1NTE3ODU1MyJ9LCJjbGllbnRfaWQiOiJjbGllbnQxIn0.kRFk3yK63BGdJkNiql5GT8-Sbrot7RDDNrS9vkn-PJg
#刷新token
http://127.0.0.1:8086/oauth/token?grant_type=refresh_token&refresh_token=5ac727dd-92e1-49b0-aefb-6ef2343062ae&client_id=client1&client_secret=client1_secret


#第三方登录：在实现从第三方登录时，出现一个bug，请求时使用localhost从第三方登录且第三方回调成功后，访问
#本地受保护的资源时，使用localhost还是现实未登录状态，使用127.0.0.1可以，未解决
#从uaoth2-server服务端登录
http://127.0.0.1:8086/oauth/authorize?client_id=third_party&response_type=code&redirect_uri=http://127.0.0.1:8085/redirectAndLogin
#从github登录
https://github.com/login/oauth/authorize?client_id=08f507a1cfcf70f74eaa&scope=user,public_repo
#判断是否从第三方登录成功
http://127.0.0.1:8085/getProtectedResoure

#基于oauth2的单点登录
http://127.0.0.1:8087/sso-client/getPrincipal
http://127.0.0.1:8088/sso-client2/getPrincipal

#登录密码加密 
PasswordUtil
#Jwt的token解密
JwtTokenUtil



