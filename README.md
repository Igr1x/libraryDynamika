### Запуск
#### Docker
Для запуска с помощью Docker :
1) Выполнить команду `docker-compose up` для создания контейнера с БД
2) Выполнить команду `docker build library .` для создания образа приложения
3) Выполнить команду `docker run library`
#### java -jar
1) Выполнить команду docker-compose up для создания контейнера с БД
2) Выполнить команду `./mvnw clean package`
3) Запустить приложения с помощью команды `java -jar target/libraryDynamika-0.0.1.jar`
