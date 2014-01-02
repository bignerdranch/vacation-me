package controllers

import play.api._
import play.api.mvc._
import play.api.libs.ws._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import vacations.OAuth

object Auth extends Controller {
  def login = Action { implicit request =>
    Ok(views.html.login(OAuth.authUrl))
  }

  def logout = Action { implicit request =>
    Redirect(routes.Auth.login).withNewSession.flashing(
    "success" -> "You are now logged out.")
  }

  def oauthCallback = Action.async { implicit request =>
    val code = request.queryString.get("code").get.mkString

    WS.url(OAuth.oauthUrl(code)).post("").map { response =>
      Redirect(routes.Application.index).withSession(
        "accessToken" -> (response.json \ "access_token").as[String])
    }
  }
}
