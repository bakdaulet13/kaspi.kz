# 🚗 СТО
## 📌 Описание

Проект реализует backend-систему для управления заявками на ремонт автомобилей.  
Основная сущность — **Заявка**.  
Заявки могут менять статусы, а вся история переходов фиксируется.

- Создание заявки
- Получение всех заявок по клиенту
- Получение всех заявок по определенному статусу
- Изменение статуса заявки через Kafka
- Сохранение истории изменения статусов (кто, когда, почему)
- Мок-уведомление клиента через логирование

## 🛠️ Технологии

- Java 21
- Spring Boot 3.5.0
- Apache Kafka
- PostgreSQL
- Docker + Docker Compose

## 🚀 Как запустить

### 1. Клонируйте репозиторий

git clone

### 2. Соберите проект

mvn clean install -DskipTests

### 3. Запустите с помощью Docker Compose

docker-compose up --build

### 4. Проект будет доступно

http://localhost:8080


📘 Пример API

POST /api/v1/order/create — создать заявку

POST /api/v1/order/update — создать заявку

GET /api/v1/order/username — получить заявки по клиенту

GET /api/v1/order/status — получить заявки по статусу

📦 Статусы заявки

    SUBMITTED

    IN_REVIEW

    IN_PROGRESS

    FINISHED