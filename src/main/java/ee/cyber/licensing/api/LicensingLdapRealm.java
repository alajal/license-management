package ee.cyber.licensing.api;

import javax.naming.NamingException;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.ldap.JndiLdapRealm;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LicensingLdapRealm extends JndiLdapRealm {
    private static final Logger LOG = LoggerFactory
            .getLogger(LicensingLdapRealm.class);

    protected AuthorizationInfo queryForAuthorizationInfo(PrincipalCollection principals,
            LdapContextFactory ldapContextFactory) throws NamingException {
        SimpleAuthorizationInfo sInfo = new SimpleAuthorizationInfo();

        //USERNAME
        Object username = principals.asSet().iterator().next().toString();

        //TODO Add roles to users here:
        if(username.equals("someUser")){
            sInfo.addRole("licensingManager");
//            sInfo.addRole("releaseEngineer");
        }

        return sInfo;
    }

}
