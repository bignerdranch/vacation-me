package controllers

import play.api._
import play.api.mvc._
import play.api.libs.ws._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import lib.Stable

object Auth extends Controller {
  def login = Action { implicit request =>
    Ok(views.html.login(Stable.authRequestUrl))
  }

  def logout = Action { implicit request =>
    Redirect(routes.Auth.login).withNewSession.flashing(
    "success" -> "You are now logged out.")
  }

  def oauthCallback = Action.async { implicit request =>
    val code = request.queryString.get("code").get.mkString
    val url = Stable.tokenRequestUrl + code

    WS.url(url).post("").map { response =>
      Redirect(routes.Application.index).withSession(
        "accessToken" -> (response.json \ "access_token").as[String])
    }
  }
}
