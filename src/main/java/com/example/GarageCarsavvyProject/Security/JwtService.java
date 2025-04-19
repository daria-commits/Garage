package com.example.GarageCarsavvyProject.Security;

import com.example.GarageCarsavvyProject.Model.Jwt;
import com.example.GarageCarsavvyProject.Model.Utilisateur;
import com.example.GarageCarsavvyProject.Repository.JwtRepository;
import com.example.GarageCarsavvyProject.Service.UtilisateurService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Transactional
@Service
public class JwtService {
    public String secret;
    public static final String BEARER = "bearer";
    private JwtRepository jwtRepository;
    private UtilisateurService utilisateurService;


    public JwtService(@Value("${jwt.secret}") String secret, UtilisateurService utilisateurService, JwtRepository jwtRepository) {
        this.secret = secret;
        this.utilisateurService = utilisateurService;
        this.jwtRepository = jwtRepository;

    }
    public Jwt tokenByValue(String value) {
        return (Jwt) this.jwtRepository.findByValeur(value)
                .orElseThrow(() -> new RuntimeException("Token inconnu"));
    }

    public Map<String, String> generate (String username){
        Utilisateur utilisateur = this.utilisateurService.loadUserByUsername(username);
        final Map<String, String>  jwtMap = this.generateJwt(utilisateur);
        final Jwt jwt = new com.example.GarageCarsavvyProject.Model.Jwt();
        jwt.setValeur(jwtMap.get(BEARER));
        jwt.setDesactive(false);
        jwt.setExpire(false);
        jwt.setUtilisateur(utilisateur);
        this.jwtRepository.save(jwt);
        return jwtMap;

    }

    public String extractUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = this.getClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    private <T> T getClaim(String token, Function<Claims, T> function){
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }



    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }



    private Date getExpirationDateFromToken(String token) {
        return  this.getClaim(token, Claims::getExpiration);
    }

    private Map<String, String> generateJwt(Utilisateur utilisateur) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 30*60*1000;
        final  Map<String, Object> claimes = Map.of(
                "nom", utilisateur.getNom(),
                "email", utilisateur.getEmail(),
                Claims.EXPIRATION,new Date(expirationTime),
                Claims.SUBJECT,utilisateur.getEmail()
        );
        final String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(utilisateur.getEmail())
                .setClaims(claimes)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

        return Map.of(BEARER,bearer);
    }

    private Key getKey(){

        final byte[] decoder = Decoders.BASE64.decode(secret);

        return Keys.hmacShaKeyFor(decoder);
    }


    public void deconnexion() {
        Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jwt jwt = this.jwtRepository.findUtilisateurValidToken(
           utilisateur.getEmail(),
              false ,
              false
        ).orElseThrow(() -> new RuntimeException("Token invalide"));
        jwt.setExpire(true);
       this.jwtRepository.save(jwt);

    }


}
