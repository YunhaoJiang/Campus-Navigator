run:
	javac -cp .:json-simple-1.1.1.jar: --module-path javafx-sdk-17.0.2/lib --add-modules javafx.controls,javafx.fxml CampusGPSApp.java
	java -cp .:json-simple-1.1.1.jar --module-path javafx-sdk-17.0.2/lib --add-modules javafx.controls,javafx.fxml CampusGPSApp

runTests: runDataWranglerTests runAlgorithmEngineerTests runBackendDeveloperTests runFrontendDeveloperTests

runDataWranglerTests:
	javac -cp .:junit5.jar:json-simple-1.1.1.jar --module-path javafx-sdk-17.0.2/lib --add-modules javafx.controls,javafx.fxml DataWranglerTests.java
	java -jar junit5.jar -cp .:json-simple-1.1.1.jar --class-path . --scan-class-path -n DataWranglerTests

runAlgorithmEngineerTests:
	javac -cp .:junit5.jar AlgorithmEngineerTests.java
	java -jar junit5.jar --class-path . --scan-classpath -n AlgorithmEngineerTests

runBackendDeveloperTests:
	javac -cp .:junit5.jar BackendDeveloperTests.java
	java -jar junit5.jar --class-path . --scan-classpath -n BackendDeveloperTests

runFrontendDeveloperTests:
	javac --module-path javafx-sdk-17.0.2/lib --add-modules javafx.controls,javafx.fxml -cp .:junit5.jar:JavaFXTester.jar:json-simple-1.1.1.jar FrontendDeveloperTests.java
	javac --module-path javafx-sdk-17.0.2/lib --add-modules javafx.controls,javafx.fxml -cp .:junit5.jar:JavaFXTester.jar:json-simple-1.1.1.jar PreIntegrationFrontendDeveloperTests.java
	java --module-path javafx-sdk-17.0.2/lib --add-modules javafx.controls,javafx.fxml --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED -jar junit5.jar -cp .:JavaFXTester.jar:json-simple-1.1.1.jar --scan-classpath -n PreIntegrationFrontendDeveloperTests
	java --module-path javafx-sdk-17.0.2/lib --add-modules javafx.controls,javafx.fxml --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED -jar junit5.jar -cp .:JavaFXTester.jar:json-simple-1.1.1.jar --scan-classpath -n FrontendDeveloperTests
clean:
	rm *.class
