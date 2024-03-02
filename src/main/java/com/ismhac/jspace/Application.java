package com.ismhac.jspace;

//import com.ismhac.jspace.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class Application implements CommandLineRunner {

//    private final AdminService adminService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        boolean initialized = adminService.initRootAdmin();
//        if (initialized) log.info("Successfully created super admin account");
//        else log.info("The supper admin account is ready");
    }
}
