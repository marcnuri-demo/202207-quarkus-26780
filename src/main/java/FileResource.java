import org.jboss.resteasy.reactive.RestResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.CacheControl;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Objects;

@Path("/")
public class FileResource {

  private static final String FALLBACK_RESOURCE = "/not-found.txt";

  @GET
  @Path("/")
  public RestResponse<InputStream> getDefaultRoot() throws IOException {
    return getStaticFile("index.html");
  }
  @GET
  @Path("/{fileName:.+}")
  public RestResponse<InputStream> getStaticFile(@PathParam("fileName") String fileName) throws IOException {
    final InputStream requestedFileStream = FileResource.class.getResourceAsStream("/static/" + fileName);
    final InputStream inputStream;
    inputStream = Objects.requireNonNullElseGet(requestedFileStream, () ->
      FileResource.class.getResourceAsStream(FALLBACK_RESOURCE));
    return RestResponse.ResponseBuilder
      .ok(inputStream)
      .cacheControl(CacheControl.valueOf("max-age=900"))
      .type(URLConnection.guessContentTypeFromStream(inputStream))
      .build();
  }

}
