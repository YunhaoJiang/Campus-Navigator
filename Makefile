runTests:
		javac -cp .:junit5.jar AlgorithmEngineerTests.java
		java -jar junit5.jar --class-path . --scan-class-path -n AlgorithmEngineerTests
clean:
		rm *.class
