package controllers

import play.api._
import play.api.mvc._

object Auth extends Controller {

  def login = Action { implicit request =>
    Redirect(routes.Application.index).withSession(Security.username -> "username")
  }

  def logout = Action { implicit request =>
    Redirect(routes.Auth.login).withNewSession.flashing(
    "success" -> "You are now logged out.")
  }

}
