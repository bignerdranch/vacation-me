package controllers

import play.api._
import play.api.mvc._
import vacations.OAuth

object Application extends Controller {

  def index = Action { implicit request =>
    request.session.get("accessToken").map { token =>
      Ok(views.html.index())
    }.getOrElse {
      Redirect(OAuth.authUrl)
    }
  }
}
