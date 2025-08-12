package com.gspann.meetingroombooking;
import org.springframework.web.filter.CorsFilter;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@SpringBootApplication
public class MeetingroomBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetingroomBookingApplication.class, args);



	}
//	@Bean
//	public CorsFilter corsFilter() {
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.addAllowedOriginPattern("*"); // or specifically "http://localhost:3000"
//		config.addAllowedHeader("*");
//		config.addAllowedMethod("*");
//
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", config);
//
//		return new CorsFilter(source);
//	}

}
