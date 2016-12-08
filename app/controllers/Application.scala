package controllers

import model._
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel
import play.api.mvc._
import play.api.libs.json.Json._
import play.api.libs.json._
import model._
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Writes}

object Application extends Controller {
  

  def index = Action { implicit request =>

     val spark = SparkSession
    .builder()
    .appName("CSYE 7200 Final Project2")
    .master("local[2]")
    .config("spark.some.config.option", "some-value")
    .getOrCreate()

     case class Node(ID: Long, Name: String)
     case class fatherNode(ID:Long, Name:String, children:List[Node])

     implicit val nodesWrites: Writes[Node] = (
    (JsPath \ "ID").write[Long] and
      (JsPath \ "Name").write[String]
    )(unlift(Node.unapply))

    implicit val fatherNodeWrites: Writes[fatherNode] = (
    (JsPath \ "ID").write[Long] and
      (JsPath \ "Name").write[String] and
      (JsPath \ "children").write[List[Node]]
    )(unlift(fatherNode.unapply))

    val result = appl.search(spark,1)
    Ok(result)

  }
  
  def search(nodeID:String) = Action{
    implicit request =>

      val spark = SparkSession
        .builder()
        .appName("CSYE 7200 Final Project2")
        .master("local[2]")
        .config("spark.some.config.option", "some-value")
        .getOrCreate()

      case class Node(ID: Long, Name: String)
      case class fatherNode(ID:Long, Name:String, children:List[Node])

      implicit val nodesWrites: Writes[Node] = (
        (JsPath \ "ID").write[Long] and
          (JsPath \ "Name").write[String]
        )(unlift(Node.unapply))

      implicit val fatherNodeWrites: Writes[fatherNode] = (
        (JsPath \ "ID").write[Long] and
          (JsPath \ "Name").write[String] and
          (JsPath \ "children").write[List[Node]]
        )(unlift(fatherNode.unapply))

      val result = appl.search(spark,nodeID.toLong)
      Ok(result)
  }

}
