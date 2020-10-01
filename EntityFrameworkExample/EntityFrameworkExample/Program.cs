using Microsoft.EntityFrameworkCore;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Data.Common;
using System.Linq;
using System.Threading;

namespace EntityFrameworkExample
{

    class Context : DbContext
    {

        public DbSet<Library> Libraries { get; set; }
        public DbSet<Book> Books { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder options)
        {
            MySqlConnection c = new MySqlConnection("SERVER=localhost;DATABASE=entitydb;UID=root;PASSWORD="+password+";");
            options.UseMySQL(c);
        }

        [Table("Libraries")]
        public class Library
        {
            [Column("Name",Order = 0)]
            public string Name { get; set; }
            [Column("Id",Order = 1)]
            public int Id { get; set; }

            public ICollection<Book> Books { get; set; }
        }
        public class Book
        {
            public int Id { get; set; }
            public string title { get; set; }
        }
        static void Main(string[] args)
        {
           using(var db = new Context())
            {
                db.Database.EnsureDeleted();
                db.Database.EnsureCreated();
                List<Book> bks = new List<Book>();
                bks.Add(new Book() { Id = 1, title = "Witcher" });
                bks.Add(new Book() { Id = 2, title = "C# 7.1 and .Net Core 2.0" });
                var library = new Library { Id=1, Name = "Library1", Books = bks};
                db.Libraries.Add(library);
                db.SaveChanges();
                var libs = db.Libraries.ToList();
                foreach(var lib in libs)
                {
                    Console.WriteLine(lib.Name + "    " + lib.Id.ToString());
                }
                var Library1 = db.Libraries.First();
                Library1.Name = "LibraryFirst";
                db.SaveChanges();
                Console.WriteLine("UPDATE");
                foreach (var lib in libs)
                {
                    Console.WriteLine(lib.Name + "    " + lib.Id.ToString());
                }
                var Lib = db.Libraries.Where(lib => lib.Name.Equals("LibraryFirst")).FirstOrDefault();
                db.Libraries.Remove(Lib);
                db.SaveChanges();
                Console.WriteLine("DEL");
                foreach (var lib in libs)
                {
                    Console.WriteLine(lib.Name + "    " + lib.Id.ToString());
                }
                foreach (var lib in libs)
                {
                    Console.WriteLine(lib.Name);
                    foreach(Book book in lib.Books)
                    {
                        Console.WriteLine(book.Id.ToString() + "   " + book.title);
                    }
                }
            }
        }
    }
}
