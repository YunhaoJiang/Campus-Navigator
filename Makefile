runTest:
	javac -cp .:junit5.jar:json-simple-1.1.1.jar DataWranglerTests.java
	java -jar junit5.jar -cp .:json-simple-1.1.1.jar --class-path . --scan-class-path -n DataWranglerTests
clean:
	rm *.class                           
