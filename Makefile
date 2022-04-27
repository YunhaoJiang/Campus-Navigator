runTests: BackendDeveloperTests.class
	java -jar junit5.jar --class-path . --scan-classpath -n BackendDeveloperTests

BackendDeveloperTests.class: BackendDeveloperTests.java
	javac -cp .:junit5.jar BackendDeveloperTests.java -Xlint

clean:
	rm *.class
