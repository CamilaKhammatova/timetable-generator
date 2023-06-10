### Запуск микросервиса (Docker)

- `mvn clean package -DskipTests` - переход в папку deploy
- `docker-compose -p application up` - запуск docker контейнеров

### API
- `http://localhost:8080/api/swagger-ui.html` - OpenApi