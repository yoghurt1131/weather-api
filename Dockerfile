FROM maven:3.6.0-jdk-8-slim

COPY cmd.sh /
RUN mkdir /app
RUN groupadd -r weather && useradd -r -g weather weather;\
  chown -R weather:weather /app
WORKDIR /app

EXPOSE 8080
USER weather
CMD ["/cmd.sh"]
