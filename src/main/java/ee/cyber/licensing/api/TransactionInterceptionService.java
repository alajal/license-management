package ee.cyber.licensing.api;

import org.aopalliance.intercept.ConstructorInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.InterceptionService;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Collections;
import java.util.List;

public class TransactionInterceptionService implements InterceptionService {

    @Inject
    private Connection conn;

    private final MethodInterceptor methodInterceptor = new MethodInterceptor() {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            try {
                conn.setAutoCommit(false);
                Object proceed = invocation.proceed();
                conn.commit();
                return proceed;
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    };

    @Override
    public Filter getDescriptorFilter() {
        return (Descriptor d) -> {
            String implementation = d.getImplementation();
            return implementation != null && implementation.startsWith("ee.cyber.licensing.api");
        };
    }

    @Override
    public List<MethodInterceptor> getMethodInterceptors(Method method) {
        GET get = method.getAnnotation(GET.class);
        POST post = method.getAnnotation(POST.class);
        DELETE delete = method.getAnnotation(DELETE.class);
        PUT put = method.getAnnotation(PUT.class);

        if (get == null && post == null && delete == null && put == null) {
            return null;
        }
        return Collections.singletonList(methodInterceptor);
    }

    @Override
    public List<ConstructorInterceptor> getConstructorInterceptors(Constructor<?> constructor) {
        return null;
    }
}
