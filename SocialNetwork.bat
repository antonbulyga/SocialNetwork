mysql -uroot -p social_network < C:\Users\Anton\Documents\SocialNetwork\main\src\main\resources\sql\fill_in.sql

call mvn clean install

cd main

call mvn spring-boot:start

cmd /k