# E-Commerce Distributed Systems Project

Bu proje, distributed systems problemlerini (distributed lock, distributed transaction, distributed observation) incelemek ve çözümler geliştirmek için tasarlanmış kapsamlı bir e-ticaret uygulamasıdır.

## Proje Amacı

Bu proje, aşağıdaki distributed systems ve database problemlerini ve çözümlerini deneyimlemenize olanak sağlar:

- **Distributed Lock**: Race condition'ları önlemek için distributed locking mekanizmaları
- **Distributed Transaction**: Multi-service işlemlerinde transaction yönetimi (Saga Pattern, Two-Phase Commit)
- **Distributed Observation**: Event sourcing, audit logging, distributed tracing
- **Message Queue Patterns**: RabbitMQ ile asynchronous messaging ve event-driven architecture
- **Caching Strategies**: Redis ile cache invalidation ve distributed caching
- **Database Problems**: Index optimizasyonu, query analizi, connection pooling (HikariCP), Hibernate performans tuning
- **Idempotency**: Idempotent operations ve retry mekanizmaları

## Gereksinimler

- **Docker** ve **Docker Compose** (servisler için)
- **Java 21** (SDKMAN ile kurulabilir)
- **Maven 3.9+**
- **Python 3.8+** (data generation için)
- **psycopg** (PostgreSQL Python driver)
- **Faker** (test data generation için)

## Kurulum

### 1. SDKMAN ile Java ve Maven Kurulumu

Proje Java 21 kullanıyor. SDKMAN ile kurulum yapabilirsiniz:

```bash
# SDKMAN'ı kur (eğer yoksa)
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"

# Java 21 ve Maven kur
sdk install java 21.0.1-tem
sdk install maven 3.9.9

# .sdkmanrc dosyası varsa otomatik kurulum
sdk env install
```

### 2. Python Kurulumu

#### Python'un Kurulu Olup Olmadığını Kontrol Et

```bash
python3 --version
# veya
python --version
```

Python 3.8 veya üzeri bir sürüm olmalıdır.

#### Python Kurulumu (Eğer yoksa)

**macOS:**
```bash
# Homebrew ile
brew install python3

# veya Python.org'dan indirip kur
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install python3 python3-pip python3-venv
```

**Linux (CentOS/RHEL):**
```bash
sudo yum install python3 python3-pip
```

**Windows:**
- Python.org'dan Python 3.8+ sürümünü indirip kurun
- Kurulum sırasında "Add Python to PATH" seçeneğini işaretleyin

#### Virtual Environment (venv) Oluşturma

Virtual environment kullanmak önerilir. Proje bağımlılıklarını izole eder:

```bash
# data-generation dizinine git
cd data-generation

# Virtual environment oluştur
python3 -m venv .venv

# Virtual environment'ı aktif et
# Linux/Mac:
source .venv/bin/activate

# Windows:
.venv\Scripts\activate

# Aktif olduğunu kontrol et (prompt'ta (.venv) görünmeli)
which python  # Linux/Mac
where python  # Windows
```

#### Python Bağımlılıklarını Kur

Virtual environment aktifken:

```bash
# pip'i güncelle (önerilir)
pip install --upgrade pip

# Proje bağımlılıklarını kur
pip install psycopg faker
```

#### Virtual Environment'ı Deaktif Etme

İşiniz bittiğinde:

```bash
deactivate
```

#### Notlar

- Virtual environment her yeni terminal açılışında tekrar aktif etmeniz gerekir
- `.venv` dizini `.gitignore`'da olduğu için Git'e commit edilmez
- Farklı projeler için farklı virtual environment'lar kullanabilirsiniz

### 3. Docker Servislerini Başlat

Proje root dizininde Docker Compose ile servisleri başlatın:

```bash
cd /path/to/project
docker compose up -d
```

Bu komut şu servisleri başlatır:
- **PostgreSQL** (Port: 2345)
  - Database: `ecommerce`
  - User: `fsk`
  - Password: `fsk`
- **RabbitMQ** (Port: 2765, Management UI: 27651)
  - User: `fsk`
  - Password: `fsk`
  - Management UI: http://localhost:27651
- **Redis** (Port: 9736)

Servislerin durumunu kontrol etmek için:
```bash
docker compose ps
```

### 4. Veritabanı Tablolarını Oluştur

```bash
cd data-generation
python create_tables.py
```

Bu script tüm tabloları, foreign key'leri ve constraint'leri oluşturur.

### 5. Test Verilerini Oluştur

Not: Bu işlem büyük miktarda veri oluşturur (500K users, 200K products, 4M orders) ve uzun sürebilir.

```bash
cd data-generation
python generate_data.py
```

Veri üretimi:
- **500,000** kullanıcı
- **200,000** ürün
- **4,000,000** sipariş
- Her kullanıcı için 1-3 adres, 1-4 kredi kartı, 1-5 hobi
- Her sipariş için order items, payment ve shipment kayıtları

### 6. Maven Build

```bash
cd ecommerce
mvn clean install -X
```

`-X` flag'i detaylı log çıktısı için kullanılır. Normal build için:
```bash
mvn clean install
```

