package ee.cyber.licensing.setup;

import ee.cyber.licensing.dao.AuthorisedUserRepository;
import ee.cyber.licensing.dao.ContactRepository;
import ee.cyber.licensing.dao.CustomerRepository;
import ee.cyber.licensing.dao.DeliveredReleaseRepository;
import ee.cyber.licensing.dao.EventRepository;
import ee.cyber.licensing.dao.FileRepository;
import ee.cyber.licensing.dao.LicenseRepository;
import ee.cyber.licensing.dao.ProductRepository;
import ee.cyber.licensing.dao.ReleaseRepository;
import ee.cyber.licensing.service.MailService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class RepositoryAndServiceBinder extends AbstractBinder {

    @Override
    protected void configure() {
        try {
            bind(LicenseRepository.class).to(LicenseRepository.class);
            bind(ProductRepository.class).to(ProductRepository.class);
            bind(CustomerRepository.class).to(CustomerRepository.class);
            bind(AuthorisedUserRepository.class).to(AuthorisedUserRepository.class);
            bind(EventRepository.class).to(EventRepository.class);
            bind(ReleaseRepository.class).to(ReleaseRepository.class);
            bind(FileRepository.class).to(FileRepository.class);
            bind(ContactRepository.class).to(ContactRepository.class);
            bind(DeliveredReleaseRepository.class).to(DeliveredReleaseRepository.class);
            bind(MailService.class).to(MailService.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
