package vacations

object OAuth {
  val clientId = sys.env.get("APPLICATION_ID").get
  val clientSecret = sys.env.get("APPLICATION_SECRET").get
  val redirectUri = sys.env.get("OAUTH_REDIRECT_URI").get
  val authUrl = "https://stable.bignerdranch.com/oauth/authorize?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirectUri + "&client_secret=" + clientSecret
  def oauthUrl(code: String) =
    "https://stable.bignerdranch.com/oauth/token?" + "client_id=" + clientId + "&client_secret=" + clientSecret + "&code=" + code + "&grant_type=authorization_code&redirect_uri=" + redirectUri
}
