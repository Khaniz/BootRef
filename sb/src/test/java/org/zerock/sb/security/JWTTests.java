package org.zerock.sb.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.sb.security.util.JWTUtil;

@SpringBootTest
@Log4j2
public class JWTTests {

    @Autowired
    JWTUtil jwtUtil;

    @Test
    public void testGenerate(){

        String jwtStr = jwtUtil.generateToken("user11");

        log.info(jwtStr);
    }

    @Test
    public void testValidate(){

        String str = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMTEiLCJpYXQiOjE2MzQ4NzAyMjksImV4cCI6MTYzNDg3MDIyOX0.h4Z85-Ol_7WQMdJAwHQE8Zqh8HgYucC0MGQtrN0y5HA";


        try{
            jwtUtil.validateToken(str);
        }catch (ExpiredJwtException ex){
            log.error("expired........................");

            log.error(ex.getMessage());
        }
        catch (JwtException ex){
            log.error(ex.getMessage());
        }



    }

}
