package muyinatech.myjaxrs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import muyinatech.myjaxrs.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/customers")
public class CustomerResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerResource.class);

    private Map<Integer, Customer> customerDB = new ConcurrentHashMap<Integer, Customer>();
    private AtomicInteger idCounter = new AtomicInteger();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCustomer(InputStream inputStream) throws IOException {
        Customer customer = readCustomer(inputStream);
        customer.setId(idCounter.incrementAndGet());
        customerDB.put(customer.getId(), customer);
        LOGGER.info("Created customer - " + customer.getId());
        return Response.created(URI.create("/customers/" + customer.getId())).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(@PathParam("id") int id) {
        final Customer customer = customerDB.get(id);
        if (customer == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        StreamingOutput streamingOutput = new StreamingOutput() {
            public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(outputStream, customer);
            }
        };
        return Response.ok().entity(streamingOutput).build();
    }

    private Customer readCustomer(InputStream inputStream) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(inputStream, Customer.class);
    }

}
