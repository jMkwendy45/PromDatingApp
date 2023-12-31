package africa.semicolon.promiscuous.utils;

import africa.semicolon.promiscuous.exception.PromiscuousException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static africa.semicolon.promiscuous.utils.JwtUtils.generateVerificationToken;

public class AppUtils {

    public    static  final String APP_NAME="promisusos inc";
    public    static  final String WELCOME_MAIL_SUBJECT="promisusos inc";
    public static final String APP_EMAIL="noreply@promiscious.org";
    public static final String BLANK_SPACE =  " ";
    public static final String EMPTY_STRING = "";

    private static final String MAIL_TEMPLATE_LOCATION= "C:\\\\Users\\\\USER\\\\IdeaProjects\\\\SpringProjects\\\\promiscuous\\\\src\\\\main\\\\resources\\\\templates\\\\index.html";
    private static final String ACTIVATE_ACCOUNT_PATH = "/user/activate?code=";
    public static final String JSON_PATCH_PATH_PREFIX = "/";
    public  static  final  String TEST_EMAIL_LOCATION ="C:\\Users\\USER\\IdeaProjects\\SpringProjects\\promiscuous\\src\\test\\java\\africa\\semicolon\\promiscuous\\resources\\images\\2.jpg";

    public static  String generateActivationLink( String baseUrl,String email){
        String token = generateVerificationToken(email);
        String activationLink = baseUrl+ACTIVATE_ACCOUNT_PATH+token;
        return activationLink;
    }
//    public static String generateToken( String email){
//     String token =   JWT.create()
//                .withClaim("user",email)
//             .withIssuer(APP_NAME)
//        .withExpiresAt(Instant.now().plusSeconds(3600))
//                .sign(Algorithm.HMAC512("secret"));
//
//        return token;
//
//    }
    public static String getMailTemplate(){
        Path  templateLocation = Paths.get(MAIL_TEMPLATE_LOCATION);

         try {
             List<String>fileContents = Files.readAllLines(templateLocation);
                String template = String.join("", fileContents);
                return template;
         }catch (IOException exception){
             throw new PromiscuousException(exception.getMessage());
         }


    }

    public static boolean matches(String first,String second){
        return first.equals(second);
    }






//    public static boolean validateToken(String token){
//        JWTVerifier verifier = JWT.require(Algorithm.HMAC512("secret"))
//                .withIssuer(APP_NAME)
//                .withClaimPresence("user")
//                .build();
//
//        return verifier.verify(token).getClaim("user") != null;
//    }
//
//    public static String extractEmailFrom(String token){
//        var claim = JWT.decode(token).getClaim("user");
//        return (String) claim.asMap().get("user");
//    }
public static List<String>getpublicPath(){
    return List.of("/api/v1/user", "/login");
}
}
