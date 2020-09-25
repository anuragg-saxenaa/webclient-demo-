
# Spring 5 WebClient with Spring Boot

## 1. Introduction

Usually when we looking for a client to perform HTTP requests in the **Spring Boot**  application we would have probably run into the  _**RestTemplate**_  or reactive  _**WebClient**_.

According to the official  [documentation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html) **RestTemplate**  class is in the maintenance mode since Spring 5 and we should consider using  **WebClient**  which is a part of the Spring WebFlux module. But what if we would like to use it in a standard Spring MVC application without migrating to Spring WebFlux?

In this article, we’ll walk you through the process of setup and consuming external APIs using  **WebClient with Spring Boot**.

## 2. What is WebClient?

As we’ve mentioned in the introduction-  [WebClient](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/reactive/function/client/WebClient.html)  is a non-blocking, reactive client, with which making the calls becomes really easy. It is a part of the spring-webflux module, which we need to add to our project to use it.
## 3. Dependencies
maven :

    <dependency>  
     <groupId>org.springframework.boot</groupId>  
     <artifactId>spring-boot-starter-webflux</artifactId>  
    </dependency>

## 4. Create a Configuration Class

     

  
    @Bean 
    public WebClient webclient() {  WebClient webClient = WebClient  
            .builder()  
            .baseUrl("https://jsonplaceholder.typicode.com/")  
            .defaultCookie("cookieKey", "cookieValue")  
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
            return webClient;  }

We are using _**WebClient.Builder**_ which allows us to configure our client’s default values (which will be common for all requests- like base url or default header) in a clean way. for list of more configuration options please visit the [documentation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/reactive/function/client/WebClient.Builder.html). 

## 5. Make Requests and Map Responses to Objects

Let start with an example API endpoint under  _https://jsonplaceholder.typicode.com/todos/1  address returning such a JSON object:

    {
      "userId": 1,
      "id": 1,
      "title": "delectus aut autem",
      "completed": false
    }
Also, let’s prepare a  ToDo domain class corresponding to our JSON,

    @Slf4j  
    @Data  
    public class ToDo {  
     private int id;  
     private String userId;  
     private String title;  
     private boolean completed;  
    }
Note : I am using lombok to create getters/setters
### 5.1. Fetch the Data Using Retrieve() Function

Let’s start simply by building a function performing  **HTTP GET**  request and retrieving the response body:

    @Service  
    public class ToDoService {  
      
        @Autowired  
      WebClient webClient;  
      
     public Mono<ToDo> getToDoById(String id) {  
            return webClient.get()  
                    .uri("/todos/{id}", id)  
                    .retrieve()  
                    .bodyToMono(ToDo.class);  
      }  
      
        public Flux<ToDo> getToDos() {  
            return webClient.get()  
                    .uri("/todos")  
                    .retrieve()  
                    .bodyToFlux(ToDo.class);  
      }  
    }


### 6. Controller

    @RestController  
    public class ToDoController {  
        @Autowired  
      ToDoService toDoService;  
      
      @GetMapping("/todos/{id}")  
        public Mono<ToDo> getTodo(@PathVariable String id) {  
            return toDoService.getToDoById(id);  
      }  
      
        @GetMapping("/todos")  
        public Flux<ToDo> getTodos() {  
            return toDoService.getToDos();  
      }  
    }

### 7. Endpoint
http://localhost:8086/todos/1
http://localhost:8086/todos

## 8. Conclusion

In this post, we’ve learned how easy it is to use  **WebClient with Spring MVC and Kotlin**. We’ve covered the most common use cases, like fetching the responses using mono and flux.
