# PayStream Hackathon Challenge

The PayStream Hackathon challenge focuses on building a high-performance, asynchronous payments system using a microservices architecture. The primary goal is to demonstrate proficiency in Spring Boot 3 and Apache Kafka by ensuring data integrity and system throughput during payment bursts.

## 1. Architectural Overview
The system consists of two microservices that communicate exclusively via Kafka.

| Component | Port | Primary Responsibility |
| :--- | :--- | :--- |
| **payment-ingestor** | 8080 | Validates incoming payment requests and account status before publishing to Kafka. |
| **payment-processor** | 8081 | Consumes payment events, applies business rules (e.g., HELD status for large amounts), and persists results. |
| **Kafka Broker** | 9092 | Manages the `payments.submitted` and `payments.processed` topics, each with 6 partitions. |

## 2. Implementation Requirements

* **Seeding Data:** Both services must load `accounts.json` into an H2 database on startup to facilitate account validation and reporting.
* **Validation:** The ingestor must use `@Valid` bean validation and return a specific JSON error structure for 400, 404, 409 (duplicate payments), and 422 (suspended accounts) errors.
* **Asynchronous Flow:**
    * Ingestor receives `POST /api/payments` and validates account status.
    * If valid, it publishes a `PaymentEvent` to Kafka using `debitAccountId` as the partition key.
    * Processor consumes the event, flags payments exceeding £250,000 as HELD, and persists the outcome to the `payment_outcomes` table.

## 3. Kafka Performance Tuning
You are required to configure and comment on at least two producer and two consumer properties in `application.yml`. Configuration without comments will result in a zero score for the tuning dimension.

* **Producer (Ingestor):** Consider `acks` for durability, `linger.ms` for batching efficiency, or `enable.idempotence` to prevent duplicate writes.
* **Consumer (Processor):** Consider `listener.concurrency` (a value up to 6 matches the partitions) and `max.poll.records` to balance throughput with processing timeouts.



