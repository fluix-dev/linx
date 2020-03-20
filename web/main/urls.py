from django.conf import settings
from django.conf.urls.static import static
from django.urls import path
from . import views

urlpatterns = [
    path('', views.index, name='index'),
    path('about', views.about, name='about'),
    path('network', views.network, name='network'),
    path('team', views.team, name='team'),
]

urlpatterns += static(settings.STATIC_URL, document_root=settings.STATIC_ROOT)