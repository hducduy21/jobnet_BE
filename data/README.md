# MongoDB Data Backup and Restore

## Dump Data:
Dump data from MongoDB container:
```bash
docker exec mongo mongodump -u root -p password
docker cp mongo:/dump mongo
```

## Restore Data:
Remove existing data in MongoDB container:
```bash
docker exec mongo rm -r data/db
```

Start MongoDB container. After that, restore the data:
```bash
docker cp mongo mongo:/dump
docker exec mongo mongorestore -u root -p password dump
```
