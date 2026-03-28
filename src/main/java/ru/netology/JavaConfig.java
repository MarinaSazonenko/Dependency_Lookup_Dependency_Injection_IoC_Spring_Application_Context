package ru.netology;

@Configuration
public class AppConfig {
    @Bean
    public MyRepository myRepository() {
        return new MyRepository();
    }

    @Bean
    public MyService myService(MyRepository repository) {
        return new MyService(repository);
    }
}
