package com.prairiegrade.ugly;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.IntStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prairiegrade.ugly.entity.Account;
import com.prairiegrade.ugly.entity.AccountType;
import com.prairiegrade.ugly.entity.Person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates a bunch of test data and dumps the corresponding JSON to the data
 * dir.
 */
public class CreateJsonData {
    private static final Logger logger = LoggerFactory.getLogger(CreateJsonData.class);

    public static void main(String[] args) throws Exception {
        Path dir = Paths.get("").resolve("data").resolve("input");
        final Random rand = new Random();

        if (Files.exists(dir)) {
            logger.error("Manually delete {} if you want fresh data", dir);
            return;
        } else {
            logger.info("Creating {}", dir);
            Files.createDirectories(dir);
        }
        logger.info("Creating new data in {}", dir);

        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        Gson gson = builder.create();

        // randomly populate some Account and Person objects
        IntStream.range(0, 10).forEach(i -> {
            logger.debug("Creating person {}", i);
            Person person = new Person();
            person.setFirstName("FirstName" + String.format("%04d", i));
            person.setLastName("LastName" + String.format("%04d", i));

            Account account = new Account();
            account.setId((long)i);
            account.setBalance(rand.nextLong());
            account.setAccountType(AccountType.CHECKING);

            person.getAccounts().add(account);
            account.setOwner(person);

            // save Account objects as JSON
            String filename = String.format("Account.%s.json", account.getId());
            Path jsonFile = dir.resolve(filename);
            try {
                try (Writer writer = Files.newBufferedWriter(jsonFile)) {
                    logger.info("Writing {} to {}", account, jsonFile);
                    gson.toJson(account, writer);
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to create " + jsonFile, e);
            }
        });
    }
}
