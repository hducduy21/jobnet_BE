1. Command rebuild index mapping
    - make sure migrated: 
        + python manage.py makemigrations
        + python manage.py migrate post
    python manage.py search_index --rebuild
