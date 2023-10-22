# local JSON database with REST server to use in local environment

REST server to load & save JSON data. Data is saved in a JSON file at shutdown.

Useful with cron job which need to load & save data, but should avoid file system operations.

# Configuration

First optional argument is the filename of the JSON file. Default is "localDatabase.json". Second optional argument is the port of the server. Default is 6491.

## Usage

### New

Get an authorization token to save data for an application.

```
POST http://localhost:6491/data/new
```

### Save

```
PUT http://localhost:6491/data/[authorization]/[key]
```

The body must be JSON data to save.

### Load

```
GET http://localhost:6491/data/[authorization]/[key]
```

### Delete

```
DEL http://localhost:6491/data/[authorization]/[key]
```