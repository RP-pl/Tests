using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ASP_.NET_MVC_Test.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace ASP_.NET_MVC_Test.Controllers
{
    public class NamesController : Controller
    {
        //https://localhost:44363/Names/Detail?name=RP
        public ActionResult Detail(string name)
        {
            var model = new NameModel { Name = name };
            return View(model);
        }
    }
}
