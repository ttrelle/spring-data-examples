FROM java:8
VOLUME /tmp
ADD spring-boot-mongo*.jar mongorest.jar
RUN sh -c 'touch /mongorest.jar'
ENV JAVA_OPTS="-Xmx256m -Xms128m"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /mongorest.jar" ]