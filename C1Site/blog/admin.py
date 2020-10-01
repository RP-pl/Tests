from django.contrib import admin
from .models import Post, Comment


#admin.site.register(Post)
@admin.register(Post)
class PostAdmin(admin.ModelAdmin):
    #pokazywane dane
    list_display = ('title','author','slug','created','status')
    #filtrowanie danych
    list_filter = ('status','created','author')
    #szuakanie
    search_fields = ('author','title')
    #uzupełnianie automatyczne
    prepopulated_fields = {'slug':('title',)}
    date_hierarchy = 'publish'
    raw_id_fields = ('author',)
    #sortowanie
    ordering = ('status','publish')
"""RĘCZNE DODAWANIE POSTÓW
>>> from django.contrib.auth.models import User
>>> from blog.models import Post
>>> user = User.objects.get(username='admin')
>>> post = Post(title='post',slug='other-post',body='XYZ',author=user)
>>> post.save()
"""
class CommentAdmin(admin.ModelAdmin):
    list_display = ('name', 'email', 'post', 'created', 'active')
    list_filter = ('active', 'created', 'updated')
    search_fields = ('name', 'email', 'body')
admin.site.register(Comment, CommentAdmin)