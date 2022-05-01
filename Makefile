runDataWranglerTests: 
	javac -cp .:junit5.jar:json-simple-1.1.1.jar DataWranglerTests.java
	java -jar junit5.jar -cp .:json-simple-1.1.1.jar --class-path . --scan-class-path -n DataWranglerTests
clean:
	rm *.class                           
runFrontendDeveloperTests: FrontendDeveloperTests.class GPSFrontend.class FDGPSBackend.class FDPlace.class CS400Graph.class

FrontendDeveloperTests.class: FrontendDeveloperTests.java
	javac FrontendDeveloperTests.java

GPSFrontend.class: GPSFrontend.java
	javac GPSFrontend.java

FDGPSBackend.class: FDGPSBackend.java
	javac FDGPSBackend.java

FDPlace.class: FDPlace.java
	javac FDPlace.java

CS400Graph.class: CS400Graph.java
	javac CS400Graph.java

runBackendDeveloperTests: BackendDeveloperTests.class
	java -jar junit5.jar --class-path . --scan-classpath -n BackendDeveloperTests

BackendDeveloperTests.class: BackendDeveloperTests.java
	javac -cp .:junit5.jar BackendDeveloperTests.java -Xlint
