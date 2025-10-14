# Event-Driven Kafka Spring Cloud Streams Project

A real-time web analytics application that uses Kafka and Spring Cloud Streams to process and visualize page view events.

## 📋 Project Overview

This project demonstrates an event-driven architecture using:
- **Spring Boot** as the application framework
- **Spring Cloud Streams** for stream processing
- **Apache Kafka** as the message broker
- **Kafka Streams** for real-time analytics
- **Server-Sent Events (SSE)** for real-time frontend updates

## 🏗️ Architecture

### Components

1. **PageEvent Model** - Data record representing page view events
2. **Event Producers** - Generate and publish page events to Kafka topics
3. **Stream Processor** - Processes events and calculates real-time analytics
4. **REST Controller** - Provides endpoints for event publishing and analytics
5. **Web Dashboard** - Real-time visualization of page view analytics

## 📁 Project Structure

```
src/main/java/com/example/eventdrivenkafkaspringcloudstreams/
├── model/
│   └── PageEvent.java              # Data record for page events
├── service/
│   └── PageEventController.java    # REST controller for events & analytics
├── web/
│   └── PageEventWeb.java          # Stream processing configuration
└── resources/
    ├── application.properties      # Application configuration
    └── static/
        └── index.html             # Real-time analytics dashboard
```

## 🔧 Configuration

### Kafka Topics & Bindings

- **Topic99**: Receives events from the supplier
- **Topic1**: Output from stream processing
- **Topic3**: Consumer topic for raw events

### Key Configuration Properties

```properties
spring.cloud.function.definition=pageEventConsumer;pageEventSupplier;kStream
spring.cloud.stream.bindings.pageEventSupplier-out-0.destination=Topic99
spring.cloud.stream.bindings.kStream-in-0.destination=Topic99
spring.cloud.stream.bindings.kStream-out-0.destination=Topic1
```

## 🚀 API Endpoints

### 1. Publish Events
```http
GET /publish?namePage={pageName}&Topic={topicName}
```
Generates and publishes a random page event to the specified Kafka topic.

### 2. Real-time Analytics
```http
GET /analytics
```
Returns Server-Sent Events stream with real-time page view counts (5-second windows).

## 🔄 Stream Processing

### Processing Pipeline

1. **Event Generation**: Random page events generated every 200ms
2. **Stream Processing**: 
   - Maps page names as keys
   - Groups by page name
   - Applies 5-second tumbling windows
   - Counts events per page per window
   - Stores results in "Abdelkebir-store" state store
3. **Real-time Query**: Controller queries the state store for latest counts

### Processed Pages
- Home
- About
- Services
- Pricing

## 🎯 Usage

### Starting the Application

1. Ensure Kafka is running
2. Start the Spring Boot application
3. Access the dashboard at `http://localhost:8080`

### Generating Events

**Manual Events:**
```bash
curl "http://localhost:8080/publish?namePage=Home&Topic=Topic99"
```

**Automatic Events:**
- The application automatically generates events every 200ms
- Events are processed in real-time through the Kafka stream

### Viewing Analytics

- Open `http://localhost:8080` to see the real-time analytics dashboard
- The chart displays page view counts for each page in 5-second windows
- Data updates every second via Server-Sent Events

## 📊 Features

- **Real-time Processing**: Events processed within seconds of generation
- **Windowed Analytics**: 5-second rolling window counts
- **Interactive Queries**: Direct querying of Kafka Streams state stores
- **Visual Dashboard**: Smooth real-time chart updates
- **Multiple Event Sources**: Both manual and automatic event generation

## 🛠️ Technology Stack

- **Backend**: Spring Boot, Spring Cloud Streams
- **Stream Processing**: Kafka Streams
- **Messaging**: Apache Kafka
- **Frontend**: HTML5, JavaScript, Smoothie Charts
- **Real-time Communication**: Server-Sent Events (SSE)

## 📈 Monitoring

The application provides:
- Console logging of processed events
- Real-time analytics endpoint
- Visual dashboard with multiple page tracking
- Windowed aggregation with configurable time windows

This project demonstrates a complete event-driven microservice architecture for real-time web analytics using modern streaming technologies.
