docker run --detach --env MYSQL_ROOT_PASSWORD=dummypassword --env MYSQL_USER=todos-user --env MYSQL_PASSWORD=dummytodos --env MYSQL_DATABASE=todos --name mysql-fse --publish 3307:3306 mysql:5.7

docker container run -p 9080:9080 --link=mysql-fse -e RDS_HOSTNAME=your_docker_toolbox_idaddress -e RDS_PORT=3307  fsedocker/fse-pm-app:1