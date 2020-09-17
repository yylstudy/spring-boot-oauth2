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
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ5eWwiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiY3VzdG9tIjoidGVzdCIsImV4cCI6MTYwMDMxNDM1NCwiYXV0aG9yaXRpZXMiOlsidXNlcjp0ZXN0IiwidXNlcjphZGQiXSwianRpIjoiNjI5OTMxMmUtMWFlNS00YzM0LWI5NmYtMmI3OTZhMTNmMjU2IiwiY2xpZW50X2lkIjoiY2xpZW50MSJ9.6vjFCSepUdz5EYZQBUeqOGjOJjDQNrNyvOXjIe8-tUA";
        Map<String, Claim> verifyToken2 = JwtTokenUtil.verifyToken(token);
        Map myUser = verifyToken2.get("userinfo").asMap();
        System.out.println(myUser);
        System.out.println(myUser.get("phone"));
    }
}
