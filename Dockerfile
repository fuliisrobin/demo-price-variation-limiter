FROM 10.5.1.16/library/openjdk:25-oraclelinux9
WORKDIR /apps
COPY demo-trading-system.jar .
CMD ["java", "-jar", "demo-trading-system.jar"]
