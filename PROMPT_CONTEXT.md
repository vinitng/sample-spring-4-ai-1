I am working on an enterprise core banking microservice built with Spring Boot, Spring Data JPA, Spring AI (PgVector), and PostgreSQL.

### Tech Stack & Configuration:
- Language & Runtime: Java 21, Spring Boot 3.3.4, Gradle 9.x
- Database: PostgreSQL on port 5432, custom schema `app_core`
- Server Port: 8081
- Database Entities:
    - `Customer` (`customer_id` UUID PK, `external_ref_id` UNIQUE, `full_name`, `email`, `status`, `is_deleted`, `created_at`, `updated_at`)
    - `Account` (`account_id` BIGSERIAL PK, `customer_id` FK, `account_number` UNIQUE, `account_type`, `currency` VARCHAR(3), `balance`, `hold_balance`, `status`, `@Version Long rowVersion`, `created_at`, `updated_at`)
- AI / Vector Store: Spring AI PgVector starter using table `public.vector_store`
- Architecture Layers:
    - Database -> Repositories (`CustomerRepository`, `AccountRepository`)
    - Domain Service Contract -> `BankingService` (interface) & `BankingServiceImpl` (implementation)
    - Orchestration Layer -> `BankingFacade` (DTO/Entity conversion, business orchestration)
    - Core REST API -> `BankingController` (`/api/v1/banking/**`)
    - UI BFF Layer -> `BankingUiAdapter` & `BankingUiController` (`/api/v1/ui/banking/**` returning `UiResponse<T>` envelopes)
    - Frontend: `src/main/resources/static/index.html` Single-Page Dashboard
    - Documentation: Springdoc OpenAPI configured at `/docs`

### Critical Architecture Rules & Constraints:
1. Lazy Loading: `Customer.accounts` is `FetchType.LAZY`. Always use explicit JPQL `JOIN FETCH` queries (e.g., `findAllWithAccounts()`, `findByExternalRefIdWithAccounts()`) in the repository when reading account collections to prevent `LazyInitializationException`.
2. Bean Conflict: Do NOT declare a custom `vectorStore` bean if using Spring AI auto-configuration (`allow-bean-definition-overriding=true` is enabled as a safeguard).
3. Types & Optimistic Locking: PostgreSQL `currency` is `VARCHAR(3)`. Always ensure `@Version Long rowVersion` is initialized to prevent null constraint violations on insert.
4. Response Wrappers: UI endpoints must wrap payloads inside `UiResponse<T>` (`{ success, message, data, timestamp }`).

---

### My Request:
[INSERT WHAT YOU WANT TO DO HERE, e.g.:
- "Add an ACID-compliant fund transfer method with optimistic locking between accounts."
- "Integrate a Spring AI RAG pipeline over our banking product FAQ PDFs stored in pgvector."
- "I encountered the following error trace: <paste error>"]