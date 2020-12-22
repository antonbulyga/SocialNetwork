mysql -uroot -p social_network < C:\Users\Anton\Documents\SocialNetwork\main\src\main\resources\sql\fill_in.sql

call mvn clean install

call java -jar main/target/main-0.0.1-SNAPSHOT.jar

cd main

call mvn spring-boot:start
