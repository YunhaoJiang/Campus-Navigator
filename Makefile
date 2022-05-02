run:
	javac -cp .:json-simple-1.1.1.jar: --module-path javafx-sdk-17.0.2/lib --add-modules javafx.controls,javafx.fxml CampusGPSApp.java
	java -cp .:json-simple-1.1.1.jar --module-path javafx-sdk-17.0.2/lib --add-modules javafx.controls,javafx.fxml CampusGPSApp

runDataWranglerTests:
	javac -cp .:junit5.jar:json-simple-1.1.1.jar DataWranglerTests.java
	java -jar junit5.jar -cp .:json-simple-1.1.1.jar --class-path . --scan-class-path -n DataWranglerTests

runAlgorithmEngineerTests:
	javac -cp .:junit5.jar AlgorithmEngineerTests.java
	java -jar junit5.jar --class-path . --scan-classpath -n AlgorithmEngineerTests

runBackendDeveloperTests:
	javac -cp .:junit5.jar BackendDeveloperTests.java
	java -jar junit5.jar --class-path . --scan-classpath -n BackendDeveloperTests

runFrontendDeveloperTests:
	javac -cp .:junit5.jar FrontendDeveloperTests.java
	java -jar junit5.jar --class-path . --scan-classpath -n FrontendDeveloperTests

clean:
	rm *.class
