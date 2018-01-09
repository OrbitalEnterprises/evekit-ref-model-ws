package enterprises.orbital.evekit.ws.sov;

import enterprises.orbital.evekit.model.AttributeSelector;
import enterprises.orbital.evekit.model.ESIRefSyncEndpoint;
import enterprises.orbital.evekit.model.sov.SovereigntyCampaign;
import enterprises.orbital.evekit.model.sov.SovereigntyCampaignParticipant;
import enterprises.orbital.evekit.model.sov.SovereigntyMap;
import enterprises.orbital.evekit.model.sov.SovereigntyStructure;
import enterprises.orbital.evekit.ws.RefHandlerUtil;
import enterprises.orbital.evekit.ws.ServiceError;
import io.swagger.annotations.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

import static enterprises.orbital.evekit.ws.RefHandlerUtil.handleStandardExpiry;

@Path("/ws/v1/sov")
@Consumes({
    "application/json"
})
@Produces({
    "application/json"
})
@Api(
    tags = {
        "Sovereignty"
    },
    produces = "application/json",
    consumes = "application/json")
public class ModelSovWS {

  @Path("/sov_campaign")
  @GET
  @ApiOperation(
      value = "Get sovereignty campaigns")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested sovereignty campaigns",
              response = SovereigntyCampaign.class,
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
  public Response getSovereigntyCampaigns(
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
      @QueryParam("campaignID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "campaignID",
          defaultValue = "{ any: true }",
          value = "Campaign ID selector") AttributeSelector campaignID,
      @QueryParam("structureID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "structureID",
          defaultValue = "{ any: true }",
          value = "Structure ID selector") AttributeSelector structureID,
      @QueryParam("systemID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "systemID",
          defaultValue = "{ any: true }",
          value = "System ID selector") AttributeSelector systemID,
      @QueryParam("constellationID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "constellationID",
          defaultValue = "{ any: true }",
          value = "Constellation ID selector") AttributeSelector constellationID,
      @QueryParam("eventType") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "eventType",
          defaultValue = "{ any: true }",
          value = "Event type selector") AttributeSelector eventType,
      @QueryParam("startTime") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "startTime",
          defaultValue = "{ any: true }",
          value = "Campaign start time selector") AttributeSelector startTime,
      @QueryParam("defenderID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "defenderID",
          defaultValue = "{ any: true }",
          value = "Defender ID selector") AttributeSelector defenderID,
      @QueryParam("defenderScore") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "defenderScore",
          defaultValue = "{ any: true }",
          value = "Defender score selector") AttributeSelector defenderScore,
      @QueryParam("attackersScore") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "attackersScore",
          defaultValue = "{ any: true }",
          value = "Attacker score selector") AttributeSelector attackersScore) {
    return RefHandlerUtil.handleStandardListRequest(at, contid, maxresults, reverse, new RefHandlerUtil.QueryCaller<SovereigntyCampaign>() {

                                                   @Override
                                                   public List<SovereigntyCampaign> getList(long contid, int maxresults, boolean reverse, AttributeSelector at,
                                                                                            AttributeSelector... others) throws IOException {
                                                     final int CAMPAIGN_ID = 0;
                                                     final int STRUCTURE_ID = 1;
                                                     final int SYSTEM_ID = 2;
                                                     final int CONSTELLATION_ID = 3;
                                                     final int EVENT_TYPE = 4;
                                                     final int START_TIME = 5;
                                                     final int DEFENDER_ID = 6;
                                                     final int DEFENDER_SCORE = 7;
                                                     final int ATTACKERS_SCORE = 8;
                                                     return SovereigntyCampaign.accessQuery(contid, maxresults, reverse, at, others[CAMPAIGN_ID], others[STRUCTURE_ID], others[SYSTEM_ID], others[CONSTELLATION_ID],
                                                                                            others[EVENT_TYPE], others[START_TIME], others[DEFENDER_ID], others[DEFENDER_SCORE], others[ATTACKERS_SCORE]);
                                                   }

                                                   @Override
                                                   public long getExpiry() {
                                                     return handleStandardExpiry(SovereigntyCampaign.class, ESIRefSyncEndpoint.REF_SOV_CAMPAIGN);
                                                   }
                                                 }, request, campaignID, structureID, systemID, constellationID, eventType,
                                                    startTime, defenderID, defenderScore, attackersScore);
  }

  @Path("/sov_campaign_part")
  @GET
  @ApiOperation(
      value = "Get sovereignty campaign participants")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested sovereignty campaign participants",
              response = SovereigntyCampaignParticipant.class,
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
  public Response getSovereigntyCampaignParticipants(
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
      @QueryParam("campaignID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "campaignID",
          defaultValue = "{ any: true }",
          value = "Campaign ID selector") AttributeSelector campaignID,
      @QueryParam("allianceID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "allianceID",
          defaultValue = "{ any: true }",
          value = "Alliance ID selector") AttributeSelector allianceID,
      @QueryParam("score") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "score",
          defaultValue = "{ any: true }",
          value = "Participant score selector") AttributeSelector score) {
    return RefHandlerUtil.handleStandardListRequest(at, contid, maxresults, reverse, new RefHandlerUtil.QueryCaller<SovereigntyCampaignParticipant>() {

      @Override
      public List<SovereigntyCampaignParticipant> getList(long contid, int maxresults, boolean reverse,
                                                          AttributeSelector at,
                                                          AttributeSelector... others) throws IOException {
        final int CAMPAIGN_ID = 0;
        final int ALLIANCE_ID = 1;
        final int SCORE = 2;
        return SovereigntyCampaignParticipant.accessQuery(contid, maxresults, reverse, at, others[CAMPAIGN_ID], others[ALLIANCE_ID], others[SCORE]);
      }

      @Override
      public long getExpiry() {
        return handleStandardExpiry(SovereigntyCampaign.class, ESIRefSyncEndpoint.REF_SOV_CAMPAIGN);
      }
    }, request, campaignID, allianceID, score);
  }

  @Path("/sov_map")
  @GET
  @ApiOperation(
      value = "Get sovereignty map information")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested sovereignty map information",
              response = SovereigntyMap.class,
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
  public Response getSovereigntyMap(
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
          value = "Corporation ID selector") AttributeSelector corporationID,
      @QueryParam("factionID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "factionID",
          defaultValue = "{ any: true }",
          value = "Faction ID selector") AttributeSelector factionID,
      @QueryParam("systemID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "systemID",
          defaultValue = "{ any: true }",
          value = "System ID selector") AttributeSelector systemID) {
    return RefHandlerUtil.handleStandardListRequest(at, contid, maxresults, reverse, new RefHandlerUtil.QueryCaller<SovereigntyMap>() {

      @Override
      public List<SovereigntyMap> getList(long contid, int maxresults, boolean reverse, AttributeSelector at,
                                          AttributeSelector... others) throws IOException {
        final int ALLIANCE_ID = 0;
        final int CORPORATION_ID = 1;
        final int FACTION_ID = 2;
        final int SYSTEM_ID = 3;
        return SovereigntyMap.accessQuery(contid, maxresults, reverse, at, others[ALLIANCE_ID], others[CORPORATION_ID], others[FACTION_ID], others[SYSTEM_ID]);
      }

      @Override
      public long getExpiry() {
        return handleStandardExpiry(SovereigntyCampaign.class, ESIRefSyncEndpoint.REF_SOV_MAP);
      }
    }, request, allianceID, corporationID, factionID, systemID);
  }

  @Path("/sov_structure")
  @GET
  @ApiOperation(
      value = "Get sovereignty structures")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested sovereignty structures",
              response = SovereigntyStructure.class,
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
  public Response getSovereigntyStructures(
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
      @QueryParam("systemID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "systemID",
          defaultValue = "{ any: true }",
          value = "System ID selector") AttributeSelector systemID,
      @QueryParam("structureID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "structureID",
          defaultValue = "{ any: true }",
          value = "Structure ID selector") AttributeSelector structureID,
      @QueryParam("structureTypeID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "structureTypeID",
          defaultValue = "{ any: true }",
          value = "Structure type ID selector") AttributeSelector structureTypeID,
      @QueryParam("vulnerabilityOccupancyLevel") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "vulnerabilityOccupancyLevel",
          defaultValue = "{ any: true }",
          value = "Vulnerability occupancy level selector") AttributeSelector vulnerabilityOccupancyLevel,
      @QueryParam("vulnerableStartTime") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "vulnerableStartTime",
          defaultValue = "{ any: true }",
          value = "Vulnerable start time selector") AttributeSelector vulnerableStartTime,
      @QueryParam("vulnerableEndTime") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "vulnerableEndTime",
          defaultValue = "{ any: true }",
          value = "Vulnerable end time selector") AttributeSelector vulnerableEndTime) {
    return RefHandlerUtil.handleStandardListRequest(at, contid, maxresults, reverse, new RefHandlerUtil.QueryCaller<SovereigntyStructure>() {

      @Override
      public List<SovereigntyStructure> getList(long contid, int maxresults, boolean reverse, AttributeSelector at,
                                                AttributeSelector... others) throws IOException {
        final int ALLIANCE_ID = 0;
        final int SYSTEM_ID = 1;
        final int STRUCTURE_ID = 2;
        final int STRUCTURE_TYPE_ID = 3;
        final int VULNERABILITY_OCCUPANCY_LEVEL = 4;
        final int VULNERABLE_START_TIME = 5;
        final int VULNERABLE_END_TIME = 6;
        return SovereigntyStructure.accessQuery(contid, maxresults, reverse, at, others[ALLIANCE_ID], others[SYSTEM_ID], others[STRUCTURE_ID], others[STRUCTURE_TYPE_ID],
                                                others[VULNERABILITY_OCCUPANCY_LEVEL], others[VULNERABLE_START_TIME], others[VULNERABLE_END_TIME]);
      }

      @Override
      public long getExpiry() {
        return handleStandardExpiry(SovereigntyCampaign.class, ESIRefSyncEndpoint.REF_SOV_STRUCTURE);
      }
    }, request, allianceID, systemID, structureID, structureTypeID, vulnerabilityOccupancyLevel, vulnerableStartTime, vulnerableEndTime);
  }

}
