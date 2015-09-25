package ee.cyber.licensing;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/licenses")
public class LicenseServlet extends HttpServlet {
    private LicenseRepository licenseRepository;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        DataSource dataSource = (DataSource) getServletContext().getAttribute("dataSource");
        licenseRepository = new LicenseRepository(dataSource);
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.getWriter().println("Server got the data from the form :)");
        try {
            List<License> licenseData = licenseRepository.findAll();
            resp.getWriter().write(gson.toJson(licenseData));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (BufferedReader reader = req.getReader()) { //reader pannakse kinni peale try lõppu
            //gsoniga hetkel, aga tulevikus saab teha Jersey'ga
            License license = gson.fromJson(reader, License.class);
            //lisa andmed andmebaasi
            licenseRepository.save(license);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