### 7. Uygulamayı Çalıştır

```bash
cd ecommerce
mvn spring-boot:run
```

Uygulama `http://localhost:7070/api` adresinde çalışacaktır.

## Proje Yapısı

```
.
├── compose.yaml                 # Docker Compose servis tanımları
├── ecommerce/                   # Spring Boot uygulaması
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/fsk/ecommerce/
│   │       │       ├── entity/          # JPA Entity'ler
│   │       │       ├── repository/      # Spring Data JPA Repositories
│   │       │       ├── service/         # Business Logic
│   │       │       ├── controller/      # REST Controllers
│   │       │       └── mapper/          # MapStruct Mappers
│   │       └── resources/
│   │           └── application.yml      # Spring Boot configuration
│   └── pom.xml
└── data-generation/             # Python data generation scripts
    ├── create_tables.py        # Tablo oluşturma script'i
    ├── generate_data.py        # Test verisi üretme script'i
    └── clear_database.py       # Veritabanını temizleme script'i
```

## Veritabanı Şeması

### Ana Tablolar

- **users**: Kullanıcı bilgileri
- **addresses**: Kullanıcı adresleri
- **accounts**: Kullanıcı hesapları
- **credit_cards**: Kayıtlı kredi kartları
- **hobbies**: Hobi listesi
- **user_hobbies**: Kullanıcı-Hobi ilişkisi (Many-to-Many)
- **products**: Ürün kataloğu
- **orders**: Siparişler
- **order_items**: Sipariş kalemleri
- **payments**: Ödemeler
- **shipments**: Kargo bilgileri

## Distributed Systems ve Database Denemeleri

### Distributed Lock

Stock management, account balance updates gibi race condition'ları test edebilirsiniz:

```java
// Örnek: Product stock güncellemesi
// Redis veya PostgreSQL advisory locks kullanarak
```

### Distributed Transaction

Order creation, payment processing gibi multi-step işlemlerde:

```java
// Saga Pattern implementasyonu
// Event-driven compensation
```

### Distributed Observation

- **Event Sourcing**: Tüm değişiklikleri event olarak kaydetme
- **Audit Logging**: Değişiklik geçmişini takip etme
- **Distributed Tracing**: Request'leri trace etme (correlation ID)

### RabbitMQ Patterns

- **Event Publishing**: Order created, payment processed gibi event'ler
- **Message Queues**: Asynchronous processing
- **Dead Letter Queues**: Failed message handling

### Redis Caching

- **Cache Invalidation**: Product, user cache invalidation stratejileri
- **Distributed Cache**: Multi-instance cache synchronization

### Database Problems

- **Index Optimization**: PostgreSQL'de index stratejileri, composite index'ler, partial index'ler
- **Query Analysis**: EXPLAIN ANALYZE ile query plan analizi, slow query tespiti
- **Connection Pooling**: HikariCP yapılandırması, pool size tuning, connection timeout ayarları
- **Hibernate Performance**: N+1 problem, lazy loading, batch fetching, second-level cache
- **Database Statistics**: ANALYZE komutu ile istatistik güncelleme, query optimizer için
- **Lock Contention**: Row-level locking, table-level locking, deadlock detection

### Spring Framework Features

- **MapStruct**: Type-safe bean mapping, compile-time code generation, Entity-DTO dönüşümleri
- **@Transactional**: Transaction yönetimi, propagation seviyeleri, isolation levels, rollback stratejileri

## Yardımcı Script'ler

### Veritabanını Temizle

```bash
cd data-generation
python clear_database.py
```

Dikkat: Bu script tüm tabloları siler!

### Tabloları Yeniden Oluştur

```bash
cd data-generation
python create_tables.py
```

## Veri İstatistikleri

Oluşturulan test verisi:
- **Users**: 500,000
- **Products**: 200,000
- **Orders**: 4,000,000
- **Order Items**: ~8,000,000 (her sipariş için 2 item)
- **Addresses**: ~1,000,000 (her kullanıcı için 1-3 adres)
- **Credit Cards**: ~1,500,000 (her kullanıcı için 1-4 kart)
- **Payments**: 4,000,000
- **Shipments**: 4,000,000

## Yapılandırma

### Application Configuration

`ecommerce/src/main/resources/application.yml` dosyasında:
- Database connection
- Redis configuration
- RabbitMQ configuration
- JPA/Hibernate settings
- Logging configuration

### Docker Compose

`compose.yaml` dosyasında servis portları ve environment variable'lar tanımlıdır.

## Notlar

- Veritabanı şeması JPA/Hibernate ile otomatik oluşturulabilir (`ddl-auto: update`)
- Manuel tablo oluşturma için `create_tables.py` kullanılabilir
- Test verisi üretimi uzun sürebilir (yaklaşık 30-60 dakika)
- Index'ler `create_tables.py` script'inde yorum satırı olarak bulunur, ihtiyaca göre aktif edilebilir

## Katkıda Bulunma

Bu proje distributed systems ve database problemlerini öğrenmek ve denemek için tasarlanmıştır. Yeni pattern'ler, çözümler ve örnekler ekleyebilirsiniz.
