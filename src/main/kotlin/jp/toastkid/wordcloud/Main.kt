package jp.toastkid.wordcloud

import spark.kotlin.Http
import spark.kotlin.ignite

/**
 * Word cloud powered by Vert.x ver 3.
 *
 * @author Toast kid
 */
object Main  {

    /** width of word-cloud.  */
    private val WIDTH = 900

    /** height of word-cloud.  */
    private val HEIGHT = 600

    /** Title.  */
    private val TITLE = "Word Cloud"

    @JvmStatic
    fun main(args: Array<String>) {
        val http: Http = ignite().port(8000)
        http.get("/a") {"AAA"}
        http.get("/hello") { "Hello Spark Kotlin!" }
    }

    /*private fun route(): Router {

        // single page entry points.
        val sub = Router.router(vertx)
        sub.get("/").handler({ handler -> handler.response().end("Hello Vert.x app.") })

        val main = Router.router(vertx).mountSubRouter("/", sub)

        // static resources.
        main.route("/assets*//*").handler(StaticHandler.create("assets"))

        val handler = TemplateHandler.create(
                FreeMarkerTemplateEngine.create(), "/templates/", "text/html")

        // word cloud app route.
        main.get("/wc").handler({ context ->
            context.put("title", TITLE)

            val sentence = context.request().getParam("sentence")

            if (sentence == null || sentence!!.trim().length() === 0) {
                handler.handle(context)
                return@main.get("/wc").handler
            }
            context.put("wcData", WordCloud().count(sentence))
            context.put("paramSentence", sentence)
            context.put("width", WIDTH)
            context.put("height", HEIGHT)
            handler.handle(context)
        })
        return main
    }*/

}
