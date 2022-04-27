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

clear:
	rm *class
