package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action { implicit request =>
    request.session.get(Security.username).map { name =>
      Ok("Hello" + name)
    }.getOrElse {
      Redirect(routes.Auth.login)
    }
  }
}
