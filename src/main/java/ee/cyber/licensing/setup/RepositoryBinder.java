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
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class RepositoryBinder extends AbstractBinder {
    //hk2 teeb objekte ja injectib ka
    //bindimine: kui on vaja klassi(to osa), siis tee see objekt (bindi sees)
    //There are to types of DataSources: tomcat's and sql's. We use both. This is the reason for long field names.
    //Pool'i loomine
    @Override
    protected void configure() {
        try {
            //injectitav objekt ->bind()
            //SIIN SAAB INJECTIDA JA LUUA INJECTITAVAT
            //bind(new LicenseRepository(dataSource));    //newga ei saa hk2 injectida
            bind(LicenseRepository.class).to(LicenseRepository.class);
            bind(ProductRepository.class).to(ProductRepository.class);
            bind(CustomerRepository.class).to(CustomerRepository.class);
            bind(AuthorisedUserRepository.class).to(AuthorisedUserRepository.class);
            bind(EventRepository.class).to(EventRepository.class);
            bind(ReleaseRepository.class).to(ReleaseRepository.class);
            bind(FileRepository.class).to(FileRepository.class);
            bind(ContactRepository.class).to(ContactRepository.class);
            bind(DeliveredReleaseRepository.class).to(DeliveredReleaseRepository.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
