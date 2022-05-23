package tuum.tuum_test;

import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tuum.tuum_test.persistence.model.Account;

@SpringBootApplication
@MapperScan("tuum.tuum_test.persistence.mapper")
@MappedTypes(Account.class)
public class TuumTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TuumTestApplication.class, args);
    }
}
