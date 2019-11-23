package com.yyl.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Map;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/22 16:35
 * @Version: 1.0
 */
public class JwtTokenUtil {
    public static String SECRET = "yyl";


    public static Map<String, Claim> verifyToken(String token) throws Exception{
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt = null;
        jwt = verifier.verify(token);
        return jwt.getClaims();
    }



    public static void main(String[] args) throws Exception {
        //过期测试
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ5eWwiLCJzY29wZSI6WyJhbGwiXSwiZXhwIjoxNTc0NDE3NTc2LCJhdXRob3JpdGllcyI6WyJ1c2VyOnRlc3QiLCJ1c2VyOmFkZCJdLCJqdGkiOiJhZjAwM2VkNi0zMTBjLTQ5MTAtYjZkYy01MjI0MDFhMTU2ZDMiLCJ1c2VyaW5mbyI6eyJwYXNzd29yZCI6bnVsbCwidXNlcm5hbWUiOiJ5eWwiLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoidXNlcjphZGQifSx7ImF1dGhvcml0eSI6InVzZXI6dGVzdCJ9XSwiYWNjb3VudE5vbkV4cGlyZWQiOnRydWUsImFjY291bnROb25Mb2NrZWQiOnRydWUsImNyZWRlbnRpYWxzTm9uRXhwaXJlZCI6dHJ1ZSwiZW5hYmxlZCI6dHJ1ZSwiaWQiOiIxIiwicGhvbmUiOiIxNTI1NTE3ODU1MyJ9LCJjbGllbnRfaWQiOiJzc28tY2xpZW50MiJ9.gUk_4bnZuPW3kenSm5AYeKsTSoHHQ5-vtvIkcW6ZZ1g";
        Map<String, Claim> verifyToken2 = JwtTokenUtil.verifyToken(token);
        Map myUser = verifyToken2.get("userinfo").asMap();
        System.out.println(myUser);
        System.out.println(myUser.get("phone"));
    }
}
