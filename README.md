# Email Service

Сервис для асинхронной отправки email сообщений с использованием RabbitMQ и Spring Boot.

## Описание

Сервис предоставляет возможность отправки различных типов email сообщений:
- Регистрационные письма
- Письма для восстановления пароля
- Рекламные рассылки
- Текстовые сообщения

### Особенности
- Асинхронная обработка запросов через RabbitMQ
- Фиксированный пул из 50 потоков для параллельной обработки писем
- HTML шаблоны для разных типов писем (поддержка динамической загрузки из БД)
- Отслеживание статуса отправки
- Механизм обратной связи через RabbitMQ
- Поддержка горизонтального масштабирования
- Автоматическая регистрация в Eureka Server

## Технологии

- Java 21
- Spring Boot 3.2.3
- Spring Mail
- Spring AMQP (RabbitMQ)
- Spring Cloud Netflix Eureka Client
- Project Lombok
- SpringDoc OpenAPI (API Documentation)
- Spring Data JPA (опционально, для работы с шаблонами в БД)

## Масштабирование и обнаружение сервисов

### Eureka интеграция

Сервис автоматически регистрируется в Eureka Server, что позволяет:
- Динамическое обнаружение инстансов сервиса
- Балансировка нагрузки между инстансами
- Автоматическое восстановление при сбоях

```properties
# Настройка подключения к существующему Eureka Server
eureka.client.service-url.defaultZone=http://eureka-server:8761/eureka/
eureka.instance.prefer-ip-address=true
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
```

### Горизонтальное масштабирование

Сервис поддерживает горизонтальное масштабирование через:

1. **Docker Swarm**
   - Встроенный инструмент для оркестрации контейнеров
   - Позволяет управлять кластером Docker-хостов как единым виртуальным хостом
   - Основные преимущества:
     - Встроенная балансировка нагрузки
     - Автоматическое восстановление при сбоях
     - Простота управления через Docker CLI
     - Встроенная система безопасности
   ```bash
   # Инициализация Swarm
   docker swarm init
   
   # Добавление рабочих нод в кластер
   docker swarm join --token <token> <manager-ip>:2377
   
   # Масштабирование сервиса
   docker service scale mail-service=3
   
   # Мониторинг состояния сервиса
   docker service ls
   docker service ps mail-service
   ```

2. **Kubernetes**
   [существующий контент]

3. **Docker Compose**
   ```bash
   # Запуск нескольких инстансов
   docker-compose up --scale mail-service=3 -d
   ```

### Отказоустойчивость

- Автоматическая реконнект к RabbitMQ при потере соединения
- Graceful shutdown с таймаутом 60 секунд
- Retry механизм для отправки email
- Балансировка нагрузки между инстансами через Eureka

### Мониторинг масштабирования

- Eureka Dashboard: `http://eureka-server:8761`
- RabbitMQ Management: `http://rabbitmq:15672`
- Метрики состояния сервиса через Spring Actuator

## Конфигурация

### Требования
- JDK 21
- RabbitMQ Server
- SMTP сервер (например, Mail.ru SMTP)
- База данных (опционально, для хранения шаблонов)
- Eureka Server (для service discovery)

### Настройка окружения

1. **Настройка RabbitMQ**
```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

2. **Настройка SMTP**
```properties
spring.mail.host=smtp.mail.ru
spring.mail.port=465
spring.mail.username=your-email@mail.ru
spring.mail.password=your-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.ssl.enable=true
```

3. **Настройка шаблонов**
```properties
# Режим работы с шаблонами (file/database)
email.templates.mode=file
# Путь к файлам шаблонов (если используется режим file)
email.templates.path=/templates
```

## Использование

### Типы писем (Subject)

```java
public enum Subject {
    REGISTRATION,    // Письмо с данными регистрации
    PASSWORD_RESET,  // Письмо для сброса пароля
    ADVERTISING,     // Рекламное письмо
    TEXT_MESSAGE     // Обычное текстовое письмо
}
```

### Статусы обработки (Status)

```java
public enum Status {
    ACCEPTED,    // Запрос принят в обработку
    PROCESSING,  // Email в процессе отправки
    SENT,        // Email успешно отправлен
    FAILED       // Ошибка при отправке
}
```

### Формат запроса

```json
{
    "id": 1,
    "email": "recipient@example.com",
    "sender": "sender@mail.ru",
    "recipientName": "John Doe",
    "subject": "REGISTRATION",
    "content": "Данные для регистрации или текст письма"
}
```

### Формат ответа

```json
{
    "requestId": 1,
    "email": "recipient@example.com",
    "status": "SENT"
}
```

## Архитектура

### Основные компоненты

1. **EmailService**
   - Обработка email запросов через фиксированный пул потоков (50 потоков)
   - Асинхронная отправка через CompletableFuture
   - Отслеживание статуса отправки
   - Graceful shutdown с таймаутом 60 секунд
   - Поддержка множественных инстансов

2. **ResponseService**
   - Отправка статусов в RabbitMQ
   - Обработка ошибок
   - Масштабируемая обработка ответов

3. **EmailTemplateUtil**
   - HTML шаблоны для разных типов писем
   - Поддержка загрузки шаблонов из файловой системы или БД
   - Форматирование контента
   - Кэширование шаблонов

### Процесс обработки запроса

1. Получение запроса
2. Отправка статуса ACCEPTED
3. Асинхронный запуск обработки в пуле потоков
4. Отправка статуса PROCESSING
5. Загрузка и применение шаблона (из файла или БД)
6. Отправка email
7. Отправка финального статуса (SENT/FAILED)

### Многопоточная обработка

- Фиксированный пул из 50 потоков для параллельной обработки писем
- Автоматическое управление очередью задач
- Graceful shutdown при остановке приложения
- Максимальная пропускная способность: 50 одновременных отправок

## Мониторинг

### Логирование

- Информационные логи для успешных операций
- Логи ошибок при сбоях
- Отслеживание всех этапов обработки
- Логирование состояния пула потоков

### API Documentation

Доступно по адресу: `http://localhost:9898/api-docs`

## Разработка

### Добавление нового типа письма

1. Добавить новый тип в enum `Subject`
2. Создать HTML шаблон (в файловой системе или БД)
3. Добавить обработку в метод `configureEmailBySubject`

### Запуск для разработки

```bash
./mvnw spring-boot:run
```

### Сборка

```bash
./mvnw clean package
```

### Docker

```bash
docker-compose up -d
```

### Запуск в production

```bash
# Запуск с подключением к существующему Eureka Server
export EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://your-eureka-server:8761/eureka/
./mvnw spring-boot:run

# Запуск через Docker с масштабированием
docker-compose up --scale mail-service=3 -d
```

## Безопасность

- Использование SSL для SMTP
- Аутентификация в RabbitMQ
- Валидация входных данных
- Безопасная обработка паролей
- Контроль количества одновременных подключений

## Лицензия

Apache License 2.0 

### Архитектура масштабирования

#### Docker Swarm
- **Manager Nodes**: Управляют кластером и могут выполнять задачи
- **Worker Nodes**: Выполняют только задачи
- **Services**: Описывают желаемое состояние приложения
- **Tasks**: Контейнеры, запущенные в рамках сервиса
- **Replicas**: Количество экземпляров сервиса

```yaml
# Пример docker-compose.yml для Swarm
version: '3.8'
services:
  mail-service:
    image: mail-service:latest
    deploy:
      replicas: 3
      restart_policy:
        condition: on-failure
      update_config:
        parallelism: 1
        delay: 10s
    environment:
      - SPRING_PROFILES_ACTIVE=swarm
      - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/
    networks:
      - mail-network
``` 