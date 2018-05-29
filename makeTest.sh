mkdir MadeTests
find ./MadeTests -type f -name '*.class' -delete
javac -cp ./lib/junit-4.12.jar:./lib/junit-4.12-extended-1.0.4.jar -d MadeTests ./tst/tsuro/*.java
java -cp ./lib/hamcrest-core-1.3.jar:./lib/junit-4.12.jar:./lib/junit-4.12-extended-1.0.4.jar:./MadeTests org.junit.runner.JUnitCore tsuro.BoardTest
java -cp ./lib/hamcrest-core-1.3.jar:./lib/junit-4.12.jar:./lib/junit-4.12-extended-1.0.4.jar:./MadeTests org.junit.runner.JUnitCore tsuro.TileTest
java -cp ./lib/hamcrest-core-1.3.jar:./lib/junit-4.12.jar:./lib/junit-4.12-extended-1.0.4.jar:./MadeTests org.junit.runner.JUnitCore tsuro.DrawPileTest
java -cp ./lib/hamcrest-core-1.3.jar:./lib/junit-4.12.jar:./lib/junit-4.12-extended-1.0.4.jar:./MadeTests org.junit.runner.JUnitCore tsuro.SPlayerTest
java -cp ./lib/hamcrest-core-1.3.jar:./lib/junit-4.12.jar:./lib/junit-4.12-extended-1.0.4.jar:./MadeTests org.junit.runner.JUnitCore tsuro.AdministratorTest
java -cp ./lib/hamcrest-core-1.3.jar:./lib/junit-4.12.jar:./lib/junit-4.12-extended-1.0.4.jar:./MadeTests org.junit.runner.JUnitCore tsuro.MPlayerTest
java -cp ./lib/hamcrest-core-1.3.jar:./lib/junit-4.12.jar:./lib/junit-4.12-extended-1.0.4.jar:./MadeTests org.junit.runner.JUnitCore tsuro.RandPlayerTest
java -cp ./lib/hamcrest-core-1.3.jar:./lib/junit-4.12.jar:./lib/junit-4.12-extended-1.0.4.jar:./MadeTests org.junit.runner.JUnitCore tsuro.LeastSymmetricPlayerTest
java -cp ./lib/hamcrest-core-1.3.jar:./lib/junit-4.12.jar:./lib/junit-4.12-extended-1.0.4.jar:./MadeTests org.junit.runner.JUnitCore tsuro.MostSymmetricPlayerTest