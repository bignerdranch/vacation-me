package controllers

import play.api._
import play.api.mvc._
import play.api.libs.ws._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future
import play.api.libs.json._

import lib.Stable
import lib.ResultsQuery
import lib.StableData

object Application extends Controller {

  def index = Action { implicit request =>
    flash.get("accessToken").map { token =>
      Async {
        val resultsUrl = ResultsQuery.resultsUrl + "&per_page=100"
        val nerdsUrl = Stable.nerds
        val authHeader = "Bearer " + token

        for {
          results <- WS.url(resultsUrl).withHeaders("Authorization" -> authHeader).get().map { response =>
            response.json
          }

          nerds <- WS.url(nerdsUrl).withHeaders("Authorization" -> authHeader).get().map { response =>
            response.json
          }
        } yield {
          val resultsData = StableData.resultData(results)
            .filter(result => result("projectName") == JsString("PTO") )
          val usersData = StableData.userData(nerds)

          val vacationingNerds = resultsData.map(result => {
            val email = result.get("userEmail")
            val nerd = usersData.filter(nerd => nerd.get("email") == email).head

            (result ++ nerd).mapValues(v => v.asOpt[String])
          }).sortBy(nerd => nerd("name"))

          Ok(views.html.index(vacationingNerds))
        }
      }
    }.getOrElse {
      Redirect(Stable.authRequestUrl)
    }
  }
}
