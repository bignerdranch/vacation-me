package lib
import play.api.libs.json._
import scala.collection.immutable.Map

object StableData {
  def resultData(json: JsValue): List[Map[String, JsValue]] = {
    json.as[List[JsObject]].map { obj =>
      Map("id" -> obj \ "id",
        "startDate" -> obj  \ "start_date",
        "endDate" -> obj  \ "end_date",
        "notes" -> obj \ "notes",
        "userEmail" -> obj  \ "_embedded" \ "user" \ "email",
        "projectName" -> obj  \ "_embedded" \ "project" \ "name",
        "projectType" -> obj  \ "_embedded" \ "project" \ "type")
    }
  }

  def userData(json: JsValue): List[Map[String, JsValue]] = {
    json.as[List[JsObject]].map { obj =>
      Map("id" -> obj \ "id",
        "email" -> obj  \ "email",
        "name" -> obj  \ "full_name",
        "team_id" -> obj  \ "team_id",
        "gravatarUrl" -> obj  \ "gravatar_url")
    }
  }
}
