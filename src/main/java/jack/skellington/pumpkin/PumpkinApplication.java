package jack.skellington.pumpkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2 // http://localhost:8080/swagger-ui.html
public class PumpkinApplication {

	public static void main(String[] args) {
		SpringApplication.run(PumpkinApplication.class, args);
	}

//	@Bean
//	public FilterRegistrationBean<ExampleFilter> loggingFilter() {
//		FilterRegistrationBean<ExampleFilter> registrationBean = new FilterRegistrationBean<>();
//
//		registrationBean.setFilter(new ExampleFilter());
//		registrationBean.addUrlPatterns("/users/*");
//
//		return registrationBean;
//	}

}
