package ee.cyber.licensing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/application")
public class ApplicationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("Server got the data from the form :)");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (BufferedReader reader = req.getReader()) { //reader pannakse kinni peale try lõppu
            //gsoniga hetkel, aga tulevikus saab teha Jersey'ga
            Gson gson = new Gson();
            Application application = gson.fromJson(reader, Application.class);
            //lisa andmed andmebaasi
            System.out.println(application.organization);
            resp.setStatus(HttpServletResponse.SC_OK);
        }

    }

}
