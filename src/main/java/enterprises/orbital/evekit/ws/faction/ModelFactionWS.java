package enterprises.orbital.evekit.ws.faction;

import enterprises.orbital.evekit.model.AttributeSelector;
import enterprises.orbital.evekit.model.ESIRefSyncEndpoint;
import enterprises.orbital.evekit.model.alliance.Alliance;
import enterprises.orbital.evekit.model.faction.*;
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

@Path("/ws/v1/faction")
@Consumes({
    "application/json"
})
@Produces({
    "application/json"
})
@Api(
    tags = {
        "Faction"
    },
    produces = "application/json",
    consumes = "application/json")
public class ModelFactionWS {

  @Path("/char_kill_stat")
  @GET
  @ApiOperation(
      value = "Get character kill stats")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested character kill stats",
              response = CharacterKillStat.class,
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
  public Response getCharacterKillStats(
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
      @QueryParam("attribute") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "attribute",
          defaultValue = "{ any: true }",
          value = "Stat attribute (LAST_WEEK, TOTAL, YESTERDAY) selector") AttributeSelector attribute,
      @QueryParam("characterID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "characterID",
          defaultValue = "{ any: true }",
          value = "Character ID selector") AttributeSelector characterID,
      @QueryParam("kills") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "kills",
          defaultValue = "{ any: true }",
          value = "Kill count selector") AttributeSelector kills) {
    return RefHandlerUtil.handleStandardListRequest(at, contid, maxresults, reverse, new RefHandlerUtil.QueryCaller<CharacterKillStat>() {

      @Override
      public List<CharacterKillStat> getList(long contid, int maxresults, boolean reverse, AttributeSelector at,
                                             AttributeSelector... others) throws IOException {
        final int ATTRIBUTE = 0;
        final int CHARACTER_ID = 1;
        final int KILLS = 2;
        return CharacterKillStat.accessQuery(contid, maxresults, reverse, at, others[ATTRIBUTE], others[CHARACTER_ID], others[KILLS]);
      }

      @Override
      public long getExpiry() {
        return handleStandardExpiry(Alliance.class, ESIRefSyncEndpoint.REF_FW_CHAR_LEADERBOARD);
      }
    }, request, attribute, characterID, kills);
  }

  @Path("/char_vp_stat")
  @GET
  @ApiOperation(
      value = "Get character victory point stats")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested character victory point stats",
              response = CharacterVictoryPointStat.class,
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
  public Response getCharacterVictoryPointStats(
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
      @QueryParam("attribute") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "attribute",
          defaultValue = "{ any: true }",
          value = "Stat attribute (LAST_WEEK, TOTAL, YESTERDAY) selector") AttributeSelector attribute,
      @QueryParam("characterID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "characterID",
          defaultValue = "{ any: true }",
          value = "Character ID selector") AttributeSelector characterID,
      @QueryParam("victoryPoints") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "victoryPoints",
          defaultValue = "{ any: true }",
          value = "Victory points selector") AttributeSelector victoryPoints) {
    return RefHandlerUtil.handleStandardListRequest(at, contid, maxresults, reverse, new RefHandlerUtil.QueryCaller<CharacterVictoryPointStat>() {

      @Override
      public List<CharacterVictoryPointStat> getList(long contid, int maxresults, boolean reverse, AttributeSelector at,
                                                     AttributeSelector... others) throws IOException {
        final int ATTRIBUTE = 0;
        final int CHARACTER_ID = 1;
        final int VICTORY_POINTS = 2;
        return CharacterVictoryPointStat.accessQuery(contid, maxresults, reverse, at, others[ATTRIBUTE], others[CHARACTER_ID], others[VICTORY_POINTS]);
      }

      @Override
      public long getExpiry() {
        return handleStandardExpiry(Alliance.class, ESIRefSyncEndpoint.REF_FW_CHAR_LEADERBOARD);
      }
    }, request, attribute, characterID, victoryPoints);
  }

  @Path("/corp_kill_stat")
  @GET
  @ApiOperation(
      value = "Get corporation kill stats list")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested corporation kill stats",
              response = CorporationKillStat.class,
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
  public Response getCorporationKillStats(
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
      @QueryParam("attribute") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "attribute",
          defaultValue = "{ any: true }",
          value = "Stat attribute (LAST_WEEK, TOTAL, YESTERDAY) selector") AttributeSelector attribute,
      @QueryParam("corporationID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "corporationID",
          defaultValue = "{ any: true }",
          value = "Corporation ID selector") AttributeSelector corporationID,
      @QueryParam("kills") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "kills",
          defaultValue = "{ any: true }",
          value = "Kill count selector") AttributeSelector kills) {
    return RefHandlerUtil.handleStandardListRequest(at, contid, maxresults, reverse, new RefHandlerUtil.QueryCaller<CorporationKillStat>() {

      @Override
      public List<CorporationKillStat> getList(long contid, int maxresults, boolean reverse, AttributeSelector at,
                                               AttributeSelector... others) throws IOException {
        final int ATTRIBUTE = 0;
        final int CORPORATION_ID = 1;
        final int KILLS = 2;
        return CorporationKillStat.accessQuery(contid, maxresults, reverse, at, others[ATTRIBUTE], others[CORPORATION_ID], others[KILLS]);
      }

      @Override
      public long getExpiry() {
        return handleStandardExpiry(Alliance.class, ESIRefSyncEndpoint.REF_FW_CORP_LEADERBOARD);
      }
    }, request, attribute, corporationID, kills);
  }

  @Path("/corp_vp_stat")
  @GET
  @ApiOperation(
      value = "Get corporation victory point stats list")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested corporation victory point stats",
              response = CorporationVictoryPointStat.class,
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
  public Response getCorporationVictoryPointStats(
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
      @QueryParam("attribute") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "attribute",
          defaultValue = "{ any: true }",
          value = "Stat attribute (LAST_WEEK, TOTAL, YESTERDAY) selector") AttributeSelector attribute,
      @QueryParam("corporationID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "corporationID",
          defaultValue = "{ any: true }",
          value = "Corporation ID selector") AttributeSelector corporationID,
      @QueryParam("victoryPoints") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "victoryPoints",
          defaultValue = "{ any: true }",
          value = "Victory points selector") AttributeSelector victoryPoints) {
    return RefHandlerUtil.handleStandardListRequest(at, contid, maxresults, reverse, new RefHandlerUtil.QueryCaller<CorporationVictoryPointStat>() {

      @Override
      public List<CorporationVictoryPointStat> getList(long contid, int maxresults, boolean reverse,
                                                       AttributeSelector at,
                                                       AttributeSelector... others) throws IOException {
        final int ATTRIBUTE = 0;
        final int CORPORATION_ID = 1;
        final int VICTORY_POINTS = 2;
        return CorporationVictoryPointStat.accessQuery(contid, maxresults, reverse, at, others[ATTRIBUTE], others[CORPORATION_ID], others[VICTORY_POINTS]);
      }

      @Override
      public long getExpiry() {
        return handleStandardExpiry(Alliance.class, ESIRefSyncEndpoint.REF_FW_CORP_LEADERBOARD);
      }
    }, request, attribute, corporationID, victoryPoints);
  }

  @Path("/faction_kill_stat")
  @GET
  @ApiOperation(
      value = "Get faction kill stats list")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested faction kill stats",
              response = FactionKillStat.class,
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
  public Response getFactionKillStats(
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
      @QueryParam("attribute") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "attribute",
          defaultValue = "{ any: true }",
          value = "Stat attribute (LAST_WEEK, TOTAL, YESTERDAY) selector") AttributeSelector attribute,
      @QueryParam("factionID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "factionID",
          defaultValue = "{ any: true }",
          value = "Faction ID selector") AttributeSelector factionID,
      @QueryParam("kills") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "kills",
          defaultValue = "{ any: true }",
          value = "Kill count selector") AttributeSelector kills) {
    return RefHandlerUtil.handleStandardListRequest(at, contid, maxresults, reverse, new RefHandlerUtil.QueryCaller<FactionKillStat>() {

      @Override
      public List<FactionKillStat> getList(long contid, int maxresults, boolean reverse, AttributeSelector at,
                                           AttributeSelector... others) throws IOException {
        final int ATTRIBUTE = 0;
        final int FACTION_ID = 1;
        final int KILLS = 2;
        return FactionKillStat.accessQuery(contid, maxresults, reverse, at, others[ATTRIBUTE], others[FACTION_ID], others[KILLS]);
      }

      @Override
      public long getExpiry() {
        return handleStandardExpiry(Alliance.class, ESIRefSyncEndpoint.REF_FW_FACTION_LEADERBOARD);
      }
    }, request, attribute, factionID, kills);
  }

  @Path("/faction_stats")
  @GET
  @ApiOperation(
      value = "Get faction stats list")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested faction stats",
              response = FactionStats.class,
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
  public Response getFactionStats(
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
      @QueryParam("factionID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "factionID",
          defaultValue = "{ any: true }",
          value = "Faction ID selector") AttributeSelector factionID,
      @QueryParam("killsLastWeek") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "killsLastWeek",
          defaultValue = "{ any: true }",
          value = "Last week's kill count selector") AttributeSelector killsLastWeek,
      @QueryParam("killsTotal") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "killsTotal",
          defaultValue = "{ any: true }",
          value = "Total kill count selector") AttributeSelector killsTotal,
      @QueryParam("killsYesterday") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "killsYesterday",
          defaultValue = "{ any: true }",
          value = "Yesterday's kill count selector") AttributeSelector killsYesterday,
      @QueryParam("pilots") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "pilots",
          defaultValue = "{ any: true }",
          value = "Pilot count selector") AttributeSelector pilots,
      @QueryParam("systemsControlled") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "systemsControlled",
          defaultValue = "{ any: true }",
          value = "Controlled systems count selector") AttributeSelector systemsControlled,
      @QueryParam("victoryPointsLastWeek") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "victoryPointsLastWeek",
          defaultValue = "{ any: true }",
          value = "Last week's victory points count selector") AttributeSelector victoryPointsLastWeek,
      @QueryParam("victoryPointsTotal") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "victoryPointsTotal",
          defaultValue = "{ any: true }",
          value = "Total victory points count selector") AttributeSelector victoryPointsTotal,
      @QueryParam("victoryPointsYesterday") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "victoryPointsYesterday",
          defaultValue = "{ any: true }",
          value = "Yesterday's victory point count selector") AttributeSelector victoryPointsYesterday) {
    return RefHandlerUtil.handleStandardListRequest(at, contid, maxresults, reverse, new RefHandlerUtil.QueryCaller<FactionStats>() {

      @Override
      public List<FactionStats> getList(long contid, int maxresults, boolean reverse, AttributeSelector at,
                                        AttributeSelector... others) throws IOException {
        final int FACTION_ID = 0;
        final int KILLS_LAST_WEEK = 1;
        final int KILLS_TOTAL = 2;
        final int KILLS_YESTERDAY = 3;
        final int PILOTS = 4;
        final int SYSTEMS_CONTROLLED = 5;
        final int VICTORY_POINTS_LAST_WEEK = 6;
        final int VICTORY_POINTS_TOTAL = 7;
        final int VICTORY_POINTS_YESTERDAY = 8;
        return FactionStats.accessQuery(contid, maxresults, reverse, at, others[FACTION_ID], others[KILLS_LAST_WEEK], others[KILLS_TOTAL], others[KILLS_YESTERDAY], others[PILOTS], others[SYSTEMS_CONTROLLED], others[VICTORY_POINTS_LAST_WEEK], others[VICTORY_POINTS_TOTAL], others[VICTORY_POINTS_YESTERDAY]);
      }

      @Override
      public long getExpiry() {
        return handleStandardExpiry(Alliance.class, ESIRefSyncEndpoint.REF_FW_STATS);
      }
    }, request, factionID, killsLastWeek, killsTotal, killsYesterday, pilots, systemsControlled, victoryPointsLastWeek, victoryPointsTotal, victoryPointsYesterday);
  }

  @Path("/faction_vp_stat")
  @GET
  @ApiOperation(
      value = "Get faction victory points stats list")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested faction victory points stats",
              response = FactionVictoryPointStat.class,
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
  public Response getFactionVictoryPointsStatus(
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
      @QueryParam("attribute") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "attribute",
          defaultValue = "{ any: true }",
          value = "Stat attribute (LAST_WEEK, TOTAL, YESTERDAY) selector") AttributeSelector attribute,
      @QueryParam("factionID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "factionID",
          defaultValue = "{ any: true }",
          value = "Faction ID selector") AttributeSelector factionID,
      @QueryParam("victoryPoints") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "victoryPoints",
          defaultValue = "{ any: true }",
          value = "Victory points selector") AttributeSelector victoryPoints) {
    return RefHandlerUtil.handleStandardListRequest(at, contid, maxresults, reverse, new RefHandlerUtil.QueryCaller<FactionVictoryPointStat>() {

      @Override
      public List<FactionVictoryPointStat> getList(long contid, int maxresults, boolean reverse, AttributeSelector at,
                                                   AttributeSelector... others) throws IOException {
        final int ATTRIBUTE = 0;
        final int FACTION_ID = 1;
        final int VICTORY_POINTS = 2;
        return FactionVictoryPointStat.accessQuery(contid, maxresults, reverse, at, others[ATTRIBUTE], others[FACTION_ID], others[VICTORY_POINTS]);
      }

      @Override
      public long getExpiry() {
        return handleStandardExpiry(Alliance.class, ESIRefSyncEndpoint.REF_FW_FACTION_LEADERBOARD);
      }
    }, request, attribute, factionID, victoryPoints);
  }

  @Path("/faction_war")
  @GET
  @ApiOperation(
      value = "Get faction war list")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested faction wars",
              response = FactionWar.class,
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
  public Response getFactionWars(
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
      @QueryParam("againstID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "againstID",
          defaultValue = "{ any: true }",
          value = "Against ID selector") AttributeSelector againstID,
      @QueryParam("factionID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "factionID",
          defaultValue = "{ any: true }",
          value = "Faction ID selector") AttributeSelector factionID) {
    return RefHandlerUtil.handleStandardListRequest(at, contid, maxresults, reverse, new RefHandlerUtil.QueryCaller<FactionWar>() {

      @Override
      public List<FactionWar> getList(long contid, int maxresults, boolean reverse, AttributeSelector at,
                                      AttributeSelector... others) throws IOException {
        final int AGAINST_ID = 0;
        final int FACTION_ID = 1;
        return FactionWar.accessQuery(contid, maxresults, reverse, at, others[AGAINST_ID], others[FACTION_ID]);
      }

      @Override
      public long getExpiry() {
        return handleStandardExpiry(Alliance.class, ESIRefSyncEndpoint.REF_FW_WARS);
      }
    }, request, againstID, factionID);
  }

  @Path("/faction_war_system")
  @GET
  @ApiOperation(
      value = "Get faction war system list")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "list of requested faction war systems",
              response = FactionWarSystem.class,
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
  public Response getFactionWarSystems(
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
      @QueryParam("occupyingFactionID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "occupyingFactionID",
          defaultValue = "{ any: true }",
          value = "Occupying faction ID selector") AttributeSelector occupyingFactionID,
      @QueryParam("owningFactionID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "owningFactionID",
          defaultValue = "{ any: true }",
          value = "Owning faction ID selector") AttributeSelector owningFactionID,
      @QueryParam("solarSystemID") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "solarSystemID",
          defaultValue = "{ any: true }",
          value = "Solar system ID selector") AttributeSelector solarSystemID,
      @QueryParam("victoryPoints") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "victoryPoints",
          defaultValue = "{ any: true }",
          value = "Victory points selector") AttributeSelector victoryPoints,
      @QueryParam("victoryPointsThreshold") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "victoryPointsThreshold",
          defaultValue = "{ any: true }",
          value = "Victory points threshold selector") AttributeSelector victoryPointsThreshold,
      @QueryParam("contested") @DefaultValue(
          value = "{ any: true }") @ApiParam(
          name = "contested",
          defaultValue = "{ any: true }",
          value = "Contested selector") AttributeSelector contested) {
    return RefHandlerUtil.handleStandardListRequest(at, contid, maxresults, reverse, new RefHandlerUtil.QueryCaller<FactionWarSystem>() {

      @Override
      public List<FactionWarSystem> getList(long contid, int maxresults, boolean reverse, AttributeSelector at,
                                            AttributeSelector... others) throws IOException {
        final int OCCUPYING_FACTION_ID = 0;
        final int OWNING_FACTION_ID = 1;
        final int SOLAR_SYSTEM_ID = 2;
        final int VICTORY_POINTS = 3;
        final int VICTORY_POINTS_THRESHOLD = 4;
        final int CONTESTED = 5;
        return FactionWarSystem.accessQuery(contid, maxresults, reverse, at, others[OCCUPYING_FACTION_ID], others[OWNING_FACTION_ID], others[SOLAR_SYSTEM_ID], others[VICTORY_POINTS], others[VICTORY_POINTS_THRESHOLD], others[CONTESTED]);
      }

      @Override
      public long getExpiry() {
        return handleStandardExpiry(Alliance.class, ESIRefSyncEndpoint.REF_FW_SYSTEMS);
      }
    }, request, occupyingFactionID, owningFactionID, solarSystemID, victoryPoints, victoryPointsThreshold, contested);
  }

}
