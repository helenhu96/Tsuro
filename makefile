test: DNE
	javac -cp lib/junit.jar:lib/guava.jar -d bin/ src/tsuro/game/*.java src/tsuro/xmlmodel/*.java tst/tsuro/game/*.java tst/tsuro/xmlmodel/*.java
	java -jar lib/junit-platform-console-standalone-1.2.0.jar --class-path bin/ -c tsuro.game.AdministratorTest
	java -jar lib/junit-platform-console-standalone-1.2.0.jar --class-path bin/ -c tsuro.game.BoardTest
	java -jar lib/junit-platform-console-standalone-1.2.0.jar --class-path bin/ -c tsuro.game.DrawPileTest
	java -jar lib/junit-platform-console-standalone-1.2.0.jar --class-path bin/ -c tsuro.game.LeastSymmetricPlayerTest
	java -jar lib/junit-platform-console-standalone-1.2.0.jar --class-path bin/ -c tsuro.game.MostSymmetricPlayerTest
	java -jar lib/junit-platform-console-standalone-1.2.0.jar --class-path bin/ -c tsuro.game.MPlayerTest
	java -jar lib/junit-platform-console-standalone-1.2.0.jar --class-path bin/ -c tsuro.game.RandPlayerTest
	java -jar lib/junit-platform-console-standalone-1.2.0.jar --class-path bin/ -c tsuro.game.SPlayerTest
	java -jar lib/junit-platform-console-standalone-1.2.0.jar --class-path bin/ -c tsuro.game.TileTest
	java -jar lib/junit-platform-console-standalone-1.2.0.jar --class-path bin/ -c tsuro.xmlmodel.ConvertedTileTest
DNE: