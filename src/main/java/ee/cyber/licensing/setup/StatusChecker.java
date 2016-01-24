package ee.cyber.licensing.setup;

/* Class for checking license status and changing it automatically if necessary, after every 12 hours. */

import ee.cyber.licensing.dao.LicenseRepository;
import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.entity.State;
import ee.cyber.licensing.service.MailService;
import org.glassfish.jersey.process.internal.RequestScope;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

import javax.inject.Inject;
import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StatusChecker implements ApplicationEventListener {

    @Inject
    private RequestScope requestScope;

    @Inject
    private LicenseRepository licenseRepository;

    @Inject
    private Connection connection;

    @Inject
    private MailService mailService;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private void checkAndChangeStatus() {
        scheduler.scheduleAtFixedRate(this::runChecker, 1, 12, TimeUnit.HOURS);
    }

    private void runChecker() {
        requestScope.runInScope(() -> {
            try {
                runInTransaction(this::updateStatus);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void runInTransaction(RunnableWithExceptions r) throws Exception {
        try {
            connection.setAutoCommit(false);
            r.run();
            connection.commit();
        } catch (Exception ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private void updateStatus() throws SQLException, IOException, MessagingException {
        List<License> licenses = licenseRepository.findAll();
        for (License license : licenses) {
            State currentState = license.getState();
            getLicenseWithNewState(license);
            State newState = license.getState();
            if (currentState != newState) {
                licenseRepository.updateLicense(license);
            }
        }
    }

    private void getLicenseWithNewState(License license) throws IOException, MessagingException {
        Date validFrom = license.getValidFrom();
        Date validTill = license.getValidTill();

        if (validFrom != null && validTill != null) {
            Instant now = Instant.now();
            Instant lastValid = validTill.toInstant();
            Instant lastActive = lastValid.minus(2, ChronoUnit.DAYS);
            if (now.isBefore(lastActive)) {
                license.setState(State.ACTIVE);
            }
            if (now.isAfter(lastActive) && now.isBefore(lastValid)) {
                license.setState(State.EXPIRATION_NEARING);
                //send mail to Admin notifying about soon expiring license
                mailService.sendExpirationNearingMail(license);
            }
            if (now.isAfter(lastValid)) {
                license.setState(State.TERMINATED);
            }
        }

    }

    @Override
    public void onEvent(ApplicationEvent event) {
        switch (event.getType()) {
            case INITIALIZATION_FINISHED:
                checkAndChangeStatus();
                break;
            case DESTROY_FINISHED:
                scheduler.shutdown();
                break;
        }
    }

    @Override
    public RequestEventListener onRequest(RequestEvent requestEvent) {
        return null;
    }

    @FunctionalInterface
    interface RunnableWithExceptions {
        void run() throws Exception;
    }
}
