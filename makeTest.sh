mkdir MadeTests
find ./MadeTests -type f -name '*.class' -delete
javac -cp ./lib/junit-4.12.jar:./lib/junit-4.12-extended-1.0.4.jar -d MadeTests ./src/Classes/*.java
java -cp ./lib/hamcrest-core-1.3.jar:./lib/junit-4.12.jar:./lib/junit-4.12-extended-1.0.4.jar:./MadeTests org.junit.runner.JUnitCore Classes.BoardTest
java -cp ./lib/hamcrest-core-1.3.jar:./lib/junit-4.12.jar:./lib/junit-4.12-extended-1.0.4.jar:./MadeTests org.junit.runner.JUnitCore Classes.TileTest
java -cp ./lib/hamcrest-core-1.3.jar:./lib/junit-4.12.jar:./lib/junit-4.12-extended-1.0.4.jar:./MadeTests org.junit.runner.JUnitCore Classes.DrawPileTest
java -cp ./lib/hamcrest-core-1.3.jar:./lib/junit-4.12.jar:./lib/junit-4.12-extended-1.0.4.jar:./MadeTests org.junit.runner.JUnitCore Classes.SPlayerTest
java -cp ./lib/hamcrest-core-1.3.jar:./lib/junit-4.12.jar:./lib/junit-4.12-extended-1.0.4.jar:./MadeTests org.junit.runner.JUnitCore Classes.AdministratorTest
java -cp ./lib/hamcrest-core-1.3.jar:./lib/junit-4.12.jar:./lib/junit-4.12-extended-1.0.4.jar:./MadeTests org.junit.runner.JUnitCore Classes.MPlayerTest
java -cp ./lib/hamcrest-core-1.3.jar:./lib/junit-4.12.jar:./lib/junit-4.12-extended-1.0.4.jar:./MadeTests org.junit.runner.JUnitCore Classes.RandPlayerTest
java -cp ./lib/hamcrest-core-1.3.jar:./lib/junit-4.12.jar:./lib/junit-4.12-extended-1.0.4.jar:./MadeTests org.junit.runner.JUnitCore Classes.LeastSymmetricPlayerTest
java -cp ./lib/hamcrest-core-1.3.jar:./lib/junit-4.12.jar:./lib/junit-4.12-extended-1.0.4.jar:./MadeTests org.junit.runner.JUnitCore Classes.MostSymmetricPlayerTest