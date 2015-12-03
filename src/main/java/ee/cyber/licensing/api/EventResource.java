package ee.cyber.licensing.api;
import ee.cyber.licensing.dao.EventRepository;
import ee.cyber.licensing.entity.Event;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("events")
public class EventResource {

    @Inject
    private EventRepository eventRepository;

    @GET
    @Produces("application/json")
    public List<Event> getEvents() throws Exception {
        return eventRepository.findAll();
    }

    @POST
    @Path("/{licenseId}")
    public Event saveEvent(@PathParam("licenseId") int licenseId, Event ev) throws Exception {
        return eventRepository.save(ev, licenseId);
    }
}
