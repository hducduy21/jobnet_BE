from django.urls import path, include
from .views import (
    PostApiView
)

urlpatterns = [
    path('', PostApiView.as_view()),
] 