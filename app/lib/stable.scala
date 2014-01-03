package lib

object Stable {
  val baseUrl = "https://stable.bignerdranch.com"

  val nerds = baseUrl + "/nerds.json"
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
