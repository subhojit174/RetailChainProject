From openjdk:8
copy ./target/retail-chain-management.jar retail-chain-management.jar
CMD ["java","-jar","retail-chain-management.jar"]