package enterprises.orbital.evekit.ws.server;

import enterprises.orbital.evekit.model.AttributeSelector;
import enterprises.orbital.evekit.model.ESIRefSyncEndpoint;
import enterprises.orbital.evekit.model.server.ServerStatus;
import enterprises.orbital.evekit.ws.HandlerUtil;
import enterprises.orbital.evekit.ws.ServiceError;
import io.swagger.annotations.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

import static enterprises.orbital.evekit.ws.HandlerUtil.handleStandardExpiry;

@Path("/ws/v1/server")
@Consumes({
    "application/json"
})
@Produces({
    "application/json"
})
@Api(
    tags = {
        "Server"
    },
    produces = "application/json",
    consumes = "application/json")
public class ModelServerWS {

  @Path("/server_status")
  @GET
  @ApiOperation(
      value = "Get server status")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested server statuses",
              response = ServerStatus.class,
              responseContainer = "array"),
          @ApiResponse(
              code = 400,
              message = "invalid attribute selector",
              response = ServiceError.class),
          @ApiResponse(
              code = 500,
              message = "internal service error",
              response = ServiceError.class),
      })
  public Response getServerStatus(
      @Context HttpServletRequest request,
      @QueryParam("at") @DefaultValue(
          value = "{ values: [ \"9223372036854775806\" ] }") @ApiParam(
          name = "at",
          defaultValue = "{ values: [ \"9223372036854775806\" ] }",
          value = "Model lifeline selector (defaults to current live data)") AttributeSelector at,
      @QueryParam("contid") @DefaultValue("-1") @ApiParam(
          name = "contid",
          defaultValue = "-1",
          value = "Continuation ID for paged results") long contid,
      @QueryParam("maxresults") @DefaultValue("1000") @ApiParam(
          name = "maxresults",
          defaultValue = "1000",
          value = "Maximum number of results to retrieve") int maxresults,
      @QueryParam("reverse") @DefaultValue("false") @ApiParam(
          name = "reverse",
          defaultValue = "false",
          value = "If true, page backwards (results less than contid) with results in descending order (by cid)") boolean reverse,
      @QueryParam("onlinePlayers") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "onlinePlayers",
          defaultValue = "{ any: true }",
          value = "Online players selector") AttributeSelector onlinePlayers,
      @QueryParam("startTime") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "startTime",
          defaultValue = "{ any: true }",
          value = "Server start time selector") AttributeSelector startTime,
      @QueryParam("serverVersion") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "serverVersion",
          defaultValue = "{ any: true }",
          value = "Server version selector") AttributeSelector serverVersion,
      @QueryParam("vip") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "vip",
          defaultValue = "{ any: true }",
          value = "Server vip selector") AttributeSelector vip) {
    return HandlerUtil.handleStandardListRequest(at, contid, maxresults, reverse, new HandlerUtil.QueryCaller<ServerStatus>() {

      @Override
      public List<ServerStatus> getList(long contid, int maxresults, boolean reverse, AttributeSelector at,
                                        AttributeSelector... others) throws IOException {
        final int ONLINE_PLAYERS = 0;
        final int START_TIME = 1;
        final int SERVER_VERSION = 2;
        final int VIP = 3;
        return ServerStatus.accessQuery(contid, maxresults, reverse, at, others[ONLINE_PLAYERS], others[START_TIME], others[SERVER_VERSION], others[VIP]);
      }

      @Override
      public long getExpiry() {
        return handleStandardExpiry(ServerStatus.class, ESIRefSyncEndpoint.REF_SERVER_STATUS);
      }
    }, request, onlinePlayers, startTime, serverVersion, vip);
  }

}
