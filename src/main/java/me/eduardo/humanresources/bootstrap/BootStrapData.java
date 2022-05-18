package me.eduardo.humanresources.bootstrap;

import me.eduardo.humanresources.domain.ContractType;
import me.eduardo.humanresources.repository.ContractTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;

@Component
public class BootStrapData implements CommandLineRunner {

    private final static Logger LOG = LoggerFactory.getLogger(BootStrapData.class);

    private final ContractTypeRepository CONTRACT_TYPE_REPOSITORY;

    public BootStrapData(ContractTypeRepository contractTypeRepository) {
        this.CONTRACT_TYPE_REPOSITORY = contractTypeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        CONTRACT_TYPE_REPOSITORY.save(new ContractType(true, Date.from(Instant.now()), "Permanent", null));
        CONTRACT_TYPE_REPOSITORY.save(new ContractType(true, Date.from(Instant.now()), "Fixed-Term", null));
        CONTRACT_TYPE_REPOSITORY.save(new ContractType(true, Date.from(Instant.now()), "External", null));


        LOG.info("Populating with default ContractTypes: " + CONTRACT_TYPE_REPOSITORY.findAll());
    }

}
