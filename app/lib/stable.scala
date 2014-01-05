package lib

object Stable {
  val baseUrl = "https://stable.bignerdranch.com"

  val nerds = baseUrl + "/users.json"
  val results = baseUrl + "/results.json"

  val baseParams =
    "?client_id=" + sys.env.get("APPLICATION_ID").get +
      "&client_secret=" + sys.env.get("APPLICATION_SECRET").get +
      "&redirect_uri=" + sys.env.get("OAUTH_REDIRECT_URI").get

  val authRequestParams =
    baseParams + "&response_type=code"

  val tokenRequestParams =
    baseParams + "&grant_type=authorization_code&code="

  val authRequestUrl = baseUrl + "/oauth/authorize" + authRequestParams
  val tokenRequestUrl = baseUrl + "/oauth/token" + tokenRequestParams
}

import java.util.Date
import java.text.SimpleDateFormat

object ResultsQuery {
  val formatter = new SimpleDateFormat("MM-dd-yyy")
  val today = formatter.format(new Date).replaceAll("-", "%2F")
  val resultsUrl = Stable.results + "?result_filter_query%5Bstart_date%5D=" + today + "&result_filter_query%5Bend_date%5D=" + today + "&result_filter_query%5Btentative%5D=0&result_filter_query%5Bbooked%5D=0&result_filter_query%5Backnowledged%5D=0&result_filter_query%5Bin_progress%5D=0&result_filter_query%5Bcompleted%5D=0&result_filter_query%5Bcompleted%5D=1&result_filter_query%5Bbilled%5D=0&result_filter_query%5Bcompensated%5D=0&result_filter_query%5Bproject_id%5D=&commit=Filter"
}
