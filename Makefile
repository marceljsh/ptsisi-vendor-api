.PHONY: build run docker-build docker-run

build:
	./mvnw clean install

run:
	./mvnw spring-boot:run

docker-build:
	docker build -t vendorapi .

docker-run:
	docker run -p 8080:8080 vendorapi
