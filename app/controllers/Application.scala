package controllers

import play.api._
import play.api.mvc._
import play.api.libs.ws._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future
import play.api.libs.json._

import lib.Stable
import lib.ResultsQuery

object Application extends Controller {

  def index = Action { implicit request =>
    request.session.get("accessToken").map { token =>
      Async {
        val resultsUrl = ResultsQuery.resultsUrl + "&per_page=100"
        val nerdsUrl = Stable.nerds
        val authHeader = "Bearer " + token

        for {
          results <- WS.url(resultsUrl).withHeaders("Authorization" -> authHeader).get().map { response =>
            response.json.as[List[JsObject]].map { obj =>
              Map("id" -> obj \ "id",
                "startDate" -> obj  \ "start_date",
                "endDate" -> obj  \ "end_date",
                "userEmail" -> obj  \ "_embedded" \ "user" \ "email",
                "projectName" -> obj  \ "_embedded" \ "project" \ "name",
                "projectType" -> obj  \ "_embedded" \ "project" \ "type")
            }
          }

          nerds <- WS.url(nerdsUrl).withHeaders("Authorization" -> authHeader).get().map { response =>
            response.json.as[List[JsObject]].map { obj =>
              Map("id" -> obj \ "id",
                "email" -> obj  \ "email",
                "name" -> obj  \ "full_name",
                "team_id" -> obj  \ "team_id",
                "gravatarUrl" -> obj  \ "gravatar_url")
            }
          }
        } yield {
          val vacations = results.filter(result =>
            result.get("projectType") == "Vacation"
          )

          val vacationingNerds = results.map(result => {
            val email = result.get("userEmail")
            val nerd = nerds.filter(nerd => nerd.get("email") == email).head

            (result ++ nerd).mapValues(v => v.as[String])
          }).sortBy(nerd => nerd("name"))

          Ok(views.html.index(vacationingNerds))
        }
      }
    }.getOrElse {
      Redirect(Stable.authRequestUrl)
    }
  }
}
