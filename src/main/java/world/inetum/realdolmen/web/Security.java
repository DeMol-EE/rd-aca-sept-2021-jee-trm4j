package world.inetum.realdolmen.web;

import javax.annotation.sql.DataSourceDefinition;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.Set;

@ApplicationScoped
@FormAuthenticationMechanismDefinition(loginToContinue = @LoginToContinue(
        loginPage = "/login.html",
        errorPage = "/login-error.html",
        useForwardToLogin = true
))
@DataSourceDefinition(
        name = "java:app/jdbc/trm4j",
        className = "com.mysql.cj.jdbc.Driver",
        url = "jdbc:mysql://trm4j_db:3306/trm4j",
        user = "acaddemicts",
        password = "acaddemicts",
        properties = {
                "useSSL=false",
                "allowPublicKeyRetrieval=true",
        }
)
public class Security implements IdentityStore {
    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential c = (UsernamePasswordCredential) credential;
            if (c.compareTo("Maarten", "Thijs")) {
                return new CredentialValidationResult("Maarten Thijs", Set.of("MANAGER"));
            } else if (c.compareTo("Robin", "De Mol")) {
                return new CredentialValidationResult("Robin De Mol", Set.of("CONSULTANT"));
            } else if (c.compareTo("Brecht", "Geeraerts")) {
                return new CredentialValidationResult("Brecht Geeraerts", Set.of("CONSULTANT"));
            } else {
                return CredentialValidationResult.INVALID_RESULT;
            }
        }
        return CredentialValidationResult.NOT_VALIDATED_RESULT;
    }
}
