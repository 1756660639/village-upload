FROM openjdk:8-jdk
LABEL maintainer=liuxx


#启动自行加载
ENV PARAMS="--server.port=8080 --spring.datasource.url=jdbc:mysql://village-mysql-vljs.village:3306/upload?characterEncoding=UTF-8&useSSL=false&autoReconnect=true&failOverReadOnly=false&useUnicode=true&serverTimezone=UTC --spring.datasource.password=root --spring.redis.host=village-redis-4spj.village"
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

COPY target/*.jar /app.jar
EXPOSE 8080

# hospital-manage
ENTRYPOINT ["/bin/sh","-c","java -Dfile.encoding=utf8  -Djava.security.egd=file:/dev/./urandom -jar /app.jar ${PARAMS}"]