package enterprises.orbital.evekit.ws.alliance;

import enterprises.orbital.evekit.model.AttributeSelector;
import enterprises.orbital.evekit.model.ESIRefSyncEndpoint;
import enterprises.orbital.evekit.model.alliance.Alliance;
import enterprises.orbital.evekit.model.alliance.AllianceIcon;
import enterprises.orbital.evekit.model.alliance.AllianceMemberCorporation;
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

@Path("/ws/v1/alliance")
@Consumes({
    "application/json"
})
@Produces({
    "application/json"
})
@Api(
    tags = {
        "Alliance"
    },
    produces = "application/json",
    consumes = "application/json")
public class ModelAllianceWS {

  @Path("/alliance")
  @GET
  @ApiOperation(
      value = "Get alliance list")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested alliances",
              response = Alliance.class,
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
  public Response getAlliances(
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
      @QueryParam("allianceID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "allianceID",
          defaultValue = "{ any: true }",
          value = "Alliance ID selector") AttributeSelector allianceID,
      @QueryParam("executorCorpID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "executorCorpID",
          defaultValue = "{ any: true }",
          value = "Executing corporation ID selector") AttributeSelector executorCorpID,
      @QueryParam("memberCount") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "memberCount",
          defaultValue = "{ any: true }",
          value = "Member count selector") AttributeSelector memberCount,
      @QueryParam("name") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "name",
          defaultValue = "{ any: true }",
          value = "Name selector") AttributeSelector name,
      @QueryParam("shortName") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "shortName",
          defaultValue = "{ any: true }",
          value = "Short name selector") AttributeSelector shortName,
      @QueryParam("startDate") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "startDate",
          defaultValue = "{ any: true }",
          value = "Start date selector") AttributeSelector startDate,
      @QueryParam("creatorID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "creatorID",
          defaultValue = "{ any: true }",
          value = "Creator ID selector") AttributeSelector creatorID,
      @QueryParam("creatorCorpID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "creatorCorpID",
          defaultValue = "{ any: true }",
          value = "Creating corporation ID selector") AttributeSelector creatorCorpID,
      @QueryParam("factionID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "factionID",
          defaultValue = "{ any: true }",
          value = "Faction ID selector") AttributeSelector factionID) {
    return HandlerUtil.handleStandardListRequest(at, contid, maxresults, reverse, new HandlerUtil.QueryCaller<Alliance>() {

      @Override
      public List<Alliance> getList(long contid, int maxresults, boolean reverse, AttributeSelector at,
                                    AttributeSelector... others) throws IOException {
        final int ALLIANCE_ID = 0;
        final int EXECUTOR_CORP_ID = 1;
        final int MEMBER_COUNT = 2;
        final int NAME = 3;
        final int SHORT_NAME = 4;
        final int START_DATE = 5;
        final int CREATOR_ID = 6;
        final int CREATOR_CORP_ID = 7;
        final int FACTION_ID = 8;
        return Alliance.accessQuery(contid, maxresults, reverse, at, others[ALLIANCE_ID], others[EXECUTOR_CORP_ID], others[MEMBER_COUNT], others[NAME], others[SHORT_NAME], others[START_DATE], others[CREATOR_ID], others[CREATOR_CORP_ID], others[FACTION_ID]);
      }

      @Override
      public long getExpiry() {
        return handleStandardExpiry(Alliance.class, ESIRefSyncEndpoint.REF_ALLIANCE);
      }
    }, request, allianceID, executorCorpID, memberCount, name, shortName, startDate, creatorID, creatorCorpID, factionID);
  }

  @Path("/alliance_member")
  @GET
  @ApiOperation(
      value = "Get alliance member list")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested alliance members",
              response = AllianceMemberCorporation.class,
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
  public Response getAllianceMembers(
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
      @QueryParam("allianceID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "allianceID",
          defaultValue = "{ any: true }",
          value = "Alliance ID selector") AttributeSelector allianceID,
      @QueryParam("corporationID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "corporationID",
          defaultValue = "{ any: true }",
          value = "Member corporation ID selector") AttributeSelector corporationID) {
    return HandlerUtil.handleStandardListRequest(at, contid, maxresults, reverse, new HandlerUtil.QueryCaller<AllianceMemberCorporation>() {

      @Override
      public List<AllianceMemberCorporation> getList(long contid, int maxresults, boolean reverse, AttributeSelector at,
                                                     AttributeSelector... others) throws IOException {
        final int ALLIANCE_ID = 0;
        final int CORPORATION_ID = 1;
        return AllianceMemberCorporation.accessQuery(contid, maxresults, reverse, at, others[ALLIANCE_ID], others[CORPORATION_ID]);
      }

      @Override
      public long getExpiry() {
        return handleStandardExpiry(AllianceMemberCorporation.class, ESIRefSyncEndpoint.REF_ALLIANCE);
      }
    }, request, allianceID, corporationID);
  }

  @Path("/alliance_icon")
  @GET
  @ApiOperation(
      value = "Get alliance icon list")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested alliance icons",
              response = AllianceIcon.class,
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
  public Response getAllianceIcons(
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
      @QueryParam("allianceID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "allianceID",
          defaultValue = "{ any: true }",
          value = "Alliance ID selector") AttributeSelector allianceID,
      @QueryParam("px64x64") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "px64x64",
          defaultValue = "{ any: true }",
          value = "64x64 icon URL selector") AttributeSelector px64x64,
      @QueryParam("px128x128") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "px128x128",
          defaultValue = "{ any: true }",
          value = "128x128 icon URL selector") AttributeSelector px128x128) {
    return HandlerUtil.handleStandardListRequest(at, contid, maxresults, reverse, new HandlerUtil.QueryCaller<AllianceIcon>() {

      @Override
      public List<AllianceIcon> getList(long contid, int maxresults, boolean reverse, AttributeSelector at,
                                        AttributeSelector... others) throws IOException {
        final int ALLIANCE_ID = 0;
        final int PX64X64 = 1;
        final int PX128x128 = 2;
        return AllianceIcon.accessQuery(contid, maxresults, reverse, at, others[ALLIANCE_ID], others[PX64X64], others[PX128x128]);
      }

      @Override
      public long getExpiry() {
        return handleStandardExpiry(AllianceIcon.class, ESIRefSyncEndpoint.REF_ALLIANCE);
      }
    }, request, allianceID, px64x64, px128x128);
  }

}
