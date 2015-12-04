package ee.cyber.licensing.dao;


import ee.cyber.licensing.entity.Event;
import ee.cyber.licensing.entity.License;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {

    @Inject
    private DataSource ds;

    @Inject
    private LicenseRepository licenseRepository;

    public Event save(Event ev) throws SQLException {
        try (Connection conn = ds.getConnection()) {

            if(ev.getLicense() == null) {
              PreparedStatement stmnt = conn.prepareStatement("INSERT INTO Event (name, description, type, dateCreated) VALUES (?, ?, ?, GETDATE())");

              stmnt.setString(1, ev.getName());
              stmnt.setString(2, ev.getDescription());
              stmnt.setString(3, ev.getType());
              stmnt.execute();

              try (ResultSet generatedKeys = stmnt.getGeneratedKeys()) {
                  if (generatedKeys.next()) {
                      ev.setId(generatedKeys.getInt(1));
                  }
              }
            }
            else {
              PreparedStatement stmnt = conn.prepareStatement("INSERT INTO Event (licenseId, name, description, type, dateCreated) VALUES (?, ?, ?, ?, GETDATE())");

              stmnt.setInt(1, ev.getLicense().getId());
              stmnt.setString(2, ev.getName());
              stmnt.setString(3, ev.getDescription());
              stmnt.setString(4, ev.getType());
              stmnt.execute();

              try (ResultSet generatedKeys = stmnt.getGeneratedKeys()) {
                  if (generatedKeys.next()) {
                      ev.setId(generatedKeys.getInt(1));
                  }
              }
            }


        }
        return ev;
    }

    public List<Event> findAll() throws SQLException {
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM Event")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<Event> events = new ArrayList<>();
                    while (resultSet.next()) {
                      int licenseId = resultSet.getInt("licenseId");

                      if(licenseId == 0) {
                        License license = null;
                        Event ev = getEvent(resultSet, null);
                        events.add(ev);
                      }
                      else {
                        License license = licenseRepository.findById(licenseId);
                        Event ev = getEvent(resultSet, license);
                        events.add(ev);
                      }
                    }
                    return events;
                }
            }
        }
    }

    public Event findById(Integer licenseId, Integer id) throws SQLException {
        List<Event> events = findAll();
        for(Event event : events){
            if(event.getId().equals(id)){
                return event;
            }
        }
        throw new IllegalArgumentException("No suitable event found");
    }

    private Event getEvent(ResultSet rs, License license) throws SQLException {
        if(license == null) {
          return new Event(
                  rs.getInt("id"),
                  rs.getString("name"),
                  rs.getString("description"),
                  rs.getString("type"),
                  rs.getDate("dateCreated")
          );
        }

        return new Event(
                rs.getInt("id"),
                license,
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("type"),
                rs.getDate("dateCreated")
        );
    }

    public Event save(Event ev, int licenseId) throws SQLException {
        if(licenseId == 0) {
          License license = null;
          ev.setLicense(license);
        }
        else {
          License license = licenseRepository.findById(licenseId);
          ev.setLicense(license);
        }

        return save(ev);
    }
}
