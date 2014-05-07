package muyinatech.myjaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("simple")
public class SimpleResource {

    @GET
    @Produces("text/plain")
    public String getMessage() {
        return "Simple message";
    }
}
