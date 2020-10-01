import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.Map;
import java.util.TreeMap;

import static spark.Spark.*;
public class SparkEx {
    public static void main(String[] args){
        port(4567);

        staticFiles.externalLocation("C:\\Users\\RP\\IdeaProjects\\SparkEx\\src\\main\\resources\\");
        threadPool(8,2,3000);
        initExceptionHandler((exception)->{
            System.out.println("Fatal error");
            System.exit(23);
        });
        get("/hello",(reqest,response)->{return "Hello";});

        get("/hello/:name",((request, response) -> {
            return "Hello " + request.params("name");
        }));

        get("/splat/*/example/*",((request, response) -> {
            return String.format("%s   %s",request.splat()[0],request.splat()[1]);
        }));

        path("/api",()->{
           before("/*",(request, response) -> System.out.println(request.splat()[0]));
           get("/name",((request, response) -> "Spark"));
           path("/user",()->{
              get("",((request, response) -> "Got user"));
              put("",((request, response) -> "Put user"));
              delete("",(request, response) -> "Del user");
           });
           get("/home",((request, response) -> {
               request.session(true);
               request.session().attribute("xyz","abc");
               response.redirect("/api/login");
               return null;
           }));
           get("/login",(request,response)->{
              response.body(request.session().attribute("xyz"));
              staticFiles.expireTime(6000);
              return response.body();
           });
        });
        get("/render/:name",(request, response) ->{
            System.out.println(request.params("name"));
            Map<String,Object> model = new TreeMap<>();
            model.put("name",request.params("name"));
            return new ThymeleafTemplateEngine().render(new ModelAndView(model, "template"));
        });
    }
}
