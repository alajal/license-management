package ee.cyber.licensing.api.exceptions;

import org.h2.jdbc.JdbcSQLException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JdbcSQLExceptionMapper implements ExceptionMapper<JdbcSQLException> {
    @Override
    public Response toResponse(JdbcSQLException exception) {
        return Response.status(500).
                entity(exception.getMessage()).
                type("text/plain").
                build();
    }
}
