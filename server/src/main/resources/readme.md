//请求获取token
http://localhost:8086/oauth/authorize?client_id=client1&response_type=code&redirect_uri=http://localhost:8085/redirect

//获取返回的token
{"access_token":"291dd83a-4328-4677-8d9c-1db589285f69","token_type":"bearer",
"refresh_token":"5ac727dd-92e1-49b0-aefb-6ef2343062ae","expires_in":1799,"scope":"read write"}


//带上token访问受限的资源
http://localhost:8085/getProtectedResoure
当访问 noauthmenu请求时及时带上token也会提示无法访问，这个和资源服务器的配置是有关系的
我们只配置了authmenu  

刷新token
http://localhost:8086/oauth/token?grant_type=refresh_token&refresh_token=5ac727dd-92e1-49b0-aefb-6ef2343062ae&client_id=client1&client_secret=client1_secret


基于oauth2的单点登录
http://localhost:8087/sso-client/getPrincipal
http://localhost:8088/sso-client2/getPrincipal

登录密码加密 
PasswordUtil
Jwt的token解密
JwtTokenUtil



