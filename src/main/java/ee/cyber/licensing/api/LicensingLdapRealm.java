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

        //TODO Create IniRealmRolePermissionSolver to add permissions to roles

        //TODO Use tags for authorization checks: @RequiresPermissions("perm"), @RequiresRoles("role")
        //(@RequiresPermissions is preferred)

        //Can add roles like this:
//        if(username.equals("user123")){
//            sInfo.addRole("someRole");
//        }

        return sInfo;
    }

}
