from django.shortcuts import render

# Create your views here.
def index(request):
    return render(request, 'index.html')

def team(request):
    return render(request, 'team.html')

def network(request):
    return render(request, 'network.html')

def about(request):
    return render(request, 'about.html')