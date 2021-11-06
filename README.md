## Running the application

*Disclaimer*: This has only been tested in 2x MacOS enviornment only (with the intel chip).

### Fast route: With docker and docker compose

#### Installation

Make sure to have the following installed:

1. [Docker](https://docs.docker.com/get-docker/)
2. [Docker-Compose](https://docs.docker.com/compose/install/)


#### Running user docker-compose

Stand in the root directory of the repo and run

```
docker-compose up
```

This will now create 3 different containers. 

1. PostgreSQL which is used for storage. (Saves data to the `data` folder found in the root of the repo).
2. The Java 17 Spring boot backend service.
3. The frontend written in react.

You should now be able to reach the application in your browser by going to:

```
http://localhost:3000/
```

The expected view for the landing page is the following

![Alt text](./img/service.png)

From here you can add a new Service for monitoring by specifying an URL and a name. 

See example of https://google.com

