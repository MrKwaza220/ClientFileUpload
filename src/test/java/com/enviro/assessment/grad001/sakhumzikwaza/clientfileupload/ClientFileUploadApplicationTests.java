package com.enviro.assessment.grad001.sakhumzikwaza.clientfileupload;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClientFileUploadApplicationTests {
    @Autowired
    private  DocumentRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @Rollback(false)
    public void testInsertDocument() throws IOException {
        File file = new File("");
        Document document = new Document();
        document.setName(file.getName());

        byte[] bytes = Files.readAllBytes(file.toPath());
        document.setContent(bytes);
        long fileSize = bytes.length;
        document.setSize(fileSize);
        document.setUploadTime(new Date());

        Document saveDoc = repo.save(document);

        Document existDoc = entityManager.find(Document.class, saveDoc.getId());

        assertThat(existDoc.getSize()).isEqualTo(fileSize);

    }

}
