to run the project use:
docker run -it --rm --name docker-maven-project -v "$(pwd)":/usr/src/mymaven -w /usr/src/mymaven maven:latest mvn clean install
docker-compose build
docker-compose up

