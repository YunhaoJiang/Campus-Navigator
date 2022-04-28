runTest:
	javac -cp .:junit5.jar DataWranglerTests.java
	java -jar json-simple-1.1.1.jar --class-path . --scan-class-path -n DataWranglerTests
	java -jar junit5.jar --class-path . --scan-class-path -n DataWranglerTests
clean:
	rm *.class
~                               
