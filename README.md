# Read Me

Swagger documentation available on: http://localhost:8080/swagger-ui.html

* I included only a couple of examples for test just to show how I would do it.
* If I was working on a bigger project I would've used MapStruct for mapping, instead of implementing one manually.
* I've only implemented one use of Fixer -> Conversion endpoint
* Api_Key for Fixer needs to be injected into the docker container (I will not be
providing api key through git, as that's a security risk)

## How to run

1. Build jar using `gradle build`
2. Run `docker build -t product-service .` while in the project main directory
3. Run `docker run -p {PORT}:8080 --env FIXER_API_KEY={YOUR_API_KEY} product-service`
4. Access via `http://localhost:{PORT}/swagger-ui.html`