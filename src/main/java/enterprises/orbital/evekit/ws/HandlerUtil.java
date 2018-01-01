package enterprises.orbital.evekit.ws;

import enterprises.orbital.base.OrbitalProperties;
import enterprises.orbital.base.PersistentProperty;
import enterprises.orbital.evekit.model.*;
import enterprises.orbital.evekit.model.alliance.Alliance;
import enterprises.orbital.evekit.model.alliance.AllianceIcon;
import enterprises.orbital.evekit.model.alliance.AllianceMemberCorporation;
import enterprises.orbital.evekit.model.server.ServerStatus;
import enterprises.orbital.evekit.model.sov.SovereigntyCampaign;
import enterprises.orbital.evekit.model.sov.SovereigntyCampaignParticipant;
import enterprises.orbital.evekit.model.sov.SovereigntyMap;
import enterprises.orbital.evekit.model.sov.SovereigntyStructure;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HandlerUtil {
  private static final Logger log = Logger.getLogger(HandlerUtil.class.getName());

  // Default max result size
  private static final String PROP_RESULT_LIMIT = "enterprises.orbital.evekit.ref_model_ws.max_results";
  private static final int DEF_RESULT_LIMIT = 1000;

  // Model object expiry map
  private static final Map<Class<? extends RefCachedData>, Long> modelExpiry = Stream.of(
      new AbstractMap.SimpleEntry<>(ServerStatus.class, TimeUnit.MILLISECONDS.convert(30, TimeUnit.SECONDS)),
      new AbstractMap.SimpleEntry<>(Alliance.class, TimeUnit.MILLISECONDS.convert(3600, TimeUnit.SECONDS)),
      new AbstractMap.SimpleEntry<>(AllianceIcon.class, TimeUnit.MILLISECONDS.convert(3600, TimeUnit.SECONDS)),
      new AbstractMap.SimpleEntry<>(AllianceMemberCorporation.class, TimeUnit.MILLISECONDS.convert(3600, TimeUnit.SECONDS)),
      new AbstractMap.SimpleEntry<>(SovereigntyCampaign.class, TimeUnit.MILLISECONDS.convert(5, TimeUnit.SECONDS)),
      new AbstractMap.SimpleEntry<>(SovereigntyCampaignParticipant.class, TimeUnit.MILLISECONDS.convert(5, TimeUnit.SECONDS)),
      new AbstractMap.SimpleEntry<>(SovereigntyMap.class, TimeUnit.MILLISECONDS.convert(3600, TimeUnit.SECONDS)),
      new AbstractMap.SimpleEntry<>(SovereigntyStructure.class, TimeUnit.MILLISECONDS.convert(120, TimeUnit.SECONDS))
                                                                                        )
                                                                                     .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

  // Standard handling for an illegal selector value
  public static Response handleIllegalSelector(Exception e) {
    ServiceError errMsg = new ServiceError(Response.Status.BAD_REQUEST.getStatusCode(), "An attribute selector contained an illegal value");
    return Response.status(Response.Status.BAD_REQUEST)
                   .entity(errMsg)
                   .build();
  }

  // Standard handling for an IO error while querying data
  public static Response handleIOError(IOException e) {
    log.log(Level.WARNING, "Error retrieving values", e);
    ServiceError errMsg = new ServiceError(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Internal error retrieving value.  If this error persists, please contact the site administrator");
    return Response.status(Response.Status.BAD_REQUEST)
                   .entity(errMsg)
                   .build();
  }

  // Standard interface for retrieving a list of results
  public interface QueryCaller<A extends RefCachedData> {
    List<A> getList(long contid, int maxresults, boolean reverse, AttributeSelector at,
                    AttributeSelector... others) throws IOException;

    long getExpiry();
  }

  // Standard determination of result expiry
  public static <A extends RefCachedData> long handleStandardExpiry(Class<A> clz, ESIRefSyncEndpoint ep) {
    long shift = TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES);
    long def = OrbitalProperties.getCurrentTime() + shift;
    try {
      return Math.max(def, ESIRefEndpointSyncTracker.getLatestFinishedTracker(ep)
                                                    .getSyncEnd() + modelExpiry.get(clz) + shift);
    } catch (IOException | TrackerNotFoundException e) {
      // Log and return current time plus a small delta
      log.log(Level.WARNING, "Error retrieving last tracker finish time", e);
      return def;
    }
  }

  // Standard handler for a list retriever with one or more attribute selectors
  public static <A extends RefCachedData> Response handleStandardListRequest(AttributeSelector at, long contid,
                                                                             int maxresults, boolean reverse,
                                                                             QueryCaller<A> query,
                                                                             HttpServletRequest request,
                                                                             AttributeSelector... sels) {
    ServiceUtil.sanitizeAttributeSelector(at);
    ServiceUtil.sanitizeAttributeSelector(sels);
    maxresults = Math.min(PersistentProperty.getIntegerPropertyWithFallback(PROP_RESULT_LIMIT, DEF_RESULT_LIMIT), maxresults);
    try {
      List<A> results = query.getList(contid, maxresults, reverse, at, sels);
      for (RefCachedData next : results) next.prepareDates();
      return ServiceUtil.finishRef(OrbitalProperties.getCurrentTime(), query.getExpiry(), results, request);
    } catch (NumberFormatException e) {
      return handleIllegalSelector(e);
    } catch (IOException e) {
      return handleIOError(e);
    }
  }


}
