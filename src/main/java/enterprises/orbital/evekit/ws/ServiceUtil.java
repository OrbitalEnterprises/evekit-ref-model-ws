package enterprises.orbital.evekit.ws;

import enterprises.orbital.evekit.model.AttributeSelector;
import enterprises.orbital.evekit.model.RefCachedData;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ServiceUtil {
  private static final Logger log                  = Logger.getLogger(ServiceUtil.class.getName());
  private static final String PROP_MIN_CACHE_DELAY = "enterprises.orbital.evekit.model.min_cache_delay";
  private static final long   DEF_MIN_CACHE_DELAY  = TimeUnit.MILLISECONDS.convert(5, TimeUnit.MINUTES);

  public static <A extends RefCachedData> Response finishRef(
                                                             long when,
                                                             long expiry,
                                                             A result,
                                                             HttpServletRequest request) {
    auditRefAccess(getSource(request), getRequestURI(request));
    ResponseBuilder rBuilder = Response.ok();
    if (result != null) rBuilder = rBuilder.entity(result);
    return stamp(rBuilder, when, expiry).build();
  }

  public static <A extends RefCachedData> Response finishRef(
                                                             long when,
                                                             long expiry,
                                                             Collection<A> result,
                                                             HttpServletRequest request) {
    auditRefAccess(getSource(request), getRequestURI(request));
    ResponseBuilder rBuilder = Response.ok();
    if (result != null) rBuilder = rBuilder.entity(result);
    return stamp(rBuilder, when, expiry).build();
  }

  public static String getSource(
                                 HttpServletRequest request) {
    return request == null ? "INTERNAL" : request.getRemoteAddr();
  }

  public static String getRequestURI(
                                     HttpServletRequest request) {
    return request == null ? "INTERNAL" : request.getRequestURI();
  }

  private static SimpleDateFormat dateFormat              = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
  public static final long        DEFAULT_EXPIRY_INTERVAL = TimeUnit.MILLISECONDS.convert(24, TimeUnit.HOURS);
  private static String           versionString           = "2.0";

  protected static String getServerTime(
                                        long tm) {
    return dateFormat.format(new Date(tm));
  }

  public static ResponseBuilder stamp(
                                      ResponseBuilder result,
                                      long when,
                                      long expiry) {
    // expiry = MIN_VALUE is a tag for no-cache
    if (expiry <= 0 && expiry != Long.MIN_VALUE) expiry = when + DEFAULT_EXPIRY_INTERVAL;
    if (expiry > 0) {
      // valid expiry
      result = result.expires(new Date(expiry));
    } else {
      // if expiry is Long.MIN_VALUE then we interpret as not cacheable
      CacheControl noC = new CacheControl();
      noC.setNoCache(true);
      result = result.cacheControl(noC);
    }
    return result.header("Date", getServerTime(when)).header("EveKit-Version", versionString);
  }

  protected static String join(
                               String delim,
                               String... args) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < args.length; i++) {
      builder.append(args[i]);
      if (i + 1 < args.length) builder.append(delim);
    }
    return builder.toString();
  }

  public static void auditRefAccess(
                                    String src,
                                    String path) {
    log.fine(join(", ", "AUDIT", "REFRESOURCEACCESS", "FROM", src, "PATH", path));
  }

  public static void sanitizeAttributeSelector(
                                               AttributeSelector as) {
    // restrict size of string parameters for all settings to less than 200 characters
    if (as.start != null && as.start.length() > 200) as.start = as.start.substring(0, 200);
    if (as.end != null && as.end.length() > 200) as.end = as.end.substring(0, 200);
    // allow at most 500 set members for set selectors and verify strings are not too long
    if (as.values.size() > 0) {
      Set<String> newSet = new HashSet<String>();
      Iterator<String> i = as.values.iterator();
      for (int j = 0; j < 500 && i.hasNext(); j++) {
        String next = i.next();
        if (next.length() > 200) next = next.substring(0, 200);
        newSet.add(next);
      }
      as.values = newSet;
    }
  }

  public static void sanitizeAttributeSelector(
                                               AttributeSelector... as) {
    for (AttributeSelector next : as)
      sanitizeAttributeSelector(next);
  }

}
